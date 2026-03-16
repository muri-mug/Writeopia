package io.writeopia.analytics

interface AnalyticsManager {
    fun track(event: String, properties: Map<String, Any> = emptyMap())
    fun identify(userId: String)
    fun reset()
}
