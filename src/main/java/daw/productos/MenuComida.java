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
 * Clase que representa el menú de comidas en el TPV.
 *
 * @author acebedo
 */
public class MenuComida {

    // Atributos necesarios para las funciones del menú de comidas.
    private FuncionesTPV funcionesTPV;
    private FuncionesCarrito funcionesCarrito;
    private FuncionesUsuario funcionesUsuario;

    // Constructor que recibe la referencia a FuncionesTPV y crea una instancia de FuncionesCarrito.
    public MenuComida(FuncionesTPV funcionesTPV) {
        this.funcionesTPV = funcionesTPV;
        this.funcionesCarrito = new FuncionesCarrito(this.funcionesUsuario);
    }

    // Listas para almacenar las diferentes categorías de comidas.
    private static List<Producto> comidas = new ArrayList<>();

    // Método principal del menú de comidas, inicializa las comidas y muestra el menú de selección de comidas.
    public void MenuComida() {
        // Mostrar el menú principal de comidas.
        menuSeleccionComidas();
    }

    // Método estático para inicializar la lista de comidas.
    public static void llamarInicializarComidas() {
        // Inicializar las listas.
        inicializarComidasDesdeCSV("Comidas.csv");
    }

    // Método estático para inicializar la lista de bebidas con datos.
    public static void inicializarComidasDesdeCSV(String archivoCSV) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            String linea;
            // Saltamos la primera línea ya que contiene los encabezados.
            br.readLine();
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                // Verificamos si hay suficientes datos en la línea.
                if (datos.length == 8) {
                    // Creamos un nuevo objeto Producto y lo agregamos a la lista de comidas.
                    Producto producto = new Producto(datos[0], datos[1], Double.parseDouble(datos[2]), Integer.parseInt(datos[3]), datos[4], datos[5], Double.parseDouble(datos[6]), datos[7]);
                    comidas.add(producto);
                } else {
                    System.out.println("Error: La línea no contiene la cantidad correcta de datos (Comidas.csv).");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método principal que muestra el menú de selección para el usuario.
    public void menuSeleccionComidas() {
        // Crear la ventana del menú.
        JFrame frame = new JFrame("Tipos de Comida");

        // Crear un panel para colocar los botones.
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Crear botones para cada opción del menú.
        // Asociar ActionListener a cada botón.
        agregarBoton(panel, "Pizzas", e -> {
            menuSeleccionPizzas();
            frame.dispose();
        });
        agregarBoton(panel, "Hamburguesas", e -> {
            menuSeleccionHamburguesas();
            frame.dispose();
        });
        agregarBoton(panel, "Kebabs", e -> {
            menuSeleccionKebabs();
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

    // Método principal que muestra el menú de pizzas al usuario.
    public void menuSeleccionPizzas() {
        // Crear la ventana del menú.
        JFrame frame = new JFrame("Tipos de Pizzas");

        // Crear un panel para colocar los botones.
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Crear botones para cada opción del menú.
        // Asociar ActionListener a cada botón.
        for (Producto pizza : comidas) {
            if ("Pizza".equals(pizza.getSubcategoria())) {
                agregarBoton(panel, pizza.getNombre(), e -> {
                    FuncionesCarrito.agregarProductoAlCarrito(pizza);
                });
            }
        }
        agregarBoton(panel, "Consultar precios", e -> {
            consultarPreciosPizzas();
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

    // Método principal que muestra el menú de hamburguesas al usuario.
    public void menuSeleccionHamburguesas() {
        // Crear la ventana del menú.
        JFrame frame = new JFrame("Tipos de Hamburguesas");

        // Crear un panel para colocar los botones.
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Crear botones para cada opción del menú.
        // Asociar ActionListener a cada botón.
        for (Producto hamburguesa : comidas) {
            if ("Hamburguesa".equals(hamburguesa.getSubcategoria())) {
                agregarBoton(panel, hamburguesa.getNombre(), e -> {
                    FuncionesCarrito.agregarProductoAlCarrito(hamburguesa);
                });
            }
        }
        agregarBoton(panel, "Consultar precios", e -> {
            consultarPreciosHamburguesas();
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

    // Método principal que muestra el menú de kebabs al usuario.
    public void menuSeleccionKebabs() {
        // Crear la ventana del menú.
        JFrame frame = new JFrame("Tipos de Kebabs");

        // Crear un panel para colocar los botones.
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Crear botones para cada opción del menú.
        // Asociar ActionListener a cada botón.
        for (Producto kebab : comidas) {
            if ("Kebab".equals(kebab.getSubcategoria())) {
                agregarBoton(panel, kebab.getNombre(), e -> {
                    FuncionesCarrito.agregarProductoAlCarrito(kebab);
                });
            }
        }
        agregarBoton(panel, "Consultar precios", e -> {
            consultarPreciosKebabs();
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

    // Método para consultar precios de hamburguesas.
    private void consultarPreciosHamburguesas() {
        // Obtener precios de hamburguesas desde la lista.
        StringBuilder preciosHamburguesas = new StringBuilder("Precios de Hamburguesas:\n");

        // Iterar sobre la lista de hamburguesas y mostrar solo los productos de la SubCategoría "Hamburguesa".
        for (Producto producto : comidas) {
            if ("Hamburguesa".equals(producto.getSubcategoria())) {
                preciosHamburguesas.append(producto.getNombre()).append(": ").append(producto.getPrecio()).append(" €").append("\n");
            }
        }

        // Mostrar el mensaje utilizando JOptionPane.
        JOptionPane.showMessageDialog(null, preciosHamburguesas.toString());

        // Volver al menú de selección de hamburguesas.
        menuSeleccionHamburguesas();
    }

    // Método para consultar precios de kebabs.
    private void consultarPreciosKebabs() {
        // Obtener precios de kebabs desde la lista.
        StringBuilder preciosKebabs = new StringBuilder("Precios de Kebabs:\n");

        // Iterar sobre la lista de kebabs y mostrar solo los productos de la SubCategoría "Kebab".
        for (Producto producto : comidas) {
            if ("Kebab".equals(producto.getSubcategoria())) {
                preciosKebabs.append(producto.getNombre()).append(": ").append(producto.getPrecio()).append(" €").append("\n");
            }
        }

        // Mostrar el mensaje utilizando JOptionPane.
        JOptionPane.showMessageDialog(null, preciosKebabs.toString());

        // Volver al menú de selección de kebabs.
        menuSeleccionKebabs();
    }

    // Método para consultar precios de pizzas.
    private void consultarPreciosPizzas() {
        // Obtener precios de pizzas desde la lista.
        StringBuilder preciosPizzas = new StringBuilder("Precios de Pizzas:\n");

        // Iterar sobre la lista de pizzas y mostrar solo los productos de la SubCategoría "Pizza".
        for (Producto producto : comidas) {
            if ("Pizza".equals(producto.getSubcategoria())) {
                preciosPizzas.append(producto.getNombre()).append(": ").append(producto.getPrecio()).append(" €").append("\n");
            }
        }

        // Mostrar el mensaje utilizando JOptionPane.
        JOptionPane.showMessageDialog(null, preciosPizzas.toString());

        // Volver al menú de selección de pizzas.
        menuSeleccionPizzas();
    }

    // Metodo para añadir nuevo producto a comidas.
    public void añadirProductoAComidas() {

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

        // Establecer la categoría del nuevo producto como "Comida".
        String categoria = "Comida";

        // Obtener un porcentaje de IVA válido para el nuevo producto.
        double iva = obtenerIVAValido();

        // Obtener una subcategoría válida para el nuevo producto.
        String subcategoria = obtenerSubcategoriaValida();

        // Obtener un nuevo ID basado en la subcategoría del nuevo producto.
        String id = obtenerNuevoID(subcategoria);

        // Crear un nuevo objeto Producto con los datos ingresados y agregarlo a la lista de comidas.
        Producto nuevoProducto = new Producto(id, nombre, precio, enStock, descripcion, categoria, iva, subcategoria);
        comidas.add(nuevoProducto);

        // Mostrar mensaje de que se ha añadido correctamente.
        JOptionPane.showMessageDialog(null, "Producto añadido correctamente a la lista de comidas.");

    }

    // Método para verificar si ya existe un producto con el mismo nombre.
    private boolean nombreProductoExistente(String nombre) {
        // Iterar sobre la lista de bebidas para verificar si existe un producto con el mismo nombre.
        for (Producto producto : comidas) {
            // Comparar el nombre del producto actual con el nombre proporcionado (ignorando mayúsculas y minúsculas).
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                return true; // Ya existe un producto con el mismo nombre.
            }
        }
        return false; // No hay ningún producto con el mismo nombre.
    }

    // Método para obtener un precio válido (mayor o igual a 0)
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

    // Método para obtener una subcategoría válida ("Hamburguesas", "Pizzas" o "Kebabs")
    private String obtenerSubcategoriaValida() {
        String subcategoria;
        // Ciclo para solicitar al usuario una subcategoría válida.
        while (true) {
            // Solicitar al usuario que ingrese la subcategoría del nuevo producto.
            subcategoria = JOptionPane.showInputDialog("Ingrese la subcategoría del nuevo producto (Hamburguesa / Pizza / Kebab):");

            // Convertir la subcategoría a minúsculas y luego a mayúsculas solo en la primera letra.
            subcategoria = subcategoria.toLowerCase();
            subcategoria = subcategoria.substring(0, 1).toUpperCase() + subcategoria.substring(1);

            // Verificar si la subcategoría ingresada es válida (Hamburguesa, Pizza o Kebab).
            if ("Hamburguesa".equals(subcategoria) || "Pizza".equals(subcategoria) || "Kebab".equals(subcategoria)) {
                // Salir del ciclo si la subcategoría ingresada es válida.
                break;
            } else {
                // Mostrar mensaje de error y solicitar nuevamente la subcategoría si no es válida.
                JOptionPane.showMessageDialog(null, "Ingrese una subcategoría válida (Hamburguesa / Pizza / Kebab).");
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
            case "pizza":
                subcategoriaLetra = 'P';
                break;
            case "hamburguesa":
                subcategoriaLetra = 'H';
                break;
            case "kebab":
                subcategoriaLetra = 'K';
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
                for (Producto producto : comidas) {
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

    // Metodo para borrar producto de comidas.
    public void borrarProducto(String nombreProducto) {
        // Iterar sobre la lista de comidas para encontrar el producto con el Nombre.
        for (Producto producto : comidas) {
            // Comparar el nombre del producto actual con el nombre proporcionado (sin distinguir mayúsculas y minúsculas).
            if (producto.getNombre().equalsIgnoreCase(nombreProducto)) {
                // Eliminar el producto de la lista.
                comidas.remove(producto);

                JOptionPane.showMessageDialog(null, "Producto eliminado correctamente.");
                return; // Salir del método después de eliminar el producto.
            }
        }

        // Si el producto con el ID proporcionado no se encuentra, mostrar un mensaje de error.
        JOptionPane.showMessageDialog(null, "No se encontró ningún producto con el nombre proporcionado.");
    }

    // Método para editar producto de comidas.
    public void editarProducto(String nombreProducto) {
        // Buscar el producto en la lista basándose en el nombre proporcionado.
        for (Producto producto : comidas) {
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
