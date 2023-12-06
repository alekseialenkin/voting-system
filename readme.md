## TASK
Design and implement a REST API using Spring-Boot/Spring Data JPA **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

* 2 types of users: admin and regular users
* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu changes each day (admins do the updates)
* Users can vote for a restaurant they want to have lunch at today
* Only one vote counted per user
* If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it (**better - link to Swagger**).

-----------------------------
P.S.: Make sure everything works with latest version that is on github :)  
P.P.S.: Assume that your API will be used by a frontend developer to build frontend on top of that.

## Project Description
The project represents a REST API developed using Spring Boot and Spring Data JPA without a frontend. Its primary purpose is to provide a voting system enabling users to decide where to have lunch based on the available restaurant options and daily menus.

## Features
- Two types of users: admin and regular users
- Admin privileges include adding restaurants and defining daily menus
- Daily menu updates are restricted to admins
- Regular users have the ability to vote for their preferred restaurant of the day
- User votes are limited to one per day
- Changing a vote is allowed before 11:00, after which the vote becomes final
- Each restaurant provides a new menu daily

## Technologies
- REST
- Java 19
- Spring Boot (including Spring Security, Spring Data JPA, Spring MVC, Spring Test)
- JUnit 5
- Maven
- JSON

## Installation and Run
The project does not require a specific installation process. Simply clone the repository, and to run it, use the command spring-boot:start -f pom.xml

## Usage and Documentation
The project is configured to automatically generate documentation using the Swagger framework. To access and test the provided endpoints, navigate to [SWAGGER Rest Api Documentation](http://localhost:8080/swagger-ui/index.html) after starting the application.