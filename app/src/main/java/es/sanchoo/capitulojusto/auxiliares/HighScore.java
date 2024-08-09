package es.sanchoo.capitulojusto.auxiliares;

import auxiliares.lectura.RowForPlayer;
import auxiliares.lectura.TableForPlayer;
import auxiliares.lectura.csv.CSVForPlayerFileReader;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class HighScore {
    private List<Player> highscores = new LinkedList<>();
    private final int CAPACITY = 5;

    public HighScore(){
        readFile();
    }
    public void rewrite() {
        FileWriter file = null;
        PrintWriter pw;
        try {
            file = new FileWriter("src/main/resources/highScore.csv");
            pw = new PrintWriter(file);

            pw.println("score,player");
            for (Player player: highscores) {
                pw.println(player.getScore()+","+ player.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != file)
                    file.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void readFile() {
            CSVForPlayerFileReader csv = new CSVForPlayerFileReader();
            TableForPlayer table = (TableForPlayer) csv.readTableFromSource("src/main/resources/highScore.csv");

            for (int i = 0; i < table.getNumRows(); i++) {
                RowForPlayer row = table.getRowAt(i);
                int score = row.getData().get(0);
                String name = row.getPlayerName();
                highscores.add(new Player(name, score));
            }
    }

    public List<Player> getPlayers() {
        return highscores;
    }

    public int getSize() {
        return highscores.size();
    }

    public void cut() {
        while (getSize() > 5){
            highscores.remove(getSize()-1);
        }
    }

    public int getMaxHighScore() {
            if (highscores.size() < CAPACITY) return Integer.MAX_VALUE;
            return highscores.get( highscores.size() -1 ).getScore();
        }

    public void update(Player player) {
        if (highscores.isEmpty()) {
            highscores.add(player);
        } else {
            for (int i = 0; i < highscores.size(); i++){
                Player aspirant = highscores.get(i);
                if (player.getScore() < aspirant.getScore()){
                    highscores.add(i,player);
                    break;
                } else if (i == highscores.size() - 1) {
                    highscores.add(player);
                    break;
                }
            }
            cut();
        }
            rewrite();
    }
}
