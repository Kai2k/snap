package com.example.android.snap

import com.example.android.snap.game.*
import com.example.android.snap.player.CardGamePlayer
import com.example.android.snap.util.CardGameUtil
import com.example.android.snap.util.SnapGameUtil
import junit.framework.TestCase.assertFalse
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

class SnapGameControllerTest {
    private lateinit var player1: CardGamePlayer
    private lateinit var player2: CardGamePlayer

    @Before
    fun setUp() {
        player1 = mock(CardGamePlayer::class.java)
        Mockito.`when`(player1.name).thenReturn("Player1")
        player2 = mock(CardGamePlayer::class.java)
        Mockito.`when`(player2.name).thenReturn("Player2")
    }

    //region Players not yet added
    @Test
    fun startGameNoPlayersAddedReports() {
        val deck = mutableListOf(Card(Card.SUIT.Heart, 5), Card(Card.SUIT.Diamond, 6))
        val gameUtil = mock(CardGameUtil::class.java)
        Mockito.`when`(gameUtil.createDeck()).thenReturn(deck)

        val controller = SnapGameController(gameUtil)

        controller.startGame()

        verify(gameUtil).reportMessage(PLAYERS_NOT_SET)
    }

    @Test
    fun startGameNoPlayer1AddedReports() {
        val deck = mutableListOf(Card(Card.SUIT.Heart, 5), Card(Card.SUIT.Diamond, 6))
        val gameUtil = mock(CardGameUtil::class.java)
        Mockito.`when`(gameUtil.createDeck()).thenReturn(deck)

        val controller = SnapGameController(gameUtil)
        controller.registerPlayer2(player2)

        controller.startGame()

        verify(gameUtil).reportMessage(PLAYERS_NOT_SET)
    }

    @Test
    fun startGameNoPlayer2AddedReports() {
        val deck = mutableListOf(Card(Card.SUIT.Heart, 5), Card(Card.SUIT.Diamond, 6))
        val gameUtil = mock(CardGameUtil::class.java)
        Mockito.`when`(gameUtil.createDeck()).thenReturn(deck)

        val controller = SnapGameController(gameUtil)
        controller.registerPlayer1(player1)

        controller.startGame()

        verify(gameUtil).reportMessage(PLAYERS_NOT_SET)
    }

    @Test
    fun readyToTakeTurnNoPlayersAddedReports() {
        val deck = mutableListOf(Card(Card.SUIT.Heart, 5), Card(Card.SUIT.Diamond, 6))
        val gameUtil = mock(CardGameUtil::class.java)
        Mockito.`when`(gameUtil.createDeck()).thenReturn(deck)

        val controller = SnapGameController(gameUtil)

        controller.readyToTakeTurn()

        verify(gameUtil).reportMessage(PLAYERS_NOT_SET)
    }

    @Test
    fun readyToTakeTurnNoPlayer1AddedReports() {
        val deck = mutableListOf(Card(Card.SUIT.Heart, 5), Card(Card.SUIT.Diamond, 6))
        val gameUtil = mock(CardGameUtil::class.java)
        Mockito.`when`(gameUtil.createDeck()).thenReturn(deck)

        val controller = SnapGameController(gameUtil)
        controller.registerPlayer2(player2)

        controller.readyToTakeTurn()

        verify(gameUtil).reportMessage(PLAYERS_NOT_SET)
    }

    @Test
    fun readyToTakeTurnNoPlayer2AddedReports() {
        val deck = mutableListOf(Card(Card.SUIT.Heart, 5), Card(Card.SUIT.Diamond, 6))
        val gameUtil = mock(CardGameUtil::class.java)
        Mockito.`when`(gameUtil.createDeck()).thenReturn(deck)

        val controller = SnapGameController(gameUtil)
        controller.registerPlayer1(player1)

        controller.readyToTakeTurn()

        verify(gameUtil).reportMessage(PLAYERS_NOT_SET)
    }

    @Test
    fun callWinNoPlayersAddedReports() {
        val deck = mutableListOf(Card(Card.SUIT.Heart, 5), Card(Card.SUIT.Diamond, 6))
        val gameUtil = mock(CardGameUtil::class.java)
        Mockito.`when`(gameUtil.createDeck()).thenReturn(deck)

        val controller = SnapGameController(gameUtil)

        controller.callWin(player1)

        verify(gameUtil).reportMessage(PLAYERS_NOT_SET)
    }

