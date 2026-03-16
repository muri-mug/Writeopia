package io.writeopia.analytics

/**
 * Catalog of all Writeopia analytics events.
 *
 * Naming convention: {domain}_{action_past_tense} in snake_case.
 * Full documentation: docs/ANALYTICS.md
 */
object WriteopiaEvents {

    // region User
    const val USER_SIGNED_IN = "user_signed_in"
    const val USER_SIGNED_UP = "user_signed_up"
    const val USER_SIGNED_OUT = "user_signed_out"
    // endregion

    // region Document
    const val DOCUMENT_CREATED = "document_created"
    const val DOCUMENT_OPENED = "document_opened"
    const val DOCUMENT_DELETED = "document_deleted"
    const val DOCUMENT_SHARED = "document_shared"
    // endregion

    // region Editor
    const val EDITOR_OPENED = "editor_opened"
    const val EDITOR_BLOCK_ADDED = "editor_block_added"
    const val EDITOR_BLOCK_DELETED = "editor_block_deleted"
    const val EDITOR_IMAGE_ADDED = "editor_image_added"
    // endregion

    // region AI
    const val AI_QUESTION_ASKED = "ai_question_asked"
    const val AI_SUGGESTION_ACCEPTED = "ai_suggestion_accepted"
    // endregion

    // region Search
    const val SEARCH_PERFORMED = "search_performed"
    // endregion

    // region Screen
    const val SCREEN_VIEWED = "screen_viewed"
    // endregion

    // region Onboarding
    const val ONBOARDING_COMPLETED = "onboarding_completed"
    // endregion

    // region Settings
    const val SETTINGS_THEME_CHANGED = "settings_theme_changed"
    // endregion
}
