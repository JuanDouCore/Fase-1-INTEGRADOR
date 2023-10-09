package ar.com.juanferrara.dao.impl;

import ar.com.juanferrara.dao.DAO;
import ar.com.juanferrara.dao.MySQLImplement;
import ar.com.juanferrara.model.domain.Pelicula;

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
        return null;
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
