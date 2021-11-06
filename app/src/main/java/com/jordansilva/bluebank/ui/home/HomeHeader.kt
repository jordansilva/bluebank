package com.jordansilva.bluebank.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jordansilva.bluebank.R
import com.jordansilva.bluebank.helper.MySemantics
import com.jordansilva.bluebank.helper.PreviewHelper
import com.jordansilva.bluebank.ui.components.MyIconButton
import com.jordansilva.bluebank.ui.theme.BlueBankTheme

@Composable
fun HomeHeader(
    accountName: String,
    isPrivateMode: Boolean,
    onPrivateMode: () -> Unit,
) {
    val backgroundColor = MaterialTheme.colors.primaryVariant
    Column(
        modifier = Modifier
            .testTag("header")
            .background(MaterialTheme.colors.primaryVariant)
            .height(120.dp)
            .padding(12.dp)
            .semantics {
                set(MySemantics.Background, backgroundColor.toArgb())
            },
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        CompositionLocalProvider(LocalContentColor provides Color.White) {
            Row(modifier = Modifier.fillMaxWidth()) {
                ProfileButton()
                Spacer(modifier = Modifier.weight(1f, true))
                VisibilityButton(isPrivateMode, onPrivateMode)
                HelpButton()
                ContactButton()
            }
            Text(stringResource(R.string.hello_username, accountName), fontWeight = FontWeight.Medium)
        }
    }
}

//                CompositionLocalProvider(LocalViewConfiguration provides wrongViewConfiguration(LocalViewConfiguration.current)) { }
@Composable
fun ProfileButton() = MyIconButton(
    iconId = R.drawable.ic_person_outline,
    contentDescription = stringResource(R.string.profile_settings),
    modifier = Modifier.background(Color(0x33FFFFFF), CircleShape),
)

@Composable
private fun VisibilityButton(isPrivateMode: Boolean, onPrivateMode: () -> Unit) {
    val iconId = if (isPrivateMode) R.drawable.ic_visibility_off else R.drawable.ic_visibility_on
    val contentDescriptionId = if (isPrivateMode) R.string.balance_show else R.string.balance_hide

    MyIconButton(
        iconId = iconId,
        contentDescription = stringResource(contentDescriptionId),
        onClick = onPrivateMode)
}

@Composable
private fun HelpButton() = MyIconButton(R.drawable.ic_help_outline, contentDescription = stringResource(R.string.help))

@Composable
private fun ContactButton() = MyIconButton(R.drawable.ic_mail_outline, contentDescription = stringResource(R.string.contact))

@Preview
@Composable
fun HeaderPreview() {
    BlueBankTheme {
        var state by remember { mutableStateOf(PreviewHelper.HomeState) }
        HomeHeader(
            accountName = PreviewHelper.AccountName,
            isPrivateMode = false,
            onPrivateMode = { state = state.copy(isPrivateMode = !state.isPrivateMode) }
        )
    }
}