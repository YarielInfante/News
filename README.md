# News

This is the situation: We are a publishing company that created an app for reading news articles. 

To serve data to the app we need a backend to provide RESTful APIs for the following use cases:

- allow an editor to create/update/delete an article display one article
- list all articles for a given author
- list all articles for a given period
- find all articles for a specific keyword

Each API should only return the data that is really needed to fulfill the use case. An article usually consists of the following information:
- header
- short 
- description 
- text
- publish date 
- author(s) 
- keyword(s)


# Implementation 

### Endpoints available

      Article
    - HTTP GET at /api/v1/articles (List of articles paginated)
    - HTTP GET at /api/v1/articles/{articleId} (gets a specific article by the id)
    - HTTP POST at /api/v1/articles (creates an article)
    - HTTP PUT at /api1/v1/article/{articleid} (updates a specific article)
    - HTTP DELETE at /api/v1/articles/{articleId} (deletes a specific article)
    - HTTP GET at /api/v1/articles?authorsId={array of authors' id } (List of articles paginated for a given set of authors's id)
    - HTTP GET at /api/v1/articles?keywordsId={array of keywords' id} (list of articles paginated for a given set of keywords' id)
    - HTTP GET at /api/v1/articles?dateFrom={date string with format dd-MM-yyyy}&dateTo=(date string with format dd-MM-yyyy) (list of articles paginated for a given period)
     
     Keyword
     - HTTP GET at /api/v1/keywords (list of keywords paginated)
     - HTTP GET at /api/v1/keywords?keyword={value} (finds keywords paginated that match with the value)
     - HTTP POST at /api/v1/keywords (creates a keyword)
     
     Author
     - HTTP GET at /api/v1/authors (list of authors paginated)
     - HTTP GET at /api/v1/authors?name={value} (finds authors paginated that match with the value)
     - HTTP POST at /api/v1/authors (creates a author)
    
   
Definition of endpoint
---
There is a .json archive that can be easily imported into postman (api client tool. Download it at https://www.getpostman.com) where you can find 
all endpoint's definitions.  


Program Execution
----
System prerequisite:
- Java 12
- Gradle

To make all these requests possible, I used:

- **Java 12**: to handle the main code development.
- **Spring Boot**: a Framework to ease development and configuration.
- **H2 Database**: relational database management system written in Java To store the information requested.
- **Gradle**: To download dependencies and compile the project.

Project configuration
  
**application.properties** file has the program default configuration parameters that are:

**Connection String.** Here it uses a database named _**news**_

        spring.datasource.url=jdbc:h2:mem:news
        username to connect
        spring.datasource.username=sa
        password to connect
        spring.datasource.password=password
        
**Application properties**

        base path to run api
        server.servlet.context-path=/api/v1
        port to run server
        server.port=8080
        
**There are different application.properties for different environments such as**
- application.properties - default configuration
- application-dev.properties - configuration for development
- application-test.properties - configuration for testing        

This program is designed to run and create the whole schema.

**There are different tables for storing information such as:**

     - article
     - keyword
     - author
     - article_author (relationship between article and author)
     - article_keyword (relationship between article and keyword)
     

The file named **data.sql** contains initial information for keywords and authors.

**Application Messages**

File named ValidationMessages_en.properties has all messages returned by the api.


Compile source code
----

To compile source code run :

        gradle clean build
        
To run compiled code compiled run :

        java -jar build/libs/News-0.0.1-SNAPSHOT.jar
        
Tests
---

Integration tests were developed using :
  
       - spring boot test
       - Mockito
            
          