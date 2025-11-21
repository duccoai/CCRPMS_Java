# CCRPMS – Candidate Career & Recruitment Promotion Management System

## 🎯 Tổng Quan Dự Án
CCRPMS là hệ thống quản lý thăng tiến và tuyển dụng cho ứng viên và nhà tuyển dụng trong một tổ chức.
Hệ thống hỗ trợ:
•	Candidate: Gửi yêu cầu nâng bậc, làm bài thi đánh giá kỹ năng.
•	Recruiter: Quản lý hồ sơ ứng tuyển, duyệt hồ sơ thăng tiến.
•	Admin: Quản lý người dùng, job, exam và tổng quan hệ thống.
Mục tiêu của dự án là tối ưu hóa quy trình quản lý nhân sự và nâng bậc một cách minh bạch, tự động.

## 📁 Cấu Trúc Dự Án
backend/
├── src/main/java/com/academy/ccrpms
│   ├── auth/             # Xác thực, đăng nhập, đăng ký
│   ├── user/             # Quản lý user và role
│   ├── job/              # Quản lý job, job application
│   ├── exam/             # Quản lý exam, câu hỏi
│   ├── promotion/        # Quản lý promotion application
│   ├── notification/     # Quản lý thông báo
│   └── common/           # BaseEntity, exception handler, utils
├── resources/
│   └── application.properties
frontend-web/
├── src/
│   ├── components/       # React components
│   ├── pages/            # Page components
│   ├── api/              # Axios API calls
│   └── App.jsx


## 🚀 Tính Năng Đã Triển Khai
### Candidate
•	Gửi hồ sơ ứng tuyển (Application).
•	Gửi hồ sơ thăng bậc (Promotion Application).
•	Làm bài thi (Exam) sau khi hồ sơ được duyệt.
•	Xem lịch sử hồ sơ và kết quả thi.
### Recruiter
•	Xem toàn bộ hồ sơ ứng viên.
•	Duyệt hồ sơ ứng tuyển.
•	Lên lịch phỏng vấn ứng viên/ chấm điểm.
•	Duyệt hồ sơ thăng bậc.
•	Quản lý job và xem hồ sơ ứng tuyển.
### Admin
•	Quản lý user và role. 
•	Xem thống kê hệ thống tổng quan.


## 🛠 Công Nghệ Sử Dụng
•	Backend: Java 17, Spring Boot 3, Spring Data JPA, PostgreSQL.
•	Frontend: ReactJS, Axios, Tailwind CSS.
•	Khác: Maven, Lombok, JWT (xác thực), Docker (tuỳ chọn).


## 📝 Hướng Dẫn Cài Đặt

### Bước 1: Tạo Database
1.	Mở pgAdmin hoặc psql.
2.	Tạo database mới, ví dụ: ccrpms
CREATE DATABASE ccrpms;
3.	Import file database.sql có trong file zip:
•	Trong pgAdmin: Chuột phải database → Restore hoặc Query Tool → chạy file SQL.
•	Hoặc dùng psql command line:
psql -U postgres -d ccrpms -f path/to/database.sql
File database.sql đã bao gồm schema và dữ liệu mẫu (users, jobs, promotions, exams…).

### Bước 2: Cấu hình Backend
1.	Mở file src/main/resources/application.properties hoặc application.yml.
2.	Cập nhật thông tin database:
spring.datasource.url=jdbc:postgresql://localhost:5432/ccrpms
spring.datasource.username=postgres
spring.datasource.password=yourpassword
# Không cần tự động tạo bảng vì đã import database.sql
spring.jpa.hibernate.ddl-auto=none

### Bước 3: Chạy Backend
1.	Mở terminal ở thư mục backend:
mvn spring-boot:run
2.	API sẽ chạy mặc định ở: http://localhost:8080

### Bước 4: Chạy Frontend
1.	Vào thư mục frontend-web:
cd frontend-web
npm install
npm run dev
2.	Truy cập frontend: http://localhost:5173

## 🎓 Kiến Thức Học Được
•	Thiết kế hệ thống quản lý phức tạp với Spring Boot & ReactJS.
•	Quản lý quan hệ nhiều bảng với PostgreSQL & JPA.
•	Xử lý luồng duyệt hồ sơ & bài thi, quyền hạn người dùng.
•	Triển khai API REST, kết hợp frontend ReactJS.
•	Tối ưu hóa luồng nghiệp vụ và bảo mật cơ bản.
