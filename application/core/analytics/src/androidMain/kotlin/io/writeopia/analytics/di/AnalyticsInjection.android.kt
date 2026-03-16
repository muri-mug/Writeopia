package io.writeopia.analytics.di

import android.content.Context
import io.writeopia.analytics.AnalyticsManager
import io.writeopia.analytics.MixpanelAndroidAnalytics
import io.writeopia.analytics.NoOpAnalyticsManager

actual class AnalyticsInjection private constructor(
    private val analyticsManager: AnalyticsManager
) {
    actual fun provideAnalyticsManager(): AnalyticsManager = analyticsManager

    actual companion object {
        private var instance: AnalyticsInjection? = null

        fun initialize(context: Context) {
            if (instance == null) {
                instance = AnalyticsInjection(MixpanelAndroidAnalytics(context))
            }
        }

        actual fun singleton(): AnalyticsInjection =
            instance ?: AnalyticsInjection(NoOpAnalyticsManager)
    }
}
