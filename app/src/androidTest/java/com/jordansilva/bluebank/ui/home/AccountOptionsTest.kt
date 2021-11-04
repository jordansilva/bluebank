package com.jordansilva.bluebank.ui.home

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.size
import androidx.test.filters.LargeTest
import com.jordansilva.bluebank.helper.*
import com.jordansilva.bluebank.ui.theme.BlueBankTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@LargeTest
class AccountOptionsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            BlueBankTheme {
                HomeAccountOptions(AccountServices)
            }
        }
    }

    @Test
    fun validateScreenshot() {
        ScreenshotComparator.assertScreenshot("account_services", composeTestRule.onRoot())
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
        val rootWidth = composeTestRule.onRoot().getUnclippedBoundsInRoot().size.width

        val buttons = composeTestRule.onAllNodes(hasRole(Role.Button))
        AccountServices.forEachIndexed { index, service ->
            buttons[index]
                .assertHasClickAction()
                .assertIsDisplayed()
                .assertTextContains(service.title)
                .assertTextContains(service.description)
                .assert(hasSize(rootWidth, ComponentHeight))
        }
    }

    private companion object {
        val AccountServices = FakeAccountRepository.AccountServices
        val ComponentHeight = 160.dp
    }
}
