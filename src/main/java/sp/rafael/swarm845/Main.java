package sp.rafael.swarm845;

import org.jboss.shrinkwrap.api.Archive;
import org.wildfly.swarm.Swarm;

/**
 * Created by rafaelszp on 11/17/16.
 */
public class Main {

    public static void main(String[] args) throws Exception{

        SwarmTool swarmTool = new SwarmTool();
        Archive war= swarmTool
                .addPackage(HelloRest.class.getPackage())
                .comResourcesPadrao()
                .criarDeployment();

        Swarm swarm = swarmTool.criarSwarm();
        swarmTool.getSwarm().start();
        swarmTool.getSwarm().deploy(war);


    }
}
