package com.example.android.snap.game

import com.example.android.snap.player.CardGamePlayer

interface CardGameController {
    fun startGame()
    fun callWin(cardGamePlayer: CardGamePlayer)
    fun readyToTakeTurn()
    fun takeTurn(cardGamePlayer: CardGamePlayer)
    fun getCardPair(): Pair<Card?, Card?>
    fun registerPlayer1(cardGamePlayer: CardGamePlayer)
    fun registerPlayer2(cardGamePlayer: CardGamePlayer)
}