package com.gilbersoncampos.cardgame.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
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
fun GameScreen(viewmodel: GameScreenViewModel = hiltViewModel()) {
    val uiState by viewmodel.uiState.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        PlayerComponent(
            playerName = "Dealer",
            hand = uiState.dealerHand,
            points = uiState.dealerPoints
        )
        DeckComponent(
            eventMessage = uiState.eventsMessage,
            idGame = uiState.deckId,
            gameIsFinished = uiState.gameIsFinished,
            onClickDraw = viewmodel::drawPlayerCard,
            onStopGame = viewmodel::stopGame,
            onRestartGame = viewmodel::restartGame

        )
        PlayerComponent(
            playerName = "Player",
            hand = uiState.playerHand,
            points = uiState.playerPoints
        )
    }
}

@Composable
fun DeckComponent(
    eventMessage: String,
    idGame: String,
    gameIsFinished: Boolean,
    onClickDraw: () -> Unit,
    onStopGame: () -> Unit,
    onRestartGame: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(eventMessage)
        Image(painter = painterResource(R.drawable.back), contentDescription = null)
        if (gameIsFinished) {
            Button(onClick = onRestartGame) {
                Text("Reiniciar")
            }
        } else {
            Row {
                Button(onClick = onClickDraw) {
                    Text("Pedir")
                }
                Button(onClick = onStopGame) {
                    Text("Parar")
                }
            }
        }

        Text("deck: $idGame")
    }
}

@Composable
@OptIn(ExperimentalCoilApi::class)
private fun PlayerComponent(
    playerName: String,
    hand: List<Card>,
    points: Int
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
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("$playerName : $points points")
        CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
            LazyRow {
                items(hand) { card ->

                    AsyncImage(
                        model = card.image,
                        contentDescription = null,
                        placeholder = painterResource(R.drawable.back)
                    )

                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

        }
    }
}

@Composable
@Preview(showBackground = true)
fun GameScreenPreview() {
    GameScreen()
}

