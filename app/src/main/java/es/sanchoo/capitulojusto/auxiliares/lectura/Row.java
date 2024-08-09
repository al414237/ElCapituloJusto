package es.sanchoo.capitulojusto.auxiliares.lectura;

import java.util.List;

public class Row {
    private List<Integer> data;

    public Row(List<Integer> data){
        this.data = data;
    }

    public List<Integer> getData(){
        return data;
    }

}
