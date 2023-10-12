package ar.com.juanferrara.dao;

import java.util.List;

/**
 * Interfaz que sirve para que cada entidad tenga predefinido
 * una implementacion de DAO con sus metodos genericos de CRUD.
 * @param <E>
 * @param <K>
 */
public interface DAO <E, K>{

    E buscar(K key);
    void insertar(E entity);
    void modificar(E entity);
    void eliminar(K key);
    List<E> listarTodos();
}
