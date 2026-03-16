package io.writeopia.analytics.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import io.writeopia.analytics.AnalyticsManager
import io.writeopia.analytics.MixpanelConfig
import io.writeopia.analytics.MixpanelHttpAnalytics

actual class AnalyticsInjection {
    private val analyticsManager: AnalyticsManager by lazy {
        MixpanelHttpAnalytics(
            token = MixpanelConfig.TOKEN,
            httpClient = HttpClient(Js),
            defaultProperties = mapOf(
                "\$os" to "Web",
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
