Description of the Car Management Application 
- 


This file provides an overview of the workflow and key functionalities of the Car Management Application. For further technical details, refer to the Postman documentation or the README file.


 By default, the application creates an Admin user with the following credentials:

  * Username: admin
  
  * Password: adminpassword
  
 NOTE - Only an Admin user can create new Admin accounts. This ensures that administrative privileges are tightly controlled.

 --- 
 
 Workflow of the Application
 -

  

  
1. Authentication and Authorization

 Admin and User Roles:
 
   Admins have full access to manage cars, users, and other admin functionalities.
   Regular users can manage their own cars only.
   
 Token-Based Authentication:
 
   Upon successful login, a JWT token is issued. This token must be included in the Authorization header for accessing protected endpoints.
   
2. Core Features

The application supports the following core features:

 Car Management(For USER Role & ADMIN Role)
 
    Add a Car: Allows users to add new cars with a title, description, tags, and up to 10 images.
    
    Search Cars: Users can search cars by a keyword (e.g., title, description, or tags).
    
    Update a Car: Users can update details of their cars.
    
    Delete a Car: Users can delete their own cars, along with associated images from the filesystem.
    
User Management (For ADMIN Role)
 
 Admin Functions: Create, update, and delete users. Create new Admin accounts.
   
 User Functions: Manage personal cars only.
   
3. Protected Endpoints

  All critical endpoints are protected and require a valid JWT token.
  Access to resources is verified:
  Users can only access their own cars.
  Admins can access all resources.
  
4. Filesystem Integration

  Images associated with cars are saved to the filesystem.
  When a car is deleted, its associated images are also removed from the filesystem.
  
5. Error Handling

  The application handles errors like:
  
      Invalid credentials (401 Unauthorized).
      Resource not found (404 Not Found).
      Access denied (403 Forbidden).
      
 --- 
      
Postman Documentation
-

* For detailed API documentation, including endpoints, request/response examples, and testing:

* Postman API Documentation - "https://documenter.getpostman.com/view/39033838/2sAYBbdUUG"

This documentation contains examples for all API calls and is essential for understanding how to interact with the application programmatically.



* Detailed API Workflow

1. Admin-Specific Features

  - Login using the default admin credentials.
  - Create additional admins using the create admin API (requires admin privileges).

2. Regular User Features

  - Register as a new user via the signup API.
  - Log in to receive a token and use it to manage personal cars.

3. Car Management
  
  - Use the Authorization token to:
  - Add, update, search, or delete cars.
  - Verify user ownership of the car before making changes.
  
4. Security Measures

  - Ensure all requests to protected endpoints include the JWT token.
  - Tokens are validated on every request, and roles are checked to grant access.
  
How to Use
-

  * Start the application.
  * Log in as the default admin to initialize the system.
  * Follow the API documentation for further operations, including managing users and cars.
