package es.sanchoo.capitulojusto.auxiliares.lectura;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private List<String> headers;
    protected List<Row> rows;

    public Table(List<String> atributos){
        headers = atributos;
        rows = new ArrayList<>();
    }

    public Row getRowAt(int rowNumber){
        return rows.get(rowNumber);
    }

    public boolean addRow(List<Integer> datos){
        return rows.add(new Row(datos));
    }


    public int getNumRows(){
        return rows.size();
    }

    public int numberOfAttributes(){
        return headers.size();
    }

    public List<String> getHeaders(){
        return headers;
    }

}
