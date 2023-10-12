package ar.com.juanferrara.model.util;

import ar.com.juanferrara.dao.impl.PeliculaDAOimp;
import ar.com.juanferrara.model.domain.Pelicula;

import java.util.List;

public class PeliculaService {

    private PeliculaDAOimp peliculaDAOimp;

    public void crearPelicula(Pelicula pelicula) {
        peliculaDAOimp.insertar(pelicula);
    }

    public void modificarPelicula(Pelicula pelicula) {
        peliculaDAOimp.modificar(pelicula);
    }

    public void eliminarPelicula(Pelicula pelicula) {
        peliculaDAOimp.eliminar(pelicula.getCodigo());
    }

    public Pelicula mostrarPelicula(int codigo) {
        return peliculaDAOimp.buscar(codigo);
    }

    public List<Pelicula> listarTodasPeliculas() {
        PeliculaDAOimp dao = new PeliculaDAOimp();
        return dao.listarTodos();
    }

    public List<Pelicula> buscarPorCriterio(String criterio, String valor) {
        if(criterio.equals("TITULO"))
            return peliculaDAOimp.listarTodosPorTitulo(valor);
        else if(criterio.equals("GENERO"))
            return peliculaDAOimp.listarTodosPorGenero(valor);

        return null;
    }

}
