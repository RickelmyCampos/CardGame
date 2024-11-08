package com.gilbersoncampos.cardgame.data.model

import android.hardware.biometrics.BiometricManager.Strings

data class Card(val code: String, val value: Int, val suit: String, val image: String)