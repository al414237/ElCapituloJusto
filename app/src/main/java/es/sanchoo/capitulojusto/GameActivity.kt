package es.sanchoo.capitulojusto

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import es.sanchoo.capitulojusto.auxiliares.Player
import es.sanchoo.capitulojusto.auxiliares.applyValueFilter
import es.sanchoo.capitulojusto.auxiliares.lectura.Table
import es.sanchoo.capitulojusto.auxiliares.lectura.csv.CSVUnlabeledFileReader
import es.sanchoo.capitulojusto.menu.GameSettings
import es.sanchoo.capitulojusto.views.GameView

class GameActivity : AppCompatActivity(), GameView {
    // CONTROLADOR: hace de intermediario entre la vista y la lógica
    private val viewModel: GameViewModel by viewModels()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)

        viewModel.showNotEnoughPanels.observe(this) { show ->
            if (show) {
                AlertDialog.Builder(this)
                    .setTitle(R.string.alert_not_panels_title)
                    .setMessage(R.string.alert_not_panels_message)
                    .setPositiveButton(R.string.alert_not_panels_accept) { dialog, _ ->
                        dialog.dismiss()
                        finish()
                    }
                    .show()
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val builder = AlertDialog.Builder(this@GameActivity)
                builder.setTitle(R.string.alert_close_title)
                builder.setMessage(R.string.alert_close_message)
                builder.setPositiveButton(R.string.alert_close_accept) { _, _ ->
                    val intent = Intent(this@GameActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    startActivity(intent)
                    finish()
                }
                builder.setNegativeButton(R.string.alert_close_dismiss) { dialog, _ ->
                    dialog.dismiss()
                }
                builder.show()
            }
        })


        requestedOrientation = SCREEN_ORIENTATION_PORTRAIT

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.players.clear()

        val nameJ1: TextView = findViewById(R.id.textNameJ1)
        val nameJ2: TextView = findViewById(R.id.textNameJ2)
        val nameJ3: TextView = findViewById(R.id.textNameJ3)
        val nameJ4: TextView = findViewById(R.id.textNameJ4)
        nameJ1.text = GameSettings.players_names[0]
        nameJ2.text = GameSettings.players_names[1]
        nameJ3.text = GameSettings.players_names[2]
        nameJ4.text = GameSettings.players_names[3]

        val guessJ1: EditText = findViewById(R.id.guessTextJ1)
        val guessJ2: EditText = findViewById(R.id.guessTextJ2)
        val guessJ3: EditText = findViewById(R.id.guessTextJ3)
        val guessJ4: EditText = findViewById(R.id.guessTextJ4)
        applyValueFilter(guessJ1, true)
        applyValueFilter(guessJ2, true)
        applyValueFilter(guessJ3, true)
        applyValueFilter(guessJ4, true)

        setPlayersInvisibles()

        viewModel.setConditions()
        viewModel.setPlayers()

        viewModel.setTable(loadSolutions())
        viewModel.nextPanel()?.image?.let { setImage(it) }

        updateTurn()

        viewModel.finishGame.observe(this) { shouldFinish ->
            if (shouldFinish == true) {
                val intent = Intent(this, EndActivity::class.java)
                val results = viewModel.getResults()
                intent.putParcelableArrayListExtra(
                    "players",
                    ArrayList<Player>(viewModel.players)
                )
                intent.putParcelableArrayListExtra(
                    "rankedPlayers",
                    ArrayList<Player>(results)
                )
                intent.putStringArrayListExtra(
                    "panels",
                    ArrayList<String>(viewModel.getImgsFromPanels())
                )
                startActivity(intent)
                finish()
            }
        }
    }

    private fun loadSolutions(): Table {
        val csv = CSVUnlabeledFileReader(this)
        return csv.readTableFromSource()
    }

    fun onNextPressed(view: View){
        val result = viewModel.onNext(getChapters())
        if (result != null){ //
            setImage(result.image)
            clearFeedback()
            clearGuesses()
            updateTurn()

        } else {
            updateScore()
            showFeedback()
        }


     }

    private fun showFeedback() {
        // val feedback: TextView = findViewById(R.id.textFeedback)
        val result: TextView = findViewById(R.id.textResult)

        val rightChapter: Int = viewModel.currentPanel!!.rightChapter
        result.text = getString(R.string.game_correct_answer, rightChapter)

        // TODO: FEEDBACK, IMPLEMENTARLO CON SONIDOS?

    }

    private fun updateScore() {
        val scoreJ1: TextView = findViewById(R.id.textScoreJ1)
        val scoreJ2: TextView = findViewById(R.id.textScoreJ2)
        val scoreJ3: TextView = findViewById(R.id.textScoreJ3)
        val scoreJ4: TextView = findViewById(R.id.textScoreJ4)


        scoreJ1.text = viewModel.players[0].score.toString()
        if (viewModel.players.size > 1) {
            scoreJ2.text = viewModel.players[1].score.toString()
            if (viewModel.players.size > 2) {
                scoreJ3.text = viewModel.players[2].score.toString()
                if (viewModel.players.size > 3) {
                    scoreJ4.text = viewModel.players[3].score.toString()
                }
            }
        }
    }

    private fun updateTurn() {
        val numPanel: TextView = findViewById(R.id.textNumPanel)
        val currentTurn: Int = viewModel.turn
        val totalTurns: Int = Constants.MAX_TURNOS

        numPanel.text = getString(R.string.game_vinyeta, currentTurn, totalTurns)
    }

    private fun clearGuesses() {
        val guessJ1: EditText = findViewById(R.id.guessTextJ1)
        val guessJ2: EditText = findViewById(R.id.guessTextJ2)
        val guessJ3: EditText = findViewById(R.id.guessTextJ3)
        val guessJ4: EditText = findViewById(R.id.guessTextJ4)

        guessJ1.setText("")
        guessJ2.setText("")
        guessJ3.setText("")
        guessJ4.setText("")
    }

    private fun clearFeedback() {
        val feedback: TextView = findViewById(R.id.textFeedback)
        val result: TextView = findViewById(R.id.textResult)

        feedback.text = ""
        result.text = getString(R.string.game_guess_answer)
    }

    private fun setPlayersInvisibles() {
        val n = GameSettings.n_players
        val auxSet = mutableSetOf<View>()
        if (n < 3){ // INVISIBLE JUGADOR 4
            val nameJ4: TextView = findViewById(R.id.textNameJ4)
            val guessJ4: EditText = findViewById(R.id.guessTextJ4)
            val scoreJ4: TextView = findViewById(R.id.textScoreJ4)
            auxSet.add(nameJ4)
            auxSet.add(guessJ4)
            auxSet.add(scoreJ4)

            if (n < 2){ // INVISIBLE JUGADOR 3
                val nameJ3: TextView = findViewById(R.id.textNameJ3)
                val guessJ3: EditText = findViewById(R.id.guessTextJ3)
                val scoreJ3: TextView = findViewById(R.id.textScoreJ3)
                auxSet.add(nameJ3)
                auxSet.add(guessJ3)
                auxSet.add(scoreJ3)

                if (n < 1){ // INVISIBLE JUGADOR 2
                    val nameJ2: TextView = findViewById(R.id.textNameJ2)
                    val guessJ2: EditText = findViewById(R.id.guessTextJ2)
                    val scoreJ2: TextView = findViewById(R.id.textScoreJ2)
                    auxSet.add(nameJ2)
                    auxSet.add(guessJ2)
                    auxSet.add(scoreJ2)
                }
            }
        }
        for (view in auxSet){
            view.visibility = View.INVISIBLE
        }

    }

    private fun setImage(imagePath: String) {
        val imageView: ImageView = findViewById(R.id.imageView)
        val resId = resources.getIdentifier(imagePath, "drawable", packageName)

        if (resId != 0) {
            imageView.setImageResource(resId)
        } else {
            imageView.setImageResource(R.drawable.default_image)
        }
    }

    private fun getChapters(): List<Int> {
        fun getTextOf(guess: EditText): Int{
            if (guess.text.toString() == "") {
                return 0
            }
            return guess.text.toString().toInt()
        }

        val chapters: MutableList<Int> = mutableListOf()

        val guessJ1: EditText = findViewById(R.id.guessTextJ1)
        if (guessJ1.visibility != View.INVISIBLE){

            chapters.add(getTextOf(guessJ1))
        }
        val guessJ2: EditText = findViewById(R.id.guessTextJ2)
        if (guessJ2.visibility != View.INVISIBLE){
            chapters.add(getTextOf(guessJ2))
        }
        val guessJ3: EditText = findViewById(R.id.guessTextJ3)
        if (guessJ3.visibility != View.INVISIBLE){
            chapters.add(getTextOf(guessJ3))
        }
        val guessJ4: EditText = findViewById(R.id.guessTextJ4)
        if (guessJ4.visibility != View.INVISIBLE){
            chapters.add(getTextOf(guessJ4))
        }
        return chapters
    }

    override fun getContext(): Context {
        return this
    }

}