package szp.rafael.swarmexample;

import org.wildfly.swarm.jaxrs.JAXRSArchive;

/**
 * Created by rafaelszp on 11/17/16.
 */
public class Main {

  public static void main(String[] args) throws Exception {

        AppBuilder appBuilder = new AppBuilder();
        JAXRSArchive archive= appBuilder
				.createContainer()
				.addPackage(Person.class.getPackage())
				.withDefaultResources()
				.createDeployment()
                .withSSL(new HTTPSCertificate("keystore.jks","changeit","swarm-error-app"))
                .getDeployment();
        appBuilder.startAndDeploy();

  }

}
