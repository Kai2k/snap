package com.example.android.snap.player

import com.example.android.snap.game.CardGameController

interface CardGamePlayer {
    val name: String

    fun notifyToTakeTurn()
    fun readyToPlay(): Boolean
    fun notifyDeckHasChanged()
    fun registerController(controller: CardGameController)
}