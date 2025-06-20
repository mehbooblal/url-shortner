# URL Shortener Coding Task

## Task

Build a simple **URL shortener** in a ** preferably JVM-based language** (e.g. Java, Kotlin).

It should:

- Accept a full URL and return a shortened URL.
- Persist the shortened URLs across restarts.
- Allow a user to **customise the shortened URL** (e.g. user provides `my-custom-alias` instead of a random string).
- Expose a **simple UI** (basic form is fine — no need for a polished design).
- Expose a **RESTful API** to perform create/read/delete operations on URLs.  
  → Refer to the provided [`openapi.yaml`](./openapi.yaml) for API structure and expected behaviour.
- Include the ability to **delete a shortened URL** via the API.
- **Have tests**.
- Be containerised (e.g. Docker).
- Include instructions for running locally.

## Rules

- Fork the repository and work in your fork. Do not push directly to the main repository.
- We suggest spending no longer than **4 hours**, but you can take longer if needed.
- Commit often with meaningful messages.
- Write tests.
- Use the provided [`openapi.yaml`](./openapi.yaml) as a reference.
- Focus on clean, maintainable code.

## Deliverables

- Working code.
- Simple UI.
- RESTful API matching the OpenAPI spec.
- Tests.
- Dockerfile. -- named Dockerfile
- README with:
  - How to build and run locally.
     -   mvn clean package
     -   ./mvnw clean install
     -   Go to the folder where the jar file is store
     -  java -jar shorturl-0.0.1-SNAPSHOT.jar
  - Example usage (UI and/or API).
  -   To use APIs :
  -   To redirect : Method Type: Get -->  http://localhost:8080/api/urls/google
  -   To Delete : Method type: Delete --> http://localhost:8080/api/urls/developer
  -   To use Web site : http://localhost:8080/
  - Any notes or assumptions.
    
