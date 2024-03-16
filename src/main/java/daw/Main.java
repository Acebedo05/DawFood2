package daw;

// Importar las clases necesarias desde otros paquetes.
import daw.tpv.FuncionesTPV;
import daw.tpv.ObjetosTPV;
import daw.productos.MenuComida;
import daw.productos.MenuBebida;
import daw.productos.MenuPostre;

/**
 * Clase principal del programa.
 * @author acebedo
 */
public class Main {

    public static void main(String[] args) {

        // Inicializar el TPV desde la clase ObjetosTPV.
        FuncionesTPV funcionesTPV = ObjetosTPV.inicializarTPV();

        // Iniciar el TPV.
        funcionesTPV.encenderTPV();

        // Inicializar el menú de comida.
        MenuComida menuComida = null;
        menuComida.llamarInicializarComidas();

        // Inicializar el menú de bebida.
        MenuBebida menuBebida = null;
        menuBebida.llamarInicializarBebidas();

        // Inicializar el menú de postre.
        MenuPostre menuPostre = null;
        menuPostre.llamarInicializarPostre();

    }
}
