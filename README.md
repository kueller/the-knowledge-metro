# “The Knowledge”

Browser game which challenges the player to map a valid path between two metro stations by memory.
Inspired by the famously challenging [course to become a London taxi driver](https://youtu.be/u7gp8KBP7ak?si=zMoo8qQ-Fc6BdXPH)
where a prospctive driver is tested to verbally provide, from memory, detailed driving directions between any two landmarks in 
London.

The version for the Paris Métro is live here: (https://knowledge.glitch.paris/)

This system was designed to be adaptable for any urban rail system. Following this introduction is an entire guide on how to setup 
an instance of The Knowledge. While I don't really expect this to be used, I am still leaving it here in case someone 
who knows what they are doing is interested enough.


# Requirements

**Note:** The guide assumes you are comfortable with the various frameworks, tools, and setup configurations. Learning how to use
them is up to you. There are many good tutorials online.

The Knowledge server is a **Spring Boot** (4.0.5) application with the Java 25 JVM. The frontend web pages and scripts are all included in the project.
The backend is written in **Kotlin** (2.3.20) and the front in standard HTML/CSS/Javascript. You are **not** required to use the frontend
I made. I'm not very good at that anyways. Anything that can call the single endpoint and pass the right parameters is valid.
Configuration is explained further in [Configuration](#configuration) and the organization of the source files in
[Source Structure](#source-structure).

The application connects to a MySQL database. MySQL is not specifically necessary. However, the database setup is important to 
understand and is discussed in depth in [Database Setup](#database-setup).

The application uses **maven** to run locally and to build a .jar file for production which I run on a VPS. Compiling and
packaging is explained more in [Building](#building).

There are 3 Javascript libraries used for minifying the frontend files automatically during the building process. They are
- [Lightning CSS](https://www.npmjs.com/package/lightningcss)
- [rollup](https://www.npmjs.com/package/rollup)
- [terser](https://www.npmjs.com/package/terser)
These are **not** required. You can skip this during compilation if you wish to minify the files yourself, or skip the step entirely.
This is also explained further in [Building](#building).

The frontend uses the [**microfuzz**](https://github.com/Nozbe/microfuzz) Javascript library for searching the station names.
But it is imported via URL so no installation needed.

There are two optional Python scripts only to help during setup. This is explained further when creating the
[JSON files](#json-libraries)

# Configuration

## Spring Boot Configuration

The `application*.properties` config files are in (src/main/resources). Some of the values are described below 
(others are assumed obvious or are better described in Spring Boot documentation):

| Parameter | Type | Description | File |
|     ---   |  --- |      ---    |  --- |
| `application.domain.url` | string (url) | The URL of the project. Should be `localhost` when running locally. | all |
| `application.path.external` | string (environment variable) | This is a path to a dirctory on the actual server filesystem that will be the equivalent of (src/main/resources). Useful for storing dynamic files. The hosting server must have the `$EXTERN_PATH` environment variable set and ponting to a directory before running the application. Keep it safe!| all |
| `application.use-mins` | boolean | If set to true the server will provide minified Javascript and CSS files. If set to false it will provide the original unminified ones. This should ideally be set to true in production. It is very useful when testing locally to have it set to false.| all |
| `spring.datasource.url` | string (uri) | URI for the database. In production this is the environment variable `$DATABASE_URI`. In dev it uses an included in-memory database for testing (h2).| all |
| `application.datasource.username` | string | Username for the database to connect to. In prod it is defined by `$KNOWLEDGE_DATABASE_USER`. | all (only custom in prod) |
| `application.datasource.password` | string | Password for the database. In prod it is `$KNOWLEDGE_DATABASE_PASSWORD` | all (only custom in prod) |
| `spring.exposed.generate-ddl` | boolean | **Caution in production.** If true the application will set up the database based on its defined schema. Set to false in production to not override anything. Databases are read-only in this application anyways.| all |
| `spring.sql.init.mode` | string | **Caution in production.** If true the application will initialize the server with the values in [data.sql](src/main/resources/data.sql). Same danger applies as the last entry. | all |

From the above table there are four (4) environment variables that must be set when running in production.
```sh
EXTERN_PATH     # Should always be set, not just in production.
DATABASE_URI
KNOWLEDGE_DATABASE_USER
KNOWLEDGE_DATABASE_PASSWORD
```

## Source Structure

A brief summary of some of the code files and what they mean.

- [com.glitch.knowledge](src/main/kotlin/com/glitch/knowledge/)
    - [config](src/main/kotlin/com/glitch/knowledge/config/): Configurations for custom Spring Boot setup.
        - [CookiePatisseire.kt](src/main/kotlin/com/glitch/knowledge/config/CookiePatisserie.kt): Helper functions for the cookie that only holds the player's language choice.
    - [model](src/main/kotlin/com/glitch/knowledge/model/): Models for the database tables. See [Database Setup](#database-setup).
    - [rest](src/main/kotlin/com/glitch/knowledge/rest/): Web endpoints.
        - [HomeController.kt](src/main/kotlin/com/glitch/knowledge/rest/HomeController.kt): Serves the HTML for the actual web page of the game.
        - [VerifyController.kt](src/main/kotlin/com/glitch/knowledge/rest/VerifyController.kt): The main and only API endpoint for the game. See [Verify](#verify-endpoint).
- [resources](src/main/resources/)
    - [static](src/main/resources/static/)
        - [js](src/main/resources/static/js) (Javascript)
            - [const](src/main/resources/static/js/const/lines.js) 
                [lines.js](src/main/resources/static/js/const/lines.js): Representation of the train lines. Used by the frontend. See [JSON Libraries](#json-libraries).
                [stations.js](src/main/resources/static/js/const/stations.js): Representation of the train stations with data for calling the backend and also integrating with minifuzz search. Also see [JSON Libraries](#json-libraries).
            - [dist](src/main/resources/static/js/dist/): The minified javascript files go here.
            - [locale](src/main/resources/static/js/locale/): For translations, since my version is multilingual.
            - [knowledge.js](src/main/resources/static/js/knowledge.js): The bulk of the frontend is here. The only important part is the call to the /verify endpoint. See [Verify](#verify-endpoint).
        - [css](src/main/resources/static/style/): CSS and the minified CSS both go here.
    - [templates](src/main/resources/templates/)
        - [index.html](src/main/resources/templates/index.html): The homepage. The HTML is designed for use with Thymeleaf.
- [script](src/script/): Python scripts for generatingthe JSON. These don't get bundled into the final .jar file. See [JSON Libraries](#json-libraries).

# Building

## Install

Simplest command is
```sh
mvn clean install
```
The installation process calls Javascript libraries to minify the Javascript and CSS files. Install the command line versions via npm:
```sh
npm install -g lightningcss
npm install -g rollup
npm install -g terser
```
If you wish to use your own minification tools you can edit the (pom.xml) under the `exec-maven-plugin` configuration.

If you wish to skip the minify process entirely use install this way.
```sh
mvn clean install -DskipMinify
```

When you have the output .jar file in `/target` you can run it with
```sh
java -XX:+UseCompactObjectHeaders -jar /var/www/glitch/knowledge-1.x.x.jar --spring.profiles.active=prod
```
Remember to check the version number and use whichever application environment you want.

## Running locally

To run the application from the command line:
```sh
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```
Use any environment you wish. Remember to set up the `application-*.properties` with the values you want.