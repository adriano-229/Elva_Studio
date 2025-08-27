package entidades;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "domicilio")
@Audited
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Domicilio implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long domicilioId;

    @NonNull
    @Column(name = "calle", nullable = false)
    private String nombreCalle;

    @Column(name = "numero", nullable = false)
    private int numero;

    @OneToOne(mappedBy = "domicilio")
    private Cliente cliente;

    public void asignarACliente(Cliente cliente) {
        this.cliente = cliente;
        if (cliente != null && cliente.getDomicilio() != this) {
            cliente.setDomicilio(this);
        }
    }

    @Override
    public String toString() {
        return "Domicilio{" +
                "id=" + domicilioId +
                ", calle='" + nombreCalle + '\'' +
                ", numero=" + numero +
                '}';
    }
}
