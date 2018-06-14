# Testcase to support payara issue 2822.

Steps to reproduce:
  * in payara configure a jdbc connection-pool and a jdbc resource named jdbc/scottDS, eg
    
    <jdbc-connection-pool datasource-classname="oracle.jdbc.pool.OracleConnectionPoolDataSource" name="scott_pool" res-type="javax.sql.ConnectionPoolDataSource">
      <property name="User" value="scott"></property>
      <property name="MaxStatements" value="0"></property>
      <property name="ImplicitCachingEnabled" value="false"></property>
      <property name="NetworkProtocol" value="tcp"></property>
      <property name="URL" value="jdbc:oracle:thin:@localhost:1521:testlap"></property>
      <property name="DataSourceName" value="OracleConnectionPoolDataSource"></property>
      <property name="LoginTimeout" value="0"></property>
      <property name="PortNumber" value="0"></property>
      <property name="ExplicitCachingEnabled" value="false"></property>
      <property name="Password" value="*****"></property>
      </jdbc-connection-pool>
      <jdbc-resource pool-name="scott_pool" jndi-name="jdbc/scottDS"></jdbc-resource>
 
 * dont forget the resource-ref
   <resource-ref ref="jdbc/scottDS"></resource-ref>
   
 Build the project using `mvn install` and deploy it to payara. At this point we get an RuntimeException
    
    java.lang.RuntimeException: Invalid resource : jdbc/myTestDS__pm
	at com.sun.enterprise.connectors.ConnectorRuntime.lookupDataSourceInDAS(ConnectorRuntime.java:593)
	at com.sun.enterprise.connectors.ConnectorRuntime.lookupPMResource(ConnectorRuntime.java:517)
	at org.glassfish.persistence.common.PersistenceHelper.lookupPMResource(PersistenceHelper.java:63)
	at org.glassfish.persistence.jpa.ProviderContainerContractInfoBase.lookupDataSource(ProviderContainerContractInfoBase.java:71)
	at org.glassfish.persistence.jpa.PersistenceUnitInfoImpl.<init>(PersistenceUnitInfoImpl.java:108)
	at org.glassfish.persistence.jpa.PersistenceUnitLoader.loadPU(PersistenceUnitLoader.java:150)
	at org.glassfish.persistence.jpa.PersistenceUnitLoader.<init>(PersistenceUnitLoader.java:114)
	at org.glassfish.persistence.jpa.JPADeployer$1.visitPUD(JPADeployer.java:225)
	at org.glassfish.persistence.jpa.JPADeployer$PersistenceUnitDescriptorIterator.iteratePUDs(JPADeployer.java:525)
	at org.glassfish.persistence.jpa.JPADeployer.createEMFs(JPADeployer.java:240)
	at org.glassfish.persistence.jpa.JPADeployer.prepare(JPADeployer.java:170)
	at com.sun.enterprise.v3.server.ApplicationLifecycle.prepareModule(ApplicationLifecycle.java:926)
	at com.sun.enterprise.v3.server.ApplicationLifecycle.deploy(ApplicationLifecycle.java:435)
	at com.sun.enterprise.v3.server.ApplicationLifecycle.deploy(ApplicationLifecycle.java:220)
	at org.glassfish.deployment.admin.DeployCommand.execute(DeployCommand.java:508)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$2$1.run(CommandRunnerImpl.java:544)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$2$1.run(CommandRunnerImpl.java:540)
	at java.security.AccessController.doPrivileged(Native Method)
	at javax.security.auth.Subject.doAs(Subject.java:360)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$2.execute(CommandRunnerImpl.java:539)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$3.run(CommandRunnerImpl.java:570)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$3.run(CommandRunnerImpl.java:562)
	at java.security.AccessController.doPrivileged(Native Method)
	at javax.security.auth.Subject.doAs(Subject.java:360)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl.doCommand(CommandRunnerImpl.java:561)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl.doCommand(CommandRunnerImpl.java:1469)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl.access$1300(CommandRunnerImpl.java:111)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$ExecutionContext.execute(CommandRunnerImpl.java:1851)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$ExecutionContext.execute(CommandRunnerImpl.java:1727)
	at com.sun.enterprise.v3.admin.AdminAdapter.doCommand(AdminAdapter.java:534)
	at com.sun.enterprise.v3.admin.AdminAdapter.onMissingResource(AdminAdapter.java:224)
	at org.glassfish.grizzly.http.server.StaticHttpHandlerBase.service(StaticHttpHandlerBase.java:189)
	at com.sun.enterprise.v3.services.impl.ContainerMapper$HttpHandlerCallable.call(ContainerMapper.java:483)
	at com.sun.enterprise.v3.services.impl.ContainerMapper.service(ContainerMapper.java:180)
	at org.glassfish.grizzly.http.server.HttpHandler.runService(HttpHandler.java:206)
	at org.glassfish.grizzly.http.server.HttpHandler.doHandle(HttpHandler.java:180)
	at org.glassfish.grizzly.http.server.HttpServerFilter.handleRead(HttpServerFilter.java:235)
	at org.glassfish.grizzly.filterchain.ExecutorResolver$9.execute(ExecutorResolver.java:119)
	at org.glassfish.grizzly.filterchain.DefaultFilterChain.executeFilter(DefaultFilterChain.java:284)
	at org.glassfish.grizzly.filterchain.DefaultFilterChain.executeChainPart(DefaultFilterChain.java:201)
	at org.glassfish.grizzly.filterchain.DefaultFilterChain.execute(DefaultFilterChain.java:133)
	at org.glassfish.grizzly.filterchain.DefaultFilterChain.process(DefaultFilterChain.java:112)
	at org.glassfish.grizzly.ProcessorExecutor.execute(ProcessorExecutor.java:77)
	at org.glassfish.grizzly.nio.transport.TCPNIOTransport.fireIOEvent(TCPNIOTransport.java:539)
	at org.glassfish.grizzly.strategies.AbstractIOStrategy.fireIOEvent(AbstractIOStrategy.java:112)
	at org.glassfish.grizzly.strategies.WorkerThreadIOStrategy.run0(WorkerThreadIOStrategy.java:117)
	at org.glassfish.grizzly.strategies.WorkerThreadIOStrategy.access$100(WorkerThreadIOStrategy.java:56)
	at org.glassfish.grizzly.strategies.WorkerThreadIOStrategy$WorkerThreadRunnable.run(WorkerThreadIOStrategy.java:137)
	at org.glassfish.grizzly.threadpool.AbstractThreadPool$Worker.doWork(AbstractThreadPool.java:593)
	at org.glassfish.grizzly.threadpool.AbstractThreadPool$Worker.run(AbstractThreadPool.java:573)
	at java.lang.Thread.run(Thread.java:748)
