# Todo Application

This is a simple Todo application built with Spring Boot, PostgreSQL, and React. It provides basic CRUD functionality for managing Todo items.

![Todo app's top page image](/assets/demo-top.png)

## Prerequisites

Backend
- Java 17 or later
- PostgreSQL database
- Gradle

Frontend
- Node.js
- pnpm

ReveseProxy(For development)
- Golang


## Setup

### Step 1: Clone the Repository

```bash
git clone https://github.com/yourusername/todo-application.git
cd todo-application
```

### Step 2: Configure the Database

1. Start PostgreSQL and create a new database and user.

```sql
CREATE DATABASE todo_db;
CREATE USER todo_user WITH ENCRYPTED PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE todo_db TO todo_user;
```

2. Update the `application.properties` file with your database credentials.

```properties
# backend/src/main/resources/application.properties
spring.application.name=demo
spring.datasource.url=jdbc:postgresql://localhost:5432/todo_db
spring.datasource.username=todo_user
spring.datasource.password=password
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Step 3: Generate RSA Public & Private Keys

```
cd backend/src/main/resources/certs

# create rsa key pair
openssl genrsa -out keypair.pem 2048

# extract public key
openssl rsa -in keypair.pem -pubout -out public.pem

# create private key in PKCS#8 format
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
```

### Step 4: Run Applications

1. backend

```
cd backend
./gradlew bootRun
```

2. frontend

```
cd frontend
pnpm i
pnpm run dev
```

3. revese proxy

```
cd proxy
go run main.go
```

## API Endpoints

Go to `http://localhost:8080/swagger-ui` and you will found this swagger ui

![Swagger's image](/assets/demo-swagger.png)

To get openapi's yaml spec file, run the command

```sh
curl localhost:8080/api-docs.yaml
```


## Contributing

Contributions are welcome! Please create a pull request or submit an issue if you find any bugs or have suggestions for improvements.

## License

This project is licensed under the MIT License.
