# Spring Security 기반 Product 관리 애플리케이션
<img width="782" alt="스크린샷 2025-06-04 오전 11 00 30" src="https://github.com/user-attachments/assets/bdd20ac8-610a-4776-845c-b82e4ce27926" />
<img width="782" alt="스크린샷 2025-06-04 오전 11 00 41" src="https://github.com/user-attachments/assets/7f66c063-86df-45f0-87b2-99178d08b9e8" />
<img width="782" alt="스크린샷 2025-06-04 오전 11 01 01" src="https://github.com/user-attachments/assets/8658a31a-35b3-48eb-aad2-c5cad2aa8b01" />
<img width="785" alt="스크린샷 2025-06-04 오전 11 01 13" src="https://github.com/user-attachments/assets/b96c25c2-d965-4dcd-8fba-a580c385ea65" />
<img width="784" alt="스크린샷 2025-06-04 오전 11 01 24" src="https://github.com/user-attachments/assets/40690334-a68d-47ef-9941-91a13c1eafb7" />




## 📋 프로젝트 개요

기존 Product CRUD 관리 시스템을 확장하여 **Spring Security 기반 사용자 인증 및 권한(Role) 기반 보안 기능**을 통합한 웹 애플리케이션입니다.

## ✨ 주요 기능

### 🔐 사용자 인증 시스템
- **회원가입**: 이메일/비밀번호 기반 회원가입
- **로그인/로그아웃**: Spring Security 기반 인증
- **비밀번호 암호화**: BCryptPasswordEncoder 사용
- **실시간 비밀번호 강도 검사**
- **로그인 성공/실패 메시지 처리**

### 👥 권한 기반 접근 제어
- **ROLE_USER**: 상품 목록 조회만 가능
- **ROLE_ADMIN**: 상품 CRUD 모든 기능 + 관리자 페이지 접근
- **UI 권한 제어**: 권한에 따른 버튼 표시/숨김
- **메서드 레벨 보안**: @PreAuthorize 어노테이션 활용

### 📦 상품 관리 시스템
- **상품 목록**: 실시간 검색 및 정렬 기능
- **상품 등록/수정/삭제**: 관리자 전용 기능
- **입력 유효성 검사**: 가격 0 이상, 필수 필드 검증
- **반응형 디자인**: 모바일 친화적 UI

### 🛠️ 관리자 대시보드
- **사용자 통계**: 총 사용자 수, 관리자 수, 일반 사용자 수
- **사용자 목록**: 전체 사용자 조회
- **권한 관리**: 일반 사용자를 관리자로 승격

## 🛠️ 기술 스택

### Backend
- **Java 21**
- **Spring Boot 3.5.0**
- **Spring Security 6.x**
- **Spring Data JPA**
- **Spring Validation**

### Frontend
- **Thymeleaf**
- **HTML5/CSS3**
- **JavaScript (ES6+)**
- **Font Awesome Icons**
- **Glassmorphism Design**

### Database
- **MySQL 8.0**
- **JPA/Hibernate**

### 개발 도구
- **Maven**
- **Spring Boot DevTools**

## 📁 프로젝트 구조

```
src/
├── main/
│   ├── java/com/example/assignment2/
│   │   ├── config/
│   │   │   ├── DataInitializer.java
│   │   │   └── SecurityConfig.java
│   │   ├── controller/
│   │   │   ├── AdminController.java
│   │   │   ├── AuthController.java
│   │   │   └── ProductController.java
│   │   ├── entity/
│   │   │   ├── Product.java
│   │   │   ├── Role.java
│   │   │   └── User.java
│   │   ├── repository/
│   │   │   ├── ProductRepository.java
│   │   │   └── UserRepository.java
│   │   ├── security/
│   │   │   └── CustomUserDetailsService.java
│   │   ├── service/
│   │   │   ├── ProductService.java
│   │   │   └── UserService.java
│   │   └── Assignment2Application.java
│   └── resources/
│       ├── templates/
│       │   ├── admin/
│       │   │   └── dashboard.html
│       │   ├── auth/
│       │   │   ├── login.html
│       │   │   └── register.html
│       │   ├── products/
│       │   │   ├── form.html
│       │   │   └── list.html
│       │   └── layout/
│       │       └── base.html
│       └── application.properties
```

## 👤 기본 계정

애플리케이션 실행 시 다음 계정들이 자동으로 생성됩니다:

### 관리자 계정
- **이메일**: admin@example.com
- **비밀번호**: admin123
- **권한**: ROLE_USER, ROLE_ADMIN

### 일반 사용자 계정
- **이메일**: user@example.com
- **비밀번호**: user123
- **권한**: ROLE_USER

## 🗄️ 데이터베이스 스키마

### users 테이블
```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
```

### user_roles 테이블
```sql
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

### products 테이블
```sql
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock INT
);
```

