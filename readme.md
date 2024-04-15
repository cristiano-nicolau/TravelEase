# Config mysql in docker

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


# Swagger

http://localhost:8080/swagger-ui/index.html


# Youtube

https://youtu.be/aQj6cqA_YdA