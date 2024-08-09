package es.sanchoo.capitulojusto.auxiliares.lectura.csv;

import es.sanchoo.capitulojusto.auxiliares.lectura.Table;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVUnlabeledFileReader extends ReaderTemplate{
    protected String lastLine;

    @Override
    public void openSource(String source) {
        try{
            fichero = new BufferedReader(new FileReader(source));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void processHeaders(String headers) {
        table = new Table(dividir(headers));
    }

    @Override
    public void processData(String data) {
        List<Integer> datos = new ArrayList<>();
        for (String dato: dividir(data)) {
            datos.add(Integer.parseInt(dato));
        }
        table.addRow(datos);
    }

    @Override
    public void closeSource() {
        try{
            fichero.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasMoreData() {
        return lastLine != null;
    }

    @Override
    public String getNextData() {
        try{
            lastLine = fichero.readLine();
            return lastLine;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    protected List<String> dividir(String linea){
        if (linea!=null) return List.of(linea.split(","));
        return null;
    }
}
