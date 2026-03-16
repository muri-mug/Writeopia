package io.writeopia.analytics

/**
 * Catalog of all Writeopia analytics property keys.
 *
 * Naming convention: snake_case, {context}_{attribute}.
 * Full documentation: docs/ANALYTICS.md
 */
object WriteopiaProperties {

    // region Global
    /** Platform the event was fired from: "android", "ios", "desktop", "web" */
    const val PLATFORM = "platform"

    /** Semantic app version at the time of the event, e.g. "0.50.0" */
    const val APP_VERSION = "app_version"
    // endregion

    // region User
    /** Authentication method used: "email", "google", "offline" */
    const val AUTH_METHOD = "auth_method"
    // endregion

    // region Document
    /** Type of document involved: "note", "folder" */
    const val DOCUMENT_TYPE = "document_type"

    /** Format used when sharing/exporting: "pdf", "markdown", "link" */
    const val SHARE_FORMAT = "share_format"
    // endregion

    // region Editor
    /**
     * Type of content block added or removed.
     * Values: "text", "h1", "h2", "h3", "check_item", "unordered_list",
     *         "ordered_list", "image", "video", "code", "table", "divider"
     */
    const val BLOCK_TYPE = "block_type"
    // endregion

    // region AI
    /** AI provider used: "writeopia", "ollama" */
    const val AI_PROVIDER = "ai_provider"
    // endregion

    // region Screen
    /**
     * Name of the screen viewed.
     * Values: "home", "editor", "login", "register", "settings",
     *         "search", "account", "onboarding"
     */
    const val SCREEN_NAME = "screen_name"
    // endregion

    // region Settings
    /** Theme selected by the user: "light", "dark", "system" */
    const val THEME = "theme"
    // endregion
}
