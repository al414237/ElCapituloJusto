package es.sanchoo.capitulojusto.auxiliares.lectura;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableForPlayer extends Table{
    public TableForPlayer(List<String> atributos){
        super(atributos);
    }

    public boolean addRow(List<Integer> numeros, String nombre) {
        RowForPlayer nuevaFila = new RowForPlayer(numeros, nombre);

        return rows.add(nuevaFila);
    }

    @Override
    public RowForPlayer getRowAt(int rowNumber) {
        return (RowForPlayer) super.getRowAt(rowNumber);
    }



}
