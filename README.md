```markdown
# Form Backend

## Overview

**Form Backend** is a simple Spring Boot application designed to manage form structures through RESTful API endpoints. It allows for creating, reading, updating, and deleting form structures, which are stored in an embedded H2 database. The application is containerized using Docker, enabling easy deployment and scalability alongside your frontend applications.

## Features

- **CRUD Operations**: Create, Read, Update, and Delete form structures.
- **RESTful API**: Intuitive and easy-to-use API endpoints.
- **Embedded H2 Database**: Simplifies local development and testing.
- **Dockerized**: Streamlines deployment with containerization.
- **Postman Collection**: Pre-configured requests for easy API testing.

## Technologies Used

- **Java 17**
- **Spring Boot 3.x**
- **Maven**
- **H2 Database**
- **Docker**
- **Lombok** (Optional)
- **Postman**

## Prerequisites

Before setting up the project, ensure you have the following installed on your machine:

- **Java Development Kit (JDK)**: Version 17 or later.
- **Apache Maven**: For building and managing project dependencies.
- **Docker**: For containerizing the application.
- **Postman**: For API testing.
- **IDE**: Optional but recommended (e.g., IntelliJ IDEA, Eclipse, VS Code).

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/form-backend.git
cd form-backend
```

*Replace `your-username` with your actual GitHub username if applicable.*

### 2. Build and Run Locally

#### 2.1. Ensure Maven and Java are Installed

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
  - **JDBC URL:** `jdbc:h2:mem:formdb`
  - **Username:** `sa`
  - **Password:** *(leave blank)*

### 3. Docker Setup

Containerizing the application simplifies deployment and ensures consistency across environments.

#### 3.1. Building the Docker Image

Ensure Docker is running on your machine. Navigate to the project root directory (where the `Dockerfile` is located) and build the Docker image:

```bash
docker build -t form-backend:latest .
```

#### 3.2. Running the Docker Container

Run the Docker container, mapping the container's port `8080` to your local machine:

```bash
docker run -d -p 8080:8080 --name form-backend form-backend:latest
```

*Explanation of Flags:*
- `-d`: Runs the container in detached mode.
- `-p 8080:8080`: Maps port `8080` of the host to port `8080` of the container.
- `--name form-backend`: Names the container `form-backend`.

#### 3.3. Persisting H2 Database Data

By default, H2 is configured as an **in-memory** database, meaning data is lost when the container stops. To persist data:

1. **Modify `application.properties`** to use a file-based H2 database:

   ```properties
   spring.datasource.url=jdbc:h2:file:/app/data/formdb
   spring.datasource.driverClassName=org.h2.Driver
   spring.datasource.username=sa
   spring.datasource.password=
   spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
   spring.jpa.hibernate.ddl-auto=update

   # Enable H2 Console
   spring.h2.console.enabled=true
   spring.h2.console.path=/h2-console
   ```

2. **Update the Dockerfile** to create a data directory:

   ```dockerfile
   # ... [Previous Dockerfile content]

   # Create data directory
   RUN mkdir -p /app/data

   # Set working directory
   WORKDIR /app

   # Copy the jar from the build stage
   COPY --from=build /app/target/form-backend-0.0.1-SNAPSHOT.jar app.jar

   # ... [Rest of the Dockerfile]
   ```

3. **Run the Container with Volume Mount:**

   ```bash
   docker run -d -p 8080:8080 -v form-data:/app/data --name form-backend form-backend:latest
   ```

   *Explanation:*
   - `-v form-data:/app/data`: Mounts the Docker volume `form-data` to `/app/data` inside the container, ensuring data persistence.

#### 3.4. Stopping and Removing the Container

- **Stop the Container:**

  ```bash
  docker stop form-backend
  ```

- **Remove the Container:**

  ```bash
  docker rm form-backend
  ```

### 4. API Documentation

Use **Postman** to interact with the API endpoints. Below are the details for each endpoint.

#### 4.1. Get All Form Structures

- **Method:** `GET`
- **URL:** `http://localhost:8080/api/form-structures`
- **Description:** Retrieves a list of all form structures.

**Sample Response:**

```json
[
    {
        "id": 1,
        "structureJson": "{\"fields\": [{\"name\": \"email\", \"type\": \"string\"}]}"
    },
    {
        "id": 2,
        "structureJson": "{\"fields\": [{\"name\": \"username\", \"type\": \"string\"}, {\"name\": \"password\", \"type\": \"password\"}]}"
    }
]
```

