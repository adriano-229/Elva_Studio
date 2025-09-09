package entidades;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "detalle_factura")
public class DetalleFactura implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long detalleFacturaId;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "subtotal")
    private double subtotal;

    public DetalleFactura(int cantidad, double subtotal) {
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public DetalleFactura() {
        // Constructor por defecto necesario para JPA
    }

    public Long getDetalleFacturaId() {
        return detalleFacturaId;
    }

    public void setDetalleFacturaId(Long detalleFacturaId) {
        this.detalleFacturaId = detalleFacturaId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
