# Doctor Appointment System

A full-stack Doctor Appointment System for managing doctors, patients, appointments, schedules and prescriptions.

The application provides separate functionality for patients and administrators with JWT-based authentication and role-based access control.

## Technologies Used

### Backend
- Java
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- REST API

### Frontend
- Angular
- TypeScript
- HTML
- CSS

### Tools
- Git
- GitHub
- Postman
- Visual Studio Code

## Features

### Patient Features

- Patient registration
- Patient login
- JWT authentication
- View active doctors
- Search doctors by specialization
- View doctor schedules
- View available appointment slots
- Book appointments
- Prevent double booking
- View personal appointments
- Cancel appointments
- View prescription after completed appointment

### Admin Features

- Admin authentication
- Admin dashboard
- View appointment statistics
- Manage doctors
- Add doctors
- Edit doctors
- Delete doctors
- Manage doctor schedules
- View appointments by date
- Filter appointments by doctor
- Complete appointments
- Mark appointments as no-show
- Create prescriptions

## Authentication & Authorization

The application uses JWT-based authentication.

Two roles are supported:

- `PATIENT`
- `ADMIN`

Protected APIs require a valid JWT token.

Admin APIs are restricted to users with the `ADMIN` role.

## Appointment Slot System

Appointment slots are generated automatically based on the doctor's weekly schedule.

The system:

- Generates slots based on the configured schedule
- Removes past slots
- Removes already booked slots
- Prevents duplicate bookings using database constraints

The default slot duration is 30 minutes.

## Project Architecture

The backend follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```
## Project Structure
doctor-appointment-system
│
├── clinic
│   ├── src
│   │   └── main
│   │       └── java
│   │           └── clinic
│   │               ├── config
│   │               ├── controller
│   │               ├── dto
│   │               ├── entity
│   │               ├── exception
│   │               ├── repository
│   │               ├── security
│   │               └── service
│   │
│   └── pom.xml
│
├── frontend
│   ├── src
│   │   └── app
│   │       ├── core
│   │       ├── features
│   │       └── shared
│   │
│   └── package.json
│
└── README.md
## Database
The project uses MySQL.
Database name:
clinic_db
Create the database before running the backend:
CREATE DATABASE clinic_db;
## Environment Variables
The backend uses environment variables for sensitive configuration.
Set the following variables:
DB_PASSWORD=your_mysql_password
JWT_SECRET=your_jwt_secret
Do not commit real passwords or JWT secrets to GitHub.
## Running the Backend
Open a terminal inside the backend folder:
cd clinic
Run the Spring Boot application using Maven:
mvn spring-boot:run
The backend API will be available at:
http://localhost:8080
## Running the Frontend
Open another terminal inside the frontend folder:
cd frontend
Install dependencies:
npm install
Start the Angular development server:
ng serve
The frontend will be available at:
http://localhost:4200
## Main API Endpoints
## Authentication
POST /api/v1/auth/register
POST /api/v1/auth/login
## Doctors
GET /api/v1/doctors
GET /api/v1/doctors/{id}
GET /api/v1/doctors/{id}/slots?date=YYYY-MM-DD
## Appointments
POST /api/v1/appointments
GET /api/v1/appointments/my
PUT /api/v1/appointments/{id}/cancel
## Admin
GET /api/v1/admin/dashboard
GET /api/v1/admin/appointments
PUT /api/v1/admin/appointments/{id}/complete
PUT /api/v1/admin/appointments/{id}/no-show
## Prescriptions
POST /api/v1/prescriptions
GET /api/v1/prescriptions/{appointmentId}
## API Authentication
For protected APIs, send the JWT token using the Authorization header:
Authorization: Bearer <JWT_TOKEN>
In Postman:
Authorization → Bearer Token
Then provide the JWT token received from the login API.
## Testing
The backend APIs were tested using Postman, including:
Patient registration
Patient login
JWT authentication
Role-based authorization
Doctor APIs
Appointment booking
Appointment cancellation
Double-booking prevention
Admin dashboard
Appointment completion
No-show management
Prescription creation
Patient prescription access
The Angular frontend was also tested end-to-end for patient and admin workflows.
## Security
Passwords are not stored as plain text.
JWT is used for authentication.
Role-based authorization protects admin APIs.
Database credentials and JWT secrets are stored using environment variables.
Sensitive credentials are not committed to GitHub.
## GitHub Repository
GitHub:
https://github.com/vivek88k-eng/doctor-appointment-system⁠�
## Author
Vivek Kumar
GitHub:
https://github.com/vivek88k-eng⁠