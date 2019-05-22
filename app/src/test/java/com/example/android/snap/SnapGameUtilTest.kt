package com.example.android.snap

import com.example.android.snap.game.Card
import com.example.android.snap.player.SnapGamePlayer
import com.example.android.snap.util.MAX_DELAY_TIME_MILLIS
import com.example.android.snap.util.SnapGameUtil
import org.junit.Assert.*
import org.junit.Test

class SnapGameUtilTest {
    private val gameUtil = SnapGameUtil.instance()

    //region Card
    @Test
    fun createDeckReturns52Cards() {
        val cardsInDeck = gameUtil.createDeck().size

        assertEquals(52, cardsInDeck)
    }

    @Test
    fun createDeckContainsShuffledCards() {
        val deck1 = gameUtil.createDeck()
        val deck2 = gameUtil.createDeck()
        var atLeastOneDifferenceWasFound = false

        for (i in 0 until deck1.size) {
            if (deck1[i] != deck2[i]) {
                atLeastOneDifferenceWasFound = true
                break
            }
        }

        assertTrue(atLeastOneDifferenceWasFound)
    }

    @Test
    fun createDeckContainsNoDuplicates() {
        val destinationCardList = mutableListOf<Card>()
        val deck = gameUtil.createDeck()
        var duplicateFound = false

        for (card in deck) {
            if (card in destinationCardList) {
                duplicateFound = true
                break
            }
            destinationCardList.add(card)
        }

        assertFalse(duplicateFound)
    }

    @Test
    fun cardValuesMatchMatchesIdenticalCards() {
        val suit = Card.SUIT.Heart
        val rank = 5
        val cards = Pair(Card(suit, rank), Card(suit, rank))

        assertTrue(gameUtil.cardValuesMatch(cards.first, cards.second))
    }

    @Test
    fun cardValuesMatchDoesMatchDifferentSuitsSameRank() {
        val suit = Card.SUIT.Heart
        val rank = 5
        val cards = Pair(
            Card(suit, rank),
            Card(Card.SUIT.Diamond, rank)
        )

        assertTrue(gameUtil.cardValuesMatch(cards.first, cards.second))
    }

    @Test
    fun cardValuesMatchDoesNotMatchDifferentRanks() {
        val suit = Card.SUIT.Heart
        val rank = 5
        val cards = Pair(Card(suit, rank), Card(suit, 6))

        assertFalse(gameUtil.cardValuesMatch(cards.first, cards.second))
    }
    //endregion

    //region task
    @Test
    fun performTaskAfterDelayRunsTask() {
        var taskCompleted = false
        val millis = System.currentTimeMillis()
        var millisAtTaskCompletion: Long = millis

        gameUtil.performTaskAfterDelay(Runnable {
            taskCompleted = true
            millisAtTaskCompletion = System.currentTimeMillis()
        })
        Thread.sleep(MAX_DELAY_TIME_MILLIS)

        assertTrue(taskCompleted)
        assertTrue(millisDeltaWithinRequiredDelay(millis, millisAtTaskCompletion))
    }

    @Test
    fun stopRunningTasksStopsRunningTasks() {
        var task1Completed = false
        var task2Completed = false

        gameUtil.performTaskAfterDelay(Runnable {
            task1Completed = true
        })
        gameUtil.performTaskAfterDelay(Runnable {
            task2Completed = true
        })
        gameUtil.stopRunningTasks()
        Thread.sleep(MAX_DELAY_TIME_MILLIS)

        assertFalse(task1Completed && task2Completed)
    }
    //endregion

    @Test
    fun getWinTextReturnsValidText() {
        val name = "George"
        assertEquals("SNAP! $name won.", gameUtil.getWinText(SnapGamePlayer(name)))
    }

    //region Helper

    private fun millisDeltaWithinRequiredDelay(start: Long, end: Long): Boolean {
        val delta = end - start
        return delta in 1..MAX_DELAY_TIME_MILLIS
    }
    //endregion
}
