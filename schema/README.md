### DB Schema Creation

**Note:** all subprojects are defined as `war` files, only to include all the dependencies.
In real projects, they must be defined as `jar` and be part of another `war` file!!!

- [Database creation](#11-db-creation)
- [WildFly configuration with the Ansible scripts](#121-if-you-use-the-ansible-scriptshttpsgithubcomalexandrsokolovansible-projectstreemasterbm_app)
- [WildFly configuration with CLI](#122-configure-a-datasource-with-jbosswildfly-using-cli)
- [WildFly configuration with static configuration](#123-static-configuration-in-standalone-fullxml-file)
- [Possible issues](#issues)

#### 1. Schema creation in JEE environments.

The idea is, that the database is created by ITO. 
This database is configured in the application configuration, like WildFly `standalone-full.xml` file.
Developers with the ITO team decide, what jndi name will be used for the datasource configuration.

#### 1.1 Db creation:

For instance `custom_db` is the database name. Sql statement: `create database custom_db;`

If you use a docker container for the db, and suppose the `my_sql` mysql docker container is running:
```bash
docker exec -it bm_my_sql sh -c "echo create database custom_db | mysql -uroot -p"
docker exec -it bm_my_sql sh -c "echo show databases | mysql -uroot -p"
```

#### 1.2 Configure the application to map the chosen jndi name to the database

##### 1.2.1 If you use the [Ansible scripts](https://github.com/AlexandrSokolov/ansible-projects/tree/master/bm_app)

If you Wildfly docker image is not built and a docker container is not running yet, run:

`installBmWfCustomDs.sh java:/customJndiDs release-6.7`

If a Wildfly docker container is already running, adopt it with:

`recreateBmWfWithCustomDs.sh java:/customJndiDs`

##### 1.2.2 Configure a datasource with JBoss/WildFly using CLI:

Suppose:
- `bm_wf` is a docker container with WildFly 
- `bm_my_sql` is a docker container with MySql
- `mysql` driver is already configured in WildFly configuration
- `java:/customJndiDs` jndi name
- `custom_db` database name

To add a new datasource run:

```
$ docker exec -it bm_wf sh -c "/opt/jboss/wildfly/bin/jboss-cli.sh --connect --controller=127.0.0.1:19990"
/subsystem=datasources/data-source=NewCustomDs:add(jndi-name="java:/customJndiDs", connection-url="jdbc:mysql://bm_my_sql:3306/custom_db?useUnicode=true&amp;characterEncoding=utf8&amp;characterResultSets=utf8;serverTimezone=Europe/Berlin", driver-name="mysql", user-name="root", password="123")
{"outcome" => "success"}
/subsystem=datasources/data-source=NewCustomDs:test-connection-in-pool
{
    "outcome" => "success",
    "result" => [true]
}
```

##### 1.2.3 Static configuration in `standalone-full.xml` file:
```xml
<datasource jta="true" jndi-name="java:/customJndiDs" pool-name="CustomDS_Pool" enabled="true" use-ccm="true" statistics-enabled="True">
  <connection-url>jdbc:mysql://bm_my_sql:3306/custom_db?useUnicode=true&amp;characterEncoding=utf8&amp;characterResultSets=utf8;serverTimezone=Europe/Berlin</connection-url>
  <driver-class>com.mysql.jdbc.Driver</driver-class>
  <driver>mysql</driver>
  <security>
      <user-name>root</user-name>
      <password>123</password>
  </security>
  <transaction-isolation>
    TRANSACTION_READ_COMMITTED
  </transaction-isolation>
  <pool>
      <min-pool-size>30</min-pool-size>
      <max-pool-size>100</max-pool-size>
      <prefill>false</prefill>
      <use-strict-min>false</use-strict-min>
      <flush-strategy>FailingConnectionOnly</flush-strategy>
  </pool>
  <validation>
      <check-valid-connection-sql>SELECT 1</check-valid-connection-sql>
      <validate-on-match>false</validate-on-match>
      <background-validation>true</background-validation>
      <background-validation-millis>60000</background-validation-millis>
  </validation>
  <timeout>
      <idle-timeout-minutes>10</idle-timeout-minutes>
      <allocation-retry>3</allocation-retry>
  </timeout>
  <statement>
      <prepared-statement-cache-size>0</prepared-statement-cache-size>
  </statement>
</datasource>
```

#### Issues:

Schema is not created/updated. 

1. In the WildFly log you see:
```
2021-04-12 11:02:35,265 INFO  [org.jboss.weld.Bootstrap] (Weld Thread Pool -- 5) WELD-000119: Not generating any bean definitions from ... because of underlying class loading error: Type liquibase.resource.ResourceAccessor from [Module "deployment....jar:main" ... ] not found. If this is unexpected, enable DEBUG logging to see the full error.
```

Your datasource module is `jar` file which does not contain all dependencies.
You can:
- include the datasource `jar` project in another `war` (the only right solution for production)
- convert the datasource `jar` project to `war` (only for testing)
- extend the datasource `jar` into the fat `jar` which includes all the dependencies (only for standalone applications)

2. You see in the WildFly log on a fresh installation:
```
Caused by: liquibase.exception.ValidationFailedException: Validation Failed:
     1 change sets check sum
          db/changelog/db.init.changelog.yml::1::asokolov was: 8:fd6da03670e7f8a748bcb2a09d7354eb but is now: 8:9dc658dde71ed13636f18bbaafc8880e
```

Make sure that your application does not connect to the same database, already used by another application with liquibase included.