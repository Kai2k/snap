package com.example.android.snap.game

class Card(@Suppress("unused") val suit: SUIT, val value: Int) {
    var rank: String

    enum class SUIT {
        Spade, Heart, Diamond, Club
    }

    init {
        if (!valueIsValid(value)) throw IllegalArgumentException(
            String.format("Value %s is invalid", value)
        )
        rank = calculateRank()
    }

    private fun calculateRank(): String {
        return when (value) {
            1 -> "Ace"
            11 -> "Jack"
            12 -> "Queen"
            13 -> "King"
            else -> value.toString()
        }
    }

    private fun valueIsValid(rank: Int): Boolean {
        return rank in (1..13)
    }

    override fun toString(): String {
        return "$rank of ${suit}s"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Card

        if (suit != other.suit) return false
        if (value != other.value) return false
        if (rank != other.rank) return false

        return true
    }

    override fun hashCode(): Int {
        var result = suit.hashCode()
        result = 31 * result + value
        result = 31 * result + rank.hashCode()
        return result
    }

}