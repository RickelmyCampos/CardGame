package com.gilbersoncampos.cardgame.data.mapper

import com.gilbersoncampos.cardgame.data.model.Card
import com.gilbersoncampos.cardgame.data.remote.dto.CardDto

fun CardDto.toModel()= Card(
    code = this.code,
    image = this.image,
    suit = this.suit,
    value = this.value.translateValue()
)
fun String.translateValue():Int{
    return when(this){
        "JACK"-> 10
        "QUEEN"->10
        "ACE"->1
        "KING"->10
        else->this.toInt()
    }
}