    @Test
    fun callWinNoPlayer1AddedReports() {
        val deck = mutableListOf(Card(Card.SUIT.Heart, 5), Card(Card.SUIT.Diamond, 6))
        val gameUtil = mock(CardGameUtil::class.java)
        Mockito.`when`(gameUtil.createDeck()).thenReturn(deck)

        val controller = SnapGameController(gameUtil)
        controller.registerPlayer2(player2)

        controller.callWin(player2)

        verify(gameUtil).reportMessage(PLAYERS_NOT_SET)
    }

    @Test
    fun callWinNoPlayer2AddedReports() {
        val deck = mutableListOf(Card(Card.SUIT.Heart, 5), Card(Card.SUIT.Diamond, 6))
        val gameUtil = mock(CardGameUtil::class.java)
        Mockito.`when`(gameUtil.createDeck()).thenReturn(deck)

        val controller = SnapGameController(gameUtil)
        controller.registerPlayer1(player1)

        controller.callWin(player1)

        verify(gameUtil).reportMessage(PLAYERS_NOT_SET)
    }

    @Test
    fun takeTurnNoPlayersAddedReports() {
        val deck = mutableListOf(Card(Card.SUIT.Heart, 5), Card(Card.SUIT.Diamond, 6))
        val gameUtil = mock(CardGameUtil::class.java)
        Mockito.`when`(gameUtil.createDeck()).thenReturn(deck)

        val controller = SnapGameController(gameUtil)

        controller.takeTurn(player1)

        verify(gameUtil).reportMessage(PLAYERS_NOT_SET)
    }

    @Test
    fun takeTurnNoPlayer1AddedReports() {
        val deck = mutableListOf(Card(Card.SUIT.Heart, 5), Card(Card.SUIT.Diamond, 6))
        val gameUtil = mock(CardGameUtil::class.java)
        Mockito.`when`(gameUtil.createDeck()).thenReturn(deck)

        val controller = SnapGameController(gameUtil)
        controller.registerPlayer2(player2)

        controller.takeTurn(player2)

        verify(gameUtil).reportMessage(PLAYERS_NOT_SET)
    }

    @Test
    fun takeTurnNoPlayer2AddedReports() {
        val deck = mutableListOf(Card(Card.SUIT.Heart, 5), Card(Card.SUIT.Diamond, 6))
        val gameUtil = mock(CardGameUtil::class.java)
        Mockito.`when`(gameUtil.createDeck()).thenReturn(deck)

        val controller = SnapGameController(gameUtil)
        controller.registerPlayer1(player1)

        controller.takeTurn(player1)

        verify(gameUtil).reportMessage(PLAYERS_NOT_SET)
    }

    @Test
    fun getCardPairNoPlayersAddedDoesNotThrow() {
        val deck = mutableListOf(Card(Card.SUIT.Heart, 5), Card(Card.SUIT.Diamond, 6))
        val gameUtil = mock(CardGameUtil::class.java)
        Mockito.`when`(gameUtil.createDeck()).thenReturn(deck)

        val controller = SnapGameController(gameUtil)

        controller.getCardPair()
    }
    //endregion

    //region Game started
    @Test
    fun startGame() {
        val deck = mutableListOf(Card(Card.SUIT.Heart, 5), Card(Card.SUIT.Diamond, 6))
        val gameUtil = mock(CardGameUtil::class.java)
        Mockito.`when`(gameUtil.createDeck()).thenReturn(deck)

        val controller = SnapGameController(gameUtil)
        controller.registerPlayer1(player1)
        controller.registerPlayer2(player2)

        controller.startGame()

        verify(gameUtil).reportMessage(STARTING_GAME)
        verify(gameUtil).reportMessage("Dealer turned 5 of Hearts")
        verify(player1).notifyToTakeTurn()
    }

