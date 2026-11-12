# Library Management System

A full-stack library management application using Java 17, Spring Boot, Spring Data JPA, MySQL, Maven, and a responsive HTML/CSS/JavaScript frontend.

## Features
- Add, search, update through REST API, and delete books
- Register and list library members
- Issue books for 14 days and return them
- Track available copies and active loans
- Prevent issuing unavailable books and deleting books with outstanding loans

## Run locally
1. Install JDK 17+, Maven 3.6+, and MySQL 8+.
2. Create database: `CREATE DATABASE library_db;`
3. Set environment variables if needed:
   - `DB_URL=jdbc:mysql://localhost:3306/library_db`
   - `DB_USERNAME=root`
   - `DB_PASSWORD=your_password`
4. Run: `mvn spring-boot:run`
5. Open http://localhost:8080

## REST API
| Method | Endpoint | Description |
|---|---|---|
| GET | /api/books | List books |
| GET | /api/books?q=java | Search books |
| POST | /api/books | Add book |
| PUT | /api/books/{id} | Update book |
| DELETE | /api/books/{id} | Delete book |
| GET | /api/members | List members |
| POST | /api/members | Register member |
| GET | /api/loans | List loans |
| GET | /api/loans/active | Active loans |
| POST | /api/loans/issue | Issue a book |
| POST | /api/loans/{id}/return | Return a book |

### Sample book JSON
```json
{"title":"Effective Java","author":"Joshua Bloch","isbn":"9780134685991","category":"Programming","totalCopies":3}
```

## Project structure
```
src/main/java/com/example/library/
  LibraryManagementApplication.java
  controller/ (BookController, MemberController, LoanController)
  entity/ (Book, Member, Loan, LoanStatus)
  repository/ (BookRepository, MemberRepository, LoanRepository)
  service/LibraryService.java
src/main/resources/
  application.properties
  static/ (index.html, styles.css, app.js)
```

## Production checklist
This is an educational starter. Before real-world use, add authentication/role-based access, database migrations, automated tests, backups, pagination, and production secrets management. Never commit database passwords.
