package io.writeopia.analytics

object NoOpAnalyticsManager : AnalyticsManager {
    override fun track(event: String, properties: Map<String, Any>) = Unit
    override fun identify(userId: String) = Unit
    override fun reset() = Unit
}
