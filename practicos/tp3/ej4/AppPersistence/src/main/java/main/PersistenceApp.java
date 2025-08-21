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
            em.getTransaction().begin(); // Inicia una nueva transacción en la base de datos

            Cliente cliente = new Cliente("Juan", "Pérez", 12345678);
            Domicilio domicilio = new Domicilio("Calle Falsa", 123);
            cliente.setDomicilio(domicilio); // Asocia el dom al cliente
            domicilio.setCliente(cliente); // Asocia el cliente al domicilio (esto es opcional, pero útil si necesitas acceder al cliente desde el domicilio)

            em.persist(cliente); // Marca el objeto cli para ser insertado en la base de datos
            em.flush(); // Fuerza la sincronización inmediata de los cambios pendientes con la base de datos

            Domicilio dom = em.find(Domicilio.class, 1L); // Busca un dom existente por su ID
            Cliente cli = em.find(Cliente.class, 1L); // Busca un cli existente por su ID
            System.out.println("Obtengo Cliente desde Domicilio: " + dom.getCliente().getNombre() + " " + dom.getCliente().getApellido());
            System.out.println("Obtengo Domicilio desde Cliente: " + cli.getDomicilio().getNombreCalle() + " " + cli.getDomicilio().getNumero());

            
            // Si se llama a flush, pero no a commit, los cambios se envían a la base de datos pero no se confirman; si ocurre un rollback o termina el bloque sin commit, los cambios se pierden.
            em.getTransaction().commit(); // Confirma la transacción y guarda los cambios en la base de datos

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Revierte la transacción en caso de error
            }
            e.printStackTrace(); // Imprime el error en la consola para depuración
        } finally {
            em.close(); // Cierra el EntityManager
            emf.close(); // Cierra el EntityManagerFactory

        }
    }
}
