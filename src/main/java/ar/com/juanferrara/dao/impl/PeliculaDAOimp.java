package ar.com.juanferrara.dao.impl;

import ar.com.juanferrara.dao.DAO;
import ar.com.juanferrara.dao.MySQLImplement;
import ar.com.juanferrara.model.domain.Pelicula;

import java.util.List;

public class PeliculaDAOimp implements DAO<Pelicula, Integer>, MySQLImplement {
    @Override
    public Pelicula buscar(Integer key) {
        return null;
    }

    @Override
    public void insertar(Pelicula entity) {

    }

    @Override
    public void modificar(Pelicula entity) {

    }

    @Override
    public void eliminar(Integer key) {

    }

    @Override
    public List<Pelicula> listarTodos() {
        return null;
    }

    public List<Pelicula> listarTodosPorTitulo(String titulo) {
        return null;
    }

    public List<Pelicula> listarTodosPorGenero(String genero) {
        return null;
    }
}
