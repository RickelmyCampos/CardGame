package com.gilbersoncampos.cardgame.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class DrawCardResponseDto(
    val success: Boolean,
    val deck_id: String,
    val cards: List<CardDto>,
    val remaining: Int
)
