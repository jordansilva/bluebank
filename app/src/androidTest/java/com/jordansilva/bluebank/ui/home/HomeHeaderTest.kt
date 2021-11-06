package com.jordansilva.bluebank.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.width
import androidx.test.platform.app.InstrumentationRegistry
import com.jordansilva.bluebank.R
import com.jordansilva.bluebank.helper.*
import com.jordansilva.bluebank.ui.theme.BlueBankTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
    fun checkSnapshot() {
        ScreenshotComparator.assertScreenshot("header", composeTestRule.onRoot())
    }

    @Test
    fun validateHeaderSize() {
        val rootWidth = composeTestRule.onRoot().getUnclippedBoundsInRoot().width

        composeTestRule
            .onNodeWithTag(TestTag)
            .assertHeightIsEqualTo(HeaderHeight)
            .assertWidthIsEqualTo(rootWidth)
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

//    @Test
//    fun clickProfileClick() {
//        val context = InstrumentationRegistry.getInstrumentation().targetContext
//        val text = context.getString(R.string.profile_settings, AccountName)
//
//        composeTestRule
//            .onNodeWithContentDescription(text)
//            .assertIsDisplayed()
//            .assert(hasRole(Role.Button))
//            .assertHasClickAction()
//    }

    @Test
    fun checkHelloMessage() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        //appContext.getString(R.string.hello_username, AccountName)
        val helloMessage = appContext.getString(R.string.hello_username, AccountName)
        composeTestRule
            .onNodeWithText(helloMessage) // Pick a node with text
            .assertIsDisplayed() // Assert it is displayed!
            .assertHasNoClickAction() // And it has no click
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
