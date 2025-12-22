# Core Banking System – Backend (v1)

A simplified Core Banking backend system built using Spring Boot, demonstrating
real-world banking workflows such as customer onboarding, KYC verification,
admin approval, and secure account creation.

---

## 🧩 Features (v1)

- User registration & authentication (JWT-based)
- Customer onboarding linked to authenticated users
- KYC document submission
- Admin approval / rejection of KYC
- Account creation only after KYC verification
- One account per customer per account type
- Deterministic and masked account number generation
- Transaction-safe operations
- Custom exception handling

---

## 🔐 Business Rules

- A user must register and log in before onboarding as a customer
- Customers cannot create accounts unless KYC is VERIFIED
- Duplicate accounts of the same type are not allowed
- Account numbers are unique and validated with a check digit
- All critical operations are transactional

---

## 🛠 Tech Stack

- Java 17+
- Spring Boot
- Spring Security (JWT)
- JPA / Hibernate
- MySQL
- JdbcTemplate
- ModelMapper

---

## 🧠 Design Decisions

- Database-level UNIQUE constraints to prevent duplicates
- Transaction boundaries using `@Transactional`
- Deterministic account number generation using internal sequence masking
- Clear separation of concerns (Controller / Service / Repository)

---

## 🚧 Future Enhancements (Planned)

- Account transactions (debit / credit)
- Balance locking
- Statements
- Audit logs
- Async notifications

---

## 📂 Project Status

**Version:** v1  
**State:** Stable  
**Purpose:** Backend system design & learning project
