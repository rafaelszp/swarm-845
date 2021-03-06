package szp.rafael.swarmexample;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * Created by rafaelszp on 12/7/16.
 */
public class Resource {


    @PersistenceUnit
    private EntityManagerFactory emf;

    @Produces
    @RequestScoped
    @Default
    public EntityManager create() {
        return emf.createEntityManager();
    }

    public void close(@Disposes EntityManager em) {
        if (em.isOpen()) {
            em.close();
        }
    }


}
