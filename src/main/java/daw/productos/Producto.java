package daw.productos;

// Importar las clases necesarias desde otros paquetes.
import java.util.Objects;

/**
 * Clase con los atributos de un Producto.
 * @author acebedo
 */
public class Producto {

    // Atributos.
    private final String id;
    private String nombre;
    private double precio;
    private int enStock;
    private String descripcion;
    private String categoria;
    private double iva;
    private String subcategoria;

    // Constructor.
    public Producto(String id, String nombre, double precio, int enStock, String descripcion, String categoria, double iva, String subcategoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.enStock = enStock;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.iva = iva;
        this.subcategoria = subcategoria;
    }

    // Getters y Setters.
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getEnStock() {
        return enStock;
    }

    public void setEnStock(int enStock) {
        this.enStock = enStock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    // Metodo para calcular el precio con IVA.
    public double getPrecioConIVA() {
        double precioConIVA = precio + (precio * iva);
        return precioConIVA;
    }

    // hashCode.
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.nombre);
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.precio) ^ (Double.doubleToLongBits(this.precio) >>> 32));
        hash = 41 * hash + this.enStock;
        hash = 41 * hash + Objects.hashCode(this.descripcion);
        hash = 41 * hash + Objects.hashCode(this.categoria);
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.iva) ^ (Double.doubleToLongBits(this.iva) >>> 32));
        hash = 41 * hash + Objects.hashCode(this.subcategoria);
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
        final Producto other = (Producto) obj;
        if (Double.doubleToLongBits(this.precio) != Double.doubleToLongBits(other.precio)) {
            return false;
        }
        if (this.enStock != other.enStock) {
            return false;
        }
        if (Double.doubleToLongBits(this.iva) != Double.doubleToLongBits(other.iva)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.categoria, other.categoria)) {
            return false;
        }
        return Objects.equals(this.subcategoria, other.subcategoria);
    }
    
    // toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Productos{");
        sb.append("id=").append(id);
        sb.append(", nombre=").append(nombre);
        sb.append(", precio=").append(precio);
        sb.append(", enStock=").append(enStock);
        sb.append(", descripcion=").append(descripcion);
        sb.append(", categoria=").append(categoria);
        sb.append(", iva=").append(iva);
        sb.append(", subcategoria=").append(subcategoria);
        sb.append('}');
        return sb.toString();
    }
    
}
