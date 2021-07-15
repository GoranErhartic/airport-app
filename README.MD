# AIRPORT GATE APP

Hi! Let's go briefly through this awesome app

# How-to start the app:

Compiled `app-1.0.jar` can be found in the root of the project.  
You can start the app in couple of different ways:
1. Run the app from root of the project using CLI by running `java -jar app-1.0.jar`
2. Dockerfile is also included so we can go ahead and create a docker image by running `docker build --tag=app:latest .` and after the build is completed we can start the app through Docker by running this command `docker run -p8081:8081 app:latest`
3. Through your IDE by running the class annotated with `SpringBootApplication` - in this case _AppApplication_

>**NOTE:** By default the app listens to port **`8081`**

## Endpoints:

All endpoints are documented by using OpenAPI documentation library and presented on Swagger UI. It can easily be accessed once the application is up and running.

Link to API documentation: http://localhost:8081/api/swagger-ui.html

## Security

REST APIs have been secured using Spring Security, and since this is a demo app, I went with the 'in memory' route with basic auth as POC how authentication and authorization could be handled.
There are two roles: ADMIN and USER. Admin can access all endpoints, and User can access all except the one for editing gate schedule - idea behind this is that ONLY Admin can set those values.

CREDENTIALS     | Username | Password
--- | --- | ---
ADMIN | admin | admin
USER    | user | user

##
> **NOTE**: Since this is a Demo app, we are using H2 embedded SQL database. On start of the application it will create the required schema and seed the tables will some data with which we can play around. 