package com.example.android.snap.util

import com.example.android.snap.game.Card
import com.example.android.snap.player.CardGamePlayer

interface CardGameUtil {
    fun createDeck(): MutableList<Card>
    fun cardValuesMatch(card1: Card, card2: Card): Boolean
    fun stopRunningTasks()
    fun performTaskAfterDelay(task: Runnable)
    fun reportMessage(message: String)
    fun getWinText(winner: CardGamePlayer): String
}