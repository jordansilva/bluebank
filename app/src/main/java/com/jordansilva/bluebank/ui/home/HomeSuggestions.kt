package com.jordansilva.bluebank.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jordansilva.bluebank.R
import com.jordansilva.bluebank.ui.components.HorizontalScroll
import com.jordansilva.bluebank.ui.components.MyButton

@Composable
fun HomeSuggestions() {
    Column(modifier = Modifier.testTag("suggestions")) {
        MyButton(
            label = stringResource(R.string.my_cards),
            iconId = R.drawable.ic_baseline_credit_card_24,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalScroll(modifier = Modifier.padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            SuggestionItem("Você tem até R$ 12.500,00\ndisponíveis para empréstimo.")
            SuggestionItem("Vamos reinventar o jeito de investir.\nCadastre-se e não fique de fora.")
            SuggestionItem("Salve seus amigos da burocracia.\nFaça um convite para o Nubank.")
        }
    }
}


@Composable
private fun SuggestionItem(label: String) = MyButton(
    label = label,
    modifier = Modifier.size(width = 280.dp, height = 90.dp),
    textStyle = MaterialTheme.typography.button.copy(fontWeight = FontWeight.Medium)
)
