# Tempo - Test

## Description of the application

This springboot application gives to the user the following endpoints :

* Look up a role for a membership, where membership is defined by a user id and a teamd id,
* Look up memberships for a role,
* Create a new role,
* Look up a roles present in DB,
* Assign a role to a member.

## How I approached the problem

To create this API, I :

* created an empty springboot application with a swagger (with open API v3),
* started to modelize the endpoints (API-First approach) in order to define : the verbs, paths, answers and DTO required,
* coded the controller (driven by unit tests , see RoleControllerTest), 
* coded the service (again, driven by unit tests, see the other unit tests),
* installed a database, and implemented the dao layer (using liquibase to create the tables I needed),
* dockerized my application, with its DB. 

## What I would ask to the developers for my task

I remarked some behaviors / use cases not really covered by my application:

* When a team/user gets deleted from the provided API, we need to cascade and delete the User/Team/Role association I created on my side.
* When I assign a role to a member, I'm checking the provided API if they exist, however when a user or a team doesn't exist, it returns "null". This answer is a bit misleading, probably a "Not found" status is better for this use case.

# Documentation

### Installation of the application

This application can be dockerized and deployed using docker/docker compose. This documentation assumes that:
* you have downloaded the files contained in the GIT repo, 
* you have docker installed on your computer.
In order to run it, you need to execute the following steps :

## Create the image of the application 

run the following command in the root folder of the application: docker build -t tempo:latest .

## Run the docker compose 

to run the application, you need the application and its associated database, run the following command in the root folder of the application : docker-compose up -d 

## Go to the swagger page

If the application is correctly running you should be able to see the swagger at the following url : http://localhost:8080/swagger-ui.html


### Technologies used

This project is using the following technologies : 

* Java/ Springboot/JPA
* PostgreSQL / liquibase
* open API v3
* lombok / Mapstruct

# Technical choices

Here is some explanations about the technical choices made during this project :

## The use of a DB and liquibase

I'm using a DB to ensure that the data are persisted, in production , this could be backup and re-deployed if data gets deleted. I'm using liquibase to create the tables as it is a safe way to ensure a DB in the expected state when starting the application.

## The use of Open API v3

This gives a REST API documentation and it can allow developers to use the API-First approach.


# What I might do differently if I were to spend additional time on the project

If I were to spend additional time on the project , I would create integration tests, especially for the part where I'm interacting with the provided API. 
I tested it with unit tests, but added comments in the code to say why they should be converted into integration tests.