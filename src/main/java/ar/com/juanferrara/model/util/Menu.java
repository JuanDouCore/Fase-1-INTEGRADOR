package ar.com.juanferrara.model.util;

import ar.com.juanferrara.model.domain.Pelicula;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Clase encargada de las operaciones de
 * menu de la consola
 */
public class Menu {

    private Scanner scanner;
    public Menu() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Metodo principal para inciar el menù
     */
    public void lanzarMenuPrincipal() {
        int opcion = 1;

        do {
            mostrarMenuPrincipal();
            System.out.println("");
            System.out.print("Ingrese opcion: ");
            scanner = new Scanner(System.in);

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
        scanner = new Scanner(System.in);
        PeliculaService peliculaService = new PeliculaService();

        println("\n\n\n");
        println("====================================");
        println("Listando peliculas...");
        println("CODIGO | TITULO | URL");
        peliculaService.listarTodasPeliculas().forEach(pelicula -> println(pelicula.getCodigo() + " | " + pelicula.getTitulo() + " | " + pelicula.getUrl()));
        println("====================================");
        System.out.println("Pulse ENTER para continuar..");
        scanner.nextLine();
    }

    /**
     * Metodo encargado de lanzar y operar el menu
     * de busqueda de peliculas
     */
    private void menuBuscarPelicula() {
        PeliculaService peliculaService = new PeliculaService();

        println("\n\n\n");
        println("====================================");
        println("¿Por que criterio desea buscar?");
        println("[1] - Por titulo");
        println("[2] - Por genero");
        println("====================================");

        String valor = "";
        boolean ingresoOpcionCorrecta = false;
        int opcion = -1;
        do {
            System.out.print("Ingrese una opcion: ");
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();

                if(opcion == 1) {
                    System.out.print("Ingrese el titulo: ");
                    valor = scanner.nextLine();

                    ingresoOpcionCorrecta = true;
                } else if(opcion == 2){
                    System.out.print("Ingrese el genero: ");
                    valor = scanner.next();

                    ingresoOpcionCorrecta = true;
                }
            } catch (InputMismatchException exception) {
                System.out.println("Opcion incorrecta....");
                scanner.nextLine();
            }
        } while (!ingresoOpcionCorrecta);

        if (opcion == 1) {
            println("====================================");
            println("Listado peliculas que coincidan con el titulo : " + valor);
            List<Pelicula> peliculas = peliculaService.buscarPorCriterio("TITULO",valor);
            if(!peliculas.isEmpty()) {
                peliculas.forEach(pelicula -> println(pelicula.getCodigo() + " | " + pelicula.getTitulo()));
            } else {
                println("No se encontraron peliculas con este titulo.");
            }
            println("====================================");
        } else if(opcion == 2) {
            println("====================================");
            println("Listado peliculas que coincidan con el genero : " + valor);
            List<Pelicula> peliculas = peliculaService.buscarPorCriterio("GENERO",valor);
            if(!peliculas.isEmpty()) {
                peliculas.forEach(pelicula -> println(pelicula.getCodigo() + " | " + pelicula.getTitulo()));
            } else {
                println("No se encontraron peliculas con este genero.");
            }
            println("====================================");
        }

        println("Desea ver la informacion de una pelicula?");

        String opcionDeseaBuscar = "no";
        boolean opcionCorrectaDeseaBuscar = false;
        do {
            System.out.print("Ingrese si/no: ");
            opcionDeseaBuscar = scanner.next();
            if(opcionDeseaBuscar.equals("si") || opcionDeseaBuscar.equals("no"))
                opcionCorrectaDeseaBuscar = true;
        } while (!opcionCorrectaDeseaBuscar);

        if(opcionDeseaBuscar.equals("si")) {
            boolean codigoCorrectoPeliculaBuscar = false;
            do {
                System.out.print("Ingrese codigo: ");
                scanner.nextLine();
                try {
                    int codigoPelicula = scanner.nextInt();

                    Pelicula pelicula = peliculaService.mostrarPelicula(codigoPelicula);
                    if(pelicula != null) {
                        println("\n");
                        println("CODIGO: " + pelicula.getCodigo());
                        println("TITULO: " + pelicula.getTitulo());
                        println("URL: " + pelicula.getUrl());
                        println("GENEROS: " + pelicula.getGenerosConFormato());
                        println("IMAGEN ALOJADA EN: " + pelicula.getImagen().getPath());
                        scanner.nextLine();
                        codigoCorrectoPeliculaBuscar = true;
                    } else {
                        println("No existe pelicula con este codigo:");
                        scanner.nextLine();
                    }
                } catch (InputMismatchException ignored) {}
            } while (!codigoCorrectoPeliculaBuscar);
        }

        println("\n Pulse ENTER para continuar...");
        scanner.nextLine();
    }

