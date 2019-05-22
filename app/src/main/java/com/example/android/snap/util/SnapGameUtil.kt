package com.example.android.snap.util

import com.example.android.snap.game.Card
import com.example.android.snap.player.CardGamePlayer
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

const val MAX_DELAY_TIME_MILLIS = 1000L
const val NUMBER_OF_PLAYERS = 2
const val NUMBER_OF_CARDS_IN_A_SUIT = 13

class SnapGameUtil private constructor() : CardGameUtil {

    private var wasShutDown = false
    private var scheduledThreadPoolExecutor = ScheduledThreadPoolExecutor(NUMBER_OF_PLAYERS)
    private val tasksWhichMayRun = mutableListOf<ScheduledFuture<*>>()

    //region API
    companion object {
        private val instance = SnapGameUtil()

        fun instance(): SnapGameUtil {
            replaceThreadPoolExecutorIfNecessary()
            return instance
        }

        private fun replaceThreadPoolExecutorIfNecessary() {
            if (instance.wasShutDown) instance.scheduledThreadPoolExecutor =
                ScheduledThreadPoolExecutor(NUMBER_OF_PLAYERS)
            instance.wasShutDown = false
        }
    }

    override fun createDeck(): MutableList<Card> {
        val deck = mutableListOf<Card>()

        for (s in Card.SUIT.values()) {
            addCards(deck, s)
        }
        deck.shuffle()
        return deck
    }

    override fun cardValuesMatch(card1: Card, card2: Card): Boolean {
        return card1.value == card2.value
    }

    override fun stopRunningTasks() {
        for (task in tasksWhichMayRun) {
            if (!task.isDone) {
                task.cancel(true)
            }
            scheduledThreadPoolExecutor.shutdownNow()
            wasShutDown = true
        }
    }

    override fun performTaskAfterDelay(task: Runnable) {
        val delay = calculateDelayMillis()
        tasksWhichMayRun.add(
            scheduledThreadPoolExecutor.schedule(
                task, delay, TimeUnit.MILLISECONDS
            )
        )
    }

    override fun reportMessage(message: String) {
        println(message)
    }

    override fun getWinText(winner: CardGamePlayer): String {
        return ("SNAP! ${winner.name} won.")
    }

    //region Private
    private fun addCards(deck: MutableList<Card>, suit: Card.SUIT) {
        for (i in 1..NUMBER_OF_CARDS_IN_A_SUIT) {
            deck.add(Card(suit, i))
        }
    }

    private fun calculateDelayMillis(): Long {
        return (Math.random() * MAX_DELAY_TIME_MILLIS).toLong()
    }
    //endregion
}
