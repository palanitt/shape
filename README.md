RESTful API service for a Shape repository

The following assumptions have been made with respect to square shape
 - Square shape are considered to have to coordinate values
 	- bottom left x,y coordinates and
 	- top right x,y coordinates
- Example coordinates for a valid square shape is bottomLeft(0,0) and topRight(5,4)
- For the sake of storing the coordinates in the data store, following format has been used. It will be
stored as a String content within the database. While processing the value, it will be split based on ':'
delimiter and the integer values are parsed.
	- 0,0:5,4 - here 0,0 represents bottomLeft and 5,4 represents topRight
	
Following dependencies are used in this project
 - Spring data JPA which provides hibernate for ORM mapping
 - In memory H2 database has been used as the data store
 - Lombok project has been used to help in generating getters and setters and building objects
 - ModelMapper has been used to map between DTO and Entity objects.
 
There are 2 end points in the resource class
- POST /shape API to store a valid square shape in the data store
- GET /shape API - to retrieve all the square shapes available in the data store

How to Build and run?
There are 2 ways
- First use following commands to pull the contents from github 
  and start the build.
  git clone https://github.com/palanitt/shape.git
  
 Approach 1:
 - Go to the project root directory and run the following commands. This required maven to be 
 available in the system.
  mvn clean install
  mvn spring-boot:run
  
 Approach 2:
 - Go to the project root directory and run the following commands to run the application as a docker
 container. This requires docker engine to be available in the system.
 - docker build -t shape-app .
 - docker run -p 8090:8080 shape-app, where 8090is the host port and 8080 is the container port
 
 Approach 3:
 - Go to the target folder under the project root directory and run the jar as a java application using 
 the following command. This requires java 8 or above to be available in the system
 - java -jar shape-0.0.1-SNAPSHOT.jar
  
  