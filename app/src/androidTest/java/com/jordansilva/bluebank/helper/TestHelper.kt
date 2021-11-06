package com.jordansilva.bluebank.helper

import androidx.compose.ui.semantics.*
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.android.apps.common.testing.accessibility.framework.utils.contrast.ContrastUtils
import com.jordansilva.bluebank.helper.AccessibilityHelper.ContrastRatioLargeText
import com.jordansilva.bluebank.helper.AccessibilityHelper.ContrastRatioNormalText
import com.jordansilva.bluebank.helper.AccessibilityHelper.LargeTextMinSize

object AccessibilityHelper {
    /**
     * The minimum text size considered large for contrast checking purposes, as taken from the WCAG
     * standards at http://www.w3.org/TR/UNDERSTANDING-WCAG20/visual-audio-contrast-contrast.html
     */
    const val LargeTextMinSize = 18

    /**
     * The minimum text size for bold text to be considered large for contrast checking purposes, as
     * taken from the WCAG standards at http://www.w3.org/TR/UNDERSTANDING-WCAG20/visual-audio-contrast-contrast.html
     */
    const val LargeBoldTextMinSize = 14

    /**
     * The minimum contrast ratio for normal text and images should be at least 4.5:1, except for the following:
     * - Large Text: Large-scale text and images of large-scale text have a contrast ratio of at least 3:1.
     * - Incidental: Text or images of text that are part of an inactive user interface component, that are pure decoration,
     *   that are not visible to anyone, or that are part of a picture that contains significant other visual content,
     *   have no contrast requirement.
     * - Logotypes: Text that is part of a logo or brand name has no contrast requirement.
     *
     * WCAG standards at https://www.w3.org/TR/WCAG21/#contrast-minimum
     */
    const val ContrastRatioNormalText = 4.5
    const val ContrastRatioLargeText = 3.0

    val MinTouchAreaHeight = 48.dp
    val MinTouchAreaWidth = 48.dp
}

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

fun hasRole(role: Role): SemanticsMatcher {
    return SemanticsMatcher.expectValue(SemanticsProperties.Role, role)
}

fun hasSize(width: Dp, height: Dp): SemanticsMatcher =
    SemanticsMatcher("Size doesn't match $width x $height") { node ->
        with(node.root!!.density) {
            return@with node.size.width.toDp() == width && node.size.height.toDp() == height
        }
    }

fun checkContrastRatio(): SemanticsMatcher {
    @Suppress("UNCHECKED_CAST")
    fun <T> SemanticsNode.getProperty(key: SemanticsPropertyKey<T>): T? {
        return config.getOrElseNullable(key) { parent?.config?.getOrNull(key) }
    }

    return SemanticsMatcher("Contrast ratio is lower than " +
            "$ContrastRatioNormalText (Regular Text) or $ContrastRatioLargeText (Large Text)") {

        // Get the custom TextSize semantic otherwise the contrast ratio
        // will be calculated using the Normal Text contrast ratio (4.5:1)
        // WCAG standards at https://www.w3.org/WAI/WCAG21/Understanding/contrast-minimum.html
        val textSize = it.config.getOrElse(MySemantics.TextSize) { LargeTextMinSize / 2f }

        // Get the semantic property from this node or its parent
        // it.config returns SemanticConfiguration with semantics
        val foreground = it.getProperty(MySemantics.Foreground) ?: return@SemanticsMatcher false
        val background = it.getProperty(MySemantics.Background) ?: return@SemanticsMatcher false

        // Calculate the contrast ratio between these colors
        val contrastRatio = ContrastUtils.calculateContrastRatio(foreground, background)

        // For Large Texts (>= 18pt) the Contrast Ratio must be 3.0 and for Regular Texts 4.5
        val requiredContrast = if (textSize >= LargeTextMinSize) ContrastRatioLargeText else ContrastRatioNormalText
        contrastRatio >= requiredContrast
    }
}
