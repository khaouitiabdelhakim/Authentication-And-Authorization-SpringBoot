# Spring Boot Authentication and Authorization: Best Tutorial Ever

## Description
This tutorial demonstrates how to implement authentication and authorization using Spring Boot. It covers the steps to set up the application, configure a MySQL database, and provides examples of API endpoints for user registration, login, and access control based on user roles.

```
If you find this repository useful or it has helped you,
please don't forget to leave a ⭐️, or even follow my GitHub account.
Your support motivates me to continue providing helpful resources.
Thank you for your appreciation! 🌟🚀💖😊👍

If you'd like to support further, consider buying me a coffee:
```
[![Buy Me A Coffee](https://img.shields.io/badge/Buy%20Me%20A%20Coffee--yellow.svg?style=for-the-badge&logo=buy-me-a-coffee)](https://www.buymeacoffee.com/kh.abdelhakim)

## Getting Started

### Prerequisites
Before starting the application, ensure you have the following installed:
- Java Development Kit (JDK)
- Apache Maven
- MySQL (using XAMPP or MySQL Workbench)

### Database Setup
Create a MySQL database named `authentication`. You can use XAMPP or MySQL Workbench to create the database.

### Application Properties
Configure the following properties in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/authentication?useSSL=false
spring.datasource.username=root
spring.datasource.password=

server.port=8080

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.sql.init.mode=always
spring.jpa.hibernate.ddl-auto=update

app.jwtCookieName=authentication-app
app.jwtSecret=qFVFzWHpdEWBMrtyp9oZtvAqm122OWyv2cCu87cN0Yhgfrd1Z1kukGmOJkX9deBU43Wyz3GTRegYNQrj7mQmj5
app.jwtExpirationMs=2592000
```

## Running the Application
Start the application using the following Maven command:
```bash
mvn spring-boot:run
```

## API Endpoints

### 1. Authentication

#### Sign Up
**Endpoint:**
```http
POST http://localhost:8080/api/auth/sign-up
```
**Request Body:**
```json
{
    "email": "client@gmail.com",
    "password": "1234",
    "role": "client"
}
```
**Response:**
```json
{
    "message": "User Registered Successfully!"
}
```

Example for admin:
```json
{
    "email": "admin@gmail.com",
    "password": "1234",
    "role": "admin"
}
```
**Response:**
```json
{
    "message": "User Registered Successfully!"
}
```

#### Sign In
**Endpoint:**
```http
POST http://localhost:8080/api/auth/sign-in
```
**Request Body:**
```json
{
    "email": "client@gmail.com",
    "password": "1234"
}
```
**Response:**
```json
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjbGllbnRAZ21haWwuY29tIiwiaWF0IjoxNzE3NDUwNjk0LCJleHAiOjE3MTc0NTMyODYsInJvbGVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0NMSUVOVCJ9XX0.vSyH4DpzZpqGvvX37AAa9Efl3dYi4rdHB-lnM2vWbLeZ5ybs7OJ3yRnZ0gWFJnvAc5WibRfiA03i6FtGMtR-pQ",
    "type": "Bearer",
    "id": 5,
    "email": "client@gmail.com",
    "roles": [
        "ROLE_CLIENT"
    ]
}
```

#### Sign Out
**Endpoint:**
```http
POST http://localhost:8080/api/auth/sign-out
```
**Headers:**
```
Authorization: Bearer <token>
```
**Response:**
```json
{
    "message": "You've Been Signed Out!"
}
```

### 2. Authorization

#### Access Profile
**Endpoint:**
```http
GET http://localhost:8080/api/app/profile
```
**Headers:**
```
Authorization: Bearer <token>
```
**Response:**
```json
{
    "id": 5,
    "email": "client@gmail.com",
    "roles": [
        "ROLE_CLIENT"
    ]
}
```

#### Get All Clients (Admin Only)
**Endpoint:**
```http
GET http://localhost:8080/api/admin/clients
```
**Headers:**
```
Authorization: Bearer <admin-token>
```
**Response (Admin):**
```json
[
    {
        "id": 1,
        "email": "client1@gmail.com",
        "password": "$2a$10$j1LuZsTjVD4hHMtegSWrOuHkN8tdk0ZUMRNEppq36W2mqk3uDDnXW",
        "role": "CLIENT"
    },
    {
        "id": 2,
        "email": "client2@gmail.com",
        "password": "$2a$10$hosM/Vbm03L3iq5cw4sSReOxzalXR1wjxjDS7Tj0WdFp5TbHTsDoi",
        "role": "CLIENT"
    },
    {
        "id": 3,
        "email": "client3@gmail.com",
        "password": "$2a$10$j5nKqbtaMADtmTYJaAoNXujci6Z2woZfu6mDBHtLekCmbbrwC37/O",
        "role": "CLIENT"
    },
    {
        "id": 5,
        "email": "client@gmail.com",
        "password": "$2a$10$.9iRFPzcxdG8vJtOmbpLJeo2fbr8gy5d6hkVcjrSzjAFRQJgPN5ee",
        "role": "CLIENT"
    }
]
```

If a non-admin user tries to access this endpoint:
**Response:**
```json
{
    "path": "/error",
    "error": "Unauthorized",
    "message": "Full authentication is required to access this resource",
    "status": 401
}
```

Follow these steps to set up and test your Spring Boot application with authentication and authorization.


## Credits

Original code by Abdelhakim Khaouiti ([khaouitiabdelhakim on GitHub](https://github.com/khaouitiabdelhakim))

## License
This project is licensed under the MIT License 

```
Copyright 2024 KHAOUITI ABDELHAKIM

Licensed under the MIT License
You may obtain a copy of the License at

http://opensource.org/licenses/MIT

Unless required by applicable law or agreed to in writing, software
distributed under the MIT License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the MIT License.
```

