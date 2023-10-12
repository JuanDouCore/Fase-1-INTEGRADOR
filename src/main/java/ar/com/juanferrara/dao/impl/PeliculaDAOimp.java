package ar.com.juanferrara.dao.impl;

import ar.com.juanferrara.dao.DAO;
import ar.com.juanferrara.dao.MySQLImplement;
import ar.com.juanferrara.model.domain.Pelicula;
import ar.com.juanferrara.model.util.ArchivosConverter;

import java.io.File;
import java.io.FileInputStream;
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
                File imagen = new File(ArchivosConverter.RUTA_DE_ARCHIVOS + titulo + ".jpg" );
                List<String> generos = obtenerGenerosDePelicula(codigo);

                Blob imagenBlob = resultSet.getBlob("imagen");
                InputStream inputStream = imagenBlob.getBinaryStream();
                ArchivosConverter.convertirInputStreamToFile(inputStream, imagen);




                pelicula = new Pelicula(codigo, titulo, url, imagen, generos);

            }

            preparedStatement.close();
        } catch (SQLException | FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return pelicula;
    }

    /**
     * Inserta una pelicula
     * @param pelicula
     */
    @Override
    public void insertar(Pelicula pelicula) {
        Connection connection = getConnection();

        String sentenceSQL = "INSERT INTO peliculas (titulo, url, imagen) VALUES (?, ?, ?);";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sentenceSQL, Statement.RETURN_GENERATED_KEYS);

            FileInputStream fileInputStream = new FileInputStream(pelicula.getImagen());

            preparedStatement.setString(1, pelicula.getTitulo());
            preparedStatement.setString(2, pelicula.getUrl());
            preparedStatement.setBlob(3, fileInputStream);


            if(preparedStatement.executeUpdate() == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                if(resultSet.next())
                    insertarGenerosDePelicula(pelicula.getGeneros(), resultSet.getInt(1));

            }

            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Modifica una pelicula
     * @param pelicula
     */
    @Override
    public void modificar(Pelicula pelicula) {
        Connection connection = getConnection();

        String sentenceSQL = "UPDATE peliculas SET titulo = ?, url = ?, imagen = ? WHERE codigo = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sentenceSQL, Statement.RETURN_GENERATED_KEYS);

            FileInputStream fileInputStream = new FileInputStream(pelicula.getImagen());

            preparedStatement.setString(1, pelicula.getTitulo());
            preparedStatement.setString(2, pelicula.getUrl());
            preparedStatement.setBlob(3, fileInputStream);
            preparedStatement.setInt(4, pelicula.getCodigo());

            if(preparedStatement.executeUpdate() == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                if(resultSet.next())
                    modificarGenerosDePelicula(pelicula.getGeneros(), resultSet.getInt(1));


            }

            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Elimina una pelicula por el codigo
     * @param codigo
     */
    @Override
    public void eliminar(Integer codigo) {
        Connection connection = getConnection();

        String sentenceSQL = "DELETE FROM peliculas WHERE codigo = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sentenceSQL);
            preparedStatement.setInt(1, codigo);

            preparedStatement.execute();
            eliminarGenerosDePelicula(codigo);

            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Lista todas las peliculas
     * (No retorna la Imagen por cuestion de optimidad)
     * @return
     */
    @Override
    public List<Pelicula> listarTodos() {
        List<Pelicula> peliculas = new ArrayList<>();

        Connection connection = getConnection();

        String sentenceSQL = "SELECT codigo, titulo, url FROM peliculas;";
        try {
            Statement preparedStatement = connection.createStatement();

            ResultSet resultSet = preparedStatement.executeQuery(sentenceSQL);
            resultSet.isClosed();

            while (resultSet.next()) {
                int codigo = resultSet.getInt("codigo");
                String titulo = resultSet.getString("titulo");
                String url = resultSet.getString("url");

                List<String> generos = obtenerGenerosDePelicula(codigo);

                peliculas.add(new Pelicula(codigo, titulo, url, null, generos));

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return peliculas;
    }

    /**
     * Lista todas las peliculas filtradas por un titulo
     * (No retorna la Imagen por cuestion de optimidad)
     * @param tituloDeBusqueda
     * @return peliculas
     */
    public List<Pelicula> listarTodosPorTitulo(String tituloDeBusqueda) {
        List<Pelicula> peliculas = new ArrayList<>();

        Connection connection = getConnection();

        String sentenceSQL = "SELECT codigo, titulo, url FROM peliculas WHERE titulo LIKE ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sentenceSQL);
            preparedStatement.setString(1, "%"+ tituloDeBusqueda + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int codigo = resultSet.getInt("codigo");
                String titulo = resultSet.getString("titulo");
                String url = resultSet.getString("url");

                List<String> generos = obtenerGenerosDePelicula(codigo);

                peliculas.add(new Pelicula(codigo, titulo, url, null, generos));


            }


            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return peliculas;
    }

    /**
     * Lista todas las peliculas filtradas por su genero
     * (No retorna la imagen Por cuestion de optimidad)
     * @param genero
     * @return peliculas
     */
    public List<Pelicula> listarTodosPorGenero(String genero) {
        List<Pelicula> peliculas = new ArrayList<>();

        Connection connection = getConnection();

        String sentenceSQL = "SELECT p.codigo, p.titulo, p.url " +
                "FROM peliculas p " +
                "INNER JOIN generos_pelicula gp ON p.codigo = gp.codigo_pelicula " +
                "WHERE gp.genero = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sentenceSQL);
            preparedStatement.setString(1, genero);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int codigo = resultSet.getInt("codigo");
                String titulo = resultSet.getString("titulo");
                String url = resultSet.getString("url");

                List<String> generos = obtenerGenerosDePelicula(codigo);

                peliculas.add(new Pelicula(codigo, titulo, url, null, generos));


            }


            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return peliculas;
    }


    /**
     * Devuelve una lista de todos los generos de una Pelicula
     * @param codigo
     * @return
     */
    private List<String> obtenerGenerosDePelicula(int codigo) {
        Connection connection = getConnection();
        List<String> generos = new ArrayList<>();

        String generosSQL = "SELECT genero FROM generos_pelicula WHERE codigo_pelicula = ?;";

        try {
            PreparedStatement generosStatement = connection.prepareStatement(generosSQL);
            generosStatement.setInt(1, codigo);

            ResultSet generosResultSet = generosStatement.executeQuery();

            while (generosResultSet.next()) {
                String genero = generosResultSet.getString("genero");
                generos.add(genero);
            }

            generosStatement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return generos;
    }

    /**
     * Inserta en otra tabla los generos especificos que tiene una pelicula
     * @param generos
     * @param codigoPelicula
     */
    private void insertarGenerosDePelicula(List<String> generos, int codigoPelicula) {
        Connection connection = getConnection();
        String insertGenerosSQL = "INSERT INTO generos_pelicula (codigo_pelicula, genero) VALUES (?, ?);";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertGenerosSQL);

            for (String genero : generos) {

                preparedStatement.setInt(1, codigoPelicula);
                preparedStatement.setString(2, genero);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void modificarGenerosDePelicula(List<String> generos, int codigoPelicula) {
        Connection connection = getConnection();
        String deleteGenerosSQL = "DELETE FROM generos_pelicula WHERE codigo_pelicula = codigoPelicula;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteGenerosSQL);

            preparedStatement.execute();

            insertarGenerosDePelicula(generos, codigoPelicula);

            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Elimina todos los generos de una pelicula
     * @param codigoPelicula
     */
    private void eliminarGenerosDePelicula(int codigoPelicula) {
        Connection connection = getConnection();
        String deleteGenerosSQL = "DELETE FROM generos_pelicula WHERE codigo_pelicula = "+codigoPelicula+";";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteGenerosSQL);

            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
