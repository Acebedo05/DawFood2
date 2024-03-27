package daw.carrito;

// Importar las clases necesarias desde otros paquetes.
import java.time.LocalDateTime;
import java.util.List;
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
    private List<String> productosComprados;

    // Constructor.
    public AtributosTicket(double precioFinal, int idPedido, LocalDateTime fechaYHoraOperacion, List<String> productosComprados) {
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

    public List<String> getProductosComprados() {
        return productosComprados;
    }

    public void setProductosComprados(List<String> productosComprados) {
        this.productosComprados = productosComprados;
    }

    // hashCode.
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + (int) (Double.doubleToLongBits(this.precioFinal) ^ (Double.doubleToLongBits(this.precioFinal) >>> 32));
        hash = 31 * hash + this.idPedido;
        hash = 31 * hash + Objects.hashCode(this.fechaYHoraOperacion);
        hash = 31 * hash + Objects.hashCode(this.productosComprados);
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
        if (!Objects.equals(this.fechaYHoraOperacion, other.fechaYHoraOperacion)) {
            return false;
        }
        return Objects.equals(this.productosComprados, other.productosComprados);
    }

    // toString.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AtributosTicket{");
        sb.append("precioFinal=").append(precioFinal);
        sb.append(", idPedido=").append(idPedido);
        sb.append(", fechaYHoraOperacion=").append(fechaYHoraOperacion);
        sb.append(", productosComprados=").append(productosComprados);
        sb.append('}');
        return sb.toString();
    }

    // Método para formatear los datos del ticket en una cadena CSV
    public String toCSVFormat() {
        StringBuilder sb = new StringBuilder();
        sb.append(precioFinal).append(",");
        sb.append(idPedido).append(",");
        sb.append(fechaYHoraOperacion).append(",");
        sb.append(productosComprados);
        return sb.toString();
    }

}
