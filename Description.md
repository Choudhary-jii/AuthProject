Default Admin Credentials

By default, the application initializes with an Admin user for management purposes. The default credentials are as follows:

Username: admin
Password: adminpassword
Note: Only an Admin has the privilege to create another Admin user. This default Admin is essential for setting up additional users and managing the application.

Workflow of the Application
1. User Registration and Authentication

Admin Creation:
A new Admin can only be created by an existing Admin using the appropriate API endpoint.
User Registration:
Regular users can register themselves using the /auth/signup endpoint.
Authentication:
Both Admins and regular users must log in to obtain a JWT token via the /auth/login endpoint.
The token is required for accessing all other protected endpoints.
2. Car Management

The application allows users to manage their cars, including adding, updating, viewing, and deleting cars.

Car Addition:
Users can add cars by providing a title, description, tags, and up to 10 images.
Car Viewing:
Users can view all cars they own or search for specific cars using keywords.
Car Updates:
Users can update the details of their cars, including images and tags.
Car Deletion:
Users can delete their cars, and associated images will be removed from the filesystem.
3. Authorization

Token Validation:
Every API endpoint, except for login and signup, requires a valid JWT token.
The token should be included in the Authorization header of each request.
Role-Based Access:
Admin: Can perform all operations, including managing users and creating new admins.
User: Can manage only their own cars and data.
4. Search and Query Features

Search Cars:
Users can search for cars they own using the /search endpoint by providing a keyword in the query parameter.
Advanced Filtering:
The backend supports filtering by tags or other attributes, allowing flexible search capabilities.
5. File Management

Image Uploads:
Users can upload up to 10 images per car. The images are stored on the filesystem, and their paths are saved in the database.
Image Deletion:
When a car is deleted, all associated images are also deleted from the filesystem.
Postman API Documentation

For a detailed explanation of all API endpoints, including request formats, response structures, and sample inputs/outputs, refer to the Postman Documentation: Postman Documentation

Important Notes

Setup:
Ensure that the application is running, and the default Admin credentials are used for initial setup.
The Admin should create other necessary users and admins as needed.
Security:
Tokens are required for all secured endpoints.
Admin privileges should be handled carefully to avoid unauthorized access.
This file provides an overview of the workflow and key functionalities of the Car Management Application. For further technical details, refer to the Postman documentation or the README file.






Description of the Car Management Application 
 - 


This file provides an overview of the workflow and key functionalities of the Car Management Application. For further technical details, refer to the Postman documentation or the README file.
Default Admin User

By default, the application creates an Admin user with the following credentials:
Username: admin
Password: adminpassword
Only an Admin user can create new Admin accounts. This ensures that administrative privileges are tightly controlled.
Workflow of the Application
1. Authentication and Authorization

Admin and User Roles:
Admins have full access to manage cars, users, and other admin functionalities.
Regular users can manage their own cars only.
Token-Based Authentication:
Upon successful login, a JWT token is issued. This token must be included in the Authorization header for accessing protected endpoints.
2. Core Features

The application supports the following core features:

Car Management
Add a Car: Allows users to add new cars with a title, description, tags, and up to 10 images.
Search Cars: Users can search cars by a keyword (e.g., title, description, or tags).
Update a Car: Users can update details of their cars.
Delete a Car: Users can delete their own cars, along with associated images from the filesystem.
User Management
Admin Functions:
Create, update, and delete users.
Create new Admin accounts.
User Functions:
Manage personal cars only.
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
Postman Documentation
For detailed API documentation, including endpoints, request/response examples, and testing:

Postman API Documentation
This documentation contains examples for all API calls and is essential for understanding how to interact with the application programmatically.

Detailed API Workflow
1. Admin-Specific Features

Login using the default admin credentials.
Create additional admins using the create admin API (requires admin privileges).
2. Regular User Features

Register as a new user via the signup API.
Log in to receive a token and use it to manage personal cars.
3. Car Management

Use the Authorization token to:
Add, update, search, or delete cars.
Verify user ownership of the car before making changes.
4. Security Measures

Ensure all requests to protected endpoints include the JWT token.
Tokens are validated on every request, and roles are checked to grant access.
How to Use
Start the application.
Log in as the default admin to initialize the system.
Follow the API documentation for further operations, including managing users and cars.
