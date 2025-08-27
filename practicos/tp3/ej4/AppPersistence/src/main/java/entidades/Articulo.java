package entidades;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "articulo")
@Audited
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Articulo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long articuloId;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "denominacion")
    private String denominacion;

    @Column(name = "precio")
    private double precio;

    // Owning side ManyToMany: cascade persist/merge so new categorías are saved with artículo
    @Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "articulo_categoria",
            joinColumns = @JoinColumn(name = "fk_articulo"),
            inverseJoinColumns = @JoinColumn(name = "fk_categoria")
    )
    private List<Categoria> categorias = new ArrayList<>();

    // Inverse side OneToMany (DetalleFactura lifecycle managed by Factura)
    @Builder.Default
    @OneToMany(mappedBy = "articulo")
    private List<DetalleFactura> detalleFacturas = new ArrayList<>();

    // Helper methods
    public void addCategoria(Categoria categoria) {
        if (categoria == null) return;
        if (!categorias.contains(categoria)) categorias.add(categoria);
        if (!categoria.getArticulos().contains(this)) categoria.getArticulos().add(this);
    }

    public void removeCategoria(Categoria categoria) {
        if (categoria == null) return;
        categorias.remove(categoria);
        categoria.getArticulos().remove(this);
    }
}