//package es.sanchoo.capitulojusto.auxiliares.lectura.csv;
//
//import android.content.Context;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import es.sanchoo.capitulojusto.auxiliares.lectura.TableForPlayer;
//
//public class CSVForPlayerFileReader extends CSVUnlabeledFileReader {
//    private TableForPlayer tableForPlayer;
//
//    public CSVForPlayerFileReader(Context context) {
//        super(context);  // Llama al constructor de la clase padre
//    }
//
//    @Override
//    public void processHeaders(String headers) {
//        tableForPlayer = new TableForPlayer(dividir(headers));
//        table = tableForPlayer;  // Asignar tableForPlayer a la variable table de la clase padre
//    }
//
//    @Override
//    public void processData(String data) {
//        List<Integer> numeros = new ArrayList<>();
//        List<String> filaDividida = dividir(data);
//        for (String dato : filaDividida) {
//            if (!dato.equals(lastOf(filaDividida))) {
//                numeros.add(Integer.parseInt(dato));
//            } else {
//                String nombre = dato;
//                tableForPlayer.addRow(numeros, nombre);  // Llamada específica a TableForPlayer
//            }
//        }
//    }
//
//    protected String lastOf(List<String> list) {
//        return list.get(list.size() - 1);
//    }
//}
