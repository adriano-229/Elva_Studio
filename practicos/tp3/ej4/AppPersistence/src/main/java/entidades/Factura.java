package entidades;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "factura")
@Audited
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Factura implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long facturaId;

    @Column(name = "fecha", nullable = false)
    private String fecha;

    @Column(name = "numero", unique = true, nullable = false)
    private int numero;

    @Column(name = "total")
    private int total;

    @ManyToOne
    @JoinColumn(name = "fk_cliente")
    private Cliente cliente;

    @Builder.Default
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleFactura> detallesFactura = new ArrayList<>();

    // Helpers bidireccionales
    public void addDetalle(DetalleFactura detalle) {
        if (detalle == null) return;
        if (!detallesFactura.contains(detalle)) {
            detallesFactura.add(detalle);
        }
        if (detalle.getFactura() != this) {
            detalle.setFactura(this);
        }
    }

    public void removeDetalle(DetalleFactura detalle) {
        if (detalle == null) return;
        detallesFactura.remove(detalle);
        if (detalle.getFactura() == this) {
            detalle.setFactura(null);
        }
    }

    @Override
    public String toString() {
        return "Factura{" +
                "id=" + facturaId +
                ", numero=" + numero +
                ", fecha='" + fecha + '\'' +
                ", total=" + total +
                '}';
    }
}
