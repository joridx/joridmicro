#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
${symbol_pound} Rest Web Service ${rootArtifactId} WEB project
------------------------------------------------------

This projects shows the basic configuration needed to start a Rest web service project within the allianz platform. 
The project is configured to work with:
* spring-security with oauth2 --> connecting with [rws-oauth](https://github.developer.allianz.io/ePac-Core/rws-oauth-authorization-server)
* config-server --> connecting with [rws-config-server](https://github.developer.allianz.io/ePac-Core/rws-config-server)   
* spring-boot
* spring-test
* spring-jpa with db2
* swagger
* jmockit
* Markdown notation to document --> take a look to [Markdown-Cheatsheet](https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet)
* [logback](https://logback.qos.ch/manual/index.html)


${symbol_pound}${symbol_pound} Install
```sh 
mvn install
````

If your are using java 7 be aware the default PermGem is not enough to run "mvn install". Increase first your PermGem to 1024 in your mvn config file. 

In Windows Open "...${symbol_escape}apache-maven-X.X.X${symbol_escape}bin${symbol_escape}mvn.cmd" and write:

```
set MAVEN_OPTS=-Xmx2048m -Xms1024m -XX:MaxPermSize=1024m
````

In Linux open "../apache-maven-X.X.X/bin/mvn" and write
```sh
export MAVEN_OPTS="-Xmx2048m -Xms1024m -XX:MaxPermSize=1024m"
````

${symbol_pound}${symbol_pound} RUN Application

${symbol_pound}${symbol_pound}${symbol_pound} Run WS

${symbol_pound}${symbol_pound}${symbol_pound} Spring Profiles
Profiles are used to start the app using certain configuration.
* dev,int,prod: The environment you are using. If you are running with dev profile it means you are running against DEV database and DEV WS
* remoteConfig: Active means you are using remote configurations, it means you are using the config server. In this case variable allianz.country must be setted. If it's not active then configuration must the properties needed should be added to your application.properties.
* database: Active by default in boilerplate. You are using DDBB configurations.
* security: Active by default in boilerplate. You are using ouath2 configurations.  

Use spring-boot:run to launch your app. Environment profiles may be local,dev,int or prod.

Example using local application.properties without multi instance: 

```sh
cd ${rootArtifactId}-web
mvn spring-boot:run "-Dspring.profiles.active=local"
````
Open URL [http://localhost:8080/${rootArtifactId}/swagger-ui.html](http://localhost:8080/rws-microsboilerplate/swagger-ui.html)
 
If you are using multi instance per country you must specify your country. If properties are provided by config server you must specify profile remoteConfig.

Example running against dev environment, with country "es" configuration (it means using config-server properties for spain) and remote properties (config-server):  
 
```sh
cd rws-microsboilerplate-web
mvn spring-boot:run "-Dspring.profiles.active=dev,remoteConfig" "-Dallianz.country=es"
````
Open URL [http://localhost:8080/${rootArtifactId}/swagger-ui.html](http://localhost:8080/${rootArtifactId}/swagger-ui.html)



${symbol_pound}${symbol_pound} TESTS
${symbol_pound}${symbol_pound}${symbol_pound} Integration Tests

Integration test classes must be named with sufix "TestIT" as MyClassTestIT.java

All function must have an integration test covering its main functionality

${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound} Run integration tests

Example for ${rootArtifactId}-web project running against dev environment and with es configuration:

```sh
cd ${rootArtifactId}-web
mvn verify "-Dspring.profiles.active=dev,remoteConfig" "-Dallianz.country=es"
````
${symbol_pound}${symbol_pound}${symbol_pound} Unit Tests

Unit test classes must be named with sufix Test as MyClassTest.java

Unit tests must be used to give fully coverage to the code (bigger than 90%). Integration tests are more complicated to implement and should only cover the main functionality for each function but unit tests must cover all flow cases.  

Unit tests are performed with jmockit
* Jmockit reference (must read): [jmockit](http://jmockit.org/)
* Take a look to our boilerplate sample: [ProdExtPointServiceImplTest.java](https://github.developer.allianz.io/ePac-Core/rws-microsboilerplate/blob/2589376dd691d74b33857e846612682c0727c897/rws-microsboilerplate-web/src/test/java/com/allianz/mygrouppackage/microsboilerplate/service/impl/ProdExtPointServiceImplTest.java)

${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound} Run Unit Tests
Run all tests:
```sh
cd ${rootArtifactId}-web
mvn test
````
Run one test:
```sh
cd ${rootArtifactId}-web
mvn test -Dtest=${package}.service.ProdExtPointServiceImplTest#testFindAll_byProduct
````

${symbol_pound}${symbol_pound} How to deal with Exceptions

* RuntimeException --> Http 400 codes. Error in the request.
* Exception --> Http 500 codes. Error in the server.

Why?

Exceptions need to be catched. For example, DDBB is down or filesystem is not accessible are conditions that at the end must return http 500
 
 
This is completely forbidden:

```java
try {
    ...
} catch (final Exception e) {
    throw new RuntimeException(e);
}
````	

Why is forbidden? Because the upper layer would produce a http 400 code instead of a http 500 code. If we get and Exception and we can not deal with it we should propagate it as Exception, eventually this will produce a 500 to the rest consumer. 

Example with Runtime:

```java
		    DateUtils.isSameDay(null, myDay);
````

DateUtils.isSameDay will produce a RuntimeException because of null parameter. This means this code has not been invoked correctly, so RuntimeException will be launched and propagated up. If this null parameters comes from the http request this will produce a 400, otherwise you should deal with this RuntimeException before reaching the top layer. RuntimeException and bad request (http 400) are related.

${symbol_pound}${symbol_pound}${symbol_pound} How to add custom Exceptions

Implement methods in GlobalDefaultExceptionHandler using ExceptionHandler to define your own Exception handle

Example:

```	java
@ControllerAdvice  
@RestController  
public class GlobalDefaultExceptionHandler extends GlobalExceptionHandlerBase  {

     //This will response your desired HTTP CODE and your custom message when a MyCustomException is launched
	@ExceptionHandler(value = MyCustomException.class)  
    public ResponseEntity<?> handleMyException(MyCustomException ex) throws JsonProcessingException {  
    	RESTResponseBeanBuilder<?> response = RESTResponseBean.builder();
    	log.error("MyCustom excepction: ", ex);
    	return response
			.success(false/true)
			.status(HttpServletResponse.SC_YOUR_DESIRED_CODE)
			.error(ex.getMyCustomeField())
			.buildResponseEntity();  
    }  
}
````
Remember by default in GlobalExceptionHandlerBase all Exceptions are returned as http 500 code and RuntimeExceptions are returned as http 400 code.

More info: [Spring Mvc Exceptionhandlers](https://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html${symbol_pound}mvc-exceptionhandlers) 

${symbol_pound}${symbol_pound} Logging

${symbol_pound}${symbol_pound}${symbol_pound} How to change log levels

Changes in bootstrap.yml affect all environments:

bootstrap.yml:
```	yml
logging.level.root: WARN
logging.level.org.springframework: INFO
logging.level.com.allianz: INFO
````

bootstrap location: ${rootArtifactId}-web/src/main/resources/bootstrap.yml

${symbol_pound}${symbol_pound}${symbol_pound} How to change log level per environment

Log level may be changed per environment.

bootstrap-dev.yml (Overrides log level for com.allianz packages to DEBUG)::

```	yml
logging.level.com.allianz: DEBUG
````

bootstrap location: ${rootArtifactId}-web/src/main/resources/bootstrap-dev.yml


${symbol_pound}${symbol_pound}${symbol_pound} How to change log file configurations

Some properties are located under bootstrap.yml. Log file name, path or format may be changed in bootstrap.yml:

bootstrap.yml:
```	yml
logging.name: ${rootArtifactId}-web
logging.path: /opt/jboss/logs/log4j
logging.pattern.console: "%d{yyyy-MM-dd HH:mm:ss} %-5level %logger{36} - %msg%n"
logging.pattern.file: "%d{yyyy-MM-dd HH:mm:ss} %-5level %logger{36} - %msg%n"
````

The rest of properties such as file size or logging by console are configured in logback-spring.xml. File per environment may be configured using spring profiles with:

```xml
<if condition='property("spring.profiles.active").contains("prod")'>
<then>
	<maxFileSize>5MB</maxFileSize>
	<totalSizeCap>5MB</totalSizeCap>
</then>
````

logback-spring.xml:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <include resource="org/springframework/boot/logging/logback/defaults.xml"/>
    <springProperty scope="context" name="LOG_NAME" source="logging.name" defaultValue="springboot"/>
    
	<include resource="org/springframework/boot/logging/logback/console-appender.xml"/>

	<appender name="FILE"
		class="ch.qos.logback.core.rolling.RollingFileAppender">
		<file>${symbol_dollar}{LOG_PATH}/${symbol_dollar}{LOG_NAME}.log</file>
		<rollingPolicy
			class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
			<fileNamePattern>${symbol_dollar}{LOG_PATH}/${symbol_dollar}{LOG_NAME}/${symbol_dollar}{LOG_NAME}-%d{yyyy-MM-dd}.%i.log.zip</fileNamePattern>
	
		
			<if condition='property("spring.profiles.active").contains("prod")'>
			<then>
				<maxFileSize>5MB</maxFileSize>
				<totalSizeCap>5MB</totalSizeCap>
			</then>
	        <else>
				<maxFileSize>1MB</maxFileSize>
				<totalSizeCap>1MB</totalSizeCap>
	        </else>
	        </if>
			
			<maxHistory>1</maxHistory>

			
		</rollingPolicy>
		<encoder>
			<pattern>${symbol_dollar}{FILE_LOG_PATTERN}</pattern>
		</encoder>
	</appender> 


	<if condition='property("spring.profiles.active").contains("local")'>
	<then>
        <root>
			<appender-ref ref="FILE" />
			<appender-ref ref="CONSOLE" />
       	</root>
	</then>
	<else>
        <root>
			<appender-ref ref="FILE" />
       	</root>
	</else>
	</if>	
     

</configuration>
````

${symbol_pound}${symbol_pound} Git Commit Message Styleguide

* Use the present tense ("Add feature" not "Added feature")
* Use the imperative mood ("Move cursor to..." not "Moves cursor to...")
* Limit the first line to 72 characters or less
* Reference issues and pull requests liberally after the first line
* Consider starting the commit message with an applicable emoji:

	* :tada: `:tada:` initial commit
	* :sparkles: `:sparkles:` when adding feature
	* :fire: `:fire:` when removing code or files
	* :rocket: `:rocket:` when improving performance
	* :wrench: `:wrench:` when refactoring
	* :lipstick: `:lipstick:` cosmetic change
	* :books: `:books:` when writing docs
	* :construction: `:construction:` work in progress
	* :speaker: `:speaker:` when adding logging
	* :bug: `:bug:` when fixing a bug
	* :white_check_mark: `:white_check_mark:` when adding tests
	* :lock: `:lock:` when dealing with security
	* :arrow_up: `:arrow_up:` when upgrading dependencies or increasing current version
	* :arrow_down: `:arrow_down:` when downgrading dependencies



${symbol_pound} Follow Allianz REST Conventions

HTTP methods and actions
 
Once you have your resources defined, you need to identify what actions apply to them and how those would map to your API. REST asks developers to use HTTP methods explicitly and establishes a one-to-one mapping between create, read, update, and delete (CRUD) operations and HTTP methods. According to this mapping:

* To retrieve a resource, use GET.
  * GET /quotations - Retrieves a list of quotations
  * GET /quotations/1984 - Retrieves a specific quotation
* To create a resource on the server, use POST.
  * POST /quotations - Creates a new quotation, use the body to send quotation data in JSON format 
* To change the state of a resource or to update it, use PUT.
  * PUT /quotations/1984 - Update quotation 1984 use the body to send quotation data in JSON format 
* To partially update a resource, use PATCH. 
  * PATCH /quotations/1984 - Update quotation 1984 use the body to send partial quotation data in JSON format 
* To remove or delete a resource, use DELETE.
  * DELETE /quotations/1984 - Delete quotation 1984 

Actions that don't fit into the world of CRUD operations might be treat it like a sub-resource with RESTful principles. 

   POST /claims/1984/close - Allows to close claim 1984
   
For more info about rest check out [Allianz convention](https://iberolatam.intrallianz.com/display/AG/REST+WebService+Naming+Conventions)   	 


A task is done and can be integrated in develop branch for delivery when:
* Functionality is finished
* Unit test coverage: Bigger than 90% 
* Integration tests: All function have one integration test covering main functionality.     
* No eclipse code warnings
* Comply wiht PMD rules (TODO) 



${symbol_pound}${symbol_pound} API URL

- [http://localhost:8080/${rootArtifactId}/api/v1/yourresource](http://localhost:8080/${rootArtifactId}/api/v1/yourresource)

${symbol_pound}${symbol_pound} Swagger API

- [http://localhost:8080/${rootArtifactId}/swagger-ui.html](http://localhost:8080/${rootArtifactId}/swagger-ui.html)


