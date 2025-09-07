package es.sanchoo.capitulojusto.auxiliares

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
class Player(
    val name: String,
    var score: Int = 0,
    val register: MutableList<Int> = mutableListOf()
) : Comparable<Player>, Parcelable {

    var minScore: Int = Int.MAX_VALUE
    var maxScore: Int = Int.MIN_VALUE

    fun getTurnMin(): List<Int> {
        val list = mutableListOf<Int>()
        var firstIndex = register.indexOf(minScore)
        var lastIndex = register.lastIndexOf(minScore)
        list.add(lastIndex)
        while (firstIndex != lastIndex) {
            val subRegister = register.subList(0, lastIndex)
            lastIndex = subRegister.lastIndexOf(minScore)
            list.add(lastIndex)
        }
        return list
    }

    fun getTurnMax(): List<Int> {
        val list = mutableListOf<Int>()
        var firstIndex = register.indexOf(maxScore)
        var lastIndex = register.lastIndexOf(maxScore)
        list.add(lastIndex)
        while (firstIndex != lastIndex) {
            val subRegister = register.subList(0, lastIndex)
            lastIndex = subRegister.lastIndexOf(maxScore)
            list.add(lastIndex)
        }
        return list
    }

    fun getScoreAtTurn(turn: Int) = register.getOrNull(turn) ?: -1

    fun addScore(score: Int) {
        this.score += score
        addRegister(score)
    }

    private fun addRegister(score: Int) {
        register.add(score)
        if (score < minScore) minScore = score
        if (score > maxScore) maxScore = score
    }

    fun restartGame() {
        score = 0
        register.clear()
        minScore = Int.MAX_VALUE
        maxScore = Int.MIN_VALUE
    }

    override fun compareTo(other: Player): Int {
        return this.score.compareTo(other.score)
    }
}
