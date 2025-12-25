# Software Shop Web Application

A full-featured e-commerce web application for software products built with Spring Boot and Thymeleaf.

## 🚀 Features

- **User Management**
  - User registration and login
  - Role-based authentication (Admin/User)
  - Secure password handling with Spring Security

- **Product Management**
  - Browse software products
  - Product search and filtering
  - Detailed product information pages
  - Product categories

- **Shopping Features**
  - Shopping cart functionality
  - Checkout process
  - Order tracking
  - Order history

- **Admin Panel**
  - Product CRUD operations (Create, Read, Update, Delete)
  - User management
  - Order management

- **Internationalization**
  - Multi-language support (English and Vietnamese)

## 🛠️ Technology Stack

- **Backend:**
  - Spring Boot 3.4.0
  - Spring MVC
  - Spring Data JPA
  - Spring Security
  - Java 17

- **Frontend:**
  - Thymeleaf template engine
  - Bootstrap CSS
  - jQuery
  - Font Awesome icons

- **Database:**
  - MySQL 8.x

- **Build Tool:**
  - Maven

## 📋 Prerequisites

Before running this application, make sure you have:

- Java Development Kit (JDK) 17 or higher
- MySQL 8.x
- Maven 3.6+ (or use included Maven wrapper)

## ⚙️ Installation & Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/quang120901/SoftwareShopWeb.git
   cd SoftwareShopWeb
   ```

2. **Setup MySQL Database**
   - Create a MySQL database named `spring_mvc`
   - Import the database schema from `Database/spring_mvc.sql`
   ```sql
   mysql -u root -p < Database/spring_mvc.sql
   ```

3. **Configure Database Connection**
   - Open `Source/src/main/resources/application.properties`
   - Update database credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/spring_mvc
   spring.datasource.username=YOUR_USERNAME
   spring.datasource.password=YOUR_PASSWORD
   ```

4. **Build the Project**
   ```bash
   cd Source
   mvnw clean install
   ```
   Or if you have Maven installed:
   ```bash
   mvn clean install
   ```

5. **Run the Application**
   ```bash
   mvnw spring-boot:run
   ```
   Or:
   ```bash
   mvn spring-boot:run
   ```

6. **Access the Application**
   - Open your browser and navigate to: `http://localhost:8080`

## 📁 Project Structure

```
SoftwareShopWeb/
├── Database/              # Database schema and scripts
│   └── spring_mvc.sql
├── Source/                # Application source code
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/webcuoiky/softwareshop/
│   │   │   │       ├── config/          # Configuration classes
│   │   │   │       ├── constants/       # Application constants
│   │   │   │       ├── controller/      # MVC Controllers
│   │   │   │       ├── model/           # Entity models
│   │   │   │       ├── repository/      # Data access layer
│   │   │   │       ├── service/         # Business logic
│   │   │   │       └── SoftwareshopApplication.java
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       ├── i18n/                # Internationalization files
│   │   │       ├── static/              # CSS, JS, Images
│   │   │       └── templates/           # Thymeleaf templates
│   │   └── test/
│   └── pom.xml
└── README.md
```

## 🎯 Usage

### For Users:
1. Register a new account or login
2. Browse the software catalog
3. Add products to your cart
4. Proceed to checkout
5. View your order history

### For Administrators:
1. Login with admin credentials
2. Access the admin panel
3. Manage products (add, edit, delete)
4. View and manage users
5. Track orders

## 🌐 Default Ports

- Application runs on: `http://localhost:8080`
- MySQL default port: `3306`

## 🔒 Security

This application uses Spring Security for:
- User authentication and authorization
- Password encryption
- CSRF protection
- Role-based access control

## 🌍 Internationalization

The application supports multiple languages:
- English (`messages_en.properties`)
- Vietnamese (`messages_vi.properties`)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is for educational purposes. Please check local laws regarding software licensing.