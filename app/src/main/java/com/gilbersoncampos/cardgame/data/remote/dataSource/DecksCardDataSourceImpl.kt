package com.gilbersoncampos.cardgame.data.remote.dataSource

import com.gilbersoncampos.cardgame.data.remote.dto.CardDto
import com.gilbersoncampos.cardgame.data.remote.dto.DeckDto
import com.gilbersoncampos.cardgame.data.remote.retrofit.DeckCardsService
import javax.inject.Inject

class DecksCardDataSourceImpl @Inject constructor(private val service: DeckCardsService) :
    DecksCardDataSource {
    override suspend fun getDeckCardsShuffled(deckId:String,deckCount: Int): DeckDto {
        return try {
            val call = service.getDeckCardsShuffled(deckId=deckId,deckCount = deckCount)
            val response = call.body()
            if (!call.isSuccessful || response == null) throw Exception("Não foi possivel gerar o Deck")
            response
        } catch (ex: Exception) {
            throw Exception("Erro :${ex.message}")
        }
    }

    override suspend fun drawCard(deckId: String): CardDto {
        return try {
            val call = service.drawACard(deckId = deckId)
            val response = call.body()
            if (!call.isSuccessful || response == null) throw Exception("Não foi possível comprar a carta")
            response.cards[0]
        } catch (ex: Exception) {
            throw Exception("Erro :${ex.message}")
        }
    }
}