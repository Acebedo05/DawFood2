package daw.productos;

// Importar las clases necesarias desde otros paquetes.
import daw.carrito.FuncionesCarrito;
import daw.modos.FuncionesUsuario;
import daw.tpv.FuncionesTPV;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
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

    // Método estático para inicializar las bebidas.
    public static void llamarInicializarBebidas() {
        // Inicializar las Bebidas.
        inicializarBebidasDesdeCSV("Bebidas.csv");
    }

    // Método estático para inicializar la lista de bebidas con datos.
    public static void inicializarBebidasDesdeCSV(String archivoCSV) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            String linea;
            // Saltamos la primera línea ya que contiene los encabezados.
            br.readLine();
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                // Verificamos si hay suficientes datos en la línea.
                if (datos.length == 8) {
                    // Creamos un nuevo objeto Producto y lo agregamos a la lista de bebidas.
                    Producto producto = new Producto(datos[0], datos[1], Double.parseDouble(datos[2]), Integer.parseInt(datos[3]), datos[4], datos[5], Double.parseDouble(datos[6]), datos[7]);
                    bebidas.add(producto);
                } else {
                    System.out.println("Error: La línea no contiene la cantidad correcta de datos (Bebidas.csv).");
                }
            }
        } catch (IOException ex) {
            System.out.println("Error accediendo a al fichero Bebidas.csv.");
        }
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
        frame.setSize(800, 200);
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
        frame.setSize(800, 200);
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
        frame.setSize(800, 200);
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
        int enStock = obtenerStockValido();

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
        // Escribir los detalles del nuevo producto en el archivo bebidas.csv.
        escribirProductoEnArchivoCSV(nuevoProducto);

        // Mostrar mensaje de que se ha añadido correctamente.
        JOptionPane.showMessageDialog(null, "Producto añadido correctamente a la lista de bebidas.");

    }

    private void escribirProductoEnArchivoCSV(Producto producto) {
        String rutaArchivo = "bebidas.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
            // Escribir los detalles del producto en una nueva línea en el archivo CSV.
            String linea = producto.getId() + ","
                    + producto.getNombre() + ","
                    + producto.getPrecio() + ","
                    + producto.getEnStock() + ","
                    + producto.getDescripcion() + ","
                    + producto.getCategoria() + ","
                    + producto.getIva() + ","
                    + producto.getSubcategoria();
            writer.write(linea);
            writer.newLine();
        } catch (IOException e) {
            // Manejar cualquier error de escritura en el archivo.
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al escribir en el archivo bebidas.csv.");
        }
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

    // Método para borrar producto de bebidas.
    public void borrarProducto(String nombreProducto) {
        // Iterar sobre la lista de bebidas para encontrar el producto con el Nombre.
        Iterator<Producto> iterator = bebidas.iterator();
        while (iterator.hasNext()) {
            Producto producto = iterator.next();
            // Comparar el nombre del producto actual con el nombre proporcionado (sin distinguir mayúsculas y minúsculas).
            if (producto.getNombre().equalsIgnoreCase(nombreProducto)) {
                // Eliminar el producto de la lista.
                iterator.remove();
                // Eliminar el producto del archivo bebidas.csv
                eliminarProductoDelArchivo(producto);
                JOptionPane.showMessageDialog(null, "Producto eliminado correctamente.");
                return; // Salir del método después de eliminar el producto.
            }
        }

        // Si el producto con el nombre proporcionado no se encuentra, mostrar un mensaje de error.
        JOptionPane.showMessageDialog(null, "No se encontró ningún producto con el nombre proporcionado.");
    }

    // Método para eliminar un producto del archivo bebidas.csv
    private void eliminarProductoDelArchivo(Producto nombreProducto) {
        String rutaArchivo = "bebidas.csv";
        File archivo = new File(rutaArchivo);
        List<String> lineas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            // Leer todas las líneas del archivo y almacenarlas en una lista, excepto la línea del producto a eliminar
            while ((linea = reader.readLine()) != null) {
                // Verificar si la línea contiene el nombre del producto a eliminar
                if (!linea.toLowerCase().contains(nombreProducto.getNombre().toLowerCase())) {
                    lineas.add(linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al intentar eliminar el producto del archivo.");
            return;
        }
        // Escribir el contenido actualizado al archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (String linea : lineas) {
                writer.write(linea + System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar el archivo bebidas.csv.");
            return;
        }
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

                            editarProductoEnArchivo(producto, eleccion, nuevoNombre);
                            producto.setNombre(nuevoNombre);
                            break;
                        case "Precio":
                            double nuevoPrecio = obtenerPrecioValido();
                            editarProductoEnArchivo(producto, eleccion, String.valueOf(nuevoPrecio));
                            producto.setPrecio(nuevoPrecio);
                            break;
                        case "En Stock":
                            int nuevoStock = obtenerStockValido();
                            editarProductoEnArchivo(producto, eleccion, String.valueOf(nuevoStock));
                            producto.setEnStock(nuevoStock);
                            break;
                        case "Descripción":
                            String nuevaDescripcion = JOptionPane.showInputDialog("Ingrese la nueva descripción del producto:");
                            editarProductoEnArchivo(producto, eleccion, nuevaDescripcion);
                            producto.setDescripcion(nuevaDescripcion);
                            break;
                        case "IVA":
                            double nuevoIVA = obtenerIVAValido();
                            editarProductoEnArchivo(producto, eleccion, String.valueOf(nuevoIVA));
                            producto.setIva(nuevoIVA);
                            break;
                        case "Subcategoría":
                            String nuevaSubcategoria = obtenerSubcategoriaValida();
                            editarProductoEnArchivo(producto, eleccion, nuevaSubcategoria);
                            producto.setSubcategoria(nuevaSubcategoria);
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

    // Método para editar el producto en el archivo bebidas.csv
    private void editarProductoEnArchivo(Producto producto, String atributo, String nuevoValor) {
        String rutaArchivo = "bebidas.csv";
        File archivo = new File(rutaArchivo);

        // Lista para almacenar las líneas actualizadas del archivo
        List<String> lineasActualizadas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            // Leer todas las líneas del archivo y almacenarlas en una lista, modificando la línea correspondiente al producto a editar
            while ((linea = reader.readLine()) != null) {
                // Si la línea actual corresponde al producto a editar, modificarla con los nuevos valores
                if (linea.toLowerCase().startsWith(producto.getId().toLowerCase())) {
                    switch (atributo) {
                        case "Nombre":
                            linea = linea.replaceFirst("(?i)" + producto.getNombre(), nuevoValor);
                            break;
                        case "Precio":
                            linea = reemplazarCampo(linea, String.valueOf(producto.getPrecio()), nuevoValor, 2);
                            break;
                        case "En Stock":
                            linea = reemplazarCampo(linea, String.valueOf(producto.getEnStock()), nuevoValor, 3);
                            break;
                        case "Descripción":
                            linea = reemplazarCampo(linea, producto.getDescripcion(), nuevoValor, 4);
                            break;
                        case "IVA":
                            linea = reemplazarCampo(linea, String.valueOf(producto.getIva()), nuevoValor, 6);
                            break;
                        case "Subcategoría":
                            linea = reemplazarCampo(linea, producto.getSubcategoria(), nuevoValor, 7);
                            break;
                    }
                }
                lineasActualizadas.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al intentar editar el producto en el archivo.");
            return;
        }

        // Escribir el contenido actualizado al archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (String linea : lineasActualizadas) {
                writer.write(linea + System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar el archivo bebidas.csv.");
            return;
        }
    }

    // Método auxiliar para reemplazar el campo especificado en una línea con un nuevo valor
    private String reemplazarCampo(String linea, String valorActual, String nuevoValor, int campoIndex) {
        // Separamos los campos de la línea utilizando la coma como delimitador
        String[] campos = linea.split(",");
        // Reemplazamos el valor del campo en el índice especificado con el nuevo valor
        campos[campoIndex] = nuevoValor;
        // Reconstruimos la línea con los campos actualizados
        StringBuilder nuevaLinea = new StringBuilder();
        for (String campo : campos) {
            nuevaLinea.append(campo).append(",");
        }
        // Eliminamos la coma adicional al final de la línea y devolvemos la línea actualizada
        return nuevaLinea.substring(0, nuevaLinea.length() - 1);
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
