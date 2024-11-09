package com.gilbersoncampos.cardgame.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class DeckDto(
    val success: Boolean,
    val deck_id: String,
    val remaining: Int,
    val shuffled: Boolean
)
