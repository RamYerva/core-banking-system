# Core Banking System – Backend (v1)

A simplified Core Banking backend system built using Spring Boot, demonstrating
real-world banking workflows such as customer onboarding, KYC verification,
admin approval, and secure account creation, with full transaction support including
deposit, withdrawal, transfer, and security pin authentication.

---

## 🧩 Features (v1)

- User registration & authentication (JWT-based)
- Customer onboarding linked to authenticated users
- KYC document submission
- Admin approval / rejection of KYC
- Account creation only after KYC verification
- One account per customer per account type
- Deterministic and masked account number generation​
- Deposit, withdrawal, and fund transfer operations​
- Transaction authentication via security PIN validation (3-attempt limit)
- Optimistic locking (@Version) for reads, pessimistic for writes
- Swagger OpenAPI documentation​
- Postman collection for API testing
- Transaction-safe operations
- Custom exception handling

---

## 🔐 Business Rules

- A user must register and log in before onboarding as a customer
- Customers cannot create accounts unless KYC is VERIFIED
- Duplicate accounts of the same type are not allowed
- Account numbers are unique and validated with a check digit
- Transactions require security PIN confirmation
- Pessimistic locks during balance updates, optimistic for balance reads
- 3 failed PIN attempts lock account for 15 minutes
- All critical operations transactional

---

## 🛠 Tech Stack

- Java 17+
- Spring Boot
- Spring Security (JWT)
- JPA / Hibernate
- MySQL
- JdbcTemplate
- ModelMapper
- SpringDoc OpenAPI (Swagger)
- Postman Collections


---

## 🧠 Design Decisions

- Database-level UNIQUE constraints to prevent duplicates
- Transaction boundaries using `@Transactional`
- Deterministic account number generation using internal sequence masking
- Optimistic locking (@Version) for high-read balance checks
- Pessimistic_WRITE locks for deposit/withdraw/transfer
- SecurityPinCheckService with attempt tracking
- Clear separation of concerns (Controller / Service / Repository)
- Swagger UI at /swagger-ui.html

---

API Documentation
Access interactive API docs:

 - Swagger UI: http://localhost:8080/swagger-ui.html     
 - OpenAPI JSON: http://localhost:8080/v3/api-docs


## 🚧 Future Enhancements (Planned)

- Balance locking
- Statements
- Audit logs
- Async notifications

---

## 📂 Project Status

**Version:** v2  
**State:** Stable  
**Purpose:** Enterprise banking backend learning project
