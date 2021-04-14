### Datasource

Project to illustrate datasource-related issues

**Project features:**
- [Schema creation, WildFly datasource and JPA configurations](schema/README.md)
- [IT of datasource layer](integration_tests/README.md)

#### Connecting to sql docker container

3.1 Via Docker Container:

`$ docker exec -it ${docker_mysql_container_name} mysql -uroot -p`

For instance if `${docker_mysql_container_name}` = `bm_my_sql`, run:

`$ docker exec -it bm_my_sql mysql -uroot -p`

And then enter `${mysql_root_pwd}` password.