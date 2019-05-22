package com.example.android.snap

import com.example.android.snap.game.SnapGameController
import com.example.android.snap.player.SnapGamePlayer

fun main() {
    val snapGame = SnapGameController()
    snapGame.registerPlayer1(SnapGamePlayer("George"))
    snapGame.registerPlayer2(SnapGamePlayer("Desmond"))
    snapGame.startGame()
}