package daw.modos;

// Importar las clases necesarias desde otros paquetes.
import daw.productos.MenuComida;
import daw.carrito.FuncionesCarrito;
import daw.tpv.AtributosTPV;
import daw.tpv.FuncionesTPV;
import daw.tpv.ObjetosTPV;
import daw.productos.MenuBebida;
import daw.productos.MenuPostre;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * Clase que define las funciones del administrador del TPV.
 * @author khalid
 */
public class FuncionesAdministrador {

    // Atributos necesarios para las funciones del administrador.
    private AtributosTPV atributosTPV;
    private FuncionesCarrito funcionesCarrito;
    private MenuComida menuComida;
    private MenuBebida menuBebida;
    private MenuPostre menuPostre;
    private FuncionesTPV funcionesTPV;

    // Constructor que inicializa los atributos y los menús.
    public FuncionesAdministrador(AtributosTPV atributosTPV) {
        this.atributosTPV = atributosTPV;
        this.menuComida = new MenuComida(this.funcionesTPV);
        this.menuBebida = new MenuBebida(this.funcionesTPV);
        this.menuPostre = new MenuPostre(this.funcionesTPV);
    }

    // Método para mostrar un menú con las opciones que tiene el administrador.
    public void mostrarMenu() {
        // Crear la ventana del menú.
        JFrame frame = new JFrame("Menú Administrador");

        // Crear un panel para colocar los botones.
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Crear botones para cada opción del menú.
        // Asociar ActionListener a cada botón.
        agregarBoton(panel, "Ver Hora TPV", e -> verHoraTPV());
        agregarBoton(panel, "Consultar Ventas", e -> consultarVentas());
        agregarBoton(panel, "Borrar Productos", e -> borrarProductos());
        agregarBoton(panel, "Añadir Productos", e -> añadirProductos());
        agregarBoton(panel, "Cambiar Datos Productos", e -> cambiarDatosProductos());
        agregarBoton(panel, "Volver al Menú Inicial", e -> volverAlMenuInicial(frame));
        agregarBoton(panel, "Apagar TPV", e -> apagarTPV());

        // Configurar la ventana.
        frame.add(panel);
        frame.setSize(400, 165);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Método para agregar un botón al panel
    private void agregarBoton(JPanel panel, String texto, ActionListener actionListener) {
        // Crear botones para cada opción del menú.
        // Asociar ActionListener a cada botón.
        JButton boton = new JButton(texto);
        boton.addActionListener(actionListener);
        // Agregar botones al panel.
        panel.add(boton);
    }

    // Método para volver al Menú Inicial
    private void volverAlMenuInicial(JFrame frame) {
        // Cerrar la ventana actual.
        frame.dispose();
        // Llamando al método encenderTPV de la clase FuncionesTPV
        FuncionesTPV funcionesTPV = ObjetosTPV.inicializarTPV();
        funcionesTPV.encenderTPV();
    }

    // Método para detener la ejecución del programa.
    private void apagarTPV() {
        System.out.println("Apagando TPV...");
        System.exit(0);
    }

    // Método para ver la hora actual del TPV.
    public void verHoraTPV() {
        Date fechaHoraActual = atributosTPV.getFechaHoraActual();
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        String horaActual = formatoHora.format(fechaHoraActual);

        JOptionPane.showMessageDialog(null, "La hora actual del TPV es: " + horaActual);
    }

    // Método para consultar las ventas realizadas.
    public static void consultarVentas() {
        FuncionesCarrito.consultarVentas();
    }

    // Método para añadir nuevos productos.
    public void añadirProductos() {
        // Preguntar al usuario qué tipo de producto desea añadir.
        String tipoProducto = JOptionPane.showInputDialog("¿Qué tipo de producto desea añadir? (Bebida, Comida o Postre)").toLowerCase();

        // Llamar al método correspondiente según la elección del usuario.
        switch (tipoProducto) {
            case "bebida":
                menuBebida.añadirProductoABebidas();
                break;
            case "comida":
                menuComida.añadirProductoAComidas();
                break;
            case "postre":
                menuPostre.añadirProductoACaseros();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Tipo de producto no válido.");
                mostrarMenu();

                break;
        }
    }

    // Método para borrar productos existentes.
    public void borrarProductos() {
        // Preguntar al usuario qué tipo de producto desea borrar.
        String tipoProducto = JOptionPane.showInputDialog("¿Qué tipo de producto desea borrar? (Bebida, Comida o Postre)").toLowerCase();

        // Llamar al método correspondiente según la elección del usuario.
        switch (tipoProducto) {
            case "bebida":
                String nombreProductoAEliminarBebida = JOptionPane.showInputDialog("Ingrese el nombre del producto que desea eliminar:");
                menuBebida.borrarProducto(nombreProductoAEliminarBebida);
                break;
            case "comida":
                String nombreProductoAEliminarComida = JOptionPane.showInputDialog("Ingrese el nombre del producto que desea eliminar:");
                menuComida.borrarProducto(nombreProductoAEliminarComida);
                break;
            case "postre":
                String nombreProductoAEliminarPostre = JOptionPane.showInputDialog("Ingrese el nombre del producto que desea eliminar:");
                menuPostre.borrarProducto(nombreProductoAEliminarPostre);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Tipo de producto no válido.");
                mostrarMenu();
                break;
        }

    }

    // Método para cambaiar datos de los productos.
    public void cambiarDatosProductos() {
        // Preguntar al usuario qué tipo de producto desea editar.
        String tipoProducto = JOptionPane.showInputDialog("¿Qué tipo de producto desea editar? (Bebida, Comida o Postre)").toLowerCase();

        // Llamar al método correspondiente según la elección del usuario.
        switch (tipoProducto) {
            case "bebida":
                String nombreProductoAEditarBebida = JOptionPane.showInputDialog("Ingrese el nombre del producto que desea editar:");
                menuBebida.editarProducto(nombreProductoAEditarBebida);
                break;
            case "comida":
                String nombreProductoAEditarComida = JOptionPane.showInputDialog("Ingrese el nombre del producto que desea editar:");
                menuComida.editarProducto(nombreProductoAEditarComida);
                break;
            case "postre":
                String nombreProductoAEditarPostre = JOptionPane.showInputDialog("Ingrese el nombre del producto que desea editar:");
                menuPostre.editarProducto(nombreProductoAEditarPostre);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Tipo de producto no válido.");
                mostrarMenu();

                break;
        }
    }

}
