package daw.productos;

// Importar las clases necesarias desde otros paquetes.
import daw.carrito.FuncionesCarrito;
import daw.modos.FuncionesUsuario;
import daw.tpv.FuncionesTPV;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Clase que representa el menú de bebidas en el TPV.
 *
 * @author acebedo
 */
public class MenuBebida {

    // Atributos necesarios para las funciones del menú de bebidas.
    private FuncionesTPV funcionesTPV;
    private FuncionesCarrito funcionesCarrito;
    private FuncionesUsuario funcionesUsuario;

    // Constructor que recibe la referencia a FuncionesTPV y crea una instancia de FuncionesCarrito.
    public MenuBebida(FuncionesTPV funcionesTPV) {
        this.funcionesTPV = funcionesTPV;
        this.funcionesCarrito = new FuncionesCarrito(this.funcionesUsuario);
    }

    // Listas para almacenar las diferentes categorías de bebidas.
    private static List<Producto> bebidas = new ArrayList<>();

    // Método principal del menú de bebidas, inicializa las bebidas y muestra el menú de selección de bebidas.
    public void MenuBebida() {
        // Mostrar el menú principal de bebidas.
        menuSeleccionBebidas();
    }

    // Método estático para inicializar la lista de bebidas.
    public static void llamarInicializarBebidas() {
        // Inicializar las listas.
        inicializarBebidas();
    }

    // Método estático para inicializar la lista de bebidas con datos.
    private static void inicializarBebidas() {
        bebidas.add(new Producto("A01", "Vino", 5.00, true, "Vino", "Bebida", 0.21, "Alcoholica"));
        bebidas.add(new Producto("A02", "Sidra", 5.00, true, "Sidra", "Bebida", 0.21, "Alcoholica"));
        bebidas.add(new Producto("A03", "Cerveza", 2.00, true, "Cerveza", "Bebida", 0.21, "Alcoholica"));
        bebidas.add(new Producto("R01", "CocaCola", 2.00, true, "CocaCola", "Bebida", 0.21, "Refresco"));
        bebidas.add(new Producto("R02", "Fanta", 2.00, true, "Fanta", "Bebida", 0.21, "Refresco"));
        bebidas.add(new Producto("R03", "Aquarius", 2.00, true, "Aquarius", "Bebida", 0.21, "Refresco"));
        bebidas.add(new Producto("W01", "Agua Natural", 1.00, true, "Agua Natural", "Bebida", 0.10, "Agua"));
        bebidas.add(new Producto("W02", "Agua con Gas", 1.00, true, "Agua con Gas", "Bebida", 0.10, "Agua"));
        bebidas.add(new Producto("W03", "Agua Fria", 1.00, true, "Agua Fría", "Bebida", 0.10, "Agua"));
    }

