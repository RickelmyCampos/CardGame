package com.gilbersoncampos.cardgame.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.Canvas
import coil3.Image
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import com.gilbersoncampos.cardgame.R
import com.gilbersoncampos.cardgame.data.model.Card

@OptIn(ExperimentalCoilApi::class)
@Composable
fun GameScreen(viewmodel: GameScreenViewModel = viewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val card = Card(
            code = "7C",
            value = 7,
            suit = "CLUBS",
            image = "https://deckofcardsapi.com/static/img/7C.png"
        )
        PlayerComponent(playerName = "Computer", card = card)
        DeckComponent()
        PlayerComponent(playerName = "Player", card = card)
    }
}

@Composable
fun DeckComponent() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(R.drawable.back), contentDescription = null)
        Row {
            Button(onClick = {}) {
                Text("Pedir")
            }
            Button(onClick = {}) {
                Text("Parar")
            }
        }
    }
}

@Composable
@OptIn(ExperimentalCoilApi::class)
private fun PlayerComponent(
    playerName: String,
    card: Card
) {
    val previewHandler = AsyncImagePreviewHandler {
        object : Image {
            override val height: Int
                get() = 100
            override val shareable: Boolean
                get() = true
            override val size: Long
                get() = 50
            override val width: Int
                get() = 50

            override fun draw(canvas: Canvas) {
            }
        }
    }
    Column (modifier = Modifier.fillMaxWidth()){
        Text(playerName)
        Row (modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center){
            CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
                repeat(4) {
                    AsyncImage(
                        model = card.image,
                        contentDescription = null,
                        placeholder = painterResource(R.drawable.back)
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun GameScreenPreview() {
    GameScreen()
}

