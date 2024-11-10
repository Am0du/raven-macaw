# Backend 

## User Service

This Spring Boot application manages user roles and authentication using JWT. It also includes database migration support through Flyway and seeds default roles if none exist.

## Features

- **Role Management**: Manages roles for users.
- **JWT Authentication**: Securely authenticates users using JSON Web Tokens.
- **Database Migration**: Uses Flyway to manage database schema migrations.
- **Database Seeding**: Automatically seeds default roles when the database is empty.

## Prerequisites

- **Java**: JDK 11 or later.
- **MySQL**: Ensure MySQL is installed and running.
- **Environment Variables**: Set up environment variables for sensitive configuration values.

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/am0du/raven-macaw.git
   cd backend
   ```

2. Install dependencies and build the project:

   ```bash
   mvn clean install
   ```

3. Configure environment variables in a  in your environment:

   ```plaintext
   connection_url=jdbc:mysql://localhost:3306/your_db
   db_mysql_user=your_username
   db_mysql_pwd=your_password
   secret_key=your_jwt_secret
   expiration_time=3600000  # 1 hour in milliseconds
   ```
  - **Datasource Configuration**: Configures the MySQL database connection. Replace `connection_url`, `db_mysql_user`, and `db_mysql_pwd` with your environment variables that store the database URL, username, and password.
  - **Flyway Migration Configuration**: Configures Flyway to handle database migrations on startup, enabling version control for your database. 
  - **JWT Configuration**: Sets the secret key and expiration time for JSON Web Token (JWT) authentication. Use environment variables to protect sensitive information.
    
4. Run the application:

   ```bash
   java -jar jarFile
   ```

## Database Seeding

When the application starts, it checks if the database has any roles defined. If no roles exist, the following roles are added:
- `ROLE_ADMIN`
- `ROLE_USER`

## Running Migrations

Flyway migrations are run automatically on startup. You can find the migration scripts in the `src/main/resources/db/migration` directory.

## API Documentation

Swagger UI is available at [http://localhost:8084/docs](http://localhost:8084/docs). Use this documentation to explore and test API endpoints directly in the browser.

