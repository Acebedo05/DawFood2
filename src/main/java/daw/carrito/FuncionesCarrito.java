package daw.carrito;

// Importar las clases necesarias desde otros paquetes.
import daw.modos.FuncionesUsuario;
import daw.productos.Producto;
import daw.tpv.FuncionesTPV;
import daw.tpv.ObjetosTPV;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Clase con las funciones relacionadas con el carrito de compras.
 *
 * @author acebedo
 */
public class FuncionesCarrito {

    private static Map<Producto, Integer> carrito = new HashMap<>();
    private static List<AtributosTarjeta> listaDeTarjetas = new ArrayList<>();
    private static List<AtributosTicket> listaDeTickets = new ArrayList<>();

    private FuncionesUsuario funcionesUsuario;
    private static int idPedidoContador = 1;

    // Constructor que inicializa la lista de tarjetas leyendo desde un archivo CSV.
    public FuncionesCarrito(FuncionesUsuario funcionesUsuario) {
        this.funcionesUsuario = funcionesUsuario;
        cargarTarjetasDesdeCSV("Tarjetas.csv");
        // Verificar si la lista de tickets está vacía antes de cargar los datos desde el archivo CSV
        if (listaDeTickets.isEmpty()) {
            leerTicketsDesdeCSV("Tickets.csv");
        }
    }

