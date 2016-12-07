# swarm-error

##Problems

1) Main.java:22
- Context won't change, I checked jboss-web.xml and it isnt reflecting swarm.context.path from project-stages.yml

2) pom.xml:100
- If I use cdi fraction this app will break after running main method. 

    - Error:
            
            2016-12-07 14:36:14,247 ERROR [org.jboss.msc.service.fail] (MSC service thread 1-2) MSC000001: Failed to start service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".WeldStartService: org.jboss.msc.service.StartException in service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".WeldStartService: Failed to start service
            	at org.jboss.msc.service.ServiceControllerImpl$StartTask.run(ServiceControllerImpl.java:1904)
            	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
            	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
            	at java.lang.Thread.run(Thread.java:745)
            Caused by: org.jboss.weld.exceptions.DeploymentException: WELD-001408: Unsatisfied dependencies for type SocketBindingGroup with qualifiers @Named
              at injection point [BackedAnnotatedField] @Inject @Named private org.wildfly.swarm.management.runtime.ManagementSocketBindingsCustomizer.group
              at org.wildfly.swarm.management.runtime.ManagementSocketBindingsCustomizer.group(ManagementSocketBindingsCustomizer.java:0)
            
            	at org.jboss.weld.bootstrap.Validator.validateInjectionPointForDeploymentProblems(Validator.java:359)
            	at org.jboss.weld.bootstrap.Validator.validateInjectionPoint(Validator.java:281)
            	at org.jboss.weld.bootstrap.Validator.validateGeneralBean(Validator.java:134)
            	at org.jboss.weld.bootstrap.Validator.validateRIBean(Validator.java:155)
            	at org.jboss.weld.bootstrap.Validator.validateBean(Validator.java:518)
            	at org.jboss.weld.bootstrap.ConcurrentValidator$1.doWork(ConcurrentValidator.java:68)
            	at org.jboss.weld.bootstrap.ConcurrentValidator$1.doWork(ConcurrentValidator.java:66)
            	at org.jboss.weld.executor.IterativeWorkerTaskFactory$1.call(IterativeWorkerTaskFactory.java:63)
            	at org.jboss.weld.executor.IterativeWorkerTaskFactory$1.call(IterativeWorkerTaskFactory.java:56)
            	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
            	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
            	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
            	at java.lang.Thread.run(Thread.java:745)
            	at org.jboss.threads.JBossThread.run(JBossThread.java:320)
            
            2016-12-07 14:36:14,251 ERROR [org.jboss.as.controller.management-operation] (main) WFLYCTL0013: Operation ("add") failed - address: (("deployment" => "f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war")) - failure description: {
                "WFLYCTL0080: Failed services" => {"jboss.deployment.unit.\"f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war\".WeldStartService" => "org.jboss.msc.service.StartException in service jboss.deployment.unit.\"f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war\".WeldStartService: Failed to start service
                Caused by: org.jboss.weld.exceptions.DeploymentException: WELD-001408: Unsatisfied dependencies for type SocketBindingGroup with qualifiers @Named
              at injection point [BackedAnnotatedField] @Inject @Named private org.wildfly.swarm.management.runtime.ManagementSocketBindingsCustomizer.group
              at org.wildfly.swarm.management.runtime.ManagementSocketBindingsCustomizer.group(ManagementSocketBindingsCustomizer.java:0)
            "},
                "WFLYCTL0412: Required services that are not installed:" => ["jboss.deployment.unit.\"f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war\".WeldStartService"],
                "WFLYCTL0180: Services with missing/unavailable dependencies" => undefined
            }
            2016-12-07 14:36:14,253 ERROR [org.jboss.as.server] (main) WFLYSRV0021: Deploy of deployment "f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war" was rolled back with the following failure message: 
            {
                "WFLYCTL0080: Failed services" => {"jboss.deployment.unit.\"f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war\".WeldStartService" => "org.jboss.msc.service.StartException in service jboss.deployment.unit.\"f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war\".WeldStartService: Failed to start service
                Caused by: org.jboss.weld.exceptions.DeploymentException: WELD-001408: Unsatisfied dependencies for type SocketBindingGroup with qualifiers @Named
              at injection point [BackedAnnotatedField] @Inject @Named private org.wildfly.swarm.management.runtime.ManagementSocketBindingsCustomizer.group
              at org.wildfly.swarm.management.runtime.ManagementSocketBindingsCustomizer.group(ManagementSocketBindingsCustomizer.java:0)
            "},
                "WFLYCTL0412: Required services that are not installed:" => ["jboss.deployment.unit.\"f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war\".WeldStartService"],
                "WFLYCTL0180: Services with missing/unavailable dependencies" => undefined
            }
            2016-12-07 14:36:14,259 INFO  [org.jboss.as.connector.deployers.jdbc] (MSC service thread 1-6) WFLYJCA0019: Stopped Driver service with driver-name = f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war_org.h2.Driver_1_4
            2016-12-07 14:36:14,261 INFO  [org.jboss.as.jpa] (ServerService Thread Pool -- 10) WFLYJPA0011: Stopping Persistence Unit (phase 2 of 2) Service 'f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war#getinapp-unit'
            2016-12-07 14:36:14,263 INFO  [org.jboss.as.jpa] (ServerService Thread Pool -- 10) WFLYJPA0011: Stopping Persistence Unit (phase 1 of 2) Service 'f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war#getinapp-unit'
            2016-12-07 14:36:14,289 INFO  [org.wildfly.extension.undertow] (MSC service thread 1-8) WFLYUT0019: Host default-host stopping
            2016-12-07 14:36:14,304 INFO  [org.jboss.as.server.deployment] (MSC service thread 1-4) WFLYSRV0028: Stopped deployment f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war (runtime-name: f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war) in 50ms
            2016-12-07 14:36:14,306 INFO  [org.jboss.as.controller] (main) WFLYCTL0183: Service status report
            WFLYCTL0184:    New missing/unsatisfied dependencies:
                  service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".WeldBootstrapService (missing) dependents: [service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".component."org.jboss.weld.servlet.WeldTerminalListener".WeldInstantiator, service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".CdiValidatorFactoryService, service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".component."javax.servlet.jsp.jstl.tlv.PermittedTaglibsTLV".WeldInstantiator, service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".component."javax.servlet.jsp.jstl.tlv.ScriptFreeTLV".WeldInstantiator] 
                  service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".component."javax.servlet.jsp.jstl.tlv.PermittedTaglibsTLV".CREATE (missing) dependents: [service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".component."javax.servlet.jsp.jstl.tlv.PermittedTaglibsTLV".START] 
                  service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".component."javax.servlet.jsp.jstl.tlv.PermittedTaglibsTLV".START (missing) dependents: [service jboss.undertow.deployment.default-server.default-host./swarm-error, service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".deploymentCompleteService, service jboss.undertow.deployment.default-server.default-host./swarm-error.UndertowDeploymentInfoService] 
                  service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".component."javax.servlet.jsp.jstl.tlv.ScriptFreeTLV".START (missing) dependents: [service jboss.undertow.deployment.default-server.default-host./swarm-error, service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".deploymentCompleteService, service jboss.undertow.deployment.default-server.default-host./swarm-error.UndertowDeploymentInfoService] 
                  service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".component."org.jboss.weld.servlet.WeldInitialListener".CREATE (missing) dependents: [service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".component."org.jboss.weld.servlet.WeldInitialListener".START] 
                  service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".component."org.jboss.weld.servlet.WeldInitialListener".START (missing) dependents: [service jboss.undertow.deployment.default-server.default-host./swarm-error, service jboss.undertow.deployment.default-server.default-host./swarm-error.UndertowDeploymentInfoService] 
                  service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".component."org.jboss.weld.servlet.WeldInitialListener".WeldInstantiator (missing) dependents: [service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".component."org.jboss.weld.servlet.WeldInitialListener".START] 
                  service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".component."org.jboss.weld.servlet.WeldTerminalListener".CREATE (missing) dependents: [service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".component."org.jboss.weld.servlet.WeldTerminalListener".START] 
                  service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".component."org.jboss.weld.servlet.WeldTerminalListener".START (missing) dependents: [service jboss.undertow.deployment.default-server.default-host./swarm-error, service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".deploymentCompleteService, service jboss.undertow.deployment.default-server.default-host./swarm-error.UndertowDeploymentInfoService] 
                  service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".component."org.jboss.weld.servlet.WeldTerminalListener".WeldInstantiator (missing) dependents: [service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".component."org.jboss.weld.servlet.WeldTerminalListener".START] 
                  service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".ee.ComponentRegistry (missing) dependents: [service jboss.undertow.deployment.default-server.default-host./swarm-error.UndertowDeploymentInfoService] 
                  service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".jndiDependencyService (missing) dependents: [service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".component."org.jboss.weld.servlet.WeldTerminalListener".START, service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".component."org.jboss.weld.servlet.WeldInitialListener".START] 
                  service jboss.persistenceunit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war#getinapp-unit" (missing) dependents: [service jboss.undertow.deployment.default-server.default-host./swarm-error, service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".deploymentCompleteService, service jboss.undertow.deployment.default-server.default-host./swarm-error.UndertowDeploymentInfoService] 
                  service jboss.undertow.deployment.default-server.default-host./swarm-error.UndertowDeploymentInfoService (missing) dependents: [service jboss.undertow.deployment.default-server.default-host./swarm-error] 
                  service jboss.undertow.deployment.default-server.default-host./swarm-error.codec (missing) dependents: [service jboss.undertow.deployment.default-server.default-host./swarm-error.UndertowDeploymentInfoService] 
                  service jboss.undertow.deployment.default-server.default-host./swarm-error.session (missing) dependents: [service jboss.undertow.deployment.default-server.default-host./swarm-error.UndertowDeploymentInfoService] 
                  service org.wildfly.request-controller.control-point."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".undertow (missing) dependents: [service jboss.undertow.deployment.default-server.default-host./swarm-error.UndertowDeploymentInfoService] 
            WFLYCTL0186:   Services which failed to start:      service jboss.deployment.unit."f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war".WeldStartService
            
            Exception in thread "main" java.lang.RuntimeException: org.wildfly.swarm.container.DeploymentException: WFSWARM0007: Deployment failed: {"WFLYCTL0080: Failed services" => {"jboss.deployment.unit.\"f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war\".WeldStartService" => "org.jboss.msc.service.StartException in service jboss.deployment.unit.\"f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war\".WeldStartService: Failed to start service
                Caused by: org.jboss.weld.exceptions.DeploymentException: WELD-001408: Unsatisfied dependencies for type SocketBindingGroup with qualifiers @Named
              at injection point [BackedAnnotatedField] @Inject @Named private org.wildfly.swarm.management.runtime.ManagementSocketBindingsCustomizer.group
              at org.wildfly.swarm.management.runtime.ManagementSocketBindingsCustomizer.group(ManagementSocketBindingsCustomizer.java:0)
            "},"WFLYCTL0412: Required services that are not installed:" => ["jboss.deployment.unit.\"f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war\".WeldStartService"],"WFLYCTL0180: Services with missing/unavailable dependencies" => undefined}
            	at sp.rafael.swarmerror.AppBuilder.startAndDeploy(AppBuilder.java:126)
            	at sp.rafael.swarmerror.Main.main(Main.java:23)
            	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
            	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
            	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
            	at java.lang.reflect.Method.invoke(Method.java:498)
            	at com.intellij.rt.execution.application.AppMain.main(AppMain.java:147)
            Caused by: org.wildfly.swarm.container.DeploymentException: WFSWARM0007: Deployment failed: {"WFLYCTL0080: Failed services" => {"jboss.deployment.unit.\"f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war\".WeldStartService" => "org.jboss.msc.service.StartException in service jboss.deployment.unit.\"f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war\".WeldStartService: Failed to start service
                Caused by: org.jboss.weld.exceptions.DeploymentException: WELD-001408: Unsatisfied dependencies for type SocketBindingGroup with qualifiers @Named
              at injection point [BackedAnnotatedField] @Inject @Named private org.wildfly.swarm.management.runtime.ManagementSocketBindingsCustomizer.group
              at org.wildfly.swarm.management.runtime.ManagementSocketBindingsCustomizer.group(ManagementSocketBindingsCustomizer.java:0)
            "},"WFLYCTL0412: Required services that are not installed:" => ["jboss.deployment.unit.\"f1e07881-9ce3-4ebc-aed0-eaaed63dadf5.war\".WeldStartService"],"WFLYCTL0180: Services with missing/unavailable dependencies" => undefined}
            	at org.wildfly.swarm.container.runtime.RuntimeDeployer.deploy(RuntimeDeployer.java:280)
            	at org.wildfly.swarm.Swarm.deploy(Swarm.java:491)
            	at sp.rafael.swarmerror.AppBuilder.startAndDeploy(AppBuilder.java:124)
            	... 6 more


## Steps to reproduce

1. Run Main.main from IDE