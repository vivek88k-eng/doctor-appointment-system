# Doctor Appointment System

A backend REST API for managing doctors, patients, appointments, schedules and prescriptions.

## Technologies Used

- Java
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- REST API
- Git & GitHub

## Features

- Patient registration and login
- JWT-based authentication
- Role-based access for PATIENT and ADMIN
- View active doctors
- Search doctors by specialization
- Doctor weekly schedules
- Automatic appointment slot generation
- Book appointments
- Cancel appointments
- View patient's appointments
- Admin appointment management
- Complete appointments
- Mark appointments as no-show
- Create prescriptions
- Admin dashboard
- Double-booking prevention

## Project Structure

```text
clinic
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── security
└── service
```


 ## Database
The project uses MySQL.
Database name:
clinic_db
## Running the Project
Set these environment variables before running the application:
DB_PASSWORD=your_mysql_password
JWT_SECRET=your_jwt_secret
Then run the Spring Boot application.
The API will be available at:
http://localhost:8080
## Main API Endpoints
Authentication
POST /api/v1/auth/register
POST /api/v1/auth/login
Doctors
GET /api/v1/doctors
GET /api/v1/doctors/{id}/slots?date=YYYY-MM-DD
Appointments
POST /api/v1/appointments
GET /api/v1/appointments/my
PUT /api/v1/appointments/{id}/cancel
Admin
GET /api/v1/admin/appointments
GET /api/v1/admin/dashboard
PUT /api/v1/admin/appointments/{id}/complete
PUT /api/v1/admin/appointments/{id}/no-show
## Prescriptions
POST /api/v1/prescriptions
## Authentication
Protected APIs require a JWT token.
In Postman, use:
Authorization → Bearer Token
and provide the JWT received from the login API.
## Author
Vivek Kumar
GitHub: https://github.com/vivek88k-eng⁠�
