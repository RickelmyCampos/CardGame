package com.gilbersoncampos.cardgame.data.remote.dataSource

import com.gilbersoncampos.cardgame.data.model.Card
import com.gilbersoncampos.cardgame.data.remote.dto.CardDto
import com.gilbersoncampos.cardgame.data.remote.dto.DeckDto

interface DecksCardDataSource {
    suspend fun getDeckCardsShuffled(deckId:String,deckCount:Int):DeckDto
    suspend fun drawCard(deckId:String):CardDto
}