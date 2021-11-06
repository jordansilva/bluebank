package com.jordansilva.bluebank.ui.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jordansilva.bluebank.R
import com.jordansilva.bluebank.helper.MySemantics
import com.jordansilva.bluebank.helper.PreviewHelper
import com.jordansilva.bluebank.helper.enableFocus
import com.jordansilva.bluebank.helper.focusBorder
import com.jordansilva.bluebank.ui.components.HorizontalScroll
import com.jordansilva.bluebank.ui.components.showToast
import com.jordansilva.bluebank.ui.theme.BlueBankTheme
import com.jordansilva.bluebank.ui.theme.Grey200

@Composable
fun HomePaymentList(options: List<PaymentOption>) {
    val context = LocalContext.current

    HorizontalScroll(modifier = Modifier
        .testTag("payment_list")
        .padding(16.dp)) {
        options.forEach {
            val label = stringResource(it.label)
            PaymentItem(iconId = it.iconId, label = label) { showToast(context, label) }
        }
    }
}

@Composable
private fun PaymentItem(@DrawableRes iconId: Int, label: String, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val hasFocus = rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .width(76.dp)
            .enableFocus(hasFocus)
            .clickable(
                onClick = onClick,
                enabled = true,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = null
            )
            .semantics(true) {},
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CompositionLocalProvider(LocalContentColor provides Color.Black) {
            val backgroundColor = Grey200
            val contentColor = LocalContentColor.current
            Button(
                onClick = onClick,
                modifier = Modifier
                    .background(backgroundColor, CircleShape)
                    .size(72.dp)
                    .focusBorder(hasFocus.value, CircleShape)
                    .clearAndSetSemantics {
                        this[MySemantics.Foreground] = contentColor.toArgb()
                        this[MySemantics.Background] = backgroundColor.toArgb()
                    },
                interactionSource = interactionSource,
                elevation = null,
                shape = CircleShape,
                colors = ButtonDefaults.textButtonColors(),
                contentPadding = PaddingValues()
            ) {
                Icon(painter = painterResource(id = iconId), tint = contentColor, contentDescription = label)
            }

            Text(label, style = MaterialTheme.typography.button.copy(letterSpacing = 0.sp), textAlign = TextAlign.Center)
        }
    }
}

enum class PaymentOption(@StringRes val label: Int, @DrawableRes val iconId: Int) {
    PIX(R.string.area_pix, R.drawable.ic_outline_contactless_24),
    Transfer(R.string.payment_transfer, R.drawable.ic_outline_payments_24),
    Deposit(R.string.payment_deposit, R.drawable.ic_baseline_money_24),
    Loan(R.string.payment_loan, R.drawable.ic_baseline_money_off_24),
    RechargePhone(R.string.payment_recharge_phone, R.drawable.ic_baseline_smartphone_24),
    Request(R.string.payment_request, R.drawable.ic_baseline_money_24),
}

@Preview(showBackground = true)
@Composable
private fun HomePaymentListPreview() {
    BlueBankTheme {
        HomePaymentList(PreviewHelper.AccountPaymentOptions)
    }
}