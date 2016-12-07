package sp.rafael.swarmerror;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by rafaelszp on 11/17/16.
 */
@Path("hello")
public class HelloRest {

    @GET
    public Response get(){
        return Response.ok().build();
    }
}
