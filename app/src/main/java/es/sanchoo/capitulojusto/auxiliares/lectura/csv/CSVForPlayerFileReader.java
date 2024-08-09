package es.sanchoo.capitulojusto.auxiliares.lectura.csv;

import es.sanchoo.capitulojusto.auxiliares.lectura.TableForPlayer;

import java.util.ArrayList;
import java.util.List;

public class CSVForPlayerFileReader extends CSVUnlabeledFileReader{
    private TableForPlayer tableForPlayer;
    @Override
    public void processHeaders(String headers) {
        tableForPlayer = new TableForPlayer(dividir(headers));
        table = tableForPlayer;
    }

    @Override
    public void processData(String data){
        List<Integer> numeros = new ArrayList<>();
        List<String> filaDividida = dividir(data);
        for (String dato: filaDividida) {
            if ( ! dato.equals( lastOf(filaDividida) ) ){
                numeros.add(Integer.parseInt(dato));
            } else {
                String nombre = dato;
                tableForPlayer.addRow(numeros, nombre);
            }
        }
    }

    protected String lastOf(List<String> list) {
        return list.get( list.size() - 1 );
    }
}
