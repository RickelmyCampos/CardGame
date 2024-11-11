package com.gilbersoncampos.cardgame.ui.screen

import com.gilbersoncampos.cardgame.data.model.Card
import com.gilbersoncampos.cardgame.data.repository.BlackJackGameRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GameScreenViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private val repository = mockk<BlackJackGameRepository>(relaxed = true)
    private lateinit var viewModel: GameScreenViewModel
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = GameScreenViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should initialize game with deck and starting hands`() = runTest {
        val deckId = "deck123"
        coEvery { repository.getShuffledDeck(any(), any()) } returns flow { emit(deckId) }
        coEvery { repository.drawCard(deckId) } returns flow {
            emit(
                Card(
                    "H10",
                    10,
                    "HEARTS",
                    image = ""
                )
            )
        }

        viewModel.createDeck(deckId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(deckId, viewModel.uiState.value.deckId)
        assertEquals(2, viewModel.uiState.value.playerHand.size)
        assertEquals(2, viewModel.uiState.value.dealerHand.size)
        assertEquals("Jogo iniciado", viewModel.uiState.value.eventsMessage)
    }

    @Test
    fun `should draw card for player and update hand and points`() = runTest {
        val deckId = "deck123"
        val card = Card("C2", 2, "CLUBS", image = "")
        coEvery { repository.getShuffledDeck(any(), any()) } returns flow { emit(deckId) }
        coEvery { repository.drawCard(deckId) } returns flow { emit(card) }
        viewModel.createDeck(deckId)
        viewModel.drawPlayerCard()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(3, viewModel.uiState.value.playerHand.size)
        assertEquals(6, viewModel.uiState.value.playerPoints)
    }

    @Test
    fun `should stop game and let dealer draw until points are 17 or more`() = runTest {
        val deckId = "deck123"
        val cardLow = Card("D4", 4, "DIAMONDS", "")
        val cardHigh = Card("S10", 10, "SPADES", "")
        coEvery { repository.getShuffledDeck(any(), any()) } returns flow { emit(deckId) }

        coEvery { repository.drawCard(deckId) } returnsMany listOf(
            flow { emit(cardLow) },
            flow { emit(cardHigh) }
        )
        viewModel.createDeck(deckId)

        viewModel.stopGame()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.uiState.value.dealerPoints >= 17)
    }
}