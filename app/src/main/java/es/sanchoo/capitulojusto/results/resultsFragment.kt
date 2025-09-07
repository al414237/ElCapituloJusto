package es.sanchoo.capitulojusto.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import es.sanchoo.capitulojusto.R
import es.sanchoo.capitulojusto.auxiliares.Player


class resultsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rankedPlayers = arguments?.getParcelableArrayList<Player>("rankedPlayers") ?: arrayListOf()


        val nameTexts = listOf(
            view.findViewById<TextView>(R.id.player1_name),
            view.findViewById<TextView>(R.id.player2_name),
            view.findViewById<TextView>(R.id.player3_name),
            view.findViewById<TextView>(R.id.player4_name)
        )

        val scoreTexts = listOf(
            view.findViewById<TextView>(R.id.player1_score),
            view.findViewById<TextView>(R.id.player2_score),
            view.findViewById<TextView>(R.id.player3_score),
            view.findViewById<TextView>(R.id.player4_score)
        )

        val playerContainers = listOf(
            view.findViewById<LinearLayout>(R.id.player1_container),
            view.findViewById<LinearLayout>(R.id.player2_container),
            view.findViewById<LinearLayout>(R.id.player3_container),
            view.findViewById<LinearLayout>(R.id.player4_container)
        )


        for (i in 0 until rankedPlayers.size) {
            val player = rankedPlayers[i]
            playerContainers[i].visibility = View.VISIBLE
            nameTexts[i].text = player.name
            scoreTexts[i].text = player.score.toString()
        }

    }
}