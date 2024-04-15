# TravelEase

This is a project for booking bus tickets. You can see the list of available buses for the date you chosose, see the details of the bus, and book a ticket.
The main purpose of this project is to learn how to do a unit tests and integration tests in Spring Boot for the backend.
The project is divided into two parts:
* The first part is the backend, which is developed in Spring Boot, with a MySQL database in docker.
* The second part is the frontend, which is developed in HTML, JS and CSS.

## Technologies

- Spring Boot
- MySQL
- Docker
- HTML
- JS
- CSS

## Tests

- Unit tests with JUnit and Mockito.
- Integration tests with Spring Boot Test and MockMvc.

See principal objectives of the project [here](https://github.com/cristiano-nicolau/TravelEase/blob/main/TQS-HW_midterm_assignment.pdf)
 
See full documentation [here](https://github.com/cristiano-nicolau/TravelEase/blob/main/Report_hw1_tqs_108536.pdf) with Test Coverage and details of the tests and API documentation.

See video of demo [here](https://youtu.be/aQj6cqA_YdA)

## Config mysql in docker

```bash
docker run --name mysql5 -e MYSQL_ROOT_PASSWORD=secret1 -e MYSQL_DATABASE=bus_tickts -e MYSQL_USER=demo -e MYSQL_PASSWORD=secret2 -p 33060:3306 -d mysql/mysql-server:5.7
```

* MYSQL_ROOT_PASSWORD=secret1 - root password
* MYSQL_DATABASE=bus_tickts - database name
* MYSQL_USER=demo - user name
* MYSQL_PASSWORD=secret2 - user password
* -p 33060:3306 - port mapping

```bash
docker exec -it mysql5 mysql -u root -p
```


## How to run

1. Clone the repository
2. Run the MySQL in docker
3. Run the backend
    - ```bash
      cd bus_tickts
      mvn spring-boot:run
      ```
4. Run the frontend
    - Open the index.html file in the browser


## How to run tests

1. Run the MySQL in docker
2. Run the backend tests
    - ```bash
      cd bus_tickts
      mvn test
      ```


