package com.thomas200593.mini_retail_app.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thomas200593.mini_retail_app.core.ui.common.Themes

object Card {
    @Composable
    fun LoadingPanel(
        modifier: Modifier = Modifier
    ){
        Card(
            modifier = modifier,
            onClick = {}
        ) {
            Text(text = "Test Card")
        }
    }
}

@Composable
@Preview
fun CardPreview(){
    Themes.ApplicationTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    }
}