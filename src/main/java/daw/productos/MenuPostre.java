package daw.productos;

// Importar las clases necesarias desde otros paquetes.
import daw.carrito.FuncionesCarrito;
import daw.modos.FuncionesUsuario;
import daw.tpv.FuncionesTPV;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Clase que representa el menú de postres en el TPV.
 *
 * @author acebedo
 */
public class MenuPostre {

    // Atributos necesarios para las funciones del menú de postres.
    private FuncionesTPV funcionesTPV;
    private FuncionesCarrito funcionesCarrito;
    private FuncionesUsuario funcionesUsuario;

    // Constructor que recibe la referencia a FuncionesTPV y crea una instancia de FuncionesCarrito.
    public MenuPostre(FuncionesTPV funcionesTPV) {
        this.funcionesTPV = funcionesTPV;
        this.funcionesCarrito = new FuncionesCarrito(this.funcionesUsuario);
    }

    // Listas para almacenar las diferentes categorías de postres.
    private static List<Producto> caseros = new ArrayList<>();

    // Método principal del menú de postres, inicializa los postres y muestra el menú de selección de postres.
    public void MenuPostre() {
        // Mostrar el menú principal de postres.
        menuSeleccionPostres();
    }

    // Método estático para inicializar la lista de postres.
    public static void llamarInicializarPostre() {
        // Inicializar los Postres.
        inicializarPostresDesdeCSV("Postres.csv");
    }

