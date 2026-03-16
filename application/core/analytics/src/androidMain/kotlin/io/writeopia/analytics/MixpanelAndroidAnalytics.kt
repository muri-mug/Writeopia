package io.writeopia.analytics

import android.content.Context
import com.mixpanel.android.mpmetrics.MixpanelAPI
import io.writeopia.analytics.MixpanelConfig
import org.json.JSONObject

class MixpanelAndroidAnalytics(context: Context) : AnalyticsManager {

    private val mixpanel = MixpanelAPI.getInstance(
        context.applicationContext,
        MixpanelConfig.TOKEN,
        false
    )

    override fun track(event: String, properties: Map<String, Any>) {
        mixpanel.track(event, JSONObject(properties))
    }

    override fun identify(userId: String) {
        mixpanel.identify(userId)
        mixpanel.people.identify(userId)
    }

    override fun reset() {
        mixpanel.reset()
    }
}
