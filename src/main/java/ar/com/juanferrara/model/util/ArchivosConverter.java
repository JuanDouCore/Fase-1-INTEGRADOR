package ar.com.juanferrara.model.util;

import java.io.*;

public class ArchivosConverter {

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
