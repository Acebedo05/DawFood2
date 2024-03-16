package daw.tpv;

// Importar las clases necesarias desde otros paquetes.
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * Clase con los atributos de un TPV.
 * @author acebedo
 */
public class AtributosTPV {

    // Atributos.
    private String ubicacion;
    private Date fechaHoraActual;
    private UUID numeroSerie;
    private String passwordAdmin;

    // Costructor.
    public AtributosTPV(String ubicacion, String passwordAdmin) {
        this.ubicacion = ubicacion;
        this.fechaHoraActual = new Date();
        this.numeroSerie = UUID.randomUUID();
        this.passwordAdmin = passwordAdmin;
    }

    // Costructor vacío.
    public AtributosTPV() {
        this.fechaHoraActual = new Date();
        this.numeroSerie = UUID.randomUUID();
    }

    // Getters y Setters.
    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Date getFechaHoraActual() {
        return fechaHoraActual;
    }

    public void setFechaHoraActual(Date fechaHoraActual) {
        this.fechaHoraActual = fechaHoraActual;
    }

    public UUID getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(UUID numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getPasswordAdmin() {
        return passwordAdmin;
    }

    public void setPasswordAdmin(String passwordAdmin) {
        this.passwordAdmin = passwordAdmin;
    }

    // hashCode.
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.ubicacion);
        hash = 53 * hash + Objects.hashCode(this.fechaHoraActual);
        hash = 53 * hash + Objects.hashCode(this.numeroSerie);
        hash = 53 * hash + Objects.hashCode(this.passwordAdmin);
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
        final AtributosTPV other = (AtributosTPV) obj;
        if (!Objects.equals(this.ubicacion, other.ubicacion)) {
            return false;
        }
        if (!Objects.equals(this.passwordAdmin, other.passwordAdmin)) {
            return false;
        }
        if (!Objects.equals(this.fechaHoraActual, other.fechaHoraActual)) {
            return false;
        }
        return Objects.equals(this.numeroSerie, other.numeroSerie);
    }

    // toString.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AtributosTPV{");
        sb.append("ubicacion=").append(ubicacion);
        sb.append(", fechaHoraActual=").append(fechaHoraActual);
        sb.append(", numeroSerie=").append(numeroSerie);
        sb.append(", passwordAdmin=").append(passwordAdmin);
        sb.append('}');
        return sb.toString();
    }
    
}
