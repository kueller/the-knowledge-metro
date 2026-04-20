# “The Knowledge”

A browser game where the objective is to map a valid path between two metro stations from memory.
Inspired by the famously challenging [course to become a London taxi driver](https://youtu.be/u7gp8KBP7ak?si=zMoo8qQ-Fc6BdXPH)
where a prospctive driver is tested to verbally provide, from memory, detailed driving directions between any two landmarks in 
London.

The version for the Paris Métro is live here: [https://knowledge.glitch.paris](https://knowledge.glitch.paris/)

This system was designed to be adaptable for any urban rail system. Following this introduction is a entire guide to setup 
an instance of The Knowledge. While I don't really expect this to be used, I am still leaving it here for anyone who might be
interested and who knows what they are doing. It will probably also help me when I eventually forget my own code.

# Requirements

> [!NOTE]
> The guide assumes you are comfortable with the various frameworks, tools, and setup configurations. Learning how to use
> them is up to you. There are many good tutorials online.

***At a glance...***

Required:
- Spring Boot 4
- Kotlin 2.3
- MySQL or similar
- Some kind of server to host on

Optional:
- Lightning CSS
- rollup
- terser
- Python

***At a much longer glance...***

The Knowledge server is a **Spring Boot** (4.0.5) application running on the Java 25 JVM. The frontend web pages and scripts are all included in the project.
The backend is written in **Kotlin** (2.3.20). Configuration is explained further in [Configuration](#configuration) and some further explanation
in [Technical Overview](#technical-overview).

The application connects to a MySQL database. MySQL is not specifically necessary. However, the database setup in general is important to 
understand and is discussed in depth in [Database Setup](#database-setup).

The frontend is basic templated HTML, CSS, and Javascript. It also uses the [Fuse](https://github.com/krisk/Fuse) Javascript library
for searching the station names, but it is imported via URL so no installation needed.

The application uses **maven** to run locally and to build a .jar file for production. The .jar file is run on a VPS. Compiling and
packaging is explained more in [Building](#building).

There are 3 Javascript libraries used for minifying the frontend files automatically during the building process. They are
- [Lightning CSS](https://www.npmjs.com/package/lightningcss)
- [rollup](https://www.npmjs.com/package/rollup)
- [terser](https://www.npmjs.com/package/terser)

These are **not** required. You can skip this during compilation if you wish to minify the files yourself, or skip the step entirely.
This is also explained further in [Building](#building).

There are two optional Python scripts only to help during setup. This is explained further when creating the
[JSON files](#json-libraries) used in the frontend.

The Paris metro version uses the official [Parisine](https://typofonderie.com/fr/fonts/parisine/details) font. The font files are not provided in the repository since it is
officially licensed. You will need your own font. 

# Overview

A light overview of how this application functions, and an introduction to some of the terminology I will use in this guide. 

> [!NOTE]
> Most of the frontend is in [knowledge.js](src/main/resources/static/js/knowledge.js).
> However, this guide will focus on the backend system and will largely not cover the scripting or frontend. The focus of this guide is for anyone to
> build their own game for any rail system in the world. This would require a different frontend tailored for that system. I am not very good at frontend anyways.

## Terminology

### Lines
There are 3 types of train lines: "standard", "loop", and "semi-loop". Semi-loop lines are not perfectly connected and can branch off from their loops, such as the 7bis seen below. Lines have [terminus](#terminus) stations unless they are type "loop". Lines that have branches can have 3 or more termini.

![Paris metro line 7bis, which has a straight line section at one end and a loop at the other end.](/etc/7bis.png)

### Stations

Any stop on a train line. Stations can connect to one or more lines. Stations can also connect to other stations (see [tunnels](#tunnels) a bit below).
For any line, stations can be one-way or two-way. 

### Terminus

Terminus stations are [stations](#stations) that are at the end of [lines](#lines). A terminus station determines which direction a player may choose to travel. 
Lines of type "semi-loop" will have one terminus. Lines of type "standard" will have two or more termini. Lines of type "loop" will have no termini, since
in a perfect loop you can reach any station on the line eventually regardless of direction.

### Tunnels

Tunnels are **walking connections** between two or more [stations](#stations) that one can take without leaving the paid area (or equivalent "station area" if there are
no fares). This is different from a station that simply services multiple lines. In a tunnel network you are walking to a _completely different station_ with a
distinct name. 

Some examples:
- In Paris, the distinct metro stations _Saint-Augustin_, _Saint-Lazare_, _Havre Caumartin_, _Opéra_, and the RER station _Auber_ can all be accessed on foot within a tunnel
network. These connections can be [incredibly long and impractical](https://youtu.be/kGW_GgolvBY?si=bLaaA3mmMEStVLVz), but they are valid. The connections are shown in the image below.
- In London, in the station _Farringdon_, you can connect from the Elizabeth Line to the Circle, Hammersmith & City, and Metropolitan Lines by taking a side exit that leaves you on the platforms of _Barbican_ station. This is not even shown on the official tube map.
- In New York City during the day one can connect with tunnels from _Times Square - 42nd St_ to _42nd St - Port Authority Bus Terminal_ to access the blue lines and to _42nd St - Bryant Park_ to access the orange lines. 

![The section of the Paris metro map that shows the multiple stations with lines connecting them, implying you can access all on foot.](/etc/tunnel.png)

> [!NOTE]
> While these connections are called "tunnels", they do not have to be underground. I did not think of a better name. I will not be changing it.

## Functional Overview

1. The player is given two randomly chosen stations, an origin and a destination.
2. From the origin, the player chooses a line to take, which direction to take that line, and a station to stop at. The player should be given as little information as possible since this is a memory game. Unfortunately it can't be done verbally like the "appearances" in the Knowledge of London.
3. The game frontend will call the server to verify the path the player has taken.
    - If the path is correct and the player has stopped at their destination, $$\textsf{\color{green}{\textbf{the player has won and the game is over.}}}$$
    - If the path is correct and the player has **not** stopped at their destination yet, **the game continues and the player starts at step 2 again. The stopping point station becomes the new "origin" station.**
    - If the path is incorrect for any reason $$\textsf{\color{red}{\textbf{the player has lost and the game is over.}}}$$

## Technical Overview

- The HTML for the main page is constructed using a Thymeleaf template. The randomly chosen stations are added here.
- The frontend will handle the selection of the line, starting station, direction, and the stopping point.
- The backend server has only one endpoint, `/maison/verify`. It only checks that the choices for each step are correct. It is explained further in [Verify](#verify-endpoint).
- Because of this the frontend will also need to mirror some database data for the player to select. Maybe I could expand further API calls if there is a good reason.

Reasons a player can fail the verify check:
- The chosen line is not connected to the starting origin station.
    - If the origin station is connected to a tunnel network, a player can fail if the chosen line does not connect to any station on the tunnel network either.
- The chosen stopping point station is not on the chosen line.
- In a "standard" or "semi-loop" line, the player chose the wrong direction of travel to reach the chosen stopping point (calculated using [station position](#station-position)).
- The player chose an impossible direction of travel from, or to, a one-way station.
- The player chose an impossible direction of travel on a semi-loop when both the starting and stopping points are one-way.
    - Using Paris 7bis as an example again, see the image below. The loop section is one-way. If the player attempted to go from _Pré-Saint-Gervais_ to _Place des Fêtes_ (the red arrow) this would be invalid since the train would exit the loop and eventually reach its terminus. The valid play wuld be to exit the loop and stop at _Botzaris_, a bidirectional station, and then enter the loop again to reach _Place des Fêtes_.
    - However, if the player went from _Place des Fêtes_ to _Botzaris_ (the blue arrow) this would be valid regardless of direction, because it assumes the player simply took the entire loop.

![The end of the 7bis strip map. A red arrow is drawn from Pré Saint Gervais to Place des Fêtes. Then a blue arrow is drawn from Place des Fêtes to Botzaris.](/etc/7bis_directions.jpg)

# Configuration

## Spring Boot Configuration

The `application*.properties` config files are in [src/main/resources](/src/main/resources). Some of the values are described below 
(others are assumed obvious or are better described in Spring Boot documentation):

| Parameter | Type | Description |
|     ---   |  --- |      ---    |
| `application.domain.url` | string (URL) | The URL of the project. Should be `localhost` when running locally. |
| `application.path.external` | string (environment variable) | This is a path to a dirctory on the actual server filesystem that will be the equivalent of [src/main/resources](/src/main/resources). Useful for storing dynamic files. The hosting server must have the `$EXTERN_PATH` environment variable set to a directory when running the application. Keep it safe!|
| `application.use-mins` | boolean | If set to true the server will provide minified Javascript and CSS files. If set to false it will provide the original unminified ones. This should ideally be set to true in production. It is very useful when testing locally to have it set to false.|
| `spring.datasource.url` | string (URI) | URI for the database. In production this is the environment variable `$DATABASE_URI`. In dev it uses an in-memory database for testing (H2).|
| `application.datasource.username` | string | Username for the database to connect to. In prod it is defined by `$KNOWLEDGE_DATABASE_USER`. |
| `application.datasource.password` | string | Password for the database. In prod it is `$KNOWLEDGE_DATABASE_PASSWORD` |
| `spring.exposed.generate-ddl` | boolean | If true the application will set up the database based on its defined schema. Set to false in production to not override anything.|
| `spring.sql.init.mode` | string | If true the application will initialize the server with the values in [data.sql](src/main/resources/data.sql). Set to false in production to not override anything. |

> [!CAUTION]
> Be sure to always set the last two entries (`spring.exposed.generate-ddl` and `spring.sql.init.mode`) as **false** in production.
> If your production database account has write permissions this could overwrite the database entirely.
> For this game, as described in [Databases](#databases), you can get away with a read-only account.

From the above table there are four (4) environment variables that must be set when running in production.
```sh
EXTERN_PATH                     # Must be set for all environments, not just in prod.
DATABASE_URI
KNOWLEDGE_DATABASE_USER
KNOWLEDGE_DATABASE_PASSWORD
```

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
If you wish to use your own minification tools you can edit the [pom.xml](/pom.xml) under the [`exec-maven-plugin`](https://www.mojohaus.org/exec-maven-plugin/)
configuration.

If you wish to skip the minify process entirely use install this way.
```sh
mvn clean install -DskipMinify
```

When you have the output .jar file in `/target` you can run it with
```sh
java -XX:+UseCompactObjectHeaders -jar /var/www/glitch/knowledge-1.x.x.jar --spring.profiles.active=prod
```
Remember to check the version number and use whichever application environment you want. 

The `-XX:-UseCompactObjectHeaders` flag is not required, but it helps performance at effectively no cost.

## Running locally

To run the application from the command line:
```sh
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```
Use any environment you wish. Remember to set up the `application-*.properties` with the values you want.
To skip minification set the `application.use-mins` config variable to `false` as well as using `-DskipMinify`.

# Databases

![Visual flowchart of how the databases connect to each other](/etc/databases.png)

The databases are set up to properly represent an entire rail system. Possibly the most important aspect of the application. I designed them to 
hopefully accomodate most types of networks that may exist in the real world.

The ORM models of the tables for the server can be found in [com.glitch.knowledge.model](src/main/kotlin/com/glitch/knowledge/model/).

> [!NOTE]
> Since rail transport systems are largely fixed, and there is no reason for the game to _add_ any data, I connect the application to a **read-only** account.
> I update the databases myself with the admin account. Unless you plan to allow migrations programatically, I also recommend you use this limited access.

## Tables

### line 
| Column Name | Type | Required? | Description |
|    ---      | ---  |     ---   |      ---    |
| **id** | int | **Yes** | Primary key. |
| **name** | varchar(16) | **Yes** | Name of the line (don't use the id even if all your lines are numbered). |
| **type** | enum | **Yes** | `"STANDARD"`, `"LOOP"`, or `"SEMI_LOOP"` (See [Terminology](#terminology)) |

### station
| Column Name | Type | Required? | Description |
|    ---      | ---  |     ---   |      ---    |
| **id** | int | **Yes** | Primary key. |
| **name** | varchar(128) | **Yes** | Actual station name. |
| **tunnel_id** | int | No | Foreign key to tunnel.id |

### station_line
Intermediate for the many-many relationship of station and line. Also includes specific information about how a station services a given line.

| Column Name | Type | Required? | Description |
|    ---      | ---  |     ---   |      ---    |
| **id** | int | **Yes** | Primary key. |
| **station_id** | int | **Yes** | Foreign key to station.id |
| **line_id** | int | **Yes** | Foreign key to line.id |
| **position** | int | **Yes** | The station's "postion" on the line. See [Station Position](#station-position) |
| **branch_id** | int | No | The branch the station is on, if any. Links to the station.id of the terminus of that branch. It is `NULL` if this station is not on any branch. Because it can be `NULL`, it is not declared explicitly as a foreign key. |
| **direction** | enum | **Yes** | `BIDIRECTIONAL`, `INCREASING`, or `DECREASING`. The last two are one-way but in different directions. See [Position](#station-position) |

> [!NOTE]
> The `branch_id` links to the **station** table and not the terminus table. But the station.id referenced should still be a terminus of the line.

### terminus
Intermediate for the many-many relationship of the terminus stations of a line.

| Column Name | Type | Required? | Description |
|    ---      | ---  |     ---   |      ---    |
| **id** | int | **Yes** | Primary key. |
| **line_id** | int | **Yes** | Foreign key to line.id |
| **station_id** | int | **Yes** | Foreign key to station.id |

> [!IMPORTANT]
> Every line should have at least **one** terminus relationship to be compatible with the `/maison/verify` endpoint.
> Since lines of type `LOOP` have no terminus in reality, you can choose any station on the line at random to use as a default value.

### tunnel_network
| Column Name | Type | Required? | Description |
|    ---      | ---  |     ---   |      ---    |
| **id** | int | **Yes** | Primary key. Stations have a many-one relationship with this id, if they are connected by the tunnel. |
| **name** | varchar(100) | **Yes** | For internal reference. Never used in the application. Choose any name you'd like. |

## Station Position
![Partial image of Paris metro line 13. All stations are given a number, increasing from the north branches southward.](/etc/station_numbers.png)

To calculate the direction of travel each station is given a number, starting from 0 at one and and counting up. Which end of a line is considered 0 is completely arbitrary.
The numbers themselves are also completely arbitrary and should not be used for any consistent analysis. In the above example, because the lower branch has fewer stations than the higher branch the numbers between _Brochant_ and _La Fourche_ jump from 5 to 8. **This is fine.** The only thing that matters is that the numbers **increase** in one direction of travel and **decrease** in the other direction. 

This is how you can tag one-way stations in the [station_line](#station_line) table. If the one way station is pointing towards the 0 terminus, it is `DECREASING`. If it is pointing to towards the non-zero terminus it is `INCREASING`.

Similarly, this is how the player's direction of travel is checked. The player's chosen end point should have a position that is between the starting point position and the chosen terminus position (it can also be equal to the terminus position if the stopping point is the terminus).

> [!NOTE]
> For semi-loop lines the player can have a valid path going the "wrong way" by circling around on the loop. The only invalid case involves one-way stations. This case was
> described at the end of [Technical Overview](#technical-overview).
>
> For "loop" lines, none of this matters.

## JSON Libraries
This is one of the few references I will give to my own frontend implementation. 

The frontend needs to mirror the database to send the ids to the `/maison/verify` endpoint. Because of this, the station and line data was converted to JSON libraries that exported
a constant to use for reference. These JSON files are generated using the help of two Python scripts in [src/script](/src/script/). They each take a CSV file as input that you
can export from database queries. Running the scripts with `--help` will show you an example query.

For lines, the script creates JSON of a list, where the index of each object is the database line id. In each object is the id, name, and then a list of terminus stations
associated with the line. Each terminus element contains an id and name. This is in [lines.js](/src/main/resources/static/js/const/lines.js).

For stations, a less hacky list of elements with id, name, and an empty list "tags". The tags are any alternative key words that would return a valid search result
when the player chooses a station. For example, a tag for the station _Châtelet - Les Halles_ allows the player to simply type in "_Châtelet_".
I might include these "alternate names" in the station table in the future. This is in [stations.js](/src/main/resources/static/js/const/stations.js).

## Testing
For local testing there are two files for populating an in-memory [H2](https://h2database.com/html/main.html) database.
- [schema.sql](/src/main/resources/schema.sql) for defining and creating the tables (in practice this is now done automatically with the [Exposed](https://www.jetbrains.com/exposed/) library).
- [data.sql](/src/main/resources/data.sql) for populating the tables.

These files contain a lot of SQL code using the same values as the production database. You can take a look at them to get a further example of what the database setup
looks like in practice.

# Verify Endpoint

`GET /maison/verify/line/{line_id}/origin/{origin_id}/destination/{destination_id}/direction/{terminus_station_id}`

The only endpoint the game runs on. It verifies a single "step" from origin to destination. The endpoint saves no state or information about the player's own game.
It only cares if the parameters provided form a valid path on the system. Therefore, it is up to the frontend to track the game state to know if the player has won the game.
A single failed validation loses the game.

The input ids are the database table ids of the lines and stations. This is not good practice. You should not be using SQL ids outside of the database. I may change this in the future.

> [!NOTE]
> Successful requests return `200`. Invalid requests return `400`. Any `400` or `500` range responses should be handled by the frontend and **not** count against the player
> since that implies a failed format or server error. A _wrong answer_ will still return `200`.

> [!NOTE]
> For this endpoint, "origin" and "destination" refer to arbitrary starting and stopping points. It does not necessarily mean the "origin" and "destination" 
> that the player receives at the start of the game. Apologies for the confusing wording there. Remember the endpoint stores no state.

> [!NOTE]
> Since all inputs are path parameters, `terminus_station_id` is always required. This is why loop lines need at least one "default" terminus as mentioned in the [terminus table](#terminus) spec.

## Input
| Path parameter | Type | Description |
|     ---        |  --- |      ---    |
| `line_id` | int | [line](#line).id |
| `origin_id` | int | [station](#station).id of the starting point. |
| `destination_id` | int | [station](#station).id of the stopping point. |
| `terminus_station_id` | int | [station](#station).id of the terminus, to decide direction. |

## Return
On a successful query the endpoint returns `200` and the following JSON

| Name | Type | Description |
| ---  |  --- |    ---      |
| **status** | int | 0 on a valid path and -1 on a wrong one. |
| **message** | string\|optional | `null` on a valid path and a failure message code on a wrong one. |
| **origin_id** | int\|optional | The starting point station id on a valid path. If a tunnel was used the id will be of the station that actually connected to the chosen line. On a wrong path the id will be `null`. |

Any invalid ids will return a `400` with no data.

> [!TIP]
> You can use the `origin_id` to check if a tunnel network was used. If the `origin_id` in the response does not equal the one in the URL, it means the player
> connected from their starting point to a different station and accessed the line from there. You can use the return `origin_id` to acknowledge the connected station.

### Example (valid path)
```JSON
{
    "status": 0,
    "message": null,
    "origin_id": 161
}
```

### Example (incorrect path)
```JSON
{
    "status": -1,
    "message": "dest-not-on-line",
    "origin_id": null
}
```

### Failure messages
The full list of possible failure messages. They can be mapped to any more proper text in the frontend. I hope they are self-explanatory. In here "dest" is short for 
"destination", or the stopping point.

```JSON
"origin-not-on-line"
"no-tunnel-to-line"
"dest-not-on-line"
"wrong-branch"
"wrong-way"
"wrong-way-one-way"
```
