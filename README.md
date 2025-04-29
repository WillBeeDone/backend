![first](https://github.com/user-attachments/assets/586d74d4-24b0-4e6b-8e30-4196b0cb661f)
WillBeeDone Backend

WillBeeDone is a backend service for a web application that manages offers (ads).
It features user authentication, CRUD operations, filtering, favorites, email integration, and secure token management.

The backend is built using Spring Boot, Spring Security, JPA/Hibernate, MySQL, and supports external services like email and DigitalOcean.

📋 Features
- User registration and login with JWT authentication
- Secure token management (Access and Refresh tokens)
- Extract user data (e.g., email) from access token
- CRUD operations for managing offers (create, read, update, delete)
- Offer filtering, pagination, and "Favorites" functionality
- Sending emails via Gmail SMTP
- Integration with DigitalOcean Spaces (if needed)
- Secured API endpoints using Spring Security
- Multipart upload support for images (max 5 MB)

🛠️ Tech Stack
- Java 17+
- Spring Boot 3.4.3
- Spring Security
- Spring Data JPA
- MySQL 8+
- JWT (JSON Web Token)
- Lombok
- Maven
- Liquibase (optional, currently disabled)
- Gmail SMTP (for sending emails)
- DigitalOcean Spaces (optional, for file storage)

🚀 Getting Started
Prerequisites:
- JDK 17 or later
- Maven 3.8 or later
- MySQL 8.0+
- Gmail account for sending emails
- (Optional) DigitalOcean Spaces credentials

🔒 Security
- API endpoints are secured using Spring Security
- JWT-based authentication and authorization
- Passwords are hashed securely using BCrypt

📬 Contact
- Developer: Artsiom Davidovich
- Email: artiomdavidovich@gmail.com
- GitHub: https://github.com/ArtsiomDavidovich

✅ Notes
- The project currently uses MySQL as the main database.
- The security system is based on JWT access tokens with custom user data extraction.
- Offer operations support filtering, sorting, pagination, and favorites functionality.
