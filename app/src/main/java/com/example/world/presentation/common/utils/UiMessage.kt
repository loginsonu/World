package com.example.world.presentation.common.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

/**
 * Ui message
 *
 * This class helps to use string resource id with or without context
 * helpful in viewmodel class , because in viewmodel we don't have ui context
 */
sealed class UiMessage {
    data class DynamicString(val text: String): UiMessage()
    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ): UiMessage()

    @Composable
    fun asString(): String {
        return when(this) {
            is DynamicString -> text
            is StringResource -> stringResource(resId, *args)
        }
    }

    fun asString(context: Context): String {
        return when(this) {
            is DynamicString -> text
            is StringResource -> context.getString(resId, *args)
        }
    }
}