    // Método principal que muestra el menú de selección para el usuario.
    public void menuSeleccionBebidas() {
        // Crear la ventana del menú.
        JFrame frame = new JFrame("Tipos de Bebidas");

        // Crear un panel para colocar los botones.
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Crear botones para cada opción del menú.
        // Asociar ActionListener a cada botón.
        agregarBoton(panel, "Alcoholicas", e -> {
            menuSeleccionAlcoholicas();
            frame.dispose();
        });
        agregarBoton(panel, "Refrescos", e -> {
            menuSeleccionRefrescos();
            frame.dispose();
        });
        agregarBoton(panel, "Aguas", e -> {
            menuSeleccionAguas();
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
        frame.setSize(400, 160);
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

    // Método principal que muestra el menú de vinos al usuario.
    public void menuSeleccionAlcoholicas() {
        // Crear la ventana del menú.
        JFrame frame = new JFrame("Tipos de Alcoholicas");

        // Crear un panel para colocar los botones.
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Crear botones para cada opción del menú.
        // Asociar ActionListener a cada botón.
        for (Producto alcoholica : bebidas) {
            if ("Alcoholica".equals(alcoholica.getSubcategoria())) {
                agregarBoton(panel, alcoholica.getNombre(), e -> {
                    FuncionesCarrito.agregarProductoAlCarrito(alcoholica);
                });
            }
        }
        agregarBoton(panel, "Consultar precios", e -> {
            consultarPreciosAlcoholicas();
            frame.dispose();
        });
        agregarBoton(panel, "Volver al menú de selección", e -> {
            funcionesTPV.iniciarModoUsuario();
            frame.dispose();
        });
        agregarBoton(panel, "Ver carrito", e -> funcionesCarrito.mostrarMenuCarritoConPrecios());

        // Configurar la ventana.
        frame.add(panel);
        frame.setSize(400, 160);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Método principal que muestra el menú de selección de refrescos al usuario.
    public void menuSeleccionRefrescos() {
        // Crear la ventana del menú.
        JFrame frame = new JFrame("Tipos de Refrescos");

        // Crear un panel para colocar los botones.
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Crear botones para cada opción del menú.
        // Asociar ActionListener a cada botón.
        for (Producto refresco : bebidas) {
            if ("Refresco".equals(refresco.getSubcategoria())) {
                agregarBoton(panel, refresco.getNombre(), e -> {
                    FuncionesCarrito.agregarProductoAlCarrito(refresco);
                });
            }
        }
        agregarBoton(panel, "Consultar precios", e -> {
            consultarPreciosRefrescos();
            frame.dispose();
        });
        agregarBoton(panel, "Volver al menú de selección", e -> {
            funcionesTPV.iniciarModoUsuario();
            frame.dispose();
        });
        agregarBoton(panel, "Ver carrito", e -> funcionesCarrito.mostrarMenuCarritoConPrecios());

        // Configurar la ventana.
        frame.add(panel);
        frame.setSize(400, 160);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Método principal que muestra el menú de selección de aguas al usuario.
    public void menuSeleccionAguas() {
        // Crear la ventana del menú.
        JFrame frame = new JFrame("Tipos de Agua");

        // Crear un panel para colocar los botones.
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Crear botones para cada opción del menú.
        // Asociar ActionListener a cada botón.
        for (Producto agua : bebidas) {
            if ("Agua".equals(agua.getSubcategoria())) {
                agregarBoton(panel, agua.getNombre(), e -> {
                    FuncionesCarrito.agregarProductoAlCarrito(agua);
                });
            }
        }
        agregarBoton(panel, "Consultar precios", e -> {
            consultarPreciosAguas();
            frame.dispose();
        });
        agregarBoton(panel, "Volver al menú de selección", e -> {
            funcionesTPV.iniciarModoUsuario();
            frame.dispose();
        });
        agregarBoton(panel, "Ver carrito", e -> funcionesCarrito.mostrarMenuCarritoConPrecios());

        // Configurar la ventana.
        frame.add(panel);
        frame.setSize(400, 160);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Método para consultar precios de alcoholicas.
    private void consultarPreciosAlcoholicas() {
        // Obtener precios de alcoholicas desde la lista.
        StringBuilder preciosAlcoholicas = new StringBuilder("Precios de Alcoholicas:\n");

        // Iterar sobre la lista de Alcoholica y mostrar solo los productos de la SubCategoría "Alcoholica".
        for (Producto producto : bebidas) {
            if ("Alcoholica".equals(producto.getSubcategoria())) {
                preciosAlcoholicas.append(producto.getNombre()).append(": ").append(producto.getPrecio()).append(" €").append("\n");
            }
        }

        // Mostrar el mensaje utilizando JOptionPane.
        JOptionPane.showMessageDialog(null, preciosAlcoholicas.toString());

        // Volver al menú de selección de alcoholicas.
        menuSeleccionAlcoholicas();
    }

    // Método para consultar precios de refrescos.
    private void consultarPreciosRefrescos() {
        // Obtener precios de refrescos desde la lista
        StringBuilder preciosRefrescos = new StringBuilder("Precios de Refrescos:\n");

        // Iterar sobre la lista de Refresco y mostrar solo los productos de la SubCategoría "Refresco".
        for (Producto producto : bebidas) {
            if ("Refresco".equals(producto.getSubcategoria())) {
                preciosRefrescos.append(producto.getNombre()).append(": ").append(producto.getPrecio()).append(" €").append("\n");
            }
        }

        // Mostrar el mensaje utilizando JOptionPane.
        JOptionPane.showMessageDialog(null, preciosRefrescos.toString());

        // Volver al menú de selección de refrescos.
        menuSeleccionRefrescos();
    }

    // Método para consultar precios de aguas.
    private void consultarPreciosAguas() {
        // Obtener precios de aguas desde la lista
        StringBuilder preciosAguas = new StringBuilder("Precios de Aguas:\n");

        // Iterar sobre la lista de Agua y mostrar solo los productos de la SubCategoría "Agua".
        for (Producto producto : bebidas) {
            if ("Agua".equals(producto.getSubcategoria())) {
                preciosAguas.append(producto.getNombre()).append(": ").append(producto.getPrecio()).append(" €").append("\n");
            }
        }

        // Mostrar el mensaje utilizando JOptionPane.
        JOptionPane.showMessageDialog(null, preciosAguas.toString());

        // Volver al menú de selección de aguas.
        menuSeleccionAguas();
    }

    // Metodo para añadir nuevo producto a bebidas.
    public void añadirProductoABebidas() {

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
        boolean enStock = true;

        // Solicitar al usuario que ingrese la descripción del nuevo producto.
        String descripcion = JOptionPane.showInputDialog("Ingrese la descripción del nuevo producto:");

        // Establecer la categoría del nuevo producto como "Bebida".
        String categoria = "Bebida";

        // Obtener un porcentaje de IVA válido para el nuevo producto.
        double iva = obtenerIVAValido();

        // Obtener una subcategoría válida para el nuevo producto.
        String subcategoria = obtenerSubcategoriaValida();

        // Obtener un nuevo ID basado en la subcategoría del nuevo producto.
        String id = obtenerNuevoID(subcategoria);

        // Crear un nuevo objeto Producto con los datos ingresados y agregarlo a la lista de bebidas.
        Producto nuevoProducto = new Producto(id, nombre, precio, enStock, descripcion, categoria, iva, subcategoria);
        bebidas.add(nuevoProducto);

        // Mostrar mensaje de que se ha añadido correctamente.
        JOptionPane.showMessageDialog(null, "Producto añadido correctamente a la lista de bebidas.");

    }

    // Método para verificar si ya existe un producto con el mismo nombre.
    private boolean nombreProductoExistente(String nombre) {
        // Iterar sobre la lista de bebidas para verificar si existe un producto con el mismo nombre.
        for (Producto producto : bebidas) {
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

    // Método para obtener una subcategoría válida ("Alcoholicas", "Aguas" o "Refrescos")
    private String obtenerSubcategoriaValida() {
        String subcategoria;
        // Ciclo para solicitar al usuario una subcategoría válida.
        while (true) {
            // Solicitar al usuario que ingrese la subcategoría del nuevo producto.
            subcategoria = JOptionPane.showInputDialog("Ingrese la subcategoría del nuevo producto (Alcoholica / Refresco / Agua):");

            // Convertir la subcategoría a minúsculas y luego a mayúsculas solo en la primera letra.
            subcategoria = subcategoria.toLowerCase();
            subcategoria = subcategoria.substring(0, 1).toUpperCase() + subcategoria.substring(1);

            // Verificar si la subcategoría ingresada es válida (Alcoholica, Refresco o Agua).
            if ("Alcoholica".equals(subcategoria) || "Refresco".equals(subcategoria) || "Agua".equals(subcategoria)) {
                // Salir del ciclo si la subcategoría ingresada es válida.
                break;
            } else {
                // Mostrar mensaje de error y solicitar nuevamente la subcategoría si no es válida.
                JOptionPane.showMessageDialog(null, "Ingrese una subcategoría válida (Alcoholica / Refresco / Agua).");
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
            case "alcoholica":
                subcategoriaLetra = 'A';
                break;
            case "refresco":
                subcategoriaLetra = 'R';
                break;
            case "agua":
                subcategoriaLetra = 'W';
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
                for (Producto producto : bebidas) {
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

    // Metodo para borrar producto de bebidas.
    public void borrarProducto(String nombreProducto) {
        // Iterar sobre la lista de bebidas para encontrar el producto con el Nombre.
        for (Producto producto : bebidas) {
            // Comparar el nombre del producto actual con el nombre proporcionado (sin distinguir mayúsculas y minúsculas).
            if (producto.getNombre().equalsIgnoreCase(nombreProducto)) {
                // Eliminar el producto de la lista.
                bebidas.remove(producto);

                JOptionPane.showMessageDialog(null, "Producto eliminado correctamente.");
                return; // Salir del método después de eliminar el producto.
            }
        }

        // Si el producto con el ID proporcionado no se encuentra, mostrar un mensaje de error.
        JOptionPane.showMessageDialog(null, "No se encontró ningún producto con el nombre proporcionado.");
    }

    // Método para editar producto de bebidas.
    public void editarProducto(String nombreProducto) {
        // Buscar el producto en la lista basándose en el nombre proporcionado.
        for (Producto producto : bebidas) {
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

    // Método para obtener un valor booleano válido para el stock.
    private boolean obtenerStockValido() {
        // Define las opciones para la respuesta de stock ("Sí" o "No").
        String[] opciones = {"Sí", "No"};

        // Muestra un cuadro de diálogo de opción con las opciones definidas.
        int eleccion = JOptionPane.showOptionDialog(
                null,
                "¿Está este producto en Stock?",
                "Stock",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        return eleccion == 0;  // Devuelve true si se selecciona "Sí", false si se selecciona "No"
    }

}
