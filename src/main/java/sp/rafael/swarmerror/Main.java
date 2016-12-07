package sp.rafael.swarmerror;

import org.wildfly.swarm.jaxrs.JAXRSArchive;

/**
 * Created by rafaelszp on 11/17/16.
 */
public class Main {

    public static void main(String[] args) throws Exception{

        AppBuilder appBuilder = new AppBuilder();
        JAXRSArchive deployment= appBuilder
                .addPackage(HelloRest.class.getPackage())
                .withDefaultResources()
                .createDeployment()
                .createContainer()
                .withSSL(new HTTPSCertificate("keystore.jks","changeit","swarm-error-app"))
                .getDeployment();
        //WHY DO I NEED TO DO THIS? Otherwise context wont change
        deployment.setContextRoot(appBuilder.getSwarm().stageConfig().resolve("swarm.context.path").getValue());
        appBuilder.startAndDeploy();
    }
}
