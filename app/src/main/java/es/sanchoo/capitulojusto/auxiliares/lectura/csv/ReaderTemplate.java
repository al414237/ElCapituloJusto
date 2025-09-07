package es.sanchoo.capitulojusto.auxiliares.lectura.csv;

import java.io.BufferedReader;

import es.sanchoo.capitulojusto.auxiliares.lectura.Table;

public abstract class ReaderTemplate {
    protected BufferedReader fichero;
    protected Table table;

    public abstract void openSource(); // SIEMPRE SERÁ solution.csv
    public abstract void processHeaders(String headers);
    public abstract void processData(String data);
    public abstract void closeSource();
    public abstract boolean hasMoreData();
    public abstract String getNextData();

    public final Table readTableFromSource() {
        openSource();
        String headers = getNextData();
        processHeaders(headers);
        String data = getNextData();
        while (hasMoreData()) {
            processData(data);
            data = getNextData();
        }
        closeSource();
        return table;
    }
}