Caused by: com.sun.appserv.connectors.internal.api.ConnectorRuntimeException: Invalid resource : jdbc/myTestDS__pm
	at org.glassfish.jdbcruntime.service.JdbcDataSource.validateResource(JdbcDataSource.java:82)
	at org.glassfish.jdbcruntime.service.JdbcDataSource.setResourceInfo(JdbcDataSource.java:63)
	at org.glassfish.jdbcruntime.JdbcRuntimeExtension.lookupDataSourceInDAS(JdbcRuntimeExtension.java:142)
	at com.sun.enterprise.connectors.ConnectorRuntime.lookupDataSourceInDAS(ConnectorRuntime.java:589)
	... 50 more

2018-06-14T19:17:32.016+0200|SCHWERWIEGEND: Exception while preparing the app
2018-06-14T19:17:32.017+0200|SCHWERWIEGEND: Exception during lifecycle processing
java.lang.RuntimeException: Invalid resource : jdbc/myTestDS__pm
	at com.sun.enterprise.connectors.ConnectorRuntime.lookupDataSourceInDAS(ConnectorRuntime.java:593)
	at com.sun.enterprise.connectors.ConnectorRuntime.lookupPMResource(ConnectorRuntime.java:517)
	at org.glassfish.persistence.common.PersistenceHelper.lookupPMResource(PersistenceHelper.java:63)
	at org.glassfish.persistence.jpa.ProviderContainerContractInfoBase.lookupDataSource(ProviderContainerContractInfoBase.java:71)
	at org.glassfish.persistence.jpa.PersistenceUnitInfoImpl.<init>(PersistenceUnitInfoImpl.java:108)
	at org.glassfish.persistence.jpa.PersistenceUnitLoader.loadPU(PersistenceUnitLoader.java:150)
	at org.glassfish.persistence.jpa.PersistenceUnitLoader.<init>(PersistenceUnitLoader.java:114)
	at org.glassfish.persistence.jpa.JPADeployer$1.visitPUD(JPADeployer.java:225)
	at org.glassfish.persistence.jpa.JPADeployer$PersistenceUnitDescriptorIterator.iteratePUDs(JPADeployer.java:525)
	at org.glassfish.persistence.jpa.JPADeployer.createEMFs(JPADeployer.java:240)
	at org.glassfish.persistence.jpa.JPADeployer.prepare(JPADeployer.java:170)
	at com.sun.enterprise.v3.server.ApplicationLifecycle.prepareModule(ApplicationLifecycle.java:926)
	at com.sun.enterprise.v3.server.ApplicationLifecycle.deploy(ApplicationLifecycle.java:435)
	at com.sun.enterprise.v3.server.ApplicationLifecycle.deploy(ApplicationLifecycle.java:220)
	at org.glassfish.deployment.admin.DeployCommand.execute(DeployCommand.java:508)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$2$1.run(CommandRunnerImpl.java:544)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$2$1.run(CommandRunnerImpl.java:540)
	at java.security.AccessController.doPrivileged(Native Method)
	at javax.security.auth.Subject.doAs(Subject.java:360)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$2.execute(CommandRunnerImpl.java:539)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$3.run(CommandRunnerImpl.java:570)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$3.run(CommandRunnerImpl.java:562)
	at java.security.AccessController.doPrivileged(Native Method)
	at javax.security.auth.Subject.doAs(Subject.java:360)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl.doCommand(CommandRunnerImpl.java:561)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl.doCommand(CommandRunnerImpl.java:1469)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl.access$1300(CommandRunnerImpl.java:111)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$ExecutionContext.execute(CommandRunnerImpl.java:1851)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$ExecutionContext.execute(CommandRunnerImpl.java:1727)
	at com.sun.enterprise.v3.admin.AdminAdapter.doCommand(AdminAdapter.java:534)
	at com.sun.enterprise.v3.admin.AdminAdapter.onMissingResource(AdminAdapter.java:224)
	at org.glassfish.grizzly.http.server.StaticHttpHandlerBase.service(StaticHttpHandlerBase.java:189)
	at com.sun.enterprise.v3.services.impl.ContainerMapper$HttpHandlerCallable.call(ContainerMapper.java:483)
	at com.sun.enterprise.v3.services.impl.ContainerMapper.service(ContainerMapper.java:180)
	at org.glassfish.grizzly.http.server.HttpHandler.runService(HttpHandler.java:206)
	at org.glassfish.grizzly.http.server.HttpHandler.doHandle(HttpHandler.java:180)
	at org.glassfish.grizzly.http.server.HttpServerFilter.handleRead(HttpServerFilter.java:235)
	at org.glassfish.grizzly.filterchain.ExecutorResolver$9.execute(ExecutorResolver.java:119)
	at org.glassfish.grizzly.filterchain.DefaultFilterChain.executeFilter(DefaultFilterChain.java:284)
	at org.glassfish.grizzly.filterchain.DefaultFilterChain.executeChainPart(DefaultFilterChain.java:201)
	at org.glassfish.grizzly.filterchain.DefaultFilterChain.execute(DefaultFilterChain.java:133)
	at org.glassfish.grizzly.filterchain.DefaultFilterChain.process(DefaultFilterChain.java:112)
	at org.glassfish.grizzly.ProcessorExecutor.execute(ProcessorExecutor.java:77)
	at org.glassfish.grizzly.nio.transport.TCPNIOTransport.fireIOEvent(TCPNIOTransport.java:539)
	at org.glassfish.grizzly.strategies.AbstractIOStrategy.fireIOEvent(AbstractIOStrategy.java:112)
	at org.glassfish.grizzly.strategies.WorkerThreadIOStrategy.run0(WorkerThreadIOStrategy.java:117)
	at org.glassfish.grizzly.strategies.WorkerThreadIOStrategy.access$100(WorkerThreadIOStrategy.java:56)
	at org.glassfish.grizzly.strategies.WorkerThreadIOStrategy$WorkerThreadRunnable.run(WorkerThreadIOStrategy.java:137)
	at org.glassfish.grizzly.threadpool.AbstractThreadPool$Worker.doWork(AbstractThreadPool.java:593)
	at org.glassfish.grizzly.threadpool.AbstractThreadPool$Worker.run(AbstractThreadPool.java:573)
	at java.lang.Thread.run(Thread.java:748)
