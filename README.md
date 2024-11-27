# Simple-book-management
Simple book management Api

Book Management API
This is a simple REST API for managing books. The API allows you to create, update, view, and delete books. It includes standard features like basic validation and error handling.

Table of Contents
API Endpoints
Response Formats
Error Handling
Authentication
Setup and Installation
Running the Application
API Endpoints
The following endpoints are available in the Book Management API:

1. Create Book
Endpoint: POST /api/users/create
Description: Creates a new book record.
Request Body:
{
  "title": "Book Title",
  "author": "Book Author",
  "publicationYear": 2020
}
Response:
Success (201 Created):
{
  "id": 201,
  "message": "Book created successfully",
  "data": "bookId"
}
Failure (400 Bad Request):
{
  "id": 400,
  "message": "Error message",
  "data": false
}
2. Update Book
Endpoint: POST /api/users/update
Description: Updates an existing book record.
Request Body:
{
  "id": "bookId",
  "title": "Updated Title",
  "author": "Updated Author",
  "publicationYear": 2022
}
Response:
Success (200 OK):
{
  "id": 200,
  "message": "Book updated successfully",
  "data": "bookId"
}
Failure (400 Bad Request):
{
  "id": 400,
  "message": "Error message",
  "data": false
}
3. View Book
Endpoint: GET /api/users/{id}
Description: Fetches details of a book by its ID.
Response:
Success (200 OK):
{
  "id": 200,
  "message": "Success",
  "data": {
    "author": "Book Author",
    "title": "Book Title",
    "publicationYear": 2020
  }
}
Failure (404 Not Found):
{
  "id": 404,
  "message": "Book not found",
  "data": false
}
4. Delete Book
Endpoint: DELETE /api/users/{id}
Description: Deletes a book by its ID.
Response:
Success (204 No Content):
{
  "id": 204,
  "message": "Book deleted successfully",
  "data": true
}
Failure (404 Not Found):
{
  "id": 404,
  "message": "Book not found",
  "data": false
}
Failure (400 Bad Request):
{
  "id": 400,
  "message": "Failed to delete book: <error-message>",
  "data": false
}
Response Formats
All API responses are in JSON format and include the following structure:

{
  "id": <HTTP status code>,
  "message": "<response message>",
  "data": <response data>
}
id: The HTTP status code indicating the result of the request.
message: A human-readable message providing details about the response.
data: The data returned in the response (could be a success value or the requested resource).
Error Handling
In the case of errors, the API returns an appropriate HTTP status code along with an error message in the following format:

400 Bad Request: For invalid requests, such as missing required fields.
404 Not Found: If the requested book does not exist.
500 Internal Server Error: If an unexpected error occurs during processing.
Authentication
This API uses Basic Authentication for endpoints that require administrative privileges. Ensure that you include the Authorization header with your username:password when making requests to protected endpoints like /create or /update.

Setup and Installation
To set up and run the Book Management API locally, follow these steps:

Prerequisites
Java 11 or higher
Maven or Gradle
MySQL Database
