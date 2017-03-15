package it.szp.rafael.swarmexample;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.arquillian.CreateSwarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import szp.rafael.swarmexample.AppBuilder;
import szp.rafael.swarmexample.HTTPSCertificate;
import szp.rafael.swarmexample.Person;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by rafael on 3/13/17.
 */
public class AppIT extends Arquillian{

  static AppBuilder builder = new AppBuilder();


  @BeforeSuite
  public void beforeSuite() throws Exception {

  }


  @Deployment(testable = false)
  public static Archive deployment() throws Exception {
	JAXRSArchive archive = builder
			.addPackage(Person.class.getPackage())
			.addResource("import.sql")
			.withDefaultResources()
			.createDeployment()
			.getDeployment();
	archive.setContextRoot("/swarm-example");
	return archive;
  }

  @CreateSwarm
  public static Swarm createSwarm() throws Exception {
	builder.createContainer();
	Swarm swarm = builder.getSwarm();
	return swarm;
  }

  @RunAsClient
  @Test
  public void shouldGet() throws IOException {
	ObjectMapper mapper = new ObjectMapper();
	final Response response = given().get("swarm-example/api/persons")
			.then().extract().response();
	final String json = response.asString();
	final List<Person> empresas = Arrays.asList(mapper.readValue(json.getBytes(), Person[].class));
	assertThat(response.getStatusCode()).isEqualTo(200);
	empresas.forEach(s-> System.out.print(s.getName()+", "));
  }

}
