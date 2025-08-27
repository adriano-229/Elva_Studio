package main;

import entidades.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceApp {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AppPersistenceUnit");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

//            // Cliente + domicilio (builder Lombok)
//            Domicilio domicilio = Domicilio.builder()
//                    .nombreCalle("Calle Principal")
//                    .numero(456)
//                    .build();
//
//            Cliente cliente = Cliente.builder()
//                    .nombre("María")
//                    .apellido("Gómez")
//                    .dni(87654321)
//                    .domicilio(domicilio)
//                    .build();
//
//
//            em.persist(cliente); // cascada a domicilio
//
//            // Categorías y artículos
//            Categoria perecederos = Categoria.builder().denominacion("Perecederos").build();
//            Categoria lacteos = Categoria.builder().denominacion("Lácteos").build();
//            Categoria limpieza = Categoria.builder().denominacion("Limpieza").build();
//
//            Articulo yogurt = Articulo.builder()
//                    .cantidad(200)
//                    .denominacion("Yogurt Entero")
//                    .precio(1499)
//                    .build();
//            Articulo detergente = Articulo.builder()
//                    .cantidad(300)
//                    .denominacion("Detergente Líquido")
//                    .precio(3000)
//                    .build();
//
//            yogurt.addCategoria(perecederos);
//            yogurt.addCategoria(lacteos);
//            detergente.addCategoria(limpieza);
//
//            em.persist(yogurt);
//            em.persist(detergente);
//
//            // Factura y detalles
//            Factura factura = Factura.builder()
//                    .numero(12)
//                    .cliente(cliente)
//                    .fecha("2023-10-10")
//                    .build();
//
//            Factura factura2 = Factura.builder().build();
//
//            DetalleFactura detalle1 = DetalleFactura.builder()
//                    .cantidad(2)
//                    .subtotal(2 * yogurt.getPrecio())
//                    .articulo(yogurt)
//                    .build();
//            factura.addDetalle(detalle1);
//
//            DetalleFactura detalle2 = DetalleFactura.builder()
//                    .cantidad(5)
//                    .subtotal(5 * detergente.getPrecio())
//                    .articulo(detergente)
//                    .build();
//            factura.addDetalle(detalle2);
//
//            factura.setTotal((int) (detalle1.getSubtotal() + detalle2.getSubtotal()));

    //            Factura factura = em.find(Factura.class, 1L);
    //            factura.setNumero(85);
    //
    //            em.merge(factura);
    //            em.remove(factura);
    //
    //            em.getTransaction().commit();

//            System.out.println("Factura persistida id=" + factura.getFacturaId());
//            System.out.println("Detalles: " + factura.getDetallesFactura().size());
//
//            System.out.println("Factura con datos: " + factura);
//            System.out.println("Factura sin datos: " + factura2);

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
