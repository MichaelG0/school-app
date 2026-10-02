# School Management System

A full-stack school management platform built with Angular and Spring Boot.

The application supports multiple user roles, academic management, assignments, student enrolment, role-specific dashboards and administrative workflows.

The project was originally built with an older Angular / Spring Boot stack and has since been revisited and modernized, including an Angular upgrade to version 19, migration toward standalone components, and a backend upgrade to Spring Boot 3.4 and Java 24.

## Features

- Role-based authentication and authorization
- Guest, Student, Teacher, Staff and Admin user roles
- JWT-based authentication with Spring Security
- Student and teacher dashboards
- Course and class management
- Assignment creation, submission and completion tracking
- Student enrolment and email confirmation flow
- Staff management area
- User profile management
- Route-level authorization guards
- Responsive Angular frontend
- PostgreSQL persistence

## Tech Stack

### Frontend

- Angular 19
- TypeScript
- RxJS
- Bootstrap
- SCSS
- Standalone Angular components
- Angular Router

### Backend

- Java 24
- Spring Boot 3.4
- Spring Security
- Spring Data JPA / Hibernate
- JWT authentication
- Maven
- JavaMail

### Database

- PostgreSQL

## Architecture

The repository is split into two applications:

```text
school-app/
├── schoolFrontend/   # Angular application
└── schoolBackend/    # Spring Boot REST API
```

The Angular frontend communicates with the Spring Boot backend through REST endpoints.

The backend follows a conventional layered structure with controllers, services, repositories, DTOs, persistence models, authentication/security configuration and centralized error handling.

## Roles

The application exposes different functionality depending on the authenticated user's role.

| Role | Example capabilities |
| --- | --- |
| Guest | Browse public content and submit an application |
| Student | View dashboard, classes and assignments |
| Teacher | Manage assigned classes and coursework |
| Staff | Access management functionality and user administration |
| Admin | Administrative access |

Angular route guards prevent users from navigating to areas outside their assigned permissions.

## Demo Accounts

The development dataset includes accounts for testing the different application roles.

| Role | Email | Password |
| --- | --- | --- |
| Guest | `guest@guest.com` | `guestguest` |
| Student | `student@student.com` | `studentstudent` |
| Staff | `staff@staff.com` | `staffstaff` |
| Teacher | `teacher@teacher.com` | `teacherteacher` |
| Admin | `admin@admin.com` | `adminadmin` |

These accounts are intended for local development and demonstration only.

## Running Locally

### Prerequisites

You will need:

- Node.js / npm
- Java 24
- PostgreSQL
- Git

### 1. Clone the repository

```bash
git clone https://github.com/MichaelG0/school-app.git
cd school-app
```

### 2. Create the PostgreSQL database

Create a local PostgreSQL database named:

```text
school_management
```

Update the datasource configuration in:

```text
schoolBackend/src/main/resources/application.properties
```

to match your local PostgreSQL credentials.

### 3. Start the backend

```bash
cd schoolBackend
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The API runs locally on:

```text
http://localhost:8080
```

### 4. Start the frontend

From another terminal:

```bash
cd schoolFrontend
npm install
npm start
```

The Angular development server will normally be available at:

```text
http://localhost:4200
```

## Email Testing

The student application / enrolment flow uses email confirmation.

For local development, the backend is configured to use an SMTP server on port `1025`, so MailDev can be used to inspect outgoing messages.

Install and run MailDev:

```bash
npm install -g maildev
maildev
```

Then open:

```text
http://localhost:1080
```

to view captured emails.

## Modernization

A major goal of the later development work on this project was keeping the original application functional while progressively updating its technology stack.

The frontend has been upgraded through multiple Angular versions to Angular 19 and now uses standalone components in several areas of the application.

The backend has similarly been updated to Spring Boot 3.4 and Java 24 while retaining the application's original functionality and data model.

This makes the repository both a full-stack application and an example of incremental legacy application modernization.

## Author

**Michael Guarino**

Full Stack Developer — Angular / Java / Spring Boot

[GitHub](https://github.com/MichaelG0)