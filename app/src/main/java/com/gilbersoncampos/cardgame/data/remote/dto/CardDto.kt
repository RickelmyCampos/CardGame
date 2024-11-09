package com.gilbersoncampos.cardgame.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CardDto(val code:String, val image:String, val value:String,val suit:String)
