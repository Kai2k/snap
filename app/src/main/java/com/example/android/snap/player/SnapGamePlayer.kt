package com.example.android.snap.player

import com.example.android.snap.game.Card
import com.example.android.snap.game.CardGameController
import com.example.android.snap.util.CardGameUtil
import com.example.android.snap.util.SnapGameUtil

const val CONTROLLER_MUST_BE_SET = "CardGameController must be set"
const val NULL_CARD_RETURNED = "getCardPair returned a null Card"

class SnapGamePlayer(
    override val name: String,
    private val gameUtil: CardGameUtil = SnapGameUtil.instance()
) : CardGamePlayer {

    private val playerStateControllerNotSet = PlayerStateControllerNotSet()
    private val playerStateReady = PlayerStateReady()
    private val playerStateNotReady = PlayerStateNotReady()
    private var playerState: PlayerState
    private lateinit var controller: CardGameController

    init {
        playerState = playerStateControllerNotSet
    }

    //region API
    override fun registerController(controller: CardGameController) {
        playerState.setController(controller)
    }

    override fun notifyDeckHasChanged() {
        playerState.notifyDeckHasChanged()
    }

    override fun notifyToTakeTurn() {
        playerState.notifyToTakeTurn()
    }

    override fun readyToPlay(): Boolean {
        return playerState.readyToPlay()
    }
    //endregion

    //region Private
    private fun checkCardsMatch(first: Card, second: Card) {
        playerState = playerStateNotReady
        gameUtil.performTaskAfterDelay(Runnable {
            if (gameUtil.cardValuesMatch(
                    first,
                    second
                )
            ) {
                controller.callWin(this@SnapGamePlayer)
            } else {
                playerState = playerStateReady
                controller.readyToTakeTurn()
            }
        })
    }

    private fun turnCard() {
        controller.takeTurn(this)
    }

    private fun cardsValid(first: Card?, second: Card?): Boolean {
        return first != null && second != null
    }
    //endregion

    //region State
    private interface PlayerState {
        fun notifyDeckHasChanged()
        fun notifyToTakeTurn()
        fun readyToPlay(): Boolean
        fun setController(controller: CardGameController)
    }

    private abstract inner class BasePlayerState : PlayerState {
        override fun notifyDeckHasChanged() {
            //do nothing
        }

        override fun notifyToTakeTurn() {
            //do nothing
        }

        override fun readyToPlay(): Boolean {
            return false
        }

        override fun setController(controller: CardGameController) {
            //do nothing
        }
    }

    private inner class PlayerStateControllerNotSet : BasePlayerState() {
        override fun notifyDeckHasChanged() {
            adviseControllerNotSet()
        }

        override fun notifyToTakeTurn() {
            adviseControllerNotSet()
        }

        override fun setController(controller: CardGameController) {
            this@SnapGamePlayer.controller = controller
            updateState()
        }

        private fun adviseControllerNotSet() {
            gameUtil.reportMessage(CONTROLLER_MUST_BE_SET)
        }

        private fun updateState() {
            playerState = playerStateReady
        }
    }

    private inner class PlayerStateReady : BasePlayerState() {
        override fun notifyDeckHasChanged() {
            val cards = controller.getCardPair()
            if (cardsValid(cards.first, cards.second)) {
                checkCardsMatch(cards.first!!, cards.second!!)
            } else {
                gameUtil.reportMessage(NULL_CARD_RETURNED)
            }
        }

        override fun notifyToTakeTurn() {
            playerState = playerStateNotReady
            gameUtil.performTaskAfterDelay(Runnable {
                turnCard()
                playerState = playerStateReady
            })
        }

        override fun readyToPlay(): Boolean {
            return true
        }
    }

    private inner class PlayerStateNotReady : BasePlayerState()
    //endregion
}