Caused by: com.sun.appserv.connectors.internal.api.ConnectorRuntimeException: Invalid resource : jdbc/myTestDS__pm
	at org.glassfish.jdbcruntime.service.JdbcDataSource.validateResource(JdbcDataSource.java:82)
	at org.glassfish.jdbcruntime.service.JdbcDataSource.setResourceInfo(JdbcDataSource.java:63)
	at org.glassfish.jdbcruntime.JdbcRuntimeExtension.lookupDataSourceInDAS(JdbcRuntimeExtension.java:142)
	at com.sun.enterprise.connectors.ConnectorRuntime.lookupDataSourceInDAS(ConnectorRuntime.java:589)
	... 50 more

2018-06-14T19:17:32.024+0200|SCHWERWIEGEND: Exception while preparing the app : Invalid resource : jdbc/myTestDS__pm
com.sun.appserv.connectors.internal.api.ConnectorRuntimeException: Invalid resource : jdbc/myTestDS__pm
	at org.glassfish.jdbcruntime.service.JdbcDataSource.validateResource(JdbcDataSource.java:82)
	at org.glassfish.jdbcruntime.service.JdbcDataSource.setResourceInfo(JdbcDataSource.java:63)
	at org.glassfish.jdbcruntime.JdbcRuntimeExtension.lookupDataSourceInDAS(JdbcRuntimeExtension.java:142)
	at com.sun.enterprise.connectors.ConnectorRuntime.lookupDataSourceInDAS(ConnectorRuntime.java:589)
	at com.sun.enterprise.connectors.ConnectorRuntime.lookupPMResource(ConnectorRuntime.java:517)
	at org.glassfish.persistence.common.PersistenceHelper.lookupPMResource(PersistenceHelper.java:63)
	at org.glassfish.persistence.jpa.ProviderContainerContractInfoBase.lookupDataSource(ProviderContainerContractInfoBase.java:71)
	at org.glassfish.persistence.jpa.PersistenceUnitInfoImpl.<init>(PersistenceUnitInfoImpl.java:108)
	at org.glassfish.persistence.jpa.PersistenceUnitLoader.loadPU(PersistenceUnitLoader.java:150)
	at org.glassfish.persistence.jpa.PersistenceUnitLoader.<init>(PersistenceUnitLoader.java:114)
	at org.glassfish.persistence.jpa.JPADeployer$1.visitPUD(JPADeployer.java:225)
	at org.glassfish.persistence.jpa.JPADeployer$PersistenceUnitDescriptorIterator.iteratePUDs(JPADeployer.java:525)
	at org.glassfish.persistence.jpa.JPADeployer.createEMFs(JPADeployer.java:240)
	at org.glassfish.persistence.jpa.JPADeployer.prepare(JPADeployer.java:170)
	at com.sun.enterprise.v3.server.ApplicationLifecycle.prepareModule(ApplicationLifecycle.java:926)
	at com.sun.enterprise.v3.server.ApplicationLifecycle.deploy(ApplicationLifecycle.java:435)
	at com.sun.enterprise.v3.server.ApplicationLifecycle.deploy(ApplicationLifecycle.java:220)
	at org.glassfish.deployment.admin.DeployCommand.execute(DeployCommand.java:508)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$2$1.run(CommandRunnerImpl.java:544)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$2$1.run(CommandRunnerImpl.java:540)
	at java.security.AccessController.doPrivileged(Native Method)
	at javax.security.auth.Subject.doAs(Subject.java:360)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$2.execute(CommandRunnerImpl.java:539)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$3.run(CommandRunnerImpl.java:570)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$3.run(CommandRunnerImpl.java:562)
	at java.security.AccessController.doPrivileged(Native Method)
	at javax.security.auth.Subject.doAs(Subject.java:360)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl.doCommand(CommandRunnerImpl.java:561)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl.doCommand(CommandRunnerImpl.java:1469)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl.access$1300(CommandRunnerImpl.java:111)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$ExecutionContext.execute(CommandRunnerImpl.java:1851)
	at com.sun.enterprise.v3.admin.CommandRunnerImpl$ExecutionContext.execute(CommandRunnerImpl.java:1727)
	at com.sun.enterprise.v3.admin.AdminAdapter.doCommand(AdminAdapter.java:534)
	at com.sun.enterprise.v3.admin.AdminAdapter.onMissingResource(AdminAdapter.java:224)
	at org.glassfish.grizzly.http.server.StaticHttpHandlerBase.service(StaticHttpHandlerBase.java:189)
	at com.sun.enterprise.v3.services.impl.ContainerMapper$HttpHandlerCallable.call(ContainerMapper.java:483)
	at com.sun.enterprise.v3.services.impl.ContainerMapper.service(ContainerMapper.java:180)
	at org.glassfish.grizzly.http.server.HttpHandler.runService(HttpHandler.java:206)
	at org.glassfish.grizzly.http.server.HttpHandler.doHandle(HttpHandler.java:180)
	at org.glassfish.grizzly.http.server.HttpServerFilter.handleRead(HttpServerFilter.java:235)
	at org.glassfish.grizzly.filterchain.ExecutorResolver$9.execute(ExecutorResolver.java:119)
	at org.glassfish.grizzly.filterchain.DefaultFilterChain.executeFilter(DefaultFilterChain.java:284)
	at org.glassfish.grizzly.filterchain.DefaultFilterChain.executeChainPart(DefaultFilterChain.java:201)
	at org.glassfish.grizzly.filterchain.DefaultFilterChain.execute(DefaultFilterChain.java:133)
	at org.glassfish.grizzly.filterchain.DefaultFilterChain.process(DefaultFilterChain.java:112)
	at org.glassfish.grizzly.ProcessorExecutor.execute(ProcessorExecutor.java:77)
	at org.glassfish.grizzly.nio.transport.TCPNIOTransport.fireIOEvent(TCPNIOTransport.java:539)
	at org.glassfish.grizzly.strategies.AbstractIOStrategy.fireIOEvent(AbstractIOStrategy.java:112)
	at org.glassfish.grizzly.strategies.WorkerThreadIOStrategy.run0(WorkerThreadIOStrategy.java:117)
	at org.glassfish.grizzly.strategies.WorkerThreadIOStrategy.access$100(WorkerThreadIOStrategy.java:56)
	at org.glassfish.grizzly.strategies.WorkerThreadIOStrategy$WorkerThreadRunnable.run(WorkerThreadIOStrategy.java:137)
	at org.glassfish.grizzly.threadpool.AbstractThreadPool$Worker.doWork(AbstractThreadPool.java:593)
	at org.glassfish.grizzly.threadpool.AbstractThreadPool$Worker.run(AbstractThreadPool.java:573)
	at java.lang.Thread.run(Thread.java:748)

    
