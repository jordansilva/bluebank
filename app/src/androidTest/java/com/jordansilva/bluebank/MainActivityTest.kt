package com.jordansilva.bluebank

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.espresso.accessibility.AccessibilityChecks
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jordansilva.bluebank.helper.ScreenshotComparator
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

//    @get:Rule
//    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val screenshotComparator = ScreenshotComparator

    @Before
    fun setup() {
        // Enable accessibility checks with Espresso
        AccessibilityChecks.enable().setRunChecksFromRootView(true)
    }

    @Test
    fun screenshot_account() {
        val accountString = composeTestRule.activity.getString(R.string.account)
        val node = composeTestRule.onNodeWithText(accountString)
        screenshotComparator.assertScreenshot("account", node)
    }

    @Test
    fun screenshot_paymentList() {
        val node = composeTestRule.onNodeWithTag("payment_list")
        screenshotComparator.assertScreenshot("payment_list", node)
    }

    @Test
    fun screenshot_suggestions() {
        val node = composeTestRule.onNodeWithTag("suggestions")
        screenshotComparator.assertScreenshot("suggestions", node)
    }

}