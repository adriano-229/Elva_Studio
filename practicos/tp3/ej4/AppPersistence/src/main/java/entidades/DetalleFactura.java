package entidades;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "detalle_factura")
@Audited
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DetalleFactura implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long detalleFacturaId;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "subtotal", nullable = false)
    private double subtotal;

    // Articulo debe existir antes; no cascades destructivos. Si quieres crear artículos on-the-fly añade CascadeType.PERSIST.
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_articulo")
    private Articulo articulo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_factura")
    private Factura factura;

    @Override
    public String toString() {
        return "DetalleFactura{" +
                "id=" + detalleFacturaId +
                ", cantidad=" + cantidad +
                ", subtotal=" + subtotal +
                '}';
    }
}
