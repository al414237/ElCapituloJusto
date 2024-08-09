package es.sanchoo.capitulojusto.auxiliares.lectura;

import java.util.List;

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
