# swarm-error

##Problems

1) running java -jar target/swarm-error-1.0-SNAPSHOT-swarm.jar I'll get this error:

		
		Could not load Logmanager "org.jboss.logmanager.LogManager"
java.lang.ClassNotFoundException: org.jboss.logmanager.LogManager
	at java.net.URLClassLoader.findClass(URLClassLoader.java:381)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:331)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
	at java.util.logging.LogManager$1.run(LogManager.java:195)
	at java.util.logging.LogManager$1.run(LogManager.java:181)
	at java.security.AccessController.doPrivileged(Native Method)
	at java.util.logging.LogManager.<clinit>(LogManager.java:181)
	at java.util.logging.Logger.demandLogger(Logger.java:448)
	at java.util.logging.Logger.getLogger(Logger.java:502)
	at org.jboss.shrinkwrap.api.ConfigurationBuilder.<clinit>(ConfigurationBuilder.java:51)
	at org.jboss.shrinkwrap.api.ShrinkWrap.createDomain(ShrinkWrap.java:60)
	at org.jboss.shrinkwrap.api.ShrinkWrap$DefaultDomainWrapper.<init>(ShrinkWrap.java:204)
	at org.jboss.shrinkwrap.api.ShrinkWrap$DefaultDomainWrapper.<clinit>(ShrinkWrap.java:199)
	at org.jboss.shrinkwrap.api.ShrinkWrap.getDefaultDomain(ShrinkWrap.java:108)
	at org.jboss.shrinkwrap.api.ShrinkWrap.create(ShrinkWrap.java:136)
	at sp.rafael.swarmerror.AppBuilder.createDeployment(AppBuilder.java:95)
	at sp.rafael.swarmerror.Main.main(Main.java:16)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.wildfly.swarm.bootstrap.MainInvoker.invoke(MainInvoker.java:37)
	at org.wildfly.swarm.bootstrap.Main.run(Main.java:44)
	at org.wildfly.swarm.bootstrap.Main.main(Main.java:35)

 


## Steps to reproduce

1. package

		mvn clean package -DskipTests

2. Run
		
		java -jar target/swarm-error-1.0-SNAPSHOT-swarm.jar

3. Try with wildfly-swarm-plugin

		mvn wildfly-swar:run 