#### 4.2. Get Form Structure by ID

- **Method:** `GET`
- **URL:** `http://localhost:8080/api/form-structures/{id}`
- **Description:** Retrieves a specific form structure by its ID.

**Sample Response:**

```json
{
    "id": 1,
    "structureJson": "{\"fields\": [{\"name\": \"email\", \"type\": \"string\"}]}"
}
```

*If the ID does not exist:*

- **Status:** `404 Not Found`
- **Body:** *(empty)*

#### 4.3. Create a New Form Structure

- **Method:** `POST`
- **URL:** `http://localhost:8080/api/form-structures`
- **Headers:**
  - `Content-Type: application/json`
- **Body:**

```json
{
    "structureJson": "{\"fields\": [{\"name\": \"firstName\", \"type\": \"string\"}, {\"name\": \"age\", \"type\": \"number\"}]}"
}
```

**Sample Response:**

- **Status:** `201 Created`
- **Body:**

  ```json
  {
      "id": 3,
      "structureJson": "{\"fields\": [{\"name\": \"firstName\", \"type\": \"string\"}, {\"name\": \"age\", \"type\": \"number\"}]}"
  }
  ```

#### 4.4. Update an Existing Form Structure

- **Method:** `PUT`
- **URL:** `http://localhost:8080/api/form-structures/{id}`
- **Headers:**
  - `Content-Type: application/json`
- **Body:**

```json
{
    "structureJson": "{\"fields\": [{\"name\": \"firstName\", \"type\": \"string\"}, {\"name\": \"age\", \"type\": \"number\"}, {\"name\": \"email\", \"type\": \"string\"}]}"
}
```

**Sample Response:**

- **Status:** `200 OK`
- **Body:**

  ```json
  {
      "id": 3,
      "structureJson": "{\"fields\": [{\"name\": \"firstName\", \"type\": \"string\"}, {\"name\": \"age\", \"type\": \"number\"}, {\"name\": \"email\", \"type\": \"string\"}]}"
  }
  ```

*If the ID does not exist:*

- **Status:** `404 Not Found`
- **Body:** *(empty)*

#### 4.5. Delete a Form Structure

- **Method:** `DELETE`
- **URL:** `http://localhost:8080/api/form-structures/{id}`
- **Description:** Deletes a form structure by its ID.

**Sample Response:**

- **Status:** `204 No Content`
- **Body:** *(empty)*

*If the ID does not exist:*

- **Status:** `404 Not Found`
- **Body:** *(empty)*

### 5. Postman Collection

To facilitate easy testing, you can import a Postman Collection with pre-configured requests for all API endpoints.

#### 5.1. Importing the Collection

1. **Open Postman.**
2. **Click on "Import"** in the top-left corner.
3. **Select "Raw Text"** and paste the following JSON content:

   ```json
   {
       "info": {
           "name": "Form Backend API",
           "_postman_id": "12345678-1234-1234-1234-123456789abc",
           "description": "Collection of API endpoints for Form Backend.",
           "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
       },
       "item": [
           {
               "name": "Get All Form Structures",
               "request": {
                   "method": "GET",
                   "header": [],
                   "url": {
                       "raw": "http://localhost:8080/api/form-structures",
                       "protocol": "http",
                       "host": ["localhost"],
                       "port": "8080",
                       "path": ["api", "form-structures"]
                   }
               },
               "response": []
           },
           {
               "name": "Get Form Structure by ID",
               "request": {
                   "method": "GET",
                   "header": [],
                   "url": {
                       "raw": "http://localhost:8080/api/form-structures/1",
                       "protocol": "http",
                       "host": ["localhost"],
                       "port": "8080",
                       "path": ["api", "form-structures", "1"]
                   }
               },
               "response": []
           },
           {
               "name": "Create New Form Structure",
               "request": {
                   "method": "POST",
                   "header": [
                       {
                           "key": "Content-Type",
                           "value": "application/json",
                           "type": "text"
                       }
                   ],
                   "body": {
                       "mode": "raw",
                       "raw": "{\n    \"structureJson\": \"{\\\"fields\\\": [{\\\"name\\\": \\\"firstName\\\", \\\"type\\\": \\\"string\\\"}, {\\\"name\\\": \\\"age\\\", \\\"type\\\": \\\"number\\\"}]}\"\n}"
                   },
                   "url": {
                       "raw": "http://localhost:8080/api/form-structures",
                       "protocol": "http",
                       "host": ["localhost"],
                       "port": "8080",
                       "path": ["api", "form-structures"]
                   }
               },
               "response": []
           },
           {
               "name": "Update Form Structure",
               "request": {
                   "method": "PUT",
                   "header": [
                       {
                           "key": "Content-Type",
                           "value": "application/json",
                           "type": "text"
                       }
                   ],
                   "body": {
                       "mode": "raw",
                       "raw": "{\n    \"structureJson\": \"{\\\"fields\\\": [{\\\"name\\\": \\\"firstName\\\", \\\"type\\\": \\\"string\\\"}, {\\\"name\\\": \\\"age\\\", \\\"type\\\": \\\"number\\\"}, {\\\"name\\\": \\\"email\\\", \\\"type\\\": \\\"string\\\"}]}\"\n}"
                   },
                   "url": {
                       "raw": "http://localhost:8080/api/form-structures/3",
                       "protocol": "http",
                       "host": ["localhost"],
                       "port": "8080",
                       "path": ["api", "form-structures", "3"]
                   }
               },
               "response": []
           },
           {
               "name": "Delete Form Structure",
               "request": {
                   "method": "DELETE",
                   "header": [],
                   "url": {
                       "raw": "http://localhost:8080/api/form-structures/3",
                       "protocol": "http",
                       "host": ["localhost"],
                       "port": "8080",
                       "path": ["api", "form-structures", "3"]
                   }
               },
               "response": []
           }
       ]
   }
   ```

