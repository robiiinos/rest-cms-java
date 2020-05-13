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

Before all, you should fill the [*.properties](src/main/resources) files in the resources directory with your configuration. This includes the database configuration.

```bash
// Compile & Package project
$ mvn package

// Start the application.
$ java -jar target/rest-project.jar
```

> Note: The Maven command will migrate / seed the database on launch.

## Database

Database (models, indexes, ...) can be found [here](src/main/resources/db/migration).

> Explications: The `Article` model serves as a base model for every article-related field that do no require to be translated (such as the slug, status, ...). The `ArticleTranslation` is a child of `Article` where every translatable-field for each article is store; including the default language (English - en). A last table `Language` list every available languages (and locale code for the API), these fields can be extended to store voice for a text-to-speech system, ...

## Documentation

Documentation about all available endpoints (for public and private API) is available [here](docs/README.md).