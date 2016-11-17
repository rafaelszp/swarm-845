package sp.rafael.swarm845;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafaelszp on 10/6/16.
 */
public class SwarmTool {


    private List<Package> packages;
    private List<String> webInfResources;
    private List<Class> classResources;
    private List<String> resources;
    private Swarm swarm;

    public SwarmTool(){
        System.setProperty("java.util.logging.manager","org.jboss.logmanager.LogManager");
        packages = new ArrayList<>();
        webInfResources = new ArrayList<>();
        classResources = new ArrayList<>();
        resources = new ArrayList<>();
    }

    public SwarmTool addPackage(Package pkg){
        packages.add(pkg);
        return this;
    }

    public SwarmTool addPackages(List<Package> pkgs){
        packages.addAll(pkgs);
        return this;
    }

    public SwarmTool addWebInfResource(String wir){
        webInfResources.add(wir);
        return this;
    }

    public SwarmTool addWebInfResources(List<String> wirs){
        webInfResources.addAll(wirs);
        return this;
    }

    public SwarmTool addClass(Class cls){
        classResources.add(cls);
        return this;
    }

    public SwarmTool addClasses(List<Class> classes){
        classResources.addAll(classes);
        return this;
    }

    public SwarmTool addResource(String res){
        resources.add(res);
        return this;
    }

    public SwarmTool addResource(List<String> resources){
        resources.addAll(resources);
        return this;
    }

    public SwarmTool comResourcesPadrao(){
        return this.addWebInfResource("src/main/webapp/WEB-INF/beans.xml")
                .addResource("META-INF/persistence.xml")
                .addResource("project-stages.yml");
    }

    public Archive criarDeployment() throws Exception {
        JAXRSArchive deployment = ShrinkWrap.create( JAXRSArchive.class );
        packages.forEach(p->deployment.addPackages(true,p));
        webInfResources.forEach(res -> deployment.addAsWebInfResource(new File(res)));
        classResources.forEach(cls -> deployment.addResource(cls));
        resources.forEach(r ->{
            deployment.addAsWebInfResource(new ClassLoaderAsset(r,SwarmTool.class.getClassLoader()),"classes/"+r);
        });

        deployment.addAllDependencies();
        return deployment;
    }

    public Swarm criarSwarm() throws Exception {
        ClassLoader cl = SwarmTool.class.getClassLoader();
        String stageConfig = cl.getResource("project-stages.yml").getFile();
        swarm = new Swarm(false).withStageConfig(new URL("file://"+stageConfig));
        swarm.fraction(datasource());
        return swarm;
    }

    public Swarm getSwarm() {
        return swarm;
    }

    protected ClassLoader getClassLoader(){
        return this.getClass().getClassLoader();
    }

    protected DatasourcesFraction datasource() {
        String module = swarm.stageConfig().resolve("jdbc.driver.module").getValue();
        String datasource = swarm.stageConfig().resolve("datasource.name").getValue();
        return new DatasourcesFraction()
                .jdbcDriver(module,(d)->{
                    d.driverClassName(swarm.stageConfig().resolve("jdbc.driver.class").getValue());
                    d.xaDatasourceClass(swarm.stageConfig().resolve("jdbc.driver.xa-class").getValue());
                    d.driverModuleName(module);
                })
                .dataSource(datasource,(ds)->{
                    ds.driverName(module);
                    ds.connectionUrl(swarm.stageConfig().resolve("database.connection.url").getValue());
                    ds.userName(swarm.stageConfig().resolve("database.connection.username").getValue());
                    ds.password(swarm.stageConfig().resolve("database.connection.password").getValue());
                });
    }

}
