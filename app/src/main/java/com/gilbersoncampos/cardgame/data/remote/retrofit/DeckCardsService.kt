package com.gilbersoncampos.cardgame.data.remote.retrofit

import com.gilbersoncampos.cardgame.data.remote.dto.DeckDto
import com.gilbersoncampos.cardgame.data.remote.dto.DrawCardResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.Path
import retrofit2.http.Query

interface DeckCardsService {

    @GET("/api/deck/{deckId}/shuffle/")
    suspend fun getDeckCardsShuffled(@Path("deckId")deckId: String,@Query("deck_count") deckCount: Int): Response<DeckDto>

    @GET("/api/deck/{deckId}/draw/")
    suspend fun drawACard(
        @Path("deckId") deckId: String
    ): Response<DrawCardResponseDto>
}