    @Test
    fun bothPlayersReadyReadyToTakeTurnNotifiesNextPlayer() {
        val controller = setUpController()

        controller.startGame()
        controller.takeTurn(player1)
        // Player1 is always notified to take a turn at the start of the game, to turn the first card
        reset(player1)
        Mockito.`when`(player1.readyToPlay()).thenReturn(true)
        Mockito.`when`(player2.readyToPlay()).thenReturn(true)
        controller.readyToTakeTurn()

        verify(player1, never()).notifyToTakeTurn()
        verify(player2).notifyToTakeTurn()
    }

    @Test
    fun player1NotReadyReadyToTakeTurnDoesNotNotifyNextPlayer() {
        val controller = setUpController()

        controller.startGame()
        controller.takeTurn(player1)
        // Player1 is always notified to take a turn at the start of the game, to turn the first card
        reset(player1)
        Mockito.`when`(player1.readyToPlay()).thenReturn(false)
        Mockito.`when`(player2.readyToPlay()).thenReturn(true)
        controller.readyToTakeTurn()

        verify(player1, never()).notifyToTakeTurn()
        verify(player2, never()).notifyToTakeTurn()
    }

    @Test
    fun player2NotReadyReadyToTakeTurnDoesNotNotifyNextPlayer() {
        val controller = setUpController()

        controller.startGame()
        controller.takeTurn(player1)
        // Player1 is always notified to take a turn at the start of the game, to turn the first card
        reset(player1)
        Mockito.`when`(player1.readyToPlay()).thenReturn(true)
        Mockito.`when`(player2.readyToPlay()).thenReturn(false)
        controller.readyToTakeTurn()

        verify(player1, never()).notifyToTakeTurn()
        verify(player2, never()).notifyToTakeTurn()
    }

    @Test
    fun takeTurnCardsRemainingTurnsCard() {
        val deck = mutableListOf(
            Card(Card.SUIT.Heart, 5), Card(Card.SUIT.Diamond, 6),
            Card(Card.SUIT.Club, 7)
        )
        val gameUtil = mock(CardGameUtil::class.java)
        Mockito.`when`(gameUtil.createDeck()).thenReturn(deck)
        val controller = SnapGameController(gameUtil)
        controller.registerPlayer1(player1)
        controller.registerPlayer2(player2)

        controller.startGame()
        // Player 1 always turns the first card following the dealer
        controller.takeTurn(player1)
        val cardPairBeforeTurn = controller.getCardPair()
        Mockito.`when`(player1.readyToPlay()).thenReturn(true)
        Mockito.`when`(player2.readyToPlay()).thenReturn(true)
        controller.readyToTakeTurn()

        controller.takeTurn(player2)
        val cardPairAfterTurn = controller.getCardPair()

        assertFalse(cardPairBeforeTurn.first == cardPairAfterTurn.first)
        assertFalse(cardPairBeforeTurn.second == cardPairAfterTurn.second)
        verify(player1, times(2)).notifyDeckHasChanged()
        verify(player2, times(2)).notifyDeckHasChanged()
    }

    @Test
    fun takeTurnNoCardsRemainingCallsDraw() {
        val deck = mutableListOf(Card(Card.SUIT.Heart, 5), Card(Card.SUIT.Diamond, 6))
        val gameUtil = mock(CardGameUtil::class.java)
        Mockito.`when`(gameUtil.createDeck()).thenReturn(deck)
        val controller = SnapGameController(gameUtil)
        controller.registerPlayer1(player1)
        controller.registerPlayer2(player2)

        controller.startGame()
        // Player 1 always turns the first card following the dealer
        controller.takeTurn(player1)
        Mockito.`when`(player1.readyToPlay()).thenReturn(true)
        Mockito.`when`(player2.readyToPlay()).thenReturn(true)
        controller.readyToTakeTurn()

        controller.takeTurn(player2)

        verify(gameUtil).stopRunningTasks()
        verify(gameUtil).reportMessage(OUTCOME_DRAW)
        verify(gameUtil).reportMessage(GAME_OVER)
    }

