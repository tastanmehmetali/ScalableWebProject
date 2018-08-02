# ScalableWebProject
This is the assignment from WAES as part of the interview process. 

The service compares two encoded(Base64) strings and manage process;
 - equals
 - different sizes
 - the same size but different data, 
	 - the differences (offset and lenght)

The service provides two POST endpoints that receives the Base64-encoded string, and a GET endpoint that returns the comparsion result (not exists, equal etc. ).

# Building & Running
On the root of the project, run:
> **mvn clean build**

## Running tests
Using JUnit for testing:

 ### Unit tests
 > **mvn clean test**

 ### Integration tests
 > **mvn clean verify**

 ### Build
 > **mvn clean install**

All tests should be run and pass after the project should be build. Then run:
	
	java -jar target/ScalableWebProject-0.0.1-SNAPSHOT.jar

## Endpoints
The application will be running on localhost:8080 (**\<host\>**) and the following endpoints will be available:

|     Type       |Service                        
|----------------|-------------------------------
|GET			 |`<host>/v1/diff/{ID}`            
|POST   	     |`<host>/v1/diff/{ID}/right`            
|POST	         |`<host>/v1/diff/{ID}/left`
|GET	         |`<host>/swagger-ui.html`

You can reach to Swagger UI page (/swagger-ui.html). The page is documentation of the api references. Also, you can try sample request and show the response.

## Architectural Design and Decisions
 ### Architecture Used
 The application is developed ***DDD* (Domain Driven Design)** and **Hexagonal Architecture** for implementation. Domain objects are the central part of the application and the application is developed and tested in isolation from its eventual run-time devices and databases. 
 Logic of the application is located the service layer that based on domain objects and using interfaces for loose coupling etc. Also, the infrastructure layer has implemented what it needs. It gives external connections. Developing it used ***TDD culture* (Test Driven Development)**.
 
### Endpoints
REST service for any clients (*Mobile*, *Web* or external app as like *postman* etc.)

### Application
SpringBoot, a famous and commonly used framework , is used to develop for the application. SpringBoot has a lot of advantages as like embeded tomcat (not need to download tomcat) etc.

### Storage
The decision is an in-Memory hash map for storage. The assignment is focused how to scale more easily and get more performance. Decided architecture (an Hexagonal Architecture) gives flexibility to choose or change the storage structure.

## Suggestions for improvement
The project can be improved.

**Docker:** The application can be run on container (adding a dockerfile )

**Database:** The application can use the real database for scalebility.

**Log Management :** to show more details and easy to understand for log systems as like *ELK (Elasticsearch, Logstash, Kibana)* 

**Load Balancer:** The request can be distribute the load.


## Authors
Mehmet Ali Taştan