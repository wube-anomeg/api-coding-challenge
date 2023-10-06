# MD Bank API

Internal API for Bank of Maryland to manage accounts and transactions.


## Getting Started

These instructions will help you to run the application on your local machine for development and testing purposes. 

### Prerequisites

Requirements for the software and other tools to build, test and push
- JAVA 17
- Spring Boot 3.1.4
- Gradle 7.2

### Database
- H2 in memory Database Engine


### Building and Running the application locally

### Packaging as jar

To build the final jar and optimize the formsGov application for production, run:
```
Change directory to the project root directory
```

Run this command to build the jar file
```
./gradlew -Pprod clean bootJar
```
Run this command to run the jar file

```
java -jar build/libs/*.jar
```

Then navigate to [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health) in your browser.
you should see the following response
```
{
    "status": "UP"
}
```

## Testing the API via Swagger UI

Navigate to [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) in your browser.

![Screenshot 2023-10-06 at 1.43.02 PM.png](..%2F..%2F..%2F..%2F..%2Fvar%2Ffolders%2F08%2Fzjtf7d2x3n5_nfsxs_10xzth0000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_XEnDpr%2FScreenshot%202023-10-06%20at%201.43.02%20PM.png)

You can test all the API endpoints from the swagger UI.

Note. The CUSTOMER and ACCOUNTS tables are  prepopulated the database with some sample data at startup.

You can check the data in the database by navigating to [http://localhost:8080/h2-console](http://localhost:8080/h2-console) in your browser.
use the following credentials to login to the H2 in meme database
```
username: sa
password: password
```
 ![Screenshot 2023-10-06 at 1.48.57 PM.png](..%2F..%2F..%2F..%2F..%2Fvar%2Ffolders%2F08%2Fzjtf7d2x3n5_nfsxs_10xzth0000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_yfVhaS%2FScreenshot%202023-10-06%20at%201.48.57%20PM.png)
 
The database has 3 tables TRANSACTION_HISTORY, CUSTOMER and ACCOUNTS
Please refer the domain model for the details of the tables and the relationships.

## Running the unit tests

``` 
./gradlew test 

```

### Running the Integration tests

```
 ./gradlew  integrationTest 

```

### Running both unit test and  Integration tests

```
 ./gradlew  test integrationTest 

```

## Versioning

We use [Semantic Versioning](http://semver.org/) for versioning.

## Authors

- **Wube Kifle** 


 