    // Método para coger las tarjetas desde un CSV.
    private void cargarTarjetasDesdeCSV(String archivo) {
        try (Scanner scanner = new Scanner(new File(archivo))) {
            // Saltar la primera línea (encabezado)
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            // Leer cada línea del archivo CSV y crear una tarjeta para cada una.
            while (scanner.hasNextLine()) {
                String[] campos = scanner.nextLine().split(",");
                if (campos.length == 5) {
                    int numeroTarjeta = Integer.parseInt(campos[0]);
                    LocalDate fechaVencimiento = LocalDate.parse(campos[1]);
                    int cvv = Integer.parseInt(campos[2]);
                    double saldo = Double.parseDouble(campos[3]);
                    String nombreTitular = campos[4];
                    listaDeTarjetas.add(new AtributosTarjeta(numeroTarjeta, fechaVencimiento, cvv, saldo, nombreTitular));
                } else {
                    System.out.println("Error al leer la línea del archivo CSV: " + Arrays.toString(campos));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo encontrar el archivo CSV: " + e.getMessage());
        } catch (DateTimeParseException | NumberFormatException e) {
            System.out.println("Error al analizar los datos del archivo CSV: " + e.getMessage());
        }
    }

    // Método para agregar productos seleccionados al carrito.
    public static void agregarProductoAlCarrito(Producto producto) {
        // Verificar si el producto está en stock
        if (producto.getEnStock()<= 0) {
            JOptionPane.showMessageDialog(null, "El producto '" + producto.getNombre() + "' no está disponible en stock.", "Producto no disponible", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener la cantidad disponible en stock y en el carrito
        int cantidadEnStock = producto.getEnStock();
        int cantidadEnCarrito = carrito.containsKey(producto) ? carrito.get(producto) : 0;

        // Calcular la cantidad máxima que se puede agregar al carrito respetando el stock
        int cantidadMaxima = Math.min(cantidadEnStock, cantidadEnStock - cantidadEnCarrito);

        // Pedir al usuario que ingrese la cantidad utilizando JOptionPane
        String cantidada = JOptionPane.showInputDialog("Ingrese la cantidad de '" + producto.getNombre() + "' que desea agregar al carrito (Stock Disponible: " + cantidadMaxima + " unidades):");

        try {
            // Convertir la cantidad ingresada a un número entero
            int cantidad = Integer.parseInt(cantidada);

            if (cantidad > 0 && cantidad <= cantidadMaxima) {
                // Verificar si el producto ya está en el carrito
                if (carrito.containsKey(producto)) {
                    // Si el producto ya está en el carrito, sumar la nueva cantidad a la cantidad existente
                    cantidad += carrito.get(producto);
                }

                // Actualizar la cantidad en el carrito
                carrito.put(producto, cantidad);

                JOptionPane.showMessageDialog(null, cantidada + " x '" + producto.getNombre() + "' ha sido agregado al carrito.");
            } else {
                JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor que 0 y no puede exceder el stock disponible. No se ha agregado nada al carrito.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido para la cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para mostrar el menú de productos seleccionados con precios usando JOptionPane
    public void mostrarMenuCarritoConPrecios() {
        try {
            // StringBuilder para construir el mensaje a mostrar.
            StringBuilder mensaje = new StringBuilder("Productos Seleccionados:\n");
            // Variables para calcular el precio total sin IVA y con IVA.
            double precioTotalSinIVA = 0;
            double precioTotalConIVA = 0;

            // Iterar sobre las entradas (producto y cantidad) en el carrito.
            for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
                Producto producto = entry.getKey();
                int cantidad = entry.getValue();

                // Agregar al mensaje el nombre del producto, su cantidad y precio (sin IVA).
                mensaje.append(producto.getNombre()).append(" (").append(cantidad).append(" unidades): ");
                mensaje.append(producto.getPrecio() * cantidad).append(" € (sin IVA)\n");

                // Calcular el precio total sin IVA sumando el precio de cada producto multiplicado por su cantidad.
                precioTotalSinIVA += producto.getPrecio() * cantidad;

                // Calcular el precio total con IVA sumando el precio con IVA de cada producto multiplicado por su cantidad.
                precioTotalConIVA += producto.getPrecioConIVA() * cantidad;
            }

            // Agregar al mensaje el precio total sin IVA y el precio total con IVA.
            mensaje.append("Precio Total sin IVA: ").append(String.format("%.2f", precioTotalSinIVA)).append(" €\n");
            mensaje.append("Precio Total con IVA: ").append(String.format("%.2f", precioTotalConIVA)).append(" €");

            // Mostrar el mensaje utilizando JOptionPane.
            JOptionPane.showMessageDialog(null, mensaje.toString(), "Carrito", JOptionPane.INFORMATION_MESSAGE);

            // Llamar al método menuSeleccion de FuncionesUsuario para continuar con el programa.
            funcionesUsuario.menuSeleccion();
        } catch (NullPointerException e) {
            // Capturar excepción NullPointerException e imprimir en la terminal.
            // System.out.println("Excepción NullPointerException: " + e.getMessage());
        }
    }

    // Método para limpiar el carrito.
    public static void noComprar() {
        carrito.clear();
    }

    // Método para realizar el proceso de pago
    public static void procesarPago() {
        // Calcular el precio total con IVA
        double precioTotal = calcularPrecioTotalConIVA();
        FuncionesCarrito funcionesCarrito = new FuncionesCarrito(null);

        // Verificar si hay productos en el carrito.
        if (precioTotal != 0) {
            boolean tarjetaValida = false;
            int intentos = 0;

            // Permitir hasta 2 intentos de pago.
            while (!tarjetaValida && intentos < 2) {
                try {
                    // Solicitar al usuario el número de tarjeta.
                    String stringNumTarj = JOptionPane.showInputDialog("Ingrese el número de su tarjeta:");

                    // Verificar si la entrada es un número.
                    if (!esNumero(stringNumTarj)) {
                        JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido para el número de tarjeta.", "Error", JOptionPane.ERROR_MESSAGE);
                        intentos++;
                    } else {
                        int numeroTarjeta = Integer.parseInt(stringNumTarj);

                        // Verificar si la tarjeta con ese número existe.
                        AtributosTarjeta tarjeta = obtenerTarjetaPorNumero(numeroTarjeta);

                        if (tarjeta != null) {

                            LocalDate fechaCaducidad = null;

                            // Si la tarjeta existe, solicitar la fecha de caducidad y el CVV.
                            try {
                                String fechaCaducidadA = JOptionPane.showInputDialog("Ingrese la fecha de caducidad (YYYY/MM/DD):");
                                fechaCaducidad = LocalDate.parse(fechaCaducidadA, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                            } catch (DateTimeParseException e) {
                                // Capturar excepción DateTimeParseException e imprimir en la terminal.
                                // System.out.println("Excepción DateTimeParseException: " + e.getMessage());
                            }

                            String cvv = JOptionPane.showInputDialog("Ingrese el CVV:");

                            // Verificar que la fecha de caducidad y el CVV no sean nulos o vacíos.
                            if (fechaCaducidad != null && cvv != null && !cvv.isEmpty()) {
                                // Verificar la fecha de caducidad y el CVV.
                                if (verificarFechaYCVV(tarjeta, fechaCaducidad, cvv)) {
                                    // Calcular el precio total con IVA.
                                    double precioTotalConIVA = calcularPrecioTotalConIVA();

                                    // Verificar si hay suficiente saldo para la compra.
                                    if (tarjeta.getSaldo() >= precioTotalConIVA) {
                                        // Realizar la compra descontando el saldo.
                                        tarjeta.setSaldo(tarjeta.getSaldo() - precioTotalConIVA);

                                        // Mostrar mensaje de compra realizada.
                                        JOptionPane.showMessageDialog(null, "Compra realizada. Gracias por su compra!");

                                        // Llamar al método ticket.
                                        ticket();

                                        // Limpiar el carrito después de la compra.
                                        carrito.clear();

                                        // Salir del bucle.
                                        tarjetaValida = true;

                                        // Llamar al método encenderTPV.
                                        llamarEncenderTPV();

                                    } else {
                                        // Mostrar mensaje de saldo insuficiente.
                                        JOptionPane.showMessageDialog(null, "Saldo insuficiente. Ingrese otra tarjeta.");
                                        intentos++;
                                    }
                                } else {
                                    // Mostrar mensaje de fecha de caducidad o CVV incorrecto.
                                    JOptionPane.showMessageDialog(null, "Fecha de caducidad o CVV incorrecto. Ingrese otra tarjeta.");
                                    intentos++;
                                }
                            } else {
                                // Mostrar mensaje de fecha de caducidad o CVV nulo o vacío.
                                JOptionPane.showMessageDialog(null, "Fecha de caducidad o CVV incorrecto. Ingrese otra tarjeta.");
                                intentos++;
                            }

                        } else {
                            // Mostrar mensaje de tarjeta no encontrada.
                            JOptionPane.showMessageDialog(null, "Tarjeta no encontrada. Ingrese otra tarjeta.");
                            intentos++;
                        }
                    }
                } catch (NumberFormatException e) {
                    // Mostrar mensaje de entrada no válida.
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    intentos++;
                }
            }

            // Si se superan los 2 intentos, limpiar el carrito y encender el TPV.
            if (intentos >= 2) {
                carrito.clear();
                llamarEncenderTPV();
            }

        } else {
            // Mostrar mensaje de carrito vacío.
            JOptionPane.showMessageDialog(null, "El carrito está vacío. No se puede realizar la compra.");

            // Limpiar el carrito después de la compra.
            carrito.clear();

            // Llamar al método encenderTPV.
            llamarEncenderTPV();
        }
    }

    // Método para verificar si una cadena es un número
    private static boolean esNumero(String cadena) {
        try {
            Integer.parseInt(cadena); // Intenta convertir la cadena a un entero.
            return true; // Si funciona, devuelve true.
        } catch (NumberFormatException e) {
            return false;  // Si hay una excepción, devuelve false.
        }
    }

    // Método para buscar una tarjeta por su número en la lista de tarjetas
    public static AtributosTarjeta obtenerTarjetaPorNumero(int numeroTarjeta) {
        for (AtributosTarjeta tarjeta : listaDeTarjetas) {
            if (tarjeta.getNumeroTarjeta() == numeroTarjeta) {
                return tarjeta; // Devuelve la tarjeta si encuentra el número proporcionado.
            }
        }
        return null; // Retorna null si no se encuentra la tarjeta con el número proporcionado.
    }

    // Método para verificar la fecha de caducidad y el CVV de una tarjeta
    private static boolean verificarFechaYCVV(AtributosTarjeta tarjeta, LocalDate fechaCaducidad, String cvv) {

        // Verificar la fecha de caducidad
        if (tarjeta.getFechaVencimiento().equals(fechaCaducidad)) {
            // Verificar el CVV
            return tarjeta.getCvv() == Integer.parseInt(cvv);
        }

        return false; // Devuelve false si la fecha de caducidad no coincide o el CVV no coincide.
    }

    // Método para calcular el precio total con IVA de los productos en el carrito
    private static double calcularPrecioTotalConIVA() {
        double precioTotalConIVA = 0;

        for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
            Producto producto = entry.getKey();
            int cantidad = entry.getValue();

            // Calcular el precio total con IVA sumando el precio con IVA de cada producto multiplicado por su cantidad.
            precioTotalConIVA += producto.getPrecioConIVA() * cantidad;
        }

        return precioTotalConIVA; // Devuelve el precio total con IVA
    }

    // Método para generar y mostrar un ticket de compra.
    private static void ticket() {
        // Calcular el precio final con IVA de los productos en el carrito.
        double precioFinal = Math.round(calcularPrecioTotalConIVA() * 100d) / 100d;

        // Generar un ID único para el pedido
        int idPedido = generarIdPedido();

        // Obtener la fecha y hora actual
        LocalDateTime fechaYHoraOperacion = LocalDateTime.now();

        // Crear una lista para almacenar la información de los productos comprados.
        List<String> productosComprados = new ArrayList<>();

        // Iterar sobre las entradas (producto y cantidad) en el carrito.
        for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
            Producto producto = entry.getKey();
            int cantidad = entry.getValue();

            // Construir la cadena con la información del producto.
            String productoInfo = producto.getId() + ","
                    + producto.getNombre() + ","
                    + producto.getDescripcion() + ","
                    + producto.getPrecio() + ","
                    + cantidad + ","
                    + producto.getIva();

            // Agregar la cadena a la lista de productos comprados.
            productosComprados.add(productoInfo);
        }

        // Crear un objeto AtributosTicket con la información.
        AtributosTicket atributosTicket = new AtributosTicket(precioFinal, idPedido, fechaYHoraOperacion, productosComprados);

        // Agregar el ticket a la lista de tickets.
        listaDeTickets.add(atributosTicket);
        // Guardar el ticket en el archivo CSV.
        guardarTicketEnCSV(atributosTicket);

        // Mostrar el ticket de compra utilizando JOptionPane.
        JOptionPane.showMessageDialog(null, atributosTicket.toString(), "Ticket de Compra", JOptionPane.INFORMATION_MESSAGE);

    }

    // Método para generar un ID de pedido único
    private static int generarIdPedido() {
        if (listaDeTickets.isEmpty()) {
            return 1; // Si la lista de tickets está vacía, el último ID de pedido es 1
        } else {
            // Obtener el ID del último ticket en la lista de tickets
            AtributosTicket ultimoTicket = listaDeTickets.get(listaDeTickets.size() - 1 );
            return ultimoTicket.getIdPedido() + 1; // Devolver el ID del último ticket
        }
    }

    // Método para volver al Menú Inicial
    private static void llamarEncenderTPV() {
        // Llamando al método encenderTPV de la clase FuncionesTPV
        FuncionesTPV funcionesTPV = ObjetosTPV.inicializarTPV(); // Inicializa la clase FuncionesTPV.
        funcionesTPV.encenderTPV(); // Llama al método encenderTPV de la instancia de FuncionesTPV.
    }

    // Método para consultar las ventas realizadas.
    public static void consultarVentas() {
        // Preguntar al usuario qué tipo de consulta desea realizar.
        String[] opciones = {"Ver las ventas de un día concreto", "Ver las ventas de hasta una fecha concreta", "Ver todas las ventas"};
        int seleccion = JOptionPane.showOptionDialog(null, "¿Qué prefieres?", "Consulta de Ventas", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;
        boolean mostrarTodos = false;

        switch (seleccion) {
            case 0:
                // Ver las ventas de un día concreto.
                String fechaStr = JOptionPane.showInputDialog("Ingrese la fecha en formato YYYY-MM-DD:");
                try {
                    fechaInicio = LocalDate.parse(fechaStr);
                    fechaFin = fechaInicio.plusDays(1);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Fecha inválida. Mostrando todas las ventas.");
                    mostrarTodos = true;
                }
                break;

            case 1:
                // Ver las ventas hasta una fecha concreta
                String fechaFinStr = JOptionPane.showInputDialog("Ingrese la fecha hasta la cual desea ver las ventas en formato YYYY-MM-DD:");
                try {
                    fechaFin = LocalDate.parse(fechaFinStr).plusDays(1);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Fecha inválida. Mostrando todas las ventas.");
                    mostrarTodos = true;
                }
                break;

            case 2:
                // Ver todas las ventas.
                mostrarTodos = true;
                break;

            default:
                // Opción no válida, mostrar todas las ventas.
                mostrarTodos = true;
                break;
        }

        // Filtrar y mostrar los tickets.
        List<AtributosTicket> ticketsFiltrados = filtrarTickets(fechaInicio, fechaFin, mostrarTodos);

        // Verificar si hay tickets registrados.
        if (ticketsFiltrados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay ventas registradas en el período especificado.");
        } else {
            // Mostrar los tickets.
            StringBuilder mensaje = new StringBuilder("Ventas Realizadas:\n\n");

            for (AtributosTicket ticket : ticketsFiltrados) {
                mensaje.append("ID Pedido: ").append(ticket.getIdPedido()).append("\n");
                mensaje.append("Fecha y Hora: ").append(ticket.getFechaYHoraOperacion()).append("\n");
                mensaje.append("Productos:\n").append(ticket.getProductosComprados()).append("\n");
                mensaje.append("Precio Final: ").append(ticket.getPrecioFinal()).append(" €\n\n");
            }

            JOptionPane.showMessageDialog(null, mensaje.toString(), "Ventas Realizadas", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Método para filtrar los tickets según las fechas y mostrarTodos.
    private static List<AtributosTicket> filtrarTickets(LocalDate fechaInicio, LocalDate fechaFin, boolean mostrarTodos) {
        List<AtributosTicket> ticketsFiltrados = new ArrayList<>(); // Lista para almacenar los tickets filtrados.

        // Recorre todos los tickets en la listaDeTickets.
        for (AtributosTicket ticket : listaDeTickets) {
            LocalDate fechaOperacion = ticket.getFechaYHoraOperacion().toLocalDate(); // Obtiene la fecha de operación del ticket.

            // Verifica las condiciones de filtrado.
            if ((fechaInicio == null || fechaOperacion.isAfter(fechaInicio) || fechaOperacion.isEqual(fechaInicio))
                    && (fechaFin == null || fechaOperacion.isBefore(fechaFin) || fechaOperacion.isEqual(fechaFin))
                    && (mostrarTodos || fechaOperacion.isEqual(LocalDate.now()))) {
                ticketsFiltrados.add(ticket); // Agrega el ticket a la lista si cumple con las condiciones.
            }
        }

        return ticketsFiltrados; // Retorna la lista de tickets filtrados.
    }

    // Método para guardar un ticket en el archivo CSV
    private static void guardarTicketEnCSV(AtributosTicket ticket) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Tickets.csv", true))) {
            // Escribir los datos del ticket en una nueva línea del archivo CSV
            writer.write(ticket.toCSVFormat());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar el ticket en el archivo CSV: " + e.getMessage());
        }
    }

    // Método para leer todos los tickets desde el archivo "Tickets.csv"
    public static List<AtributosTicket> leerTicketsDesdeCSV(String archivo) {

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean primeraLinea = true; // Bandera para identificar la primera línea

            while ((linea = br.readLine()) != null) {
                // Si es la primera línea, simplemente pasa a la siguiente iteración del bucle
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                // Dividir la línea en campos utilizando coma como delimitador
                String[] campos = linea.split(",");

                // Verificar que haya suficientes campos para crear un ticket
                if (campos.length >= 4) {
                    try {
                        // Extraer los campos para construir un objeto AtributosTicket
                        double precioFinal = Double.parseDouble(campos[0]);
                        int idPedido = Integer.parseInt(campos[1]);
                        LocalDateTime fechaYHoraOperacion = LocalDateTime.parse(campos[2]);
                        List<String> productosComprados = Arrays.asList(Arrays.copyOfRange(campos, 3, campos.length));

                        // Crear un nuevo objeto AtributosTicket y agregarlo a la lista
                        listaDeTickets.add(new AtributosTicket(precioFinal, idPedido, fechaYHoraOperacion, productosComprados));
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException | DateTimeParseException e) {
                        // Manejar errores de conversión o formato de datos
                        System.out.println("Error al procesar la línea del archivo CSV: " + linea);
                        e.printStackTrace();
                    }
                } else {
                    // Si no hay suficientes campos, muestra un mensaje de error
                    System.out.println("Error: número incorrecto de campos en la línea del archivo CSV: " + linea);
                }
            }
        } catch (IOException e) {
            // Manejar cualquier error de lectura del archivo
            e.printStackTrace();
        }

        return listaDeTickets;
    }

}
