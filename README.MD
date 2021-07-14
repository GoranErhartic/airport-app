# AIRPORT GATE APP

Hi! Let's go briefly through this awesome app


# How-to start the app:

You can start the app in couple of different ways:
1. Build the application by running following command `mvn clean package`. Once the build is successfully completed you can go ahead and run the app from CLI by running `java -jar target/app-1.0.jar` (by default, app is set to run on port `8081`)
2. Dockerfile is also included so we can go ahead and create a docker container by running `docker build --tag=app:latest .` and after the build is completed we can start the app through Docker by running this command `docker run -p8888:8081 app:latest` (here we are doing port forwarding and the app will be reachable on port `8888`)
3. Through your IDE by running the class annotated with `SpringBootApplication` - in this case _AppApplication_

## Endpoints:

All endpoints are documented by using OpenAPI documentation library and presented on Swagger UI. It can easily be accessed once the application is up and running.

Link to API documentation: http://localhost:8081/api/swagger-ui.html

## Security

REST APIs have been secured using Spring Security, and since this is a demo app, I went with the 'in memory' route as POC how authentication and authorization would be handled.
There are two roles: ADMIN and USER. Admin can access all endpoints, and User can access all except the one for editing gate schedule - idea behind this is that ONLY Admin can set those values.

##
> **NOTE**: Since this is a Demo app, we are using H2 embedded SQL database. On start of the application it will create the required schema and seed the tables will some data with which we can play around. 