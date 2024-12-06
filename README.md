This project provides a lightweight authentication system using Spring Boot for the backend. It supports basic login, signup, and role-based access control. The frontend is minimal, allowing interaction via API endpoints.
A more detailed overview of the project is decription file, this readme file mainly focus on installation/requirements

Features:
-

User Registration (Signup)
User Login with JWT Token Generation
Role-based Access Control (Admin/User)
Minimal Frontend with Login Page
Secure API Endpoints
Installation Guide

Prerequisites: 
-

Java: Ensure you have Java 17 installed.
Maven: Required for dependency management.
MySQL: A running MySQL server.
Postman: For API testing (optional).

Setup Steps:
-

Clone the Repository:
  git clone <repository-url>
  cd <repository-folder>
  
Configure the Database:
  Update the application.properties file in src/main/resources with your MySQL credentials:
  spring.datasource.url=jdbc:mysql://localhost:3306/authdb
  spring.datasource.username=your_username
  spring.datasource.password=your_password
  spring.jpa.hibernate.ddl-auto=update
  
Build and Run:

Build the project:
  mvn clean install
  
Run the application:
  mvn spring-boot:run
  
Access the Application:
  The backend runs on http://localhost:8080.
  
  
API Endpoints
-

Authentication

  Login:
      POST /auth/login
      Request Body:
      {
        "username": "user123",
        "password": "password123"
      }
    Response: JWT Token.

Signup:
    GET /auth/signuppage (Redirects to a signup form).
    POST /auth/signup
    Request Body:
    {
      "username": "newuser",
      "password": "password123",
      "email": "email@example.com",
      "roles": ["USER"]
    }
    Response: Success or Error Message.
    
Protected Routes

  Admin Access:
    GET /protected/admin-homepage
    Requires JWT token with ADMIN role.
    
  User Access:
    GET /protected/user-homepage
    Requires JWT token with USER role.
    
Accessing the Minimal Frontend

  Login Page:
    http://localhost:8080/auth/loginpage

    
Testing the API
-

Use Postman or any API testing tool to test the endpoints.
Include the JWT token in the Authorization header for protected routes:
Authorization: Bearer <your_token>


Contributing
-

Feel free to submit issues or contribute to the project. Ensure code is well-documented and tested.

For detailed documentation and Postman collections, see the description file.
