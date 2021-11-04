package com.jordansilva.bluebank.ui.home

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.jordansilva.bluebank.helper.*
import com.jordansilva.bluebank.ui.theme.BlueBankTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@LargeTest
class PaymentListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            BlueBankTheme {
                HomePaymentList(PaymentOptions)
            }
        }
    }

    @Test
    fun validateScreenshot() {
        ScreenshotComparator.assertScreenshot("home_payment_list", composeTestRule.onRoot())
    }

    // It's better to have a separate test class for components
    @Test
    fun validateItemScreenshot() {
        val firstButton = composeTestRule.onAllNodes(hasRole(Role.Button)).onFirst()
        ScreenshotComparator.assertScreenshot("home_payment_item", firstButton)
    }

    // Accessibility Checks
    @Test
    fun checkAccessibility_contentDescription() {
        composeTestRule
            .onAllNodes(SemanticsMatcher.keyIsDefined(SemanticsActions.OnClick))
            .assertAll(hasAnyContentDescription())
    }

    @Test
    fun checkAccessibility_touchTargetArea() {
        composeTestRule
            .onAllNodes(SemanticsMatcher.keyIsDefined(SemanticsActions.OnClick))
            .assertAll(hasMinTouchArea(MinTouchAreaWidth, MinTouchAreaHeight))
    }

    @Test
    fun checkAccessibility_contrastColor() {
        composeTestRule
            .onAllNodes(SemanticsMatcher.keyIsDefined(SemanticsActions.OnClick))
            .assertAll(hasMinTouchArea(MinTouchAreaWidth, MinTouchAreaHeight))
    }

    @Test
    fun checkIfAllOptionsAvailable() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        val images = composeTestRule.onAllNodes(hasRole(Role.Image), true)
        PaymentOptions.forEachIndexed { index, service ->
            images[index] // Icon (Image)
                .assertWidthIsEqualTo(IconSize)
                .assertHeightIsEqualTo(IconSize)
                .onParent() // Inner Button with Circle
                .assertWidthIsEqualTo(IconButtonWidth)
                .assertHeightIsEqualTo(IconButtonHeight)
                .onSibling() // Text
                .assertTextEquals(context.getString(service.label))
                .onParent() // Button
                .assert(hasRole(Role.Button))
                .assertHasClickAction()
                .assertHeightIsAtLeast(IconButtonHeight)
                .assertWidthIsEqualTo(ButtonWidth)
        }
    }

    private companion object {
        val PaymentOptions = FakeAccountRepository.PaymentOptions
        val IconButtonHeight = 72.dp
        val IconButtonWidth = 72.dp

        val ButtonWidth = 76.dp
        val IconSize = 24.dp
    }
}