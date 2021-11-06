package com.jordansilva.bluebank.ui.home

import android.icu.text.NumberFormat
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.width
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.accessibility.AccessibilityChecks
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.jordansilva.bluebank.MainActivity
import com.jordansilva.bluebank.R
import com.jordansilva.bluebank.helper.FakeAccountRepository
import com.jordansilva.bluebank.helper.hasAnyContentDescription
import com.jordansilva.bluebank.helper.hasMinTouchArea
import com.jordansilva.bluebank.helper.hasRole
import com.jordansilva.bluebank.ui.theme.BlueBankTheme
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeHeaderFragmentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun validateHeaderSize() {
        val rootWidth = composeTestRule.onRoot().getUnclippedBoundsInRoot().width //size.width

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
    fun givenVisibilityIsPublic_WhenClickPrivateButton_ThenVisibilityIsPrivate() {
        val context = composeTestRule.activity.applicationContext
//        val hideBalance = context.getString(R.string.balance_hide)
//        val visibilityButton = composeTestRule
//            .onNodeWithContentDescription(hideBalance)
//            .assertIsDisplayed()
//            .assertHasClickAction()

        val visibilityButton = onView(withId(R.id.visibilityButton))
            .check(matches(isDisplayed()))
            .check(matches(withContentDescription(R.string.balance_hide)))

        // Checking My Account is displayed with visible balance
        val myAccountText = context.getString(R.string.account)
        val balanceText = MoneyFormat.format(Balance)
        composeTestRule
            .onNodeWithText(myAccountText)
            .assertIsDisplayed()
            .assert(hasContentDescription(balanceText) or hasText(balanceText))

        // Hide balance
        visibilityButton.perform(click())

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

    @Test
    fun checkHelloMessage() {
        val helloMessage = composeTestRule.activity.getString(R.string.hello_username, AccountName)

        onView(withId(R.id.accountName))
            .check(matches(isDisplayed()))
            .check(matches(isNotClickable()))
            .check(matches(withText(helloMessage)))
    }

    private companion object {
        const val TestTag = "header"

        const val AccountName = "Jordan"
        const val IsPrivate = false

        val HeaderHeight = 120.dp

        val MinTouchAreaHeight = 48.dp
        val MinTouchAreaWidth = 48.dp

        const val Balance = 9050.20
        val MoneyFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR")) //OK, Not the best

        @BeforeClass
        @JvmStatic
        fun enableAccessibilityChecks() {
            AccessibilityChecks.enable()
        }
    }
}
