package com.gilbersoncampos.cardgame.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilbersoncampos.cardgame.data.mapper.toModel
import com.gilbersoncampos.cardgame.data.model.Card
import com.gilbersoncampos.cardgame.data.remote.dataSource.DecksCardDataSource
import com.gilbersoncampos.cardgame.data.repository.BlackJackGameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor(private val repository: BlackJackGameRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(
        GameUiState(
            playerHand = listOf(),
            dealerHand = listOf(),
            playerPoints = 0,
            dealerPoints = 0,
            eventsMessage = "Iniciando",
            deckId = "",
            gameIsFinished = false
        )
    )
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    init {
        createDeck("new")
    }

    fun restartGame() {
        _uiState.update { old ->
            old.copy(
                playerHand = listOf(),
                dealerHand = listOf(),
                playerPoints = 0,
                dealerPoints = 0,
                gameIsFinished = false
            )
        }
        createDeck(_uiState.value.deckId)
    }

    private fun createDeck(deckId: String) {
        viewModelScope.launch {
            updateEventMessage("Embaralhando Cartas ...")

            repository.getShuffledDeck(deckId = deckId, 6).collect { id ->
                _uiState.update { it.copy(deckId = id) }
                updateEventMessage("Jogo iniciado")
                repeat(2) {
                    drawCardDealer()
                    drawPlayerCard()
                }
            }
        }
    }

    private fun updateEventMessage(message: String) {
        _uiState.update { it.copy(eventsMessage = message) }
    }

    fun drawPlayerCard() {
        viewModelScope.launch {

            repository.drawCard(_uiState.value.deckId).collect { card ->
                val updatedPlayerHand = _uiState.value.playerHand + card
                _uiState.update {
                    it.copy(
                        playerHand = updatedPlayerHand,
                        playerPoints = calculateHandValue(updatedPlayerHand)
                    )
                }
            }

        }
    }

    private suspend fun drawCardDealer() {
        repository.drawCard(_uiState.value.deckId).collect { card ->
            val updatedDealerHand = _uiState.value.dealerHand + card
            _uiState.update {
                it.copy(
                    dealerHand = updatedDealerHand,
                    dealerPoints = calculateHandValue(updatedDealerHand)
                )
            }
        }
    }

    private fun calculateHandValue(hand: List<Card>): Int {
        var total = hand.sumOf { it.value }
        var aces = hand.count { it.value == 1 }

        while (total > 21 && aces > 0) {
            total -= 10
            aces--
        }
        return total
    }

    private fun isBust(point: Int) = point > 21

    //fun hasBlackjack(point: Int) = point == 21
    fun stopGame() {
        viewModelScope.launch {
            while (_uiState.value.dealerPoints < 17) {
                drawCardDealer()
            }
            updateEventMessage(checkWinner())
            _uiState.update { old ->
                old.copy(gameIsFinished = true)
            }
        }
    }

    private fun checkWinner(): String {
        return when {
            isBust(_uiState.value.playerPoints) -> "Dealer Wins!"
            isBust(_uiState.value.dealerPoints) -> "Player Wins!"
            _uiState.value.playerPoints > _uiState.value.dealerPoints -> "Player Wins!"
            _uiState.value.dealerPoints > _uiState.value.playerPoints -> "Dealer Wins!"
            else -> "It's a Tie!"
        }
    }
}

data class GameUiState(
    val playerHand: List<Card>,
    val dealerHand: List<Card>,
    val playerPoints: Int,
    val dealerPoints: Int,
    val eventsMessage: String,
    val deckId: String,
    val gameIsFinished: Boolean
)