4. **Click "Continue"** and then **"Import"** to add the collection to Postman.

*Note: Adjust the IDs in the URLs (`/1`, `/3`) as per your existing data.*

### 6. Testing with Postman

Use the imported Postman Collection to test each API endpoint:

1. **Open Postman and select the "Form Backend API" collection.**
2. **Execute Requests Sequentially:**
   - **Create a New Form Structure** to generate new data.
   - **Get All Form Structures** to verify creation.
   - **Get Form Structure by ID** to fetch specific data.
   - **Update Form Structure** to modify existing data.
   - **Delete Form Structure** to remove data.
3. **Verify Responses:** Ensure that each request returns the expected status codes and response bodies.

### 7. Troubleshooting

#### 7.1. Maven Not Recognized

**Error:**

```plaintext
'mvn' is not recognized as the name of a cmdlet...
```

**Solution:**

- Ensure Maven is installed.
- Add Maven's `bin` directory to your system's `PATH` environment variable.
- Restart your terminal or IDE after updating the `PATH`.

#### 7.2. Lombok Compilation Errors

**Error:**

```plaintext
cannot find symbol method getStructureJson()
```

**Solution:**

- Ensure Lombok is included in your `pom.xml` with the correct version and scope.
- Install the Lombok plugin in your IDE (IntelliJ IDEA, Eclipse).
- Enable annotation processing in your IDE settings.
- Rebuild the project.

#### 7.3. Docker Build Errors

**Error:**

```plaintext
/bin/sh: line 1: addgroup: command not found
```

**Solution:**

- Use Alpine-compatible flags for user and group creation.
- Update the Dockerfile to use `-S` and `-G` flags instead of `--system` and `--ingroup`.

**Updated Commands:**

```dockerfile
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
```

#### 7.4. Application Not Accessible

**Issue:**

- API endpoints return connection refused or 500 errors.

**Solution:**

- Ensure the application is running (`mvn spring-boot:run` or Docker container is up).
- Verify port mappings (`-p 8080:8080`).
- Check application logs for runtime errors.

### 8. Additional Recommendations

- **Environment Variables:** Use environment variables for sensitive configurations.
- **Profiles:** Utilize Spring profiles (`dev`, `prod`) for different environments.
- **Logging:** Configure logging levels and consider integrating with logging services.
- **Health Checks:** Implement health check endpoints for monitoring.
- **API Validation:** Validate input data to prevent malformed entries.
- **Unit Tests:** Implement unit and integration tests to ensure code quality.

### 9. Contributing

Contributions are welcome! Please follow these steps:

1. **Fork the Repository**
2. **Create a Feature Branch**

   ```bash
   git checkout -b feature/YourFeature
   ```

3. **Commit Your Changes**

   ```bash
   git commit -m "Add some feature"
   ```

4. **Push to the Branch**

   ```bash
   git push origin feature/YourFeature
   ```

5. **Open a Pull Request**

Provide a clear description of your changes and the problem they solve.

### 10. License

This project is licensed under the [MIT License](LICENSE).
