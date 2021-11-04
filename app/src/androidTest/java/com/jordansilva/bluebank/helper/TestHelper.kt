package com.jordansilva.bluebank.helper

import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


val MinTouchAreaHeight = 48.dp
val MinTouchAreaWidth = 48.dp

private val NATIVE_DPAD_DOWN = NativeKeyEvent(NativeKeyEvent.ACTION_DOWN, NativeKeyEvent.KEYCODE_DPAD_DOWN)
val NavigateDownKeyEvent = KeyEvent(NATIVE_DPAD_DOWN)

fun hasMinTouchArea(width: Dp, height: Dp): SemanticsMatcher =
    SemanticsMatcher("has touch area less than $width x $height") { node ->
        with(node.root!!.density) {
            val touchBounds = node.touchBoundsInRoot
            return@with ((touchBounds.width.toDp() >= width && touchBounds.height.toDp() >= height)
                    || (node.size.width.toDp() >= width && node.size.height.toDp() >= height))
        }
    }

fun hasAnyContentDescription(): SemanticsMatcher =
    SemanticsMatcher("${SemanticsProperties.ContentDescription.name} exists") {
        it.config.contains(SemanticsProperties.ContentDescription) || it.config.contains(SemanticsProperties.Text)
    }

fun hasRole(role: Role): SemanticsMatcher = SemanticsMatcher.expectValue(SemanticsProperties.Role, role)

fun hasSize(width: Dp, height: Dp): SemanticsMatcher =
    SemanticsMatcher("Size doesn't match $width x $height") { node ->
        with(node.root!!.density) {
            return@with node.size.width.toDp() == width && node.size.height.toDp() == height
        }
    }
