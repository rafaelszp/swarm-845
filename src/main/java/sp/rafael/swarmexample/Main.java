package sp.rafael.swarmexample;

import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

/**
 * Created by rafaelszp on 11/17/16.
 */
public class Main {

  public static void main(String[] args) throws Exception {

        AppBuilder appBuilder = new AppBuilder();
        JAXRSArchive deployment= appBuilder
				.createContainer()
				.addPackage(Person.class.getPackage())
				.withDefaultResources()
				.createDeployment()
                .withSSL(new HTTPSCertificate("keystore.jks","changeit","swarm-error-app"))
                .getDeployment();
        appBuilder.startAndDeploy();

//	ClassLoader cl = Main.class.getClassLoader();
//	String stageConfig = cl.getResource("project-stages.yml").getFile();
//	Swarm swarm = new Swarm(false).withConfig(new URL("file://" + stageConfig));
//	JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
//	deployment.addPackages(true, Person.class.getPackage());
//
//	deployment.addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"));
//	String persistenceFile = "META-INF/persistence.xml";
//	File persistenceXml = new File(cl.getResource(persistenceFile).getFile());
//	deployment.addAsWebInfResource(persistenceXml,"classes/"+persistenceFile);
//	System.out.println(deployment.toString());
//
//	swarm.fraction(new LoggingFraction().applyDefaults());
//	swarm.fraction(datasource(swarm));
//	swarm.start();
//	swarm.deploy(deployment);

  }

  protected static DatasourcesFraction datasource(Swarm swarm) {
	String module = swarm.stageConfig().resolve("jdbc.driver.module").getValue();
	String datasource = swarm.stageConfig().resolve("datasource.name").getValue();
	return new DatasourcesFraction()
			.jdbcDriver(module, (d) -> {
			  d.driverClassName(swarm.stageConfig().resolve("jdbc.driver.class").getValue());
			  d.xaDatasourceClass(swarm.stageConfig().resolve("jdbc.driver.xa-class").getValue());
			  d.driverModuleName(module);
			})
			.dataSource(datasource, (ds) -> {
			  ds.driverName(module);
			  ds.connectionUrl(swarm.stageConfig().resolve("database.connection.url").getValue());
			  ds.userName(swarm.stageConfig().resolve("database.connection.username").getValue());
			  ds.password(swarm.stageConfig().resolve("database.connection.password").getValue());
			});
  }
}
