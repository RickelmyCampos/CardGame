package com.gilbersoncampos.cardgame.data.repository

import com.gilbersoncampos.cardgame.data.mapper.toModel
import com.gilbersoncampos.cardgame.data.model.Card
import com.gilbersoncampos.cardgame.data.remote.dataSource.DecksCardDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BlackJackGameRepositoryImpl @Inject constructor(private val dataSource: DecksCardDataSource) :
    BlackJackGameRepository {
    override fun getShuffledDeck(deckId: String, cardsCount: Int): Flow<String> {
        return flow {
            val result = dataSource.getDeckCardsShuffled(deckId, cardsCount)
            emit(result.deck_id)
        }
    }

    override fun drawCard(deckId: String): Flow<Card> {
        return flow {
            emit(dataSource.drawCard(deckId).toModel())
        }
    }
}