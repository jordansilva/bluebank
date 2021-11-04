package com.jordansilva.bluebank.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jordansilva.bluebank.helper.PreviewHelper
import com.jordansilva.bluebank.ui.components.SectionHeader
import com.jordansilva.bluebank.ui.theme.BlueBankTheme
import com.jordansilva.bluebank.ui.theme.Grey700

@Composable
fun HomeAccountOptions(options: List<AccountServices>) {
    Column(modifier = Modifier
        .testTag("account_options")
        .padding(vertical = 16.dp)) {
        Divider()
        options.forEach {
            Item(it.iconId, it.title, it.description)
            Divider()
        }
    }
}

@Composable
private fun Item(@DrawableRes iconId: Int, title: String, description: String) {
    Button(
        onClick = { },
        modifier = Modifier.fillMaxWidth().height(160.dp),
        shape = RectangleShape,
        elevation = null,
        colors = ButtonDefaults.textButtonColors(),
    ) {
        CompositionLocalProvider(LocalContentColor provides Color.Black) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
            ) {
                Icon(painter = painterResource(id = iconId), contentDescription = null)
                SectionHeader(title)
                Text(text = description, style = MaterialTheme.typography.button.copy(color = Grey700, fontWeight = FontWeight.Normal))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeAccountOptionsPreview() {
    BlueBankTheme {
        HomeAccountOptions(PreviewHelper.AccountServices)
    }
}