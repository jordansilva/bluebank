package com.jordansilva.bluebank.ui.home

import android.icu.text.NumberFormat
import androidx.activity.ComponentActivity
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.jordansilva.bluebank.MainActivity
import com.jordansilva.bluebank.R
import com.jordansilva.bluebank.helper.*
import com.jordansilva.bluebank.helper.AccessibilityHelper.MinTouchAreaHeight
import com.jordansilva.bluebank.helper.AccessibilityHelper.MinTouchAreaWidth
import com.jordansilva.bluebank.ui.theme.BlueBankTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

@LargeTest
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        composeTestRule.setContent {
            BlueBankTheme {
                HomeScreen(viewModel = HomeViewModel(FakeAccountRepository()))
            }
        }
    }

    @Test
    fun validateHomeScreenScreenshot() {
        ScreenshotComparator.assertScreenshot("home_screen", composeTestRule.onRoot())
    }

    // Accessibility Checks
    @Test
    fun accessibilityContentDescription() {
        composeTestRule
            .onAllNodes(hasClickAction() or hasRole(Role.Image))
            .assertAll(hasAnyContentDescription())
    }

    @Test
    fun accessibilityTouchTargetArea() {
        composeTestRule
            .onAllNodes(hasClickAction())
            .assertAll(hasMinTouchArea(MinTouchAreaWidth, MinTouchAreaHeight))
    }

    @Test
    fun accessibilityContrastColor() {
        composeTestRule
            .onAllNodes(hasClickAction())
            .assertAll(checkContrastRatio())
    }

    @Test
    fun validateHomeScreenFocusable() {
        composeTestRule
            .onAllNodes(hasClickAction())
            .onFirst()
            .assertIsNotFocused()

        // Press down to focus first element
        val dpadDown = NativeKeyEvent(NativeKeyEvent.ACTION_DOWN, NativeKeyEvent.KEYCODE_DPAD_RIGHT)
        composeTestRule.onRoot().performKeyPress(keyEvent = KeyEvent(dpadDown))

        composeTestRule
            .onAllNodes(hasClickAction())
            .onFirst()
            .assertIsFocused()

        ScreenshotComparator.assertScreenshot("home_screen_focusable", composeTestRule.onRoot())
    }

    // Functionality
    @Test
    fun givenVisibilityIsPublic_WhenClickPrivateButton_ThenVisibilityIsPrivate() {
        val context = composeTestRule.activity.applicationContext
        val hideBalance = context.getString(R.string.balance_hide)
        val visibilityButton = composeTestRule
            .onNodeWithContentDescription(hideBalance)
            .assertIsDisplayed()
            .assertHasClickAction()

        // Checking My Account is displayed with visible balance
        val myAccountText = context.getString(R.string.account)
        val balanceText = MoneyFormat.format(Balance)
        composeTestRule
            .onNodeWithText(myAccountText)
            .assertIsDisplayed()
            .assert(hasContentDescription(balanceText) or hasText(balanceText))

        // Hide balance
        visibilityButton.performClick()

        // Check no balance is displayed
        composeTestRule
            .onNodeWithText(balanceText)
            .assertDoesNotExist()

        // Check My Account has not visible balance text
        val balanceHiddenText = context.getString(R.string.balance_hidden)
        composeTestRule
            .onNodeWithText(myAccountText)
            .assertIsDisplayed()
            .assert(hasContentDescription(balanceHiddenText) or hasText(balanceHiddenText))
    }

    private companion object {
        const val Balance = FakeAccountRepository.Balance
        val MoneyFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR")) //OK, Not the best

    }
}