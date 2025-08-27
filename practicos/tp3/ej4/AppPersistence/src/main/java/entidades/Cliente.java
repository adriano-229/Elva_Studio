package entidades;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cliente")
@Audited
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cliente implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long clienteId;

    @NonNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NonNull
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "dni", unique = true, nullable = false)
    private int dni;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fk_domicilio")
    private Domicilio domicilio;

    @Builder.Default
    @OneToMany(mappedBy = "cliente", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Factura> facturas = new ArrayList<>();

    // Helper methods
    public void addFactura(Factura factura) {
        if (factura == null) return;
        if (!facturas.contains(factura)) facturas.add(factura);
        if (factura.getCliente() != this) factura.setCliente(this);
    }

    public void removeFactura(Factura factura) {
        if (factura == null) return;
        facturas.remove(factura);
        if (factura.getCliente() == this) factura.setCliente(null);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + clienteId +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni=" + dni +
                '}';
    }
}
