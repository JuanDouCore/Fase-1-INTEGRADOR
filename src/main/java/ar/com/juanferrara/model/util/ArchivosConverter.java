package ar.com.juanferrara.model.util;

import java.io.*;

/**
 * Clase de utilidad para manejar archivos
 */
public class ArchivosConverter {

    /**
     * Metodo que en base al InputStream que recibe en bytes lo mapea y escribe
     * sobre un File para convertirlo en el archivo con sus respectivos bytes
     * @param inputStream
     * @param file
     * @throws FileNotFoundException
     */
    public static void convertirInputStreamToFile(InputStream inputStream, File file) throws FileNotFoundException {
        try(FileOutputStream archivoBinario = new FileOutputStream(file)) {
            byte[] bytes = inputStream.readAllBytes();
            archivoBinario.write(bytes);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
