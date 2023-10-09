package ar.com.juanferrara.dao.impl;

import ar.com.juanferrara.dao.DAO;
import ar.com.juanferrara.dao.MySQLImplement;
import ar.com.juanferrara.model.domain.Pelicula;
import ar.com.juanferrara.model.util.ArchivosConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que cumple la implementacion como DAO para
 * la entidad Pelicula
 */
public class PeliculaDAOimp implements DAO<Pelicula, Integer>, MySQLImplement {

    /**
     * Busca una pelicula por el codigo especificado
     * y la retorna
     * @param key
     * @return pelicula
     */
    @Override
    public Pelicula buscar(Integer key) {
        Pelicula pelicula = null;

        Connection connection = getConnection();

        String sentenceSQL = "SELECT * FROM peliculas WHERE codigo = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sentenceSQL);
            preparedStatement.setInt(1, key);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int codigo = resultSet.getInt("codigo");
                String titulo = resultSet.getString("titulo");
                String url = resultSet.getString("url");
                File imagen = new File("D:" + File.separator + "Archivos" + File.separator + titulo + ".jpg" );

                Blob imagenBlob = resultSet.getBlob("imagen");
                InputStream inputStream = imagenBlob.getBinaryStream();
                ArchivosConverter.convertirInputStreamToFile(inputStream, imagen);


                String generosSQL = "SELECT genero FROM generos_pelicula WHERE codigo_pelicula = ?;";
                PreparedStatement generosStatement = connection.prepareStatement(generosSQL);
                generosStatement.setInt(1, codigo);

                ResultSet generosResultSet = generosStatement.executeQuery();

                List<String> generos = new ArrayList<>();

                while (generosResultSet.next()) {
                    String genero = generosResultSet.getString("genero");
                    generos.add(genero);
                }
                generosResultSet.close();
                generosStatement.close();

                pelicula = new Pelicula(codigo, titulo, url, imagen, generos);

                resultSet.close();
                preparedStatement.close();
            }

            preparedStatement.close();
        } catch (SQLException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return pelicula;
    }

    /**
     * Inserta una pelicula
     * @param entity
     */
    @Override
    public void insertar(Pelicula entity) {

    }

    /**
     * Modifica una pelicula
     * @param entity
     */
    @Override
    public void modificar(Pelicula entity) {

    }

    /**
     * Elimina una pelicula por el codigo
     * @param key
     */
    @Override
    public void eliminar(Integer key) {

    }

    /**
     * Lista todas las peliculas
     * @return
     */
    @Override
    public List<Pelicula> listarTodos() {
        return null;
    }

    /**
     * Lista todas las peliculas filtradas por un titulo
     * @param titulo
     * @return peliculas
     */
    public List<Pelicula> listarTodosPorTitulo(String titulo) {
        return null;
    }

    /**
     * Lista todas las peliculas filtradas por su genero
     * @param genero
     * @return peliculas
     */
    public List<Pelicula> listarTodosPorGenero(String genero) {
        return null;
    }
}
