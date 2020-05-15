# rest-cms-java

JAVA project offering a public and private API, with authentication, for a Content Management System (CMS).

The application will start 2 multi-threaded Jetty server, each serving their respective RESTful API (one for public, one for private).
All public endpoints are read-only; and support localization, up to `n` languages; the default language is `en` for English.

## Technological stack

### Requirements

- JAVA 11.x

- Maven 3.x

- MySQL 8.x

### Packages

- [SparkJava](http://sparkjava.com/) is used as a base framework to build API's.

- [Apache Log4j 2](https://logging.apache.org/log4j/2.x/) is used for application logging.

- [Auth0 JWT](https://github.com/auth0/java-jwt) is user authentication / authorization.

- [HikariCP](https://github.com/brettwooldridge/HikariCP) is used for JDBC connection pooling.

- [Flyway](https://flywaydb.org/) is used for database state management (migrations, seeds, ...).

- [JOOQ](https://www.jooq.org/) is used for database mapping.

- [Lombok](https://projectlombok.org/) is used for boilerplate code.

- [Hibernate Validator](https://hibernate.org/validator/) is used for incoming request validation.

- [Gson](https://github.com/google/gson) is used for POJO conversion to JSON and back.

## Usage

#### 1. Setup the database

- Install a MySQL 8.x server.

- Create a user

```
$ CREATE USER 'my_username'@'%' IDENTIFIED BY 'my_password';
```

- Create the database

```
$ CREATE DATABASE rest_cms_java CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

- Give permissions to the user for the database

```
$ GRANT ALL PRIVILEGES ON rest_cms_java.* TO 'my_username'@'%';
$ FLUSH PRIVILEGES;
```

#### 2. Fill the project property files

```bash
// Configuration for the database
$ vim src/main/resources/datasource.properties

// Configuration for the JWT auth (secret key for signing)
$ vim src/main/resources/auth.properties
```

> Note: The `schema` property is here in case your database schema is not the same as your database name. Fill this property with your database name or, if you changed it manually, your main schema for this project. 

#### 3. Compile / Package the project

```bash
$ mvn package
```

> Note: The Maven command will migrate / seed the database on launch.

#### 4. Run the project

```bash
$ java -jar target/rest-cms-java.jar
```

## Troubleshooting

If you have any errors due to JOOQ, make sure that your [datasource property file](src/main/resources/datasource.properties) is filled with the correct information; and that the migrations / seeders are deployed.

Migrations and seeding of the database is done by FlyWay package building; if you experience any issue with FlyWay; please run the following command manually :

```
$ mvn flyway:migrate -Dflyway.url="..."  -Dflyway.schemas=... -Dflyway.user=... -Dflyway.password=...
```

## Testing

This project contains unit tests which you can see [here](src/test/java/com/github/robiiinos/service/external/ArticleServiceTest.java).

## Database

Database (models, indexes, ...) can be found [here](src/main/resources/db/migrations).

- Explanations:

The `Article` model serves as a base model for every article-related field that do no require to be translated (such as the slug, status, ...).

The `ArticleTranslation` is a child of `Article` where every translatable-field for each article is store; including the default language.

A last table `Language` list every available languages (and locale code for the API), these fields can be extended to store voice for a text-to-speech system, ...

## Documentation

Documentation about all available endpoints (for public and private API) is available [here](docs/README.md).