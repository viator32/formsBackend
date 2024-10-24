# Form Backend

## Overview

**Form Backend** is a Spring Boot application designed to manage form structures through a set of RESTful API endpoints. It allows users to create, read, update, delete, search, and paginate through form structures. Additionally, it provides a summarized view of all forms without exposing their JSON structures. The application is integrated with **Swagger UI** for interactive API documentation and testing.

## Features

- **CRUD Operations:** Create, Read, Update, and Delete form structures.
- **Search Functionality:** Search forms by name with pagination support.
- **Form Summaries:** Fetch a list of all forms containing only `id`, `name`, and `dateCreated`.
- **Pagination:** Retrieve forms in paginated responses to handle large datasets efficiently.
- **Swagger UI Integration:** Interactive API documentation and testing interface.
- **Validation:** Ensures data integrity with input validation.
- **Security Configuration:** Configurable access to API endpoints (if needed).

## Technologies Used

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **H2 Database**
- **Springdoc OpenAPI (Swagger UI)**
- **Maven**

## Prerequisites

Before setting up the project, ensure you have the following installed on your machine:

- **Java Development Kit (JDK) 17** or later.
- **Apache Maven** for building and managing project dependencies.
- **IDE** (Optional but recommended) such as IntelliJ IDEA, Eclipse, or VS Code.
- **Postman** (Optional) for API testing.
- **Docker** (Optional) for containerizing the application.

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/form-backend.git
cd form-backend
```

*Replace `your-username` with your actual GitHub username if applicable.*

### 2. Build and Run Locally

#### 2.1. Ensure Maven and Java Are Installed

Verify that both Maven and Java are correctly installed and configured in your system's `PATH`.

```bash
mvn -version
java -version
```

#### 2.2. Install Dependencies and Build the Project

Navigate to the project root directory and run:

```bash
mvn clean install
```

This command will compile the project, run tests, and package the application into a JAR file located in the `target/` directory.

#### 2.3. Run the Application

Start the Spring Boot application using Maven:

```bash
mvn spring-boot:run
```

Alternatively, you can run the packaged JAR file:

```bash
java -jar target/form-backend-0.0.1-SNAPSHOT.jar
```

#### 2.4. Access the Application

- **API Endpoints:** `http://localhost:8080/api/form-structures`
- **H2 Console:** `http://localhost:8080/h2-console`
  - **JDBC URL:** `jdbc:h2:file:/app/data/formdb`
  - **Username:** `sa`
  - **Password:** *(leave blank)*
- **Swagger UI:** `http://localhost:8080/swagger-ui/index.html`

### 3. Docker Setup (Optional)

Containerizing the application simplifies deployment and ensures consistency across environments.

#### 3.1. Building the Docker Image

Ensure Docker is running on your machine. Navigate to the project root directory (where the `Dockerfile` is located) and build the Docker image:

```bash
docker build -t form-backend:latest .
```

#### 3.2. Running the Docker Container

Run the Docker container, mapping the container's port `8080` to your local machine and mounting a volume for data persistence:

```bash
docker run -d -p 8080:8080 -v form-data:/app/data --name form-backend form-backend:latest
```

*Explanation of Flags:*
- `-d`: Runs the container in detached mode.
- `-p 8080:8080`: Maps port `8080` of the host to port `8080` of the container.
- `-v form-data:/app/data`: Mounts the Docker volume `form-data` to `/app/data` inside the container for data persistence.
- `--name form-backend`: Names the container `form-backend`.

#### 3.3. Stopping and Removing the Container

- **Stop the Container:**

  ```bash
  docker stop form-backend
  ```

- **Remove the Container:**

  ```bash
  docker rm form-backend
  ```

### 4. Using Swagger UI

**Swagger UI** provides an interactive interface to explore and test your API endpoints.

#### 4.1. Access Swagger UI

After running the application, open your web browser and navigate to:

```
http://localhost:8080/swagger-ui/index.html
```

#### 4.2. Explore API Endpoints

- **API Documentation:** Swagger UI will display all available API endpoints, grouped by controllers.
- **Interactive Testing:** Click on any endpoint to expand its details, view required parameters, and execute requests directly from the browser.
- **Authentication (If Configured):** If your API requires authentication, use the "Authorize" button in Swagger UI to input your credentials.

#### 4.3. Example Workflow

1. **Retrieve All Form Structures:**
   - **Endpoint:** `GET /api/form-structures`
   - **Description:** Fetches a paginated list of all form structures.
   - **Parameters:**
     - `page` (query, optional): Page number (default `0`).
     - `size` (query, optional): Number of records per page (default `10`).

2. **Create a New Form Structure:**
   - **Endpoint:** `POST /api/form-structures`
   - **Description:** Creates a new form structure.
   - **Body:**
     ```json
     {
         "name": "Registration Form",
         "structureJson": "{\"fields\": [{\"name\": \"firstName\", \"type\": \"string\"}, {\"name\": \"age\", \"type\": \"number\"}]}"
     }
     ```

