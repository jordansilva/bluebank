package com.jordansilva.bluebank.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalScroll(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(12.dp),
    content: @Composable () -> Unit,
) {
    Row(modifier = Modifier
        .horizontalScroll(rememberScrollState())
        .then(modifier),
        horizontalArrangement = horizontalArrangement) {
        content()
    }
}

@Composable
fun VerticalScroll(content: @Composable ColumnScope.() -> Unit) =
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        content()
    }