    // Método estático para inicializar la lista de Postres con datos.
    public static void inicializarPostresDesdeCSV(String archivoCSV) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            String linea;
            // Saltamos la primera línea ya que contiene los encabezados.
            br.readLine();
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                // Verificamos si hay suficientes datos en la línea.
                if (datos.length == 8) {
                    // Creamos un nuevo objeto Producto y lo agregamos a la lista de caseros.
                    Producto producto = new Producto(datos[0], datos[1], Double.parseDouble(datos[2]), Integer.parseInt(datos[3]), datos[4], datos[5], Double.parseDouble(datos[6]), datos[7]);
                    caseros.add(producto);
                } else {
                    System.err.println("Error: La línea no contiene la cantidad correcta de datos (Postres.csv).");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método principal que muestra el menú de selección para el usuario.
    public void menuSeleccionPostres() {
        // Crear la ventana del menú.
        JFrame frame = new JFrame("Tipos de Postres");

        // Crear un panel para colocar los botones.
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Crear botones para cada opción del menú.
        // Asociar ActionListener a cada botón.
        agregarBoton(panel, "Caseros", e -> {
            menuSeleccionCaseros();
            frame.dispose();
        });
        agregarBoton(panel, "Volver al menú de selección", e -> {
            funcionesTPV.iniciarModoUsuario();
            frame.dispose();
        });
        agregarBoton(panel, "Ver carrito", e -> {
            funcionesCarrito.mostrarMenuCarritoConPrecios();
        });

        // Configurar la ventana.
        frame.add(panel);
        frame.setSize(800, 200);
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

    // Método principal que muestra el menú de caseros al usuario.
    public void menuSeleccionCaseros() {
        // Crear la ventana del menú.
        JFrame frame = new JFrame("Tipos de Caseros");

        // Crear un panel para colocar los botones.
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Crear botones para cada opción del menú.
        // Asociar ActionListener a cada botón.
        for (Producto caseros : caseros) {
            if ("Casero".equals(caseros.getSubcategoria())) {
                agregarBoton(panel, caseros.getNombre(), e -> {
                    FuncionesCarrito.agregarProductoAlCarrito(caseros);
                });
            }
        }
        agregarBoton(panel, "Consultar precios", e -> {
            consultarPreciosCaseros();
            frame.dispose();
        });
        agregarBoton(panel, "Volver al menú de selección", e -> {
            funcionesTPV.iniciarModoUsuario();
            frame.dispose();
        });
        agregarBoton(panel, "Ver carrito", e -> funcionesCarrito.mostrarMenuCarritoConPrecios());

        // Configurar la ventana.
        frame.add(panel);
        frame.setSize(800, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Método para consultar precios de caseros.
    private void consultarPreciosCaseros() {
        // Obtener precios de caseros desde la lista.
        StringBuilder preciosCaseros = new StringBuilder("Precios de Caseros:\n");

        // Iterar sobre la lista de Casero y mostrar solo los productos de la SubCategoría "Casero".
        for (Producto producto : caseros) {
            if ("Casero".equals(producto.getSubcategoria())) {
                preciosCaseros.append(producto.getNombre()).append(": ").append(producto.getPrecio()).append(" €").append("\n");
            }
        }

        // Mostrar el mensaje utilizando JOptionPane.
        JOptionPane.showMessageDialog(null, preciosCaseros.toString());

        // Volver al menú de selección de caseros.
        menuSeleccionCaseros();
    }

    // Metodo para añadir nuevo producto a caseros.
    public void añadirProductoACaseros() {

        // Solicitar al usuario que ingrese el nombre del nuevo producto.
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del nuevo producto:");

        // Validar si ya existe un producto con el mismo nombre.
        if (nombreProductoExistente(nombre)) {
            // Mostrar mensaje de error y salir del método si ya existe un producto con el mismo nombre.
            JOptionPane.showMessageDialog(null, "Ya existe un producto con el nombre proporcionado.");
            return;
        }

        // Obtener un precio válido para el nuevo producto.
        double precio = obtenerPrecioValido();

        // Establecer que el nuevo producto está en stock por defecto.
        int enStock = obtenerStockValido();

        // Solicitar al usuario que ingrese la descripción del nuevo producto.
        String descripcion = JOptionPane.showInputDialog("Ingrese la descripción del nuevo producto:");

        // Establecer la categoría del nuevo producto como "Postre".
        String categoria = "Postre";

        // Obtener un porcentaje de IVA válido para el nuevo producto.
        double iva = obtenerIVAValido();

        // Obtener una subcategoría válida para el nuevo producto.
        String subcategoria = obtenerSubcategoriaValida();

        // Obtener un nuevo ID basado en la subcategoría del nuevo producto.
        String id = obtenerNuevoID(subcategoria);

        // Crear un nuevo objeto Producto con los datos ingresados y agregarlo a la lista de caseros.
        Producto nuevoProducto = new Producto(id, nombre, precio, enStock, descripcion, categoria, iva, subcategoria);
        caseros.add(nuevoProducto);

        // Mostrar mensaje de que se ha añadido correctamente.
        JOptionPane.showMessageDialog(null, "Producto añadido correctamente a la lista de caseros.");

    }

    // Método para verificar si ya existe un producto con el mismo nombre.
    private boolean nombreProductoExistente(String nombre) {
        // Iterar sobre la lista de bebidas para verificar si existe un producto con el mismo nombre.
        for (Producto producto : caseros) {
            // Comparar el nombre del producto actual con el nombre proporcionado (ignorando mayúsculas y minúsculas).
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                return true; // Ya existe un producto con el mismo nombre.
            }
        }
        return false; // No hay ningún producto con el mismo nombre.
    }

    // Método para obtener un precio válido (mayor o igual a 0).
    private double obtenerPrecioValido() {
        double precio;
        // Solicitar al usuario un precio válido.
        while (true) {
            try {
                // Solicitar al usuario que ingrese el precio del nuevo producto.
                precio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio del nuevo producto:"));

                // Validar que el precio sea mayor o igual a 0.
                if (precio < 0) {
                    throw new NumberFormatException();
                }

                // Validar que el número tenga como máximo dos decimales.
                BigDecimal bd = new BigDecimal(precio);
                bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                if (bd.doubleValue() != precio) {
                    throw new NumberFormatException();
                }

                // Salir del ciclo si el precio ingresado es válido.
                break;
            } catch (NumberFormatException e) {
                // Capturar excepción si se ingresa un valor no numérico o un precio negativo.
                JOptionPane.showMessageDialog(null, "Ingrese un precio válido igual o mayor que 0 o que tenga máximo dos decimales.");
            }
        }
        return precio; // Retornar el precio válido.
    }

    // Método para obtener un porcentaje de IVA válido (mayor o igual a 0)
    private double obtenerIVAValido() {
        double iva;
        // Ciclo para solicitar al usuario un porcentaje de IVA válido.
        while (true) {
            try {
                // Solicitar al usuario que ingrese el porcentaje de IVA del nuevo producto (ejemplo: '0.10').
                iva = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el porcentaje de IVA del nuevo producto (Ej: '0.10'):"));

                // Validar que el porcentaje de IVA sea mayor o igual a 0.
                if (iva < 0) {
                    throw new NumberFormatException();
                }

                // Salir del ciclo si el porcentaje de IVA ingresado es válido.
                break;
            } catch (NumberFormatException e) {
                // Capturar excepción si se ingresa un valor no numérico o un porcentaje de IVA negativo.
                JOptionPane.showMessageDialog(null, "Ingrese un porcentaje de IVA válido igual o mayor que 0.");
            }
        }
        return iva; // Retornar el porcentaje de IVA válido.
    }

    // Método para obtener una subcategoría válida ("Casero")
    private String obtenerSubcategoriaValida() {
        String subcategoria;
        // Ciclo para solicitar al usuario una subcategoría válida.
        while (true) {
            // Solicitar al usuario que ingrese la subcategoría del nuevo producto.
            subcategoria = JOptionPane.showInputDialog("Ingrese la subcategoría del nuevo producto (Casero):");

            // Convertir la subcategoría a minúsculas y luego a mayúsculas solo en la primera letra.
            subcategoria = subcategoria.toLowerCase();
            subcategoria = subcategoria.substring(0, 1).toUpperCase() + subcategoria.substring(1);

            // Verificar si la subcategoría ingresada es válida (Casero).
            if ("Casero".equals(subcategoria)) {
                // Salir del ciclo si la subcategoría ingresada es válida.
                break;
            } else {
                // Mostrar mensaje de error y solicitar nuevamente la subcategoría si no es válida.
                JOptionPane.showMessageDialog(null, "Ingrese una subcategoría válida (Casero).");
            }
        }
        return subcategoria; // Retornar la subcategoría válida.
    }

    // Método para obtener un ID basado en la subcategoría.
    private String obtenerNuevoID(String subcategoria) {
        String nuevoID;
        char subcategoriaLetra;

        // Determinar la primera letra en función de la subcategoria.
        switch (subcategoria.toLowerCase()) {
            case "casero":
                subcategoriaLetra = 'C';
                break;
            default:
                throw new IllegalArgumentException("Subcategoría no válida");
        }

        // Bucle para garantizar la obtención de un ID único.
        while (true) {
            nuevoID = JOptionPane.showInputDialog("Ingrese el nuevo ID (EJ: " + subcategoria + ": " + subcategoriaLetra + "01):");

            // Verificar el formato del ID ingresado.
            if (nuevoID.length() == 3
                    && nuevoID.charAt(0) == subcategoriaLetra
                    && Character.isDigit(nuevoID.charAt(1))
                    && Character.isDigit(nuevoID.charAt(2))) {

                // Verificar si el ID ya existe en la lista.
                boolean idExiste = false;
                for (Producto producto : caseros) {
                    if (producto.getId().equalsIgnoreCase(nuevoID)) {
                        idExiste = true;
                        break;
                    }
                }

                // Si el ID es único, salir del bucle. De lo contrario, mostrar un mensaje de que el ID ya existe.
                if (!idExiste) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "El ID ingresado ya existe.");
                }
            } else {
                // Mostrar un mensaje de error si el formato del ID es incorrecto.
                JOptionPane.showMessageDialog(null, "El formato del ID es incorrecto. Debe ser " + subcategoriaLetra + " seguido por dos dígitos (EJ: " + subcategoriaLetra + "01).");
            }
        }
        return nuevoID; // Devolver el ID único obtenido.
    }

    // Metodo para borrar producto de caseros.
    public void borrarProducto(String nombreProducto) {
        // Iterar sobre la lista de caseros para encontrar el producto con el Nombre.
        for (Producto producto : caseros) {
            // Comparar el nombre del producto actual con el nombre proporcionado (sin distinguir mayúsculas y minúsculas).
            if (producto.getNombre().equalsIgnoreCase(nombreProducto)) {
                // Eliminar el producto de la lista.
                caseros.remove(producto);

                JOptionPane.showMessageDialog(null, "Producto eliminado correctamente.");
                return; // Salir del método después de eliminar el producto.
            }
        }

        // Si el producto con el ID proporcionado no se encuentra, mostrar un mensaje de error.
        JOptionPane.showMessageDialog(null, "No se encontró ningún producto con el nombre proporcionado.");
    }

    // Método para editar producto de caseros.
    public void editarProducto(String nombreProducto) {
        // Buscar el producto en la lista basándose en el nombre proporcionado.
        for (Producto producto : caseros) {
            if (producto.getNombre().equalsIgnoreCase(nombreProducto)) {
                // Obtener el nombre actual del producto antes de realizar la edición.
                String nombreActual = producto.getNombre();

                // Solicitar al usuario que elija qué atributo desea editar.
                String[] opciones = {"Nombre", "Precio", "En Stock", "Descripción", "IVA", "Subcategoría"};
                String eleccion = (String) JOptionPane.showInputDialog(
                        null,
                        "Selecciona el atributo que deseas editar:",
                        "Editar Producto",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opciones,
                        opciones[0]
                );

                // Actualizar el atributo seleccionado según la elección del usuario.
                if (eleccion != null) {
                    switch (eleccion) {
                        case "Nombre":
                            String nuevoNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre del producto:");

                            // Validar si ya existe un producto con el mismo nombre.
                            if (nombreProductoExistente(nuevoNombre)) {
                                JOptionPane.showMessageDialog(null, "Ya existe un producto con el nombre proporcionado.");
                                return;
                            }

                            producto.setNombre(nuevoNombre);
                            break;
                        case "Precio":
                            producto.setPrecio(obtenerPrecioValido());
                            break;
                        case "En Stock":
                            producto.setEnStock(obtenerStockValido());
                            break;
                        case "Descripción":
                            producto.setDescripcion(JOptionPane.showInputDialog("Ingrese la nueva descripción del producto:"));
                            break;
                        case "IVA":
                            producto.setIva(obtenerIVAValido());
                            break;
                        case "Subcategoría":
                            producto.setSubcategoria(obtenerSubcategoriaValida());
                            break;
                    }

                    JOptionPane.showMessageDialog(null, "Producto editado correctamente.");
                }

                return;
            }
        }

        // Si no se encuentra el producto con el nombre proporcionado:
        JOptionPane.showMessageDialog(null, "No se encontró ningún producto con el nombre proporcionado.");
    }

    // Método para obtener la cantidad de stock válida para el producto.
    private int obtenerStockValido() {
        int stock = 0;
        boolean entradaValida = false;

        // Solicitar al usuario la cantidad de stock hasta que se ingrese un valor válido.
        while (!entradaValida) {
            try {
                // Solicitar al usuario que ingrese la cantidad de stock del producto.
                String entrada = JOptionPane.showInputDialog("Ingrese la cantidad de stock del producto:");
                if (entrada == null) {
                    // Si el usuario cancela, salir del método.
                    return 0;
                }

                // Convertir la entrada a entero.
                stock = Integer.parseInt(entrada);

                // Verificar que la cantidad de stock sea un valor positivo.
                if (stock >= 0) {
                    entradaValida = true;
                } else {
                    // Mostrar un mensaje de error si la cantidad de stock es negativa.
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un valor positivo para la cantidad de stock.");
                }
            } catch (NumberFormatException e) {
                // Mostrar un mensaje de error si la entrada no es un número válido.
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un valor numérico para la cantidad de stock.");
            }
        }

        return stock;
    }

}
