package com.jordansilva.bluebank.ui.home

import androidx.compose.runtime.*
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.size
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.jordansilva.bluebank.R
import com.jordansilva.bluebank.helper.*
import com.jordansilva.bluebank.ui.theme.BlueBankTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeHeaderTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        var isPrivateMode by mutableStateOf(IsPrivate)
        composeTestRule.setContent {
            BlueBankTheme {
                HomeHeader(
                    accountName = AccountName,
                    isPrivateMode = isPrivateMode,
                    onPrivateMode = { isPrivateMode = !isPrivateMode }
                )
            }
        }
    }

    @Test
    fun validateHeaderScreenshot() {
        ScreenshotComparator.assertScreenshot("header", composeTestRule.onRoot())
    }

    @Test
    fun validateHeaderSize() {
        val rootWidth = composeTestRule.onRoot().getUnclippedBoundsInRoot().size.width

        composeTestRule
            .onNodeWithTag(TestTag)
            .assertHeightIsEqualTo(HeaderHeight)
            .assertWidthIsEqualTo(rootWidth)
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

    // Functionality

    @Test
    fun givenVisibilityIsPublic_WhenClickPrivateButton_ThenVisibilityIsPrivate() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val hideBalance = context.getString(R.string.balance_hide)
        composeTestRule
            .onNodeWithContentDescription(hideBalance)
            .assertIsDisplayed()
            .assert(hasRole(Role.Button))
            .assertHasClickAction()
            .performClick()

        val showBalance = context.getString(R.string.balance_show)
        composeTestRule
            .onNodeWithContentDescription(showBalance)
            .assertIsDisplayed()
            .assert(hasRole(Role.Button))
            .assertHasClickAction()
    }

    @Test
    fun clickProfileClick() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val text = context.getString(R.string.profile_settings, AccountName)

        composeTestRule
            .onNodeWithContentDescription(text)
            .assertIsDisplayed()
            .assert(hasRole(Role.Button))
            .assertHasClickAction()
    }

    @Test
    fun checkHelloMessage() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val helloMessage = appContext.getString(R.string.hello_username, AccountName)
        composeTestRule
            .onNodeWithText(helloMessage)
            .assertIsDisplayed()
            .assertHasNoClickAction()
    }

    private companion object {
        const val TestTag = "header"

        const val AccountName = "Jordan"
        const val IsPrivate = false

        val HeaderHeight = 120.dp

        val MinTouchAreaHeight = 48.dp
        val MinTouchAreaWidth = 48.dp
    }
}
