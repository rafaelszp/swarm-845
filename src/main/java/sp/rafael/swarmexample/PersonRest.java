package sp.rafael.swarmexample;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by rafaelszp on 11/17/16.
 */
@Path("persons")
@Produces({MediaType.APPLICATION_JSON})
public class PersonRest {


    @Inject
    EntityManager em;

    Logger logger = Logger.getLogger(this.getClass().getName());

    @GET
    public Response get(){
        List<Person> all = em.createQuery("select p from Person p").getResultList();
        logger.info(String.format("Showing {%s} person(s)",all.size()));
        return Response.ok(all).build();
    }
}
