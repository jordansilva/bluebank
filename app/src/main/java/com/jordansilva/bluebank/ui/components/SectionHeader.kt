package com.jordansilva.bluebank.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jordansilva.bluebank.R
import com.jordansilva.bluebank.ui.theme.SectionHeaderStyle

@Composable
fun SectionHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = SectionHeaderStyle)
        Icon(painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_ios_24), contentDescription = null, modifier = Modifier.size(14.dp))
    }
}