3. **Retrieve a Specific Form Structure:**
   - **Endpoint:** `GET /api/form-structures/{id}`
   - **Description:** Fetches a form structure by its ID.

4. **Update an Existing Form Structure:**
   - **Endpoint:** `PUT /api/form-structures/{id}`
   - **Description:** Updates a form structure by its ID.
   - **Body:**
     ```json
     {
         "name": "Updated Registration Form",
         "structureJson": "{\"fields\": [{\"name\": \"firstName\", \"type\": \"string\"}, {\"name\": \"age\", \"type\": \"number\"}, {\"name\": \"email\", \"type\": \"string\"}]}"
     }
     ```

5. **Delete a Form Structure:**
   - **Endpoint:** `DELETE /api/form-structures/{id}`
   - **Description:** Deletes a form structure by its ID.

6. **Search Form Structures by Name:**
   - **Endpoint:** `GET /api/form-structures/search`
   - **Description:** Searches for form structures by name.
   - **Parameters:**
     - `name` (query, required): The name or partial name to search for.
     - `page` (query, optional): Page number (default `0`).
     - `size` (query, optional): Number of records per page (default `10`).

7. **Fetch All Form Summaries:**
   - **Endpoint:** `GET /api/form-structures/summary`
   - **Description:** Retrieves a list of all forms with only `id`, `name`, and `dateCreated`.

#### 4.4. Example Usage

1. **Creating a New Form:**
   - Navigate to the `POST /api/form-structures` endpoint in Swagger UI.
   - Click "Try it out" and input the JSON body.
   - Click "Execute" to send the request.
   - Observe the response with the created form's details.

2. **Fetching Form Summaries:**
   - Navigate to the `GET /api/form-structures/summary` endpoint.
   - Click "Try it out" and "Execute" without any parameters.
   - View the list of form summaries in the response.

### 5. API Documentation

The application uses **Springdoc OpenAPI** to generate interactive API documentation available through **Swagger UI**.

#### 5.1. Accessing OpenAPI Docs Directly

You can access the raw OpenAPI JSON documentation at:

```
http://localhost:8080/v3/api-docs
```

#### 5.2. Swagger UI Features

- **Endpoint Descriptions:** Detailed information about each API endpoint, including HTTP methods, paths, parameters, request bodies, and responses.
- **Model Schemas:** Definitions of the data models used in requests and responses.
- **Interactive Testing:** Execute API calls directly from the documentation interface.
- **Authentication Support:** If applicable, configure authentication tokens or credentials within Swagger UI.

### 6. Troubleshooting

#### 6.1. Maven Build Errors

- **Error: No goals have been specified for this build.**
  - **Cause:** Running `mvn` without specifying a goal or lifecycle phase.
  - **Solution:** Always include a goal like `clean install`, `spring-boot:run`, etc.
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

#### 6.2. Whitelabel Error Page When Accessing Swagger UI

- **Error:** `Whitelabel Error Page` with `404 Not Found` when accessing `http://localhost:8080/swagger-ui/index.html`.
  
  **Possible Causes:**
  - **Missing Springdoc Dependency:** Ensure `springdoc-openapi-starter-webmvc-ui` is included in `pom.xml`.
  - **Incorrect Application Properties:** Verify `springdoc.swagger-ui.path` is correctly set or use default paths.
  - **No REST Controllers Defined:** Swagger requires at least one REST controller to generate documentation.
  - **Spring Security Blocking Access:** Ensure Swagger endpoints are permitted in security configurations.
  
  **Solutions:**
  1. **Verify Dependencies:**
     - Check `pom.xml` for the Springdoc dependency.
     - Run `mvn clean install` to ensure dependencies are downloaded.
  
  2. **Check Application Properties:**
     - Ensure Swagger paths are correctly configured.
  
  3. **Confirm REST Controllers Exist:**
     - Ensure at least one controller with endpoints is defined.
  
  4. **Review Security Configurations:**
     - Permit access to Swagger endpoints in `SecurityConfig.java`.
  
  5. **Rebuild and Restart Application:**
     - Run `mvn clean install` and `mvn spring-boot:run`.
  
  6. **Clear Browser Cache:**
     - Sometimes, cached responses can cause 404 errors.

#### 6.4. Application Not Accessible

- **Issue:** API endpoints return connection refused or 500 errors.
  
  **Solutions:**
  - **Ensure Application is Running:** Check if the Spring Boot application is active.
  - **Verify Port Mappings:** Ensure the application is running on port `8080` or adjust accordingly.
  - **Check Application Logs:** Look for runtime errors in the console or log files.
  - **Firewall Settings:** Ensure that local firewall settings are not blocking port `8080`.


### 7. License

This project is licensed under the [MIT License](LICENSE).
