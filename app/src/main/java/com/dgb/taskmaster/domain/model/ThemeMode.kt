package com.dgb.taskmaster.domain.model

/**
 * Preferencia de apariencia persistida en DataStore.
 */
enum class ThemeMode(val storageKey: String) {
    LIGHT("light"),
    DARK("dark"),
    SYSTEM("system"),
    ;

    companion object {
        fun fromStorage(value: String?): ThemeMode =
            entries.find { it.storageKey == value } ?: SYSTEM
    }
}
