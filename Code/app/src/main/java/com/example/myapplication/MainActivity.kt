package com.example.myapplication
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    // ... Existing code ...
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // ... Your existing onCreate code ...
      /*  val fragment = EditCardFragment()
        fragment.show(supportFragmentManager, "editCard")
*/
        // Sample cards to test
        val sampleCards = listOf(
            Card(Rank.ACE, Suit.HEARTS),
            Card(Rank.KING, Suit.HEARTS),
            Card(Rank.QUEEN, Suit.HEARTS),
            Card(Rank.JACK, Suit.HEARTS),
            Card(Rank.TEN, Suit.HEARTS)
        )

        // Calculate the best hand for the sample cards
        val bestHandRank = bestHand(sampleCards)

        // Display the result in the TextView
        val resultTextView: TextView = findViewById(R.id.resultTextView)
        resultTextView.text = "Best Hand: $bestHandRank"
    }

    fun bestHand(cards: List<Card>): HandRank {
        return when {
            isRoyalFlush(cards) -> HandRank.ROYAL_FLUSH
            isStraightFlush(cards) -> HandRank.STRAIGHT_FLUSH
            isFourOfAKind(cards) -> HandRank.FOUR_OF_A_KIND
            isFullHouse(cards) -> HandRank.FULL_HOUSE
            isFlush(cards) -> HandRank.FLUSH
            isStraight(cards) -> HandRank.STRAIGHT
            isThreeOfAKind(cards) -> HandRank.THREE_OF_A_KIND
            isTwoPair(cards) -> HandRank.TWO_PAIR
            isOnePair(cards) -> HandRank.ONE_PAIR
            else -> HandRank.HIGH_CARD
        }
    }

    private fun isRoyalFlush(cards: List<Card>): Boolean {
        val sortedCards = cards.sortedBy { it.rank }
        return isFlush(cards) && sortedCards.map { it.rank } == listOf(
            Rank.TEN, Rank.JACK, Rank.QUEEN, Rank.KING, Rank.ACE
        )
    }

    private fun isStraightFlush(cards: List<Card>): Boolean {
        return isFlush(cards) && isStraight(cards)
    }

    private fun isFourOfAKind(cards: List<Card>): Boolean {
        return cards.groupBy { it.rank }.any { it.value.size == 4 }
    }

    private fun isFullHouse(cards: List<Card>): Boolean {
        val groups = cards.groupBy { it.rank }
        return groups.any { it.value.size == 3 } && groups.any { it.value.size == 2 }
    }

    private fun isFlush(cards: List<Card>): Boolean {
        return cards.groupBy { it.suit }.size == 1
    }

    private fun isStraight(cards: List<Card>): Boolean {
        val sortedCards = cards.sortedBy { it.rank }.map { it.rank.ordinal }
        return sortedCards.zipWithNext().all { it.second - it.first == 1 }
    }

    private fun isThreeOfAKind(cards: List<Card>): Boolean {
        return cards.groupBy { it.rank }.any { it.value.size == 3 }
    }

    private fun isTwoPair(cards: List<Card>): Boolean {
        return cards.groupBy { it.rank }.count { it.value.size == 2 } == 2
    }

    private fun isOnePair(cards: List<Card>): Boolean {
        return cards.groupBy { it.rank }.any { it.value.size == 2 }
    }

    // TODO: Implement the logic for the above functions like `isRoyalFlush`, `isStraightFlush`, etc.

    data class Card(val rank: Rank, val suit: Suit)
    enum class Rank { TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE }
    enum class Suit { CLUBS, DIAMONDS, HEARTS, SPADES }
    enum class HandRank {
        ROYAL_FLUSH, STRAIGHT_FLUSH, FOUR_OF_A_KIND, FULL_HOUSE, FLUSH,
        STRAIGHT, THREE_OF_A_KIND, TWO_PAIR, ONE_PAIR, HIGH_CARD
    }
}

