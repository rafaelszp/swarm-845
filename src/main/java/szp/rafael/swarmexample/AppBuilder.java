package szp.rafael.swarmexample;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.logging.LoggingFraction;
import org.wildfly.swarm.undertow.UndertowFraction;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafaelszp on 10/6/16.
 */
public class AppBuilder {


  private List<Package> packages;
  private List<String> webInfResources;
  private List<Class> classResources;
  private List<String> resources;
  private Swarm swarm;
  private HTTPSCertificate certificate;
  private JAXRSArchive deployment;

  public AppBuilder() {
//        System.setProperty("java.util.logging.manager","org.jboss.logmanager.LogManager");
	packages = new ArrayList<>();
	webInfResources = new ArrayList<>();
	classResources = new ArrayList<>();
	resources = new ArrayList<>();
  }

  public AppBuilder addPackage(Package pkg) {
	packages.add(pkg);
	return this;
  }

  public AppBuilder addPackages(List<Package> pkgs) {
	packages.addAll(pkgs);
	return this;
  }

  public AppBuilder addWebInfResource(String wir) {
	webInfResources.add(wir);
	return this;
  }

  public AppBuilder addWebInfResources(List<String> wirs) {
	webInfResources.addAll(wirs);
	return this;
  }

  public AppBuilder addClass(Class cls) {
	classResources.add(cls);
	return this;
  }

  public AppBuilder addClasses(List<Class> classes) {
	classResources.addAll(classes);
	return this;
  }

  public AppBuilder addResource(String res) {
	resources.add(res);
	return this;
  }

  public AppBuilder addResource(List<String> resources) {
	resources.addAll(resources);
	return this;
  }

  public AppBuilder withDefaultResources() {
	return this.addWebInfResource("src/main/webapp/WEB-INF/beans.xml")
			.addResource("META-INF/persistence.xml")
			.addResource("project-stages.yml");
  }

  public AppBuilder withSSL(HTTPSCertificate cert) {
	this.certificate = cert;
	swarm.fraction(UndertowFraction.createDefaultFraction(cert.getKeystorePath(),
			cert.getPassword(), cert.getAlias()));
	return this;
  }

  public JAXRSArchive getDeployment() {
	return deployment;
  }

  public AppBuilder createDeployment() throws Exception {
	this.deployment = ShrinkWrap.create(JAXRSArchive.class);
	packages.forEach(p -> {
	  System.out.printf("Adding package [%s]\n", p);
	  this.deployment.addPackages(true, p);
	});
	webInfResources.forEach(res -> {
	  System.out.printf("Adding resource [%s]\n", res);
	  this.deployment.addAsWebInfResource(new File(res));
	});
	classResources.forEach(cls -> {
	  System.out.printf("Adding class resource [%s]\n", cls.getName());
	  this.deployment.addResource(cls);
	});
	resources.forEach(r -> {
	  System.out.printf("Adding resource [%s]\n", r);
	  this.deployment.addAsWebInfResource(new ClassLoaderAsset(r, AppBuilder.class.getClassLoader()), "classes/" + r);
	});

	this.deployment.addAllDependencies();
	return this;
  }


  public AppBuilder createContainer() throws Exception {
	ClassLoader cl = AppBuilder.class.getClassLoader();
	String stageConfig = cl.getResource("project-stages.yml").getFile();
	try {
	  this.swarm = new Swarm(false).withConfig(new URL("file://" + stageConfig));
	} catch (Exception e) {
	  this.swarm = new Swarm(false);
	}

	LoggingFraction loggingFraction = new LoggingFraction();
	this.swarm.fraction(loggingFraction.applyDefaults());
	this.swarm.fraction(datasource());
	return this;
  }

  public Swarm getSwarm() {
	return swarm;
  }

  public void startAndDeploy() {
	try {
	  this.swarm.start();
	  System.out.println(this.deployment.toString(true));
	  this.swarm.deploy(this.deployment);
	} catch (Exception e) {
	  throw new RuntimeException(e);
	}
  }

  protected ClassLoader getClassLoader() {
	return this.getClass().getClassLoader();
  }

  public DatasourcesFraction datasource() {
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
