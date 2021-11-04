package com.jordansilva.bluebank.ui.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.filters.LargeTest
import com.jordansilva.bluebank.MainActivityTest
import com.jordansilva.bluebank.helper.*
import com.jordansilva.bluebank.ui.theme.BlueBankTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@LargeTest
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule(ComponentActivity::class.java)

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

    @Test
    fun validateHomeScreenFocusable() {
        composeTestRule
            .onAllNodes(SemanticsMatcher.keyIsDefined(SemanticsActions.OnClick))
            .onFirst()
            .assertIsNotFocused()

        // Press down to focus first element
        composeTestRule.onRoot().performKeyPress(keyEvent = NavigateDownKeyEvent)

        composeTestRule
            .onAllNodes(SemanticsMatcher.keyIsDefined(SemanticsActions.OnClick))
            .onFirst()
            .assertIsFocused()

        ScreenshotComparator.assertScreenshot("home_screen_focusable", composeTestRule.onRoot())
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
//
//    // Functionality
    @Test
    fun givenVisibilityIsPublic_WhenClickPrivateButton_ThenVisibilityIsPrivate() {
//        val context = InstrumentationRegistry.getInstrumentation().targetContext
//        val hideBalance = context.getString(R.string.balance_hide)
//        composeTestRule
//            .onNodeWithContentDescription(hideBalance)
//            .assertIsDisplayed()
//            .assertHasRole(Role.Button)
//            .assertHasClickAction()
//            .performClick()
//
//        val showBalance = context.getString(R.string.balance_show)
//        composeTestRule
//            .onNodeWithContentDescription(showBalance)
//            .assertIsDisplayed()
//            .assertHasRole(Role.Button)
//            .assertHasClickAction()
    }
//
//    @Test
//    fun clickProfileClick() {
//        val context = InstrumentationRegistry.getInstrumentation().targetContext
//        val text = context.getString(R.string.profile_settings, AccountName)
//
//        composeTestRule
//            .onNodeWithContentDescription(text)
//            .assertIsDisplayed()
//            .assertHasRole(Role.Button)
//            .assertHasClickAction()
//    }
//
//    @Test
//    fun checkHelloMessage() {
//        val context = InstrumentationRegistry.getInstrumentation().targetContext
//        val helloMessage = context.getString(R.string.hello_username, AccountName)
//        composeTestRule
//            .onNodeWithText(helloMessage)
//            .assertIsDisplayed()
//            .assertHasNoClickAction()
//    }

    private companion object {


    }
}