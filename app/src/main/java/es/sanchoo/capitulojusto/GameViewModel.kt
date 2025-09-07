package es.sanchoo.capitulojusto

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.sanchoo.capitulojusto.auxiliares.Panel
import es.sanchoo.capitulojusto.auxiliares.Player
import es.sanchoo.capitulojusto.auxiliares.lectura.Table
import java.util.PriorityQueue
import java.util.Random
import kotlin.math.abs
import kotlin.math.min
import es.sanchoo.capitulojusto.Constants.MAX_TURNOS
import es.sanchoo.capitulojusto.menu.GameSettings

// TODO: TENGO QUE REESTRUCTURARLO, NO ES GameVM QUIEN NOTIFICA a GameA, SINO AL REVÉS;

enum class State {
    GUESSING, CHECKING
}


class GameViewModel: ViewModel() {
    // aquí incluiremos las funciones y variables que afectan a la lógica del programa
    var turn: Int = 1
    var players: MutableList<Player> = mutableListOf()

    var currentPanel: Panel? = null
    var panels: MutableList<Panel> = mutableListOf()


    private var conditionsSet: MutableSet<Int> = mutableSetOf()
    private var limitChapter: Int = 1000 // luego se cambia

    private var state : State = State.GUESSING

    private val _finishGame = MutableLiveData<Boolean>()
    val finishGame: LiveData<Boolean> = _finishGame

    private val _showNotEnoughPanels = MutableLiveData<Boolean>()
    val showNotEnoughPanels: LiveData<Boolean> get() = _showNotEnoughPanels

    fun onNext(chapters: List<Int>): Panel?{
        when(state){
            State.GUESSING -> {
                showResults(chapters)
                state = State.CHECKING
                turn++
                return null
            }
            State.CHECKING -> {
                state = State.GUESSING
                return nextPanel()
            }
        }
    }

    fun setConditions(){
        limitChapter = GameSettings.max_cap

        val admitEasy = GameSettings.dificultad[0]
        val admitMedium = GameSettings.dificultad[1]
        val admitHard = GameSettings.dificultad[2]

        if (admitEasy) conditionsSet.add(1)
        if (admitMedium) conditionsSet.add(2)
        if (admitHard) conditionsSet.add(3)
    }

    fun setPlayers(){
        players.clear()

        val n = GameSettings.n_players
        players.add(Player(GameSettings.players_names[0]))
        if (n > 0) {
            players.add(Player(GameSettings.players_names[1]))
            if (n > 1) {
                players.add(Player(GameSettings.players_names[2]))
                if (n > 2) {
                    players.add(Player(GameSettings.players_names[3]))
                }
            }
        }
    }


    fun setTable(table: Table) {
        setOrderOfPanels(table)
    }
    private fun setOrderOfPanels(table: Table) {
        val maxPanels = min(table.numRows, MAX_TURNOS)

        val rng = Random(System.nanoTime())
        val randomNumbers = (0 until table.numRows).shuffled(rng)

        for (num in randomNumbers) {
            val newPanel = getNewPanel(num, table)

            if (newPanel.rightChapter <= limitChapter &&
                conditionsSet.contains(newPanel.difficulty)
            ) {
                panels.add(newPanel)
            }

            if (panels.size >= maxPanels) break
        }

        if (panels.size < maxPanels) {
//            Log.w("Paneles", "No se encontraron suficientes paneles que cumplan las condiciones. Generados: ${panels.size}")
            _showNotEnoughPanels.value = true
        }

    }

    private fun getNewPanel(i: Int, table: Table): Panel {
        val row: List<Int> = table.getRowAt(i).data

        val fileName: String = row[0].toString()
        val chapter: Int = row[1]
        val difficulty: Int = row[2]

        return Panel(fileName, chapter, difficulty)

    }

    fun nextPanel(): Panel? {
        currentPanel = null
        if (turn > panels.size) {
            onGameFinished()
        } else {
            currentPanel = panels[turn-1]
        }
        return currentPanel

    }

    private fun showResults(chapters: List<Int>){
        val rightChapter = currentPanel!!.rightChapter

        for (i in 0 until players.size) {
            val player = players[i]
            val guess = chapters[i]

            if (guess == rightChapter) {
                player.addScore(-10)
            } else {
                val difference = abs(guess - rightChapter)
                player.addScore(difference)
            }
        }

        // TODO? VISTA: FEEDBACK
    }

    fun onGameFinished() {
        _finishGame.value = true
        restart()
    }

    fun restart() {
        _finishGame.value = false
        turn = 1
        players.forEach { it.restartGame() }
        panels.clear()
        state = State.GUESSING
    }

    fun getResults(): List<Player>{
        val ranking = PriorityQueue<Player>()

        players.forEach { ranking.add(it) }

        val result = mutableListOf<Player>()
        while (ranking.isNotEmpty()) {
            result.add(ranking.poll()!!)
        }

        return result
    }

    fun getImgsFromPanels(): MutableList<String> {
        val imgPanels = mutableListOf<String>()
        panels.forEach { imgPanels.add(it.image) }
        return imgPanels
    }
}
