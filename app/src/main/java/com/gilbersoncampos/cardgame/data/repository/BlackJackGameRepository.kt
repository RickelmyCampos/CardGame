package com.gilbersoncampos.cardgame.data.repository

import com.gilbersoncampos.cardgame.data.model.Card
import kotlinx.coroutines.flow.Flow

interface BlackJackGameRepository {
    fun getShuffledDeck(deckId: String, cardsCount: Int): Flow<String>
    fun drawCard(deckId: String): Flow<Card>
}