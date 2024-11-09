package com.gilbersoncampos.cardgame.ui.screen

import androidx.lifecycle.ViewModel
import com.gilbersoncampos.cardgame.data.model.Card
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor():ViewModel() {
    fun drawCards(){

    }
    fun stopGame(){

    }

}
data class GameUiState(val playerHand:List<Card>,val dealerHand:List<Card>,val points:Int)