    @Test
    fun callWinCardsDoNotMatchReports() {
        val deck = mutableListOf(
            Card(Card.SUIT.Heart, 5), Card(Card.SUIT.Diamond, 6),
            Card(Card.SUIT.Club, 7)
        )
        val gameUtil = mock(CardGameUtil::class.java)
        Mockito.`when`(gameUtil.createDeck()).thenReturn(deck)
        val controller = SnapGameController(gameUtil)
        controller.registerPlayer1(player1)
        controller.registerPlayer2(player2)

        controller.startGame()
        // Player 1 always turns the first card following the dealer
        controller.takeTurn(player1)
        Mockito.`when`(player1.readyToPlay()).thenReturn(true)
        Mockito.`when`(player2.readyToPlay()).thenReturn(true)
        controller.readyToTakeTurn()

        controller.takeTurn(player2)
        controller.callWin(player2)

        verify(gameUtil).reportMessage(SNAP_INVALID)
    }

    @Test
    fun callWinCardsMatchEndsGame() {
        val card2 = Card(Card.SUIT.Diamond, 6)
        val card3 = Card(Card.SUIT.Club, 6)
        val winText = SnapGameUtil.instance().getWinText(player2)
        val deck = mutableListOf(Card(Card.SUIT.Heart, 5), card2, card3)
        val gameUtil = mock(CardGameUtil::class.java)
        Mockito.`when`(gameUtil.createDeck()).thenReturn(deck)
        Mockito.`when`(gameUtil.cardValuesMatch(card3, card2)).thenReturn(true)
        Mockito.`when`(gameUtil.getWinText(player2)).thenReturn(winText)
        val controller = SnapGameController(gameUtil)
        controller.registerPlayer1(player1)
        controller.registerPlayer2(player2)

        controller.startGame()
        // Player 1 always turns the first card following the dealer
        controller.takeTurn(player1)
        Mockito.`when`(player1.readyToPlay()).thenReturn(true)
        Mockito.`when`(player2.readyToPlay()).thenReturn(true)
        controller.readyToTakeTurn()

        controller.takeTurn(player2)
        controller.callWin(player2)

        verify(gameUtil).stopRunningTasks()
        verify(gameUtil).reportMessage(winText)
        verify(gameUtil).reportMessage(GAME_OVER)
    }

    @Test
    fun bothPlayersCallWinEndsGameCorrectly() {
        /* We expect 6 calls to reportMessage():
        -Game started
        -Dealer turns card
        -Player1 turns card
        -Player2 turns card
        -Snap
        -Game over */
        val numberOfExpectedCallsToReportMessage = 6
        val card2 = Card(Card.SUIT.Diamond, 6)
        val card3 = Card(Card.SUIT.Club, 6)
        val winText = SnapGameUtil.instance().getWinText(player2)
        val deck = mutableListOf(Card(Card.SUIT.Heart, 5), card2, card3)
        val gameUtil = mock(CardGameUtil::class.java)
        Mockito.`when`(gameUtil.createDeck()).thenReturn(deck)
        Mockito.`when`(gameUtil.cardValuesMatch(card3, card2)).thenReturn(true)
        Mockito.`when`(gameUtil.getWinText(player2)).thenReturn(winText)
        val controller = SnapGameController(gameUtil)
        controller.registerPlayer1(player1)
        controller.registerPlayer2(player2)

        controller.startGame()
        // Player 1 always turns the first card following the dealer
        controller.takeTurn(player1)
        Mockito.`when`(player1.readyToPlay()).thenReturn(true)
        Mockito.`when`(player2.readyToPlay()).thenReturn(true)
        controller.readyToTakeTurn()

        controller.takeTurn(player2)
        controller.callWin(player2)
        controller.callWin(player1)

        verify(gameUtil).stopRunningTasks()
        verify(gameUtil).reportMessage(winText)
        verify(gameUtil).reportMessage(GAME_OVER)
        verify(gameUtil, times(numberOfExpectedCallsToReportMessage)).reportMessage(myAny(String::class.java))
    }
    //endregion

    //region Helpers
    private fun setUpController(): CardGameController {
        val controller = SnapGameController()

        controller.registerPlayer1(player1)
        controller.registerPlayer2(player2)
        return controller
    }

    private fun <T> myAny(clazz: Class<T>): T {
        any(clazz)
        return uninitialized()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> uninitialized(): T = null as T
    //endregion
}