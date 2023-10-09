package ar.com.juanferrara.model.util;

import java.util.Scanner;

public class Menu {

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
    private void mostrarMenuPrincipal() {

    }
    private void menuListarPeliculas() {
    }

    private void menuBuscarPelicula() {
    }

    private void menuModificarPelicula() {
    }

    private void menuEliminarPelicula() {
    }

    private void menuCrearPelicula() {
    }



    private void println(String string) {
        System.out.println(string);
    }
}
