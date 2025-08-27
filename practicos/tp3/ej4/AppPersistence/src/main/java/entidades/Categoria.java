package entidades;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categoria")
@Audited
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Categoria implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long categoriaId;

    @NonNull
    @Column(name = "denominacion", unique = true, nullable = false)
    private String denominacion;

    // Lado inverso: no definimos cascade aquí; lo controla el owning side (Articulo.categorias)
    @Builder.Default
    @ManyToMany(mappedBy = "categorias")
    private List<Articulo> articulos = new ArrayList<>();

    public void addArticulo(Articulo articulo) {
        if (articulo == null) return;
        if (!articulos.contains(articulo)) articulos.add(articulo);
        if (!articulo.getCategorias().contains(this)) articulo.getCategorias().add(this);
    }

    public void removeArticulo(Articulo articulo) {
        if (articulo == null) return;
        articulos.remove(articulo);
        articulo.getCategorias().remove(this);
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + categoriaId +
                ", denominacion='" + denominacion + '\'' +
                '}';
    }
}
