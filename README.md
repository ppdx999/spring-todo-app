# Todo Application

This is a simple Todo application built with Spring Boot, PostgreSQL, and Lombok. It provides basic CRUD functionality for managing Todo items.

![Todo app's top page image](/assets/demo-top.png)

## Prerequisites

- Java 17 or later
- PostgreSQL database
- Gradle
- A text editor or IDE of your choice

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
# src/main/resources/application.properties
spring.application.name=demo
spring.datasource.url=jdbc:postgresql://localhost:5432/todo_db
spring.datasource.username=todo_user
spring.datasource.password=password
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Step 3: Build and Run the Application

1. Navigate to the project directory and build the application.

```bash
./gradlew clean build
```

2. Run the application.

```bash
./gradlew bootRun
```

The application should now be running at `http://localhost:8080`.

## Contributing

Contributions are welcome! Please create a pull request or submit an issue if you find any bugs or have suggestions for improvements.

## License

This project is licensed under the MIT License.
