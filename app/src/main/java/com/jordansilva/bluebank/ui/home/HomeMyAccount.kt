package com.jordansilva.bluebank.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jordansilva.bluebank.R
import com.jordansilva.bluebank.helper.MySemantics
import com.jordansilva.bluebank.helper.PreviewHelper
import com.jordansilva.bluebank.ui.components.SectionHeader
import com.jordansilva.bluebank.ui.theme.BlueBankTheme
import com.jordansilva.bluebank.ui.theme.Grey300
import java.text.NumberFormat
import java.util.*

@Composable
fun HomeMyAccount(balance: Double, isPrivateMode: Boolean) {
    val buttonColors = ButtonDefaults.textButtonColors()
    val contentColor by buttonColors.contentColor(true)
    val backgroundColor by buttonColors.backgroundColor(true)
    val fontSize = MaterialTheme.typography.button.fontSize.value

    Button(
        onClick = { },
        modifier = Modifier
            .fillMaxWidth()
            .semantics {
                this[MySemantics.Foreground] = contentColor.toArgb()
                this[MySemantics.Background] = backgroundColor.toArgb()
                this[MySemantics.TextSize] = fontSize
            },
        shape = RectangleShape,
        elevation = null,
        colors = buttonColors,
    ) {
        CompositionLocalProvider(LocalContentColor provides Color.Black) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)) {

                SectionHeader(stringResource(R.string.account))
                Balance(balance, isPrivateMode)
            }
        }
    }
}

@Composable
fun Balance(balance: Double, isPrivateMode: Boolean) {
    val fontSize = 24.sp
    if (isPrivateMode) {
        Text(
            text = stringResource(R.string.balance_hidden),
            color = Grey300,
            fontSize = fontSize,
            modifier = Modifier
                .background(Grey300)
                .fillMaxWidth(.8f)
        )
    } else {
        Text(text = FormatCurrency.format(balance), fontSize = fontSize)
    }
}

private val FormatCurrency = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

@Preview(showBackground = true)
@Composable
private fun HomeAccountPreview() {
    BlueBankTheme {
        HomeMyAccount(PreviewHelper.AccountBalance, false)
    }
}