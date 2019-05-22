package com.example.android.snap.game

import com.example.android.snap.player.CardGamePlayer
import com.example.android.snap.util.CardGameUtil
import com.example.android.snap.util.SnapGameUtil

const val PLAYERS_NOT_SET = "Players not set"
const val STARTING_GAME = "Starting game"
const val OUTCOME_DRAW = "Game was a draw."
const val GAME_OVER = "Game over"
const val SNAP_INVALID = "No snap! Cards don't match!"

class SnapGameController(private val gameUtil: CardGameUtil = SnapGameUtil.instance()) : CardGameController {
    private val deck = gameUtil.createDeck()
    private val gameStatePlayersNotSet = GameStatePlayersNotSet()
    private val gameStateNotStarted = GameStateNotStarted()
    private val gameStateStartedBeforeFirstCardTurn = GameStateStartedBeforeFirstCardTurn()
    private val gameStateStartedAfterFirstCardTurn = GameStateStartedAfterFirstCardTurn()
    private val gameStateEnded = GameStateEnded()
    private lateinit var lastCardGamePlayerToTakeTurn: CardGamePlayer
    private lateinit var turnedCard: Card
    private lateinit var player1: CardGamePlayer
    private lateinit var player2: CardGamePlayer
    private var gameState: GameState
    private var currentCardFacingUp: Card

    init {
        currentCardFacingUp = deck.removeAt(0)
        gameState = gameStatePlayersNotSet
    }

    //region API
    override fun startGame() {
        gameState.startGame()
    }

    override fun readyToTakeTurn() {
        gameState.readyToTakeTurn()
    }

    override fun callWin(cardGamePlayer: CardGamePlayer) {
        gameState.notifyWin(cardGamePlayer)
    }

    override fun takeTurn(cardGamePlayer: CardGamePlayer) {
        gameState.takeTurn(cardGamePlayer)
    }

    override fun getCardPair(): Pair<Card?, Card?> {
        val firstCard: Card? = if (::turnedCard.isInitialized) turnedCard else null
        return Pair(firstCard, currentCardFacingUp)
    }

    override fun registerPlayer1(cardGamePlayer: CardGamePlayer) {
        gameState.setPlayer1(cardGamePlayer)
    }

    override fun registerPlayer2(cardGamePlayer: CardGamePlayer) {
        gameState.setPlayer2(cardGamePlayer)
    }

    //endregion

    //region Private
    private fun prepareCardsForNextMove() {
        currentCardFacingUp = turnedCard
    }

    private fun notifyPlayersDeckHasChanged() {
        player1.notifyDeckHasChanged()
        player2.notifyDeckHasChanged()
    }

    private fun allPlayersReadyToPlay(): Boolean {
        return player1.readyToPlay() &&
                player2.readyToPlay()
    }

    private fun endGameDraw() {
        gameOver(OUTCOME_DRAW)
    }

    private fun gameOver(message: String) {
        gameState = gameStateEnded
        gameUtil.stopRunningTasks()
        gameUtil.reportMessage(message)
        gameUtil.reportMessage(GAME_OVER)
    }

    private fun endGameWin(winner: CardGamePlayer) {
        if (gameUtil.cardValuesMatch(turnedCard, currentCardFacingUp)) {
            gameOver(gameUtil.getWinText(winner))
        } else {
            gameUtil.reportMessage(SNAP_INVALID)
        }
    }

    @Synchronized
    private fun takeTurnInternal(cardGamePlayer: CardGamePlayer) {
        if (deck.size > 0) {
            turnedCard = deck.removeAt(0)
            lastCardGamePlayerToTakeTurn = cardGamePlayer
            reportMove(cardGamePlayer.name, turnedCard)
            notifyPlayersDeckHasChanged()
        } else {
            endGameDraw()
        }
    }

    private fun reportMove(
        cardGamePlayer: String,
        card: Card
    ) {
        gameUtil.reportMessage(
            "$cardGamePlayer turned $card"
        )
    }
    //endregion

