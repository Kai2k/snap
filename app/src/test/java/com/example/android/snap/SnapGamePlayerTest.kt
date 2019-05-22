package com.example.android.snap

import com.example.android.snap.game.Card
import com.example.android.snap.game.CardGameController
import com.example.android.snap.player.CONTROLLER_MUST_BE_SET
import com.example.android.snap.player.CardGamePlayer
import com.example.android.snap.player.NULL_CARD_RETURNED
import com.example.android.snap.player.SnapGamePlayer
import com.example.android.snap.util.CardGameUtil
import com.example.android.snap.util.MAX_DELAY_TIME_MILLIS
import com.example.android.snap.util.SnapGameUtil
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

class SnapGamePlayerTest {
    private val playerName = "John"

    @Test
    fun notifyDeckHasChangedNoControllerReports() {
        val gameUtil: CardGameUtil = mock(CardGameUtil::class.java)

        SnapGamePlayer(playerName, gameUtil).notifyDeckHasChanged()

        verify(gameUtil).reportMessage(CONTROLLER_MUST_BE_SET)
    }

    @Test
    fun notifyDeckHasChangedNullCardReturnedReports() {
        val gameUtil: CardGameUtil = mock(CardGameUtil::class.java)
        val card = Card(Card.SUIT.Heart, 5)
        val gameController: CardGameController = mock(CardGameController::class.java)
        Mockito.`when`(gameController.getCardPair()).thenReturn(Pair(card, null))
        val player = createPlayer(gameUtil, gameController)

        player.notifyDeckHasChanged()

        verify(gameUtil).reportMessage(NULL_CARD_RETURNED)
    }

    @Test
    fun notifyDeckHasChangedMatchingCardsCallsWin() {
        val card1 = Card(Card.SUIT.Heart, 5)
        val card2 = Card(Card.SUIT.Diamond, 5)
        val gameUtil = SnapGameUtil.instance()
        val gameController: CardGameController = mock(CardGameController::class.java)
        Mockito.`when`(gameController.getCardPair()).thenReturn(Pair(card1, card2))

        val gamePlayer = createPlayer(gameUtil, gameController)
         gamePlayer.notifyDeckHasChanged()
        Thread.sleep(MAX_DELAY_TIME_MILLIS)

        verify(gameController).callWin(gamePlayer)
        verify(gameController, never()).readyToTakeTurn()
    }

    @Test
    fun notifyDeckHasChangedNonMatchingCardsCallsReadyToTakeTurn() {
        val card1 = Card(Card.SUIT.Heart, 5)
        val card2 = Card(Card.SUIT.Diamond, 6)
        val gameUtil = SnapGameUtil.instance()
        val gameController: CardGameController = mock(CardGameController::class.java)
        Mockito.`when`(gameController.getCardPair()).thenReturn(Pair(card1, card2))

        val gamePlayer = createPlayer(gameUtil, gameController)
        gamePlayer.notifyDeckHasChanged()
        Thread.sleep(MAX_DELAY_TIME_MILLIS)

        verify(gameController, never()).callWin(gamePlayer)
        verify(gameController).readyToTakeTurn()
    }

    @Test
    fun notifyTakeTurnNoControllerReports() {
        val gameUtil: CardGameUtil = mock(CardGameUtil::class.java)

        SnapGamePlayer(playerName, gameUtil).notifyToTakeTurn()
        verify(gameUtil).reportMessage(CONTROLLER_MUST_BE_SET)
    }

    @Test
    fun readyToPlayNoControllerReturnsFalse() {
        assertFalse(SnapGamePlayer(playerName).readyToPlay())
    }

    @Test
    fun readyToPlayControllerSetReturnsTrue() {
        val gameController: CardGameController = mock(CardGameController::class.java)
        val gameUtil: CardGameUtil = mock(CardGameUtil::class.java)
        val player = createPlayer(gameUtil, gameController)

        assertTrue(player.readyToPlay())
    }

    @Test
    fun readyToPlayWhenPlayerBusyReturnsFalse() {
        val card1 = Card(Card.SUIT.Heart, 5)
        val card2 = Card(Card.SUIT.Diamond, 5)
        val gameController: CardGameController = mock(CardGameController::class.java)
        Mockito.`when`(gameController.getCardPair()).thenReturn(Pair(card1, card2))
        val gameUtil: CardGameUtil = mock(CardGameUtil::class.java)
        val player = createPlayer(gameUtil, gameController)

        player.notifyDeckHasChanged()

        assertFalse(player.readyToPlay())
    }

    @Test
    fun callRegisterControllerTwiceDoesNotThrow() {
        val player = SnapGamePlayer(playerName)

        player.registerController(mock(CardGameController::class.java))
        player.registerController(mock(CardGameController::class.java))
    }

    //region Helpers
    private fun createPlayer(
        gameUtil: CardGameUtil,
        gameController: CardGameController
    ): CardGamePlayer {
        return SnapGamePlayer(playerName, gameUtil).apply {
            registerController(gameController)
        }
    }
    //endregion
}