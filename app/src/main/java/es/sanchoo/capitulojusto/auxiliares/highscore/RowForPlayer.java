package es.sanchoo.capitulojusto.auxiliares.highscore;

import java.util.List;

import es.sanchoo.capitulojusto.auxiliares.lectura.Row;

public class RowForPlayer extends Row {
    private String playerName;

    public RowForPlayer(List<Integer> data, String nombre){
        super(data);
        playerName = nombre;
    }

    public String getPlayerName() {
        return playerName;
    }
}
