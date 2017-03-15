package szp.rafael.swarmexample;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

/**
 * Created by rafaelszp on 11/17/16.
 */
@Path("hello")
public class HelloRest {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @GET
    public Response get(){
        logger.info(String.format("I'll say hello"));
        return Response.ok().build();
    }
}
