/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPA;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author Gianluca Bensabat Calvano
 */
public class EMF {

    @PersistenceUnit
    private static EntityManagerFactory emf;

    public static EntityManager getEM() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("tarefa03PU");
        }
        return emf.createEntityManager();
    }
}
