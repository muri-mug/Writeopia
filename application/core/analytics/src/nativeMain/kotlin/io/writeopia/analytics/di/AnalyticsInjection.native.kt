package io.writeopia.analytics.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.writeopia.analytics.AnalyticsManager
import io.writeopia.analytics.MixpanelConfig
import io.writeopia.analytics.MixpanelHttpAnalytics
import platform.Foundation.NSProcessInfo

actual class AnalyticsInjection {
    private val analyticsManager: AnalyticsManager by lazy {
        MixpanelHttpAnalytics(
            token = MixpanelConfig.TOKEN,
            httpClient = HttpClient(Darwin),
            defaultProperties = mapOf(
                "\$os" to "iOS",
                "\$os_version" to NSProcessInfo.processInfo.operatingSystemVersionString,
            )
        )
    }

    actual fun provideAnalyticsManager(): AnalyticsManager = analyticsManager

    actual companion object {
        private var instance: AnalyticsInjection? = null

        actual fun singleton(): AnalyticsInjection =
            instance ?: AnalyticsInjection().also { instance = it }
    }
}
