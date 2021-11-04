package com.jordansilva.bluebank.helper

import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.ViewConfiguration
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.jordansilva.bluebank.ui.theme.Amber700

fun Modifier.enableFocus(hasFocus: MutableState<Boolean>): Modifier {
    return this
        .focusRequester(FocusRequester())
        .onFocusEvent { hasFocus.value = it.isFocused }
        .focusable()
}

fun Modifier.enableFocus(shape: Shape? = null): Modifier = composed {
    val hasFocus = rememberSaveable { mutableStateOf(false) }
    this
        .enableFocus(hasFocus)
        .focusBorder(hasFocus.value, shape)
}

fun Modifier.focusBorder(hasFocus: Boolean, shape: Shape? = null): Modifier {
    val borderModifier = if (hasFocus) {
        Modifier
            .border(3.dp, Amber700, shape ?: RoundedCornerShape(16.dp))
    } else {
        Modifier
    }

    return this.then(borderModifier)
}


fun wrongViewConfiguration(configuration: ViewConfiguration): ViewConfiguration {
    return object : ViewConfiguration {
        override val doubleTapMinTimeMillis: Long
            get() = configuration.doubleTapMinTimeMillis
        override val doubleTapTimeoutMillis: Long
            get() = configuration.doubleTapTimeoutMillis
        override val longPressTimeoutMillis: Long
            get() = configuration.longPressTimeoutMillis
        override val touchSlop: Float
            get() = configuration.touchSlop
        override val minimumTouchTargetSize: DpSize
            get() = DpSize(12.dp, 12.dp)

    }
}