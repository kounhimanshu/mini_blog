# Mini Blogging Website REST API Documentation

This is a REST API for a mini-blogging platform, developed using **Spring Boot**. It allows users to register, log in, create posts, and comment on posts. Authentication is handled via **JWT (JSON Web Token)**.

## Authentication

This API uses **JWT-based authentication**. After successful login or registration, you will receive a **JWT token** in the response. This token must be included in the **Authorization Header** of all requests requiring authentication.

### Authorization Header

For every request requiring authentication (like creating a post, commenting, or fetching user-specific data), include the following header:






You can obtain the JWT token after logging in or registering a user.

---

## Endpoints

### 1. AuthenticationController

---

#### Register a User

**`POST /register`**

- **Description:** Registers a new user with the given details.
- **Request Body:**
    ```json
    {
      "username": "john_doe",
      "password": "password123",
      "email": "john@example.com",
      "name": "John Doe",
      "bio": "springboot developer"
    }
    ```
- **Response:**
    ```json
    {
      "username": "john_doe",
      "id": 1,
      "token": "your-jwt-token"
    }
    ```

---

#### Login a User

**`POST /login`**

- **Description:** Authenticates a user and provides a JWT token for further requests.
- **Request Body:**
    ```json
    {
      "username": "john_doe",
      "password": "password123"
    }
    ```
- **Response:**
    ```json
    {
      "token": "your-jwt-token"
    }
    ```

---

### 2. UserController

---

#### Get User by Username

**`GET /users/{username}`**

- **Description:** Retrieves user details for the specified username.
- **Authentication:** Yes (JWT)
- **Response:**
    ```json
    {
      "id": 1,
      "username": "john_doe",
      "email": "john@example.com",
      "name": "John Doe",
      "bio": "springboot developer"
    }
    ```

---

#### Update User Details

**`PUT /users/{username}`**

- **Description:** Updates user details.
- **Authentication:** Yes (JWT)
- **Request Body:**
    ```json
    {
      "email": "updatedemail@example.com",
      "name": "John Updated",
      "bio": "Java Developer"
    }
    ```
- **Response:**
    ```json
    {
      "id": 1,
      "username": "john_doe",
      "email": "updatedemail@example.com",
      "name": "John Updated",
      "bio": "Java Developer"
    }
    ```

---

#### Delete User

**`DELETE /users/{username}`**

- **Description:** Deletes a user by username.
- **Authentication:** Yes (JWT)
- **Response:**
    ```json
    {
      "message": "User deleted successfully."
    }
    ```

---

### 3. PostController

---

#### Get All Posts

**`GET /posts`**

- **Description:** Retrieves all posts in the system.
- **Response:**
    ```json
    [
      {
        "id": 1,
        "username": "john_doe",
        "title": "First Blog Post",
        "content": "This is the content of the blog post",
        "tags": ["Java", "Spring Boot"],
        "createdAt": "timestamp",
        "updatedAt": "timestamp",
        "comments": []
      }
    ]
    ```

---

#### Get a Post by ID

**`GET /posts/{id}`**

- **Description:** Retrieves a post by its ID.
- **Response:**
    ```json
    {
      "id": 1,
      "username": "john_doe",
      "title": "First Blog Post",
      "content": "This is the content of the blog post",
      "tags": ["Java", "Spring Boot"],
      "createdAt": "timestamp",
      "updatedAt": "timestamp",
      "comments": []
    }
    ```

---

#### Create a New Post

**`POST /posts`**

- **Description:** Creates a new post.
- **Authentication:** Yes (JWT)
- **Request Body:**
    ```json
    {
      "title": "First Blog Post",
      "content": "This is the content of the blog post",
      "tags": ["Java", "Spring Boot"]
    }
    ```
- **Response:**
    ```json
    {
      "id": 1,
      "username": "john_doe",
      "title": "First Blog Post",
      "content": "This is the content of the blog post",
      "tags": ["Java", "Spring Boot"],
      "createdAt": "timestamp",
      "updatedAt": "timestamp",
      "comments": []
    }
    ```

---

#### Update a Post

**`PUT /posts/{id}`**

- **Description:** Updates a post by its ID.
- **Authentication:** Yes (JWT)
- **Request Body:**
    ```json
    {
      "content": "Updated content of the post"
    }
    ```
- **Response:**
    ```json
    {
      "id": 1,
      "username": "john_doe",
      "title": "First Blog Post",
      "content": "Updated content of the post",
      "tags": ["Java", "Spring Boot"],
      "createdAt": "timestamp",
      "updatedAt": "timestamp",
      "comments": []
    }
    ```

---

#### Delete a Post

**`DELETE /posts/{id}`**

- **Description:** Deletes a post by its ID.
- **Authentication:** Yes (JWT)
- **Response:**
    ```json
    {
      "message": "Post deleted successfully."
    }
    ```

---

### 4. CommentController

---

#### Get All Comments for a Post

**`GET /posts/{postId}/comments`**

- **Description:** Retrieves all comments for a specific post.
- **Response:**
    ```json
    [
      {
        "id": 1,
        "content": "This is a comment",
        "username": "john_doe",
        "createdAt": "timestamp",
        "updatedAt": "timestamp"
      }
    ]
    ```

---

#### Create a Comment

**`POST /posts/{postId}/comments`**

- **Description:** Adds a comment to a specific post.
- **Authentication:** Yes (JWT)
- **Request Body:**
    ```json
    {
      "content": "This is a comment on the post."
    }
    ```
- **Response:**
    ```json
    {
      "commentId": 1,
      "postId": 1,
      "username": "john_doe",
      "content": "This is a comment on the post"
    }
    ```

---

#### Update a Comment

**`PUT /posts/{postId}/comments/{commentId}`**

- **Description:** Updates an existing comment for a specific post.
- **Authentication:** Yes (JWT)
- **Request Body:**
    ```json
    {
      "content": "Updated comment content"
    }
    ```
- **Response:**
    ```json
    {
      "commentId": 1,
      "postId": 1,
      "username": "john_doe",
      "content": "Updated comment content",
      "updatedAt": "timestamp"
    }
    ```

---

#### Get a Comment by ID

**`GET /posts/{postId}/comments/{commentId}`**

- **Description:** Retrieves a specific comment by its ID for a given post.
- **Response:**
    ```json
    {
      "id": 1,
      "content": "This is a comment",
      "username": "john_doe",
      "createdAt": "timestamp",
      "updatedAt": "timestamp"
    }
    ```

---

#### Delete a Comment

**`DELETE /posts/{postId}/comments/{commentId}`**

- **Description:** Deletes a specific comment by its ID for a given post.
- **Authentication:** Yes (JWT)
- **Response:**
    ```json
    {
      "message": "Comment deleted successfully."
    }
    ```

---

### Note on Authentication

For any **POST, PUT, or DELETE** request, you must include the JWT token in the headers as follows:



