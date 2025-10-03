# Rent-Car Backend API

A robust and scalable REST API backend for a car rental web application, built with modern technologies and designed based on a Figma UI/UX template.

## 🚗 Overview

The Rent-Car Backend is a comprehensive API that powers a full-featured car rental platform. It provides essential functionalities including user management, vehicle inventory management, booking system, payment processing, notifications, and location tracking.

## ✨ Features

### Core Functionality
- **User Management**: Registration, authentication, profile management
- **Car Inventory**: Complete vehicle catalog with categories, features, and images
- **Booking System**: Full booking lifecycle management with status tracking
- **Payment Processing**: Secure payment handling and transaction management
- **Location Services**: GPS tracking and office/location management
- **Notifications**: Real-time notifications for users and admins
- **Review System**: User reviews and ratings for cars and services
- **Audit Logging**: Comprehensive logging of system activities
- **Penalty Management**: Fine and penalty tracking system

### Advanced Features
- **JWT Authentication**: Secure token-based authentication with refresh tokens
- **Role-Based Access Control**: User and admin roles with appropriate permissions
- **File Upload**: Image management via MinIO object storage
- **Caching**: Redis-based caching for improved performance
- **Email Notifications**: SMTP-based email service for user communications
- **API Documentation**: Complete OpenAPI/Swagger documentation
- **Database Migrations**: Liquibase-managed database versioning
- **Async Processing**: Background job processing for notifications and tasks
- **Scheduled Tasks**: Automated maintenance and reminder tasks

## 🛠 Tech Stack

### Backend Framework
- **Java 17**
- **Spring Boot 3.5.3**
- **Spring Security** (Authentication & Authorization)
- **Spring Data JPA** (ORM)
- **Spring Cache** (Caching)
- **Spring Mail** (Email)
- **Spring Validation** (Input validation)

### Database & Storage
- **PostgreSQL** (Primary database)
- **Redis** (Caching & session storage)
- **MinIO** (Object storage for images)
- **Liquibase** (Database migrations)

### Security & Authentication
- **JWT (JSON Web Tokens)** for stateless authentication
- **BCrypt** for password hashing
- **Spring Security** for authorization

### API & Documentation
- **RESTful API** design
- **OpenAPI 3.0** (Swagger) documentation
- **SpringDoc OpenAPI** for API docs generation

### Development Tools
- **Maven** (Build tool)
- **Lombok** (Code generation)
- **MapStruct** (Object mapping)
- **Thymeleaf** (Email templates)

## 📋 Prerequisites

Before running this application, make sure you have the following installed:

- **Java 17** or higher
- **Maven 3.6+**
- **PostgreSQL 12+**
- **Redis 6+**
- **MinIO** (optional, for file storage)

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/rent-car-backend.git
cd rent-car-backend
```

### 2. Database Setup
Create a PostgreSQL database and user:
```sql
CREATE DATABASE rentcar_db;
CREATE USER rentcar_user WITH ENCRYPTED PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE rentcar_db TO rentcar_user;
```

### 3. Environment Configuration
Create a `.env` file in the root directory or set environment variables:

```env
# Database Configuration
DB_HOSTNAME=localhost
DB_PORT=5432
DB_NAME=rentcar_db
DB_USERNAME=rentcar_user
DB_PASSWORD=your_password

# JWT Configuration
SECRET_KEY=your_jwt_secret_key_here

# Email Configuration
EMAIL_HOST=smtp.gmail.com
EMAIL_PORT=587
EMAIL_NAME=your_email@gmail.com
EMAIL_APP_PASSWORD=your_app_password

# File Upload Configuration
UPLOAD_PATH=/path/to/upload/directory

# MinIO Configuration (optional)
MINIO_HOSTNAME=localhost
MINIO_PORT=9000
MINIO_ACCESS_KEY=minio_access_key
MINIO_SECRET_KEY=minio_secret_key
```

### 4. Run the Application

For development:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080` with the `dev` profile active.

For production:
```bash
mvn clean package
java -jar target/Rent-Car-0.0.1-SNAPSHOT.jar --spring.profiles.active=product
```

## 📚 API Documentation

Once the application is running, you can access:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/v3/api-docs

### Key API Endpoints

#### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration
- `GET /api/auth/verify` - Refresh token verification

#### Cars
- `GET /api/cars` - Get all cars
- `GET /api/cars/{id}` - Get car by ID
- `POST /api/cars` - Create new car (Admin)
- `PUT /api/cars/{id}` - Update car (Admin)

#### Bookings
- `POST /api/bookings` - Create booking
- `GET /api/bookings/my` - Get user's bookings
- `POST /api/bookings/{id}/cancel` - Cancel booking

#### Users
- `GET /api/users/profile` - Get current user profile
- `PUT /api/users/profile` - Update user profile

## 🏗 Project Structure

```
src/main/java/uz/dev/rentcar/
├── controller/          # REST controllers
├── entity/              # JPA entities
│   └── template/        # Abstract entity templates
├── repository/          # Data access layer
├── service/             # Business logic
├── config/              # Configuration classes
├── payload/             # DTOs and request/response objects
├── mapper/              # MapStruct mappers
├── utils/               # Utility classes
├── enums/               # Enumeration types
├── exceptions/          # Custom exceptions
├── filter/              # Servlet filters
├── handler/             # Exception handlers
├── listener/            # Event listeners
└── projection/          # JPA projections
```

## 🗄 Database Schema

The application uses Liquibase for database versioning. Key tables include:

- `users` - User accounts and profiles
- `cars` - Vehicle inventory
- `bookings` - Rental bookings
- `payments` - Payment transactions
- `offices` - Rental locations
- `reviews` - User reviews
- `notifications` - System notifications
- `audit_logs` - Activity logging

## 🔐 Security

The application implements multiple security layers:

- **JWT Authentication**: Stateless authentication with access and refresh tokens
- **Password Encryption**: BCrypt hashing for secure password storage
- **Role-Based Access**: USER and ADMIN roles with appropriate permissions
- **Input Validation**: Comprehensive validation using Bean Validation
- **CORS Configuration**: Configured for frontend integration
- **Security Headers**: HTTP security headers for additional protection

## 📧 Notifications

The system supports multiple notification channels:

- **Email Notifications**: SMTP-based email service for bookings, confirmations, and reminders
- **In-App Notifications**: Database-stored notifications for real-time user alerts
- **Admin Alerts**: Special notifications for administrative actions

## 🧪 Testing

Run tests with:
```bash
mvn test
```

## 📦 Build & Deployment

### Build JAR
```bash
mvn clean package
```

### Docker Support (if configured)
```bash
docker build -t rent-car-backend .
docker run -p 8080:8080 rent-car-backend
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For support, please contact the development team or create an issue in this repository.

---

**Built with ❤️ using Spring Boot**