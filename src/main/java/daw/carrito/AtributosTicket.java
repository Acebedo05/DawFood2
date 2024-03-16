package daw.carrito;

// Importar las clases necesarias desde otros paquetes.
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Clase con los atributos de un Ticket.
 * @author acebedo
 */
public class AtributosTicket {

    // Atributos.
    private double precioFinal;
    private int idPedido;
    private LocalDateTime fechaYHoraOperacion;
    private String productosComprados;

    // Constructor.
    public AtributosTicket(double precioFinal, int idPedido, LocalDateTime fechaYHoraOperacion, String productosComprados) {
        this.precioFinal = precioFinal;
        this.idPedido = idPedido;
        this.fechaYHoraOperacion = fechaYHoraOperacion;
        this.productosComprados = productosComprados;
    }

    // Getters y setters.
    public double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public LocalDateTime getFechaYHoraOperacion() {
        return fechaYHoraOperacion;
    }

    public void setFechaYHoraOperacion(LocalDateTime fechaYHoraOperacion) {
        this.fechaYHoraOperacion = fechaYHoraOperacion;
    }

    public String getProductosComprados() {
        return productosComprados;
    }

    public void setProductosComprados(String productosComprados) {
        this.productosComprados = productosComprados;
    }

    // hashCode.
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.precioFinal) ^ (Double.doubleToLongBits(this.precioFinal) >>> 32));
        hash = 59 * hash + this.idPedido;
        hash = 59 * hash + Objects.hashCode(this.fechaYHoraOperacion);
        hash = 59 * hash + Objects.hashCode(this.productosComprados);
        return hash;
    }

    // equals.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AtributosTicket other = (AtributosTicket) obj;
        if (Double.doubleToLongBits(this.precioFinal) != Double.doubleToLongBits(other.precioFinal)) {
            return false;
        }
        if (this.idPedido != other.idPedido) {
            return false;
        }
        if (!Objects.equals(this.productosComprados, other.productosComprados)) {
            return false;
        }
        return Objects.equals(this.fechaYHoraOperacion, other.fechaYHoraOperacion);
    }

    // toString.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Restaurante DawFood, Ticket de compra:\n\n");
        sb.append("Numero de pedido: ").append(idPedido);
        sb.append("\nHorarioOperacion -> ").append(fechaYHoraOperacion);
        sb.append("\nPrecio Total: ").append(precioFinal).append(" €");
        sb.append("\n" + productosComprados);
        return sb.toString();
    }

}
