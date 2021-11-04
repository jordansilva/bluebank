package com.jordansilva.bluebank.ui.components

import android.content.Context
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.jordansilva.bluebank.helper.SpeechHelper
import com.jordansilva.bluebank.helper.enableFocus
import com.jordansilva.bluebank.ui.theme.Grey200

@Composable
fun MyButton(
    label: String,
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int? = null,
    textStyle: TextStyle = MaterialTheme.typography.button,
) {
    val context = LocalContext.current
    Button(
        onClick = { showToast(context, label) },
        modifier = modifier
            .enableFocus()
            .defaultMinSize(minWidth = 48.dp, minHeight = 60.dp),
        elevation = null,
        colors = ButtonDefaults.buttonColors(backgroundColor = Grey200, contentColor = Color.Black)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            iconId?.let {
                Icon(painter = painterResource(id = iconId), contentDescription = null)
                Spacer(modifier = Modifier.width(16.dp))
            }
            Text(label, style = textStyle)
        }
    }
}

@Composable
fun MyIconButton(
    @DrawableRes iconId: Int,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    onClick: (() -> Unit)? = null,
) {
    val context = LocalContext.current
    IconButton(
        onClick = {
            showToast(context, contentDescription)
            onClick?.invoke()
        },
        modifier = modifier.enableFocus(CircleShape)
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = contentDescription
        )
    }
}

fun showToast(context: Context, text: String?) {
    if (!text.isNullOrBlank()) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()

    }

//    SpeechHelper.get(context).speak(text ?: "Sem descrição")
}