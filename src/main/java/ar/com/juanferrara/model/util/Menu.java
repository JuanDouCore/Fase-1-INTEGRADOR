package ar.com.juanferrara.model.util;

import java.util.Scanner;

/**
 * Clase encargada de las operaciones de
 * menu de la consola
 */
public class Menu {

    /**
     * Metodo principal para inciar el menù
     */
    public void lanzarMenuPrincipal() {
        mostrarMenuPrincipal();
        int opcion = 1;

        do {
            System.out.println("");
            System.out.print("Ingrese opcion: ");
            Scanner scanner = new Scanner(System.in);

            boolean ingresoNumero = false;
            try {
                opcion = scanner.nextInt();
                ingresoNumero = true;
            } catch (NumberFormatException exception) {
                println("POR FAVOR INGRESE NÙMERO VALIDO");
            }

            if(ingresoNumero) {
                switch (opcion) {
                    case 1:
                        menuCrearPelicula();
                        break;
                    case 2:
                        menuEliminarPelicula();
                        break;
                    case 3:
                        menuModificarPelicula();
                        break;
                    case 4:
                        menuBuscarPelicula();
                        break;
                    case 5:
                        menuListarPeliculas();
                        break;
                    case 0:
                        System.out.println("FIN DEL PROGRAMA...");
                        scanner.next();
                }
            }
        } while (opcion != 0);
    }

    /**
     * Metodo encargado de mostrar las opciones
     * del menu principal
     */
    private void mostrarMenuPrincipal() {
        println("====================================");
        println("[1] - Crear una pelicula");
        println("[2] - Eliminar una pelicula");
        println("[3] - Modificar una pelicula");
        println("[4] - Buscar una pelicula");
        println("[5] - Listar peliculas");
        println("====================================");
    }

    /**
     * Metodo encargado de lanzar y operar el menu
     * de listar peliculas
     */
    private void menuListarPeliculas() {
        Scanner scanner = new Scanner(System.in);
        PeliculaService peliculaService = new PeliculaService();

        println("\n\n\n");
        println("====================================");
        println("Listando peliculas...");
        println("CODIGO | TITULO | URL");
        peliculaService.listarTodasPeliculas().forEach(pelicula -> println(pelicula.getCodigo() + " | " + pelicula.getTitulo() + " | " + pelicula.getUrl()));
        println("====================================");
        System.out.println("Pulse una letra para continuar..");
        scanner.next();
        scanner.close();
    }

    /**
     * Metodo encargado de lanzar y operar el menu
     * de busqueda de peliculas
     */
    private void menuBuscarPelicula() {
    }

    /**
     * Metodo encargado de lanzar y operar el menu
     * para modificar una pelicula
     */
    private void menuModificarPelicula() {
    }

    /**
     * Metodo encargado de lanzar y operar el menu
     * para eliminar una pelicula
     */
    private void menuEliminarPelicula() {
    }

    /**
     * Metodo encargado de lanzar y operar el menu
     * para crear peliculas
     */
    private void menuCrearPelicula() {
    }


    /**
     * Metodo de utilidad pora ahorrar escribir todo un sysout
     * @param string
     */
    private void println(String string) {
        System.out.println(string);
    }
}
