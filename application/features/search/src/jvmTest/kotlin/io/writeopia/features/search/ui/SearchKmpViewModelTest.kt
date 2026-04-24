package io.writeopia.features.search.ui

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.writeopia.analytics.AnalyticsManager
import io.writeopia.analytics.WriteopiaEvents
import io.writeopia.features.search.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SearchKmpViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var searchRepository: SearchRepository
    private lateinit var analyticsManager: AnalyticsManager

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        searchRepository = mockk(relaxed = true) {
            every { searchNotesAndFoldersLocally(any()) } returns flowOf(emptyList())
            every { searchNotesAndFoldersRemotely(any()) } returns flowOf(emptyList())
        }
        analyticsManager = mockk(relaxed = true)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ── State ────────────────────────────────────────────────────────────────

    @Test
    fun `initial search state is empty`() = runTest {
        val vm = SearchKmpViewModel(searchRepository, analyticsManager)
        assertEquals("", vm.searchState.value)
    }

    @Test
    fun `typing a query updates search state`() = runTest {
        val vm = SearchKmpViewModel(searchRepository, analyticsManager)

        vm.onSearchType("hello")

        assertEquals("hello", vm.searchState.value)
    }

    @Test
    fun `replacing query with another updates search state`() = runTest {
        val vm = SearchKmpViewModel(searchRepository, analyticsManager)

        vm.onSearchType("first")
        vm.onSearchType("second")

        assertEquals("second", vm.searchState.value)
    }

    @Test
    fun `clearing query resets search state to empty`() = runTest {
        val vm = SearchKmpViewModel(searchRepository, analyticsManager)

        vm.onSearchType("something")
        vm.onSearchType("")

        assertEquals("", vm.searchState.value)
    }

    // ── Analytics ────────────────────────────────────────────────────────────

    @Test
    fun `first non-empty query tracks SEARCH_PERFORMED event`() = runTest {
        val vm = SearchKmpViewModel(searchRepository, analyticsManager)

        vm.onSearchType("k")

        verify(exactly = 1) { analyticsManager.track(WriteopiaEvents.SEARCH_PERFORMED) }
    }

    @Test
    fun `empty query does not track any analytics event`() = runTest {
        val vm = SearchKmpViewModel(searchRepository, analyticsManager)

        vm.onSearchType("")

        verify(exactly = 0) { analyticsManager.track(any()) }
    }

    @Test
    fun `subsequent non-empty queries do not re-track analytics`() = runTest {
        val vm = SearchKmpViewModel(searchRepository, analyticsManager)

        vm.onSearchType("k")
        vm.onSearchType("ko")
        vm.onSearchType("kot")

        verify(exactly = 1) { analyticsManager.track(WriteopiaEvents.SEARCH_PERFORMED) }
    }

    @Test
    fun `clearing and retyping re-tracks analytics event`() = runTest {
        val vm = SearchKmpViewModel(searchRepository, analyticsManager)

        vm.onSearchType("first")
        vm.onSearchType("")       // clear → state is empty again
        vm.onSearchType("second") // first char of second search → should track again

        verify(exactly = 2) { analyticsManager.track(WriteopiaEvents.SEARCH_PERFORMED) }
    }

    // ── Query results ────────────────────────────────────────────────────────

    @Test
    fun `query results are initially empty`() = runTest {
        val vm = SearchKmpViewModel(searchRepository, analyticsManager)
        advanceUntilIdle()

        assertTrue(vm.queryResults.value.isEmpty())
    }

    @Test
    fun `query results combine local and remote results`() = runTest {
        val localItem = io.writeopia.features.search.repository.SearchItem.DocumentInfo(
            id = "local-1",
            label = "Local Note",
        )
        val remoteItem = io.writeopia.features.search.repository.SearchItem.DocumentInfo(
            id = "remote-1",
            label = "Remote Note",
        )
        every { searchRepository.searchNotesAndFoldersLocally(any()) } returns flowOf(listOf(localItem))
        every { searchRepository.searchNotesAndFoldersRemotely(any()) } returns flowOf(listOf(remoteItem))

        val vm = SearchKmpViewModel(searchRepository, analyticsManager)

        val results = mutableListOf<List<io.writeopia.features.search.repository.SearchItem>>()
        backgroundScope.launch(testDispatcher) {
            vm.queryResults.toList(results)
        }

        vm.onSearchType("note")
        advanceUntilIdle()

        val combined = results.flatten()
        assertTrue(combined.any { it == localItem }, "Local result missing from combined results")
        assertTrue(combined.any { it == remoteItem }, "Remote result missing from combined results")
    }

    // ── init ─────────────────────────────────────────────────────────────────

    @Test
    fun `init does not throw and state remains valid`() = runTest {
        val vm = SearchKmpViewModel(searchRepository, analyticsManager)

        vm.init()

        assertEquals("", vm.searchState.value)
    }
}
