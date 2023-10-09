package ar.com.juanferrara.dao;

import java.util.List;

public interface DAO <E, K>{

    E buscar(K key);
    void insertar(E entity);
    void modificar(E entity);
    void eliminar(K key);
    List<E> listarTodos();
}