    //region State
    private interface GameState {
        fun notifyWin(cardGamePlayer: CardGamePlayer)
        fun notifyNextPlayerToTakeTurn()
        fun takeTurn(cardGamePlayer: CardGamePlayer)
        fun setPlayer1(cardGamePlayer: CardGamePlayer)
        fun setPlayer2(cardGamePlayer: CardGamePlayer)
        fun readyToTakeTurn()
        fun startGame()
    }

    private abstract inner class BaseGameState : GameState {
        override fun notifyWin(cardGamePlayer: CardGamePlayer) {
            //do nothing
        }

        override fun takeTurn(cardGamePlayer: CardGamePlayer) {
            //do nothing
        }

        override fun notifyNextPlayerToTakeTurn() {
            //do nothing
        }

        override fun setPlayer1(cardGamePlayer: CardGamePlayer) {
            //do nothing
        }

        override fun setPlayer2(cardGamePlayer: CardGamePlayer) {
            //do nothing
        }

        override fun readyToTakeTurn() {
            if (allPlayersReadyToPlay()) {
                prepareCardsForNextMove()
                notifyNextPlayerToTakeTurn()
            }
        }

        override fun startGame() {
            //do nothing
        }
    }

    private inner class GameStatePlayersNotSet : BaseGameState() {
        private var player1set = false
        private var player2set = false

        override fun notifyWin(cardGamePlayer: CardGamePlayer) {
            gameUtil.reportMessage(PLAYERS_NOT_SET)
        }

        override fun setPlayer1(cardGamePlayer: CardGamePlayer) {
            player1 = cardGamePlayer
            player1.registerController(this@SnapGameController)
            player1set = true
            updateState()
        }

        override fun setPlayer2(cardGamePlayer: CardGamePlayer) {
            player2 = cardGamePlayer
            player2.registerController(this@SnapGameController)
            player2set = true
            updateState()
        }

        private fun updateState() {
            if (player1set && player2set) gameState = gameStateNotStarted
        }

        override fun readyToTakeTurn() {
            gameUtil.reportMessage(PLAYERS_NOT_SET)
        }

        override fun takeTurn(cardGamePlayer: CardGamePlayer) {
            gameUtil.reportMessage(PLAYERS_NOT_SET)
        }

        override fun startGame() {
            gameUtil.reportMessage(PLAYERS_NOT_SET)
        }
    }

    private inner class GameStateNotStarted : BaseGameState() {
        override fun readyToTakeTurn() {
            // do nothing
        }

        override fun startGame() {
            updateState()
            gameUtil.reportMessage("Starting game")
            reportMove("Dealer", currentCardFacingUp)
            gameState.notifyNextPlayerToTakeTurn()
        }

        private fun updateState() {
            gameState = gameStateStartedBeforeFirstCardTurn
        }
    }

    private inner class GameStateStartedBeforeFirstCardTurn : BaseGameState() {
        override fun takeTurn(cardGamePlayer: CardGamePlayer) {
            takeTurnInternal(cardGamePlayer)
            updateState()
        }

        override fun notifyNextPlayerToTakeTurn() {
            player1.notifyToTakeTurn()
        }

        private fun updateState() {
            gameState = gameStateStartedAfterFirstCardTurn
        }

        override fun readyToTakeTurn() {
            // do nothing
        }
    }

    private inner class GameStateStartedAfterFirstCardTurn : BaseGameState() {
        override fun notifyWin(cardGamePlayer: CardGamePlayer) {
            endGameWin(cardGamePlayer)
        }

        override fun takeTurn(cardGamePlayer: CardGamePlayer) {
            takeTurnInternal(cardGamePlayer)
        }

        override fun notifyNextPlayerToTakeTurn() {
            if (lastCardGamePlayerToTakeTurn == player1) {
                player2.notifyToTakeTurn()
            } else {
                player1.notifyToTakeTurn()
            }
        }
    }

    private inner class GameStateEnded : BaseGameState()
    //endregion
}

