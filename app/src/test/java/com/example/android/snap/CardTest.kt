package com.example.android.snap

import com.example.android.snap.game.Card
import junit.framework.TestCase.assertEquals
import org.junit.Test

class CardTest {

    @Test(expected = IllegalArgumentException::class)
    fun constructWithValueLessThan1Throws() {
        Card(Card.SUIT.Heart, 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun constructWithValueGreaterThan13Throws() {
        Card(Card.SUIT.Heart, 14)
    }

    @Test
    fun toStringReturnsCorrectValue() {
        assertEquals(
            "5 of Hearts",
            Card(Card.SUIT.Heart, 5).toString()
        )
    }

}