    /**
     * Metodo encargado de lanzar y operar el menu
     * para modificar una pelicula
     */
    private void menuModificarPelicula() {
        Scanner scanner = new Scanner(System.in);
        PeliculaService peliculaService = new PeliculaService();

        println("\n\n\n");
        println("====================================");
        println("Ingrese el codigo de la pelicula que desea modificar");

        int codigoPelicula = 0;
        boolean ingresoCodigoCorrecto = false;
        do {
            System.out.print("codigo: ");
            try {
                codigoPelicula = scanner.nextInt();
                ingresoCodigoCorrecto = true;
            } catch (InputMismatchException exception) {
                println("Ingrese un codigo correct...");
                scanner.nextLine();
            }
        } while (!ingresoCodigoCorrecto);

        Pelicula pelicula = peliculaService.mostrarPelicula(codigoPelicula);
        if(pelicula!=null) {
            boolean ingresoValoresCorrectamente = false;
            do {
                try {
                    println("\n");

                    scanner.nextLine();

                    println("Titulo actual: " + pelicula.getTitulo());
                    System.out.print("Ingrese titulo nuevo: ");
                    pelicula.setTitulo(scanner.nextLine());

                    println("URL actual: " + pelicula.getUrl());
                    System.out.print("Ingrese URL nueva: ");
                    pelicula.setUrl(scanner.next());

                    println("Generos actuales: " + pelicula.getGenerosConFormato());
                    System.out.print("¿Cuantos generos tiene ahora la pelicula? ");
                    List<String> generos = new ArrayList<>();
                    int cantGeneros = scanner.nextInt();
                    scanner.nextLine();
                    for(int i = 0; i < cantGeneros; i++) {
                        System.out.print("Ingrese genero #" + (i+1) + ": ");
                        generos.add(scanner.nextLine());
                        println("\n");
                    }
                    pelicula.setGeneros(generos);

                    println("Ahora coloque la imagen de la Pelicula en " + ArchivosConverter.RUTA_DE_ARCHIVOS);
                    println("Una vez que la coloco ahi, escriba el nombre del archivo con su extension ejemplo mi_imagen.jpg");
                    System.out.print("Nombre del archivo: ");
                    File imagen = new File(ArchivosConverter.RUTA_DE_ARCHIVOS + scanner.next());
                    if(!imagen.exists()) {
                        println("La imagen que ingresaste no existe...");
                    } else {
                        pelicula.setImagen(imagen);
                        ingresoValoresCorrectamente = true;
                    }
                } catch (InputMismatchException exception) {
                    println("Ingresaste valores incorrectamente... Reintentalo");
                }
            } while (!ingresoValoresCorrectamente);

            peliculaService.modificarPelicula(pelicula);
            println("Pelicula modificada correctamente...");
        } else {
            println("No existe pelicula con este codigo...");
        }

        scanner.nextLine();
        println("Pulse ENTER para continuar...");
        scanner.nextLine();
    }

    /**
     * Metodo encargado de lanzar y operar el menu
     * para eliminar una pelicula
     */
    private void menuEliminarPelicula() {
        Scanner scanner = new Scanner(System.in);
        PeliculaService peliculaService = new PeliculaService();

        println("\n\n\n");
        println("====================================");
        println("Ingrese el codigo de la pelicula que desea eliminar");

        int codigoPelicula = 0;
        boolean ingresoCodigoCorrecto = false;
        do {
            System.out.print("codigo: ");
            try {
                codigoPelicula = scanner.nextInt();
                ingresoCodigoCorrecto = true;
            } catch (InputMismatchException exception) {
                println("Ingresa un codigo correcto...");
                scanner.nextLine();
            }
        } while (!ingresoCodigoCorrecto);

        Pelicula pelicula = peliculaService.mostrarPelicula(codigoPelicula);
        if(pelicula!=null) {
            peliculaService.eliminarPelicula(pelicula);
            println("Eliminada correctamente...");
        } else {
            println("No existe pelicula con este codigo...");
        }

        scanner.nextLine();
        println("Pulse ENTER para continuar...");
        scanner.nextLine();
    }

    /**
     * Metodo encargado de lanzar y operar el menu
     * para crear peliculas
     */
    private void menuCrearPelicula() {
        Scanner scanner = new Scanner(System.in);
        PeliculaService peliculaService = new PeliculaService();

        println("\n\n\n");
        println("====================================");
        println("Creando nueva pelicula...");

        String titulo = "";
        String url = "";
        List<String> generos = new ArrayList<>();
        File imagen = null;

        boolean ingresoValoresCorrectamente = false;
        do {
            try {
                System.out.print("Ingrese el titulo: ");
                titulo = scanner.nextLine();

                System.out.print("Ingrese la URL: ");
                url = scanner.next();


                System.out.print("¿Cuantos generos tiene la pelicula? ");
                int cantGeneros = scanner.nextInt();
                scanner.nextLine();
                for(int i = 0; i < cantGeneros; i++) {
                    System.out.print("Ingrese genero #" + (i+1) + ": ");
                    generos.add(scanner.nextLine());
                }

                println("Ahora coloque la imagen de la Pelicula en " + ArchivosConverter.RUTA_DE_ARCHIVOS);
                println("Una vez que la coloco ahi, escriba el nombre del archivo con su extension ejemplo mi_imagen.jpg");
                System.out.print("Nombre del archivo: ");
                imagen = new File(ArchivosConverter.RUTA_DE_ARCHIVOS + scanner.next());
                if(!imagen.exists()) {
                    println("La imagen que ingresaste no existe...");
                    scanner.nextLine();
                } else {
                    ingresoValoresCorrectamente = true;
                }
            } catch (InputMismatchException exception) {
                println("Ingresaste un valor incorrecto, reintetalo...");
                scanner.nextLine();
            }
        } while (!ingresoValoresCorrectamente);

        Pelicula pelicula = new Pelicula(titulo, url, imagen, generos);
        peliculaService.crearPelicula(pelicula);

        scanner.nextLine();
        println("Pelicula creada con exito. Pulse ENTER para continuar...");
        scanner.nextLine();
    }


    /**
     * Metodo de utilidad pora ahorrar escribir todo un sysout
     * @param string
     */
    private void println(String string) {
        System.out.println(string);
    }
}
