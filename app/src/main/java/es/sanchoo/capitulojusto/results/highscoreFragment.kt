package es.sanchoo.capitulojusto.results

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import es.sanchoo.capitulojusto.R
import es.sanchoo.capitulojusto.auxiliares.HighScoreManager
import es.sanchoo.capitulojusto.auxiliares.Player

class highscoreFragment : Fragment() {

    private var players: ArrayList<Player>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        players = arguments?.getParcelableArrayList("players")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_highscore, container, false)
        val containerLayout = root.findViewById<LinearLayout>(R.id.highscoreContainer)

        val manager = HighScoreManager(requireContext())
        val top10 = manager.getTop10()

        val currentPlayers = players ?: listOf()

        top10.forEachIndexed { index, entry ->
            val tv = TextView(requireContext())
            tv.text = "${index + 1}. ${entry.name} - ${entry.score}"
            tv.textSize = when (index) {
                0 -> 26f       // primero
                in 1..2 -> 22f // top 3
                in 3..4 -> 18f // top 5
                else -> 16f    // resto
            }
            // Resaltar en rojo y negrita si el jugador participó en la partida actual
            val isCurrentPlayer = currentPlayers.any { it.name == entry.name && it.score == entry.score }

            if (isCurrentPlayer) {
                tv.setTypeface(null, Typeface.BOLD)
                tv.setTextColor(android.graphics.Color.parseColor("#F44336"))
            } else {
                tv.setTypeface(null, Typeface.NORMAL)
                tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
            containerLayout.addView(tv)

        }

        return root
    }
}
