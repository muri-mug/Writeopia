package io.writeopia.analytics

import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class MixpanelHttpAnalytics(
    private val token: String,
    private val httpClient: HttpClient
) : AnalyticsManager {

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private var distinctId: String = "anonymous"

    override fun track(event: String, properties: Map<String, Any>) {
        scope.launch {
            runCatching {
                val propsJson = buildJsonObject {
                    put("token", token)
                    put("distinct_id", distinctId)
                    properties.forEach { (key, value) ->
                        when (value) {
                            is String -> put(key, value)
                            is Int -> put(key, value)
                            is Long -> put(key, value)
                            is Double -> put(key, value)
                            is Float -> put(key, value.toDouble())
                            is Boolean -> put(key, value)
                            else -> put(key, value.toString())
                        }
                    }
                }
                val payload = buildJsonArray {
                    add(
                        buildJsonObject {
                            put("event", event)
                            put("properties", propsJson)
                        }
                    )
                }
                httpClient.post("https://api.mixpanel.com/track") {
                    headers {
                        append(HttpHeaders.ContentType, "application/json")
                    }
                    setBody(payload.toString())
                }
            }
        }
    }

    override fun identify(userId: String) {
        distinctId = userId
    }

    override fun reset() {
        distinctId = "anonymous"
    }
}
