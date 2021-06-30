# Neptune Bank App

This application was generated using JHipster 6.2.0, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v6.2.0](https://www.jhipster.tech/documentation-archive/v6.2.0).

## Requirements

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Java][] 8 or above
2. [Maven][] 3.3 or above
3. [Node.js][] 10 or above
4. [MySQL][] 5.7 or above

## Development

After installing the requirements, you should be able to run the following command to install development tools. You will only need to run this command when dependencies change in [package.json](package.json).

```
npm install
```

We use Maven, npm scripts and [Webpack][] as our build system. To deploy the application run the following commands:

```
./mvnw clean
./mvnw -Pdev
```

After deployment navigate to [http://localhost:4080](http://localhost:4080) in your browser.

Alternatively, to create a blissful development experience where your browser auto-refreshes when files change on your hard drive also run the following command in a separate terminal:

```
npm start
```

This will start a [Browsersync][] server on port 3001 and a proxy server to handle the auto-refreshing on port 9000. Your browser should open automatically once the proxy is ready.

To configure the server port open the configuration file `config/application-dev.yml` and set `server.port` to the desired port (note the port numbers below 1024 might require sudo or other system configuration in order to run the application).

The development server runs on a [H2][] database which is automatically created during deployment along with its table and is populated with some pre-defined data. To view or manage the database, navigate to [http://localhost:4080/h2-console](http://localhost:4080/h2-console) after the application has been deployed.

### Managing dependencies

Npm is also used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by specifying a newer version in [package.json](package.json). You can also run `npm update` and `npm install` to manage dependencies. Add the `help` flag on any command to see how you can use it. For example, `npm help update`.

The `npm run` command will list all of the scripts available to run for this project.

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

## Production

### Configuration

To configure the system for production:

1. Create a database for the application in the MySQL server with the name of your preference (the tables are created and/or updated during the first run)
2. Open the configuration file [config/application-prod.yml](./config/application-prod.yml) and set:
   1. `spring.datasource.url` to the database host, port and schema (defaults to **localhost**, **3306** and **neptune** respectively).
   2. `spring.datasource.username` to the database username (defaults to **neptune**)
   3. `spring.datasource.password` to the password of the database user set on previous step (defaults to **neptune**)
   4. `server.port` with server port (defaults to **8080**)

### Packaging as jar

To build the final jar and optimize the Neptune Bank app for production, run:

```
./mvnw -Pprod clean package
```

This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files. To ensure everything worked, run:

```
java -jar target/neptunebank-app*.jar
```

Then navigate to [http://localhost:4080](http://localhost:4080) in your browser.

Refer to [Using JHipster in production][] for more details.

## Testing

To run the application's unit tests, run:

```
./mvnw test
```

To also run the application's integration tests, first deploy the application and then run the following:

```
./mvnw verify
```

Note that this the above command will interfere with the `target` directory so the application should be deployed from somewhere else.

### Client tests

Unit tests are run by [Jest][] and written with [Jasmine][]. They're located in [src/test/javascript/](src/test/javascript/) and can be run with:

```
npm test
```

For more information, refer to the [Running tests page][].

### Code quality

Code quality analysis is done using SonarQube. You can run a Sonar analysis with using the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the maven plugin. For the latter, edit the `sonar.host.url`, `sonar.projectKey` and `sonar.login` properties in the [sonar-project.properties](./sonar-project.properties) configuration file.

Then, compile or package the application and then run the analysis using:

```
./mvnw initialize sonar:sonar
```

The the `initialize` phase is responsible for loading the properties file. Alternatively, the required properties can be provided as command-line parameters using the following command:

```
./mvnw sonar:sonar -Dsonar.projectKey=<projectKey> -Dsonar.host.url=<url> -Dsonar.login=<login>
```

For more information, refer to the [Code quality page][].

## Using Docker to simplify development

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a mysql database in a docker container, run:

```
docker-compose -f src/main/docker/mysql.yml up -d
```

To stop it and remove the container, run:

```
docker-compose -f src/main/docker/mysql.yml down
```

You can also fully dockerize your application and all the services that it depends on. To achieve this, first build a docker image of your app by running:

```
./mvnw -Pprod package jib:dockerBuild
```

Then run:

```
docker-compose -f src/main/docker/app.yml up -d
```

You can also start a local Sonar server (accessible on http://localhost:9001) for local testing purposes with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

To list the containers and view their status run:

```
docker ps -a
```

To view the container logs run:

```
docker logs -f <container_name>
```

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

[jhipster homepage and latest documentation]: https://www.jhipster.tech
[jhipster 6.2.0 archive]: https://www.jhipster.tech/documentation-archive/v6.2.0
[using jhipster in development]: https://www.jhipster.tech/documentation-archive/v6.2.0/development/
[using docker and docker-compose]: https://www.jhipster.tech/documentation-archive/v6.2.0/docker-compose
[using jhipster in production]: https://www.jhipster.tech/documentation-archive/v6.2.0/production/
[running tests page]: https://www.jhipster.tech/documentation-archive/v6.2.0/running-tests/
[code quality page]: https://www.jhipster.tech/documentation-archive/v6.2.0/code-quality/
[node.js]: https://nodejs.org/
[java]: https://adoptopenjdk.net/
[maven]: https://maven.apache.org/
[mysql]: https://dev.mysql.com/
[yarn]: https://yarnpkg.org/
[webpack]: https://webpack.github.io/
[angular cli]: https://cli.angular.io/
[browsersync]: http://www.browsersync.io/
[jest]: https://facebook.github.io/jest/
[jasmine]: http://jasmine.github.io/2.0/introduction.html
[protractor]: https://angular.github.io/protractor/
[leaflet]: http://leafletjs.com/
[definitelytyped]: http://definitelytyped.org/
