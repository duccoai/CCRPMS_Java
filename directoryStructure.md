CCRPMS/
├── .github/                          # GitHub-specific configs
│   ├── workflows/
│   │   ├── ci-backend.yml            # Backend CI (Maven, Test, Build)
│   │   ├── ci-frontend.yml           # Frontend CI (npm, lint, test)
│   │   └── deploy.yml                # Optional: Deploy to Render/Vercel
│   ├── ISSUE_TEMPLATE/
│   │   ├── bug_report.md
│   │   └── feature_request.md
│   └── PULL_REQUEST_TEMPLATE.md
│
├── backend/                          # Spring Boot (Java)
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── com/
    │   │   │       └── academy/
    │   │   │           └── ccrpms/
    │   │   │               ├── CcrpmsApplication.java
    │   │   │               │
    │   │   │               ├── config/                     # Global Configurations
    │   │   │               │   ├── SecurityConfig.java
    │   │   │               │   ├── JwtConfig.java
    │   │   │               │   ├── SwaggerConfig.java
    │   │   │               │   ├── WebConfig.java
    │   │   │               │   ├── MailConfig.java
    │   │   │               │   └── DatabaseConfig.java
    │   │   │               │
    │   │   │               ├── common/                     # Shared Utilities
    │   │   │               │   ├── constants/
    │   │   │               │   │   ├── Role.java
    │   │   │               │   │   ├── Status.java
    │   │   │               │   │   └── AppConstants.java
    │   │   │               │   ├── exception/
    │   │   │               │   │   ├── GlobalExceptionHandler.java
    │   │   │               │   │   ├── ResourceNotFoundException.java
    │   │   │               │   │   └── BusinessValidationException.java
    │   │   │               │   ├── dto/
    │   │   │               │   │   ├── ApiResponse.java
    │   │   │               │   │   └── PageResponse.java
    │   │   │               │   └── utils/
    │   │   │               │       ├── DateUtils.java
    │   │   │               │       ├── FileUtils.java
    │   │   │               │       └── PasswordGenerator.java
    │   │   │               │
    │   │   │               ├── auth/                       # Authentication & RBAC
    │   │   │               │   ├── controller/
    │   │   │               │   │   └── AuthController.java
    │   │   │               │   ├── service/
    │   │   │               │   │   ├── AuthService.java
    │   │   │               │   │   └── JwtService.java
    │   │   │               │   ├── dto/
    │   │   │               │   │   ├── LoginRequest.java
    │   │   │               │   │   ├── LoginResponse.java
    │   │   │               │   │   └── RegisterRequest.java
    │   │   │               │   └── filter/
    │   │   │               │       └── JwtAuthenticationFilter.java
    │   │   │               │
    │   │   │               ├── user/                       # User Management (HR, Recruiter, Candidate)
    │   │   │               │   ├── entity/
    │   │   │               │   │   ├── User.java
    │   │   │               │   │   └── Role.java
    │   │   │               │   ├── repository/
    │   │   │               │   │   └── UserRepository.java
    │   │   │               │   ├── service/
    │   │   │               │   │   └── UserService.java
    │   │   │               │   └── controller/
    │   │   │               │       └── UserController.java
    │   │   │               │
    │   │   │               ├── campaign/                   # Recruitment & Promotion Campaigns
    │   │   │               │   ├── entity/
    │   │   │               │   │   └── Campaign.java
    │   │   │               │   ├── repository/
    │   │   │               │   │   └── CampaignRepository.java
    │   │   │               │   ├── service/
    │   │   │               │   │   └── CampaignService.java
    │   │   │               │   ├── dto/
    │   │   │               │   │   ├── CampaignRequest.java
    │   │   │               │   │   └── CampaignResponse.java
    │   │   │               │   └── controller/
    │   │   │               │       └── CampaignController.java
    │   │   │               │
    │   │   │               ├── application/                # Candidate Applications
    │   │   │               │   ├── entity/
    │   │   │               │   │   ├── Application.java
    │   │   │               │   │   └── Document.java
    │   │   │               │   ├── repository/
    │   │   │               │   │   ├── ApplicationRepository.java
    │   │   │               │   │   └── DocumentRepository.java
    │   │   │               │   ├── service/
    │   │   │               │   │   ├── ApplicationService.java
    │   │   │               │   │   └── DocumentValidationService.java
    │   │   │               │   ├── dto/
    │   │   │               │   │   ├── ApplicationSubmitRequest.java
    │   │   │               │   │   └── ApplicationStatusResponse.java
    │   │   │               │   └── controller/
    │   │   │               │       └── ApplicationController.java
    │   │   │               │
    │   │   │               ├── interview/                  # Interview Scheduling & Scoring
    │   │   │               │   ├── entity/
    │   │   │               │   │   ├── Interview.java
    │   │   │               │   │   └── InterviewScore.java
    │   │   │               │   ├── repository/
    │   │   │               │   │   ├── InterviewRepository.java
    │   │   │               │   │   └── InterviewScoreRepository.java
    │   │   │               │   ├── service/
    │   │   │               │   │   └── InterviewService.java
    │   │   │               │   ├── dto/
    │   │   │               │   │   └── InterviewScoreRequest.java
    │   │   │               │   └── controller/
    │   │   │               │       └── InterviewController.java
    │   │   │               │
    │   │   │               ├── exam/                       # Promotion Exams (English + Professional)
    │   │   │               │   ├── entity/
    │   │   │               │   │   ├── Exam.java
    │   │   │               │   │   ├── Question.java
    │   │   │               │   │   └── CandidateAnswer.java
    │   │   │               │   ├── repository/
    │   │   │               │   │   ├── ExamRepository.java
    │   │   │               │   │   ├── QuestionRepository.java
    │   │   │               │   │   └── CandidateAnswerRepository.java
    │   │   │               │   ├── service/
    │   │   │               │   │   ├── ExamService.java
    │   │   │               │   │   └── GradingService.java
    │   │   │               │   ├── dto/
    │   │   │               │   │   └── ExamResultResponse.java
    │   │   │               │   └── controller/
    │   │   │               │       └── ExamController.java
    │   │   │               │
    │   │   │               ├── notification/               # Email/SMS Notifications
    │   │   │               │   ├── service/
    │   │   │               │   │   ├── NotificationService.java
    │   │   │               │   │   └── EmailService.java
    │   │   │               │   └── template/
    │   │   │               │       ├── email/
    │   │   │               │       │   ├── application-submitted.html
    │   │   │               │       │   ├── interview-scheduled.html
    │   │   │               │       │   └── promotion-result.html
    │   │   │               │
    │   │   │               ├── report/                     # Dashboards & Analytics
    │   │   │               │   ├── service/
    │   │   │               │   │   └── ReportService.java
    │   │   │               │   ├── dto/
    │   │   │               │   │   └── RecruitmentStats.java
    │   │   │               │   └── controller/
    │   │   │               │       └── ReportController.java
    │   │   │               │
    │   │   │               └── audit/                      # Audit Logs
    │   │   │                   ├── entity/
    │   │   │                   │   └── AuditLog.java
    │   │   │                   ├── repository/
    │   │   │                   │   └── AuditLogRepository.java
    │   │   │                   └── service/
    │   │   │                       └── AuditService.java
    │   │   │
    │   │   └── resources/
    │   │       ├── static/
    │   │       │   └── uploads/                     # Document storage (configurable)
    │   │       ├── templates/                       # Email templates (Thymeleaf)
    │   │       ├── application.yml
    │   │       ├── application-dev.yml
    │   │       ├── application-prod.yml
    │   │       ├── db/
    │   │       │   └── migrations/                  # Flyway or Liquibase
    │   │       │       ├── V1__init.sql
    │   │       │       └── V2__add_audit_tables.sql
    │   │       └── logback-spring.xml
    │   │
    │   └── test/
    │       └── java/
    │           └── com/academy/ccrpms/
    │               ├── CcrpmsApplicationTests.java
    │               ├── controller/
    │               │   ├── AuthControllerTest.java
    │               │   └── CampaignControllerTest.java
    │               ├── service/
    │               │   └── ApplicationServiceTest.java
    │               └── repository/
    │                   └── UserRepositoryTest.java
    │
    ├── Dockerfile
    ├── docker-compose.yml
    ├── pom.xml                                              # Maven
    ├── build.gradle                                         # Optional: Gradle alternative
    ├── .gitignore
    ├── README.md
    └── mvnw / gradlew                                       # Maven/Gradle wrapper
│
├── frontend-web/                     # React.js (Admin, Recruiter, Candidate Portals)
│   ├── public/
│   │   ├── index.html
│   │   └── favicon.ico
│   ├── src/
│   │   ├── assets/
│   │   ├── components/
│   │   │   ├── layout/
│   │   │   ├── forms/
│   │   │   └── dashboard/
│   │   ├── pages/
│   │   │   ├── auth/
│   │   │   ├── candidate/
│   │   │   ├── recruiter/
│   │   │   └── admin/
│   │   ├── services/
│   │   │   └── api.js
│   │   ├── context/
│   │   │   └── AuthContext.js
│   │   ├── App.jsx
│   │   └── index.js
│   ├── Dockerfile
│   ├── package.json
│   ├── vite.config.js
│   ├── .env.example
│   └── .dockerignore
│
├── mobile/                           # React Native (Candidate App)
│   ├── android/
│   ├── ios/
│   ├── src/
│   │   ├── screens/
│   │   ├── components/
│   │   ├── navigation/
│   │   └── services/
│   ├── App.js
│   ├── package.json
│   ├── metro.config.js
│   └── .env.example
│
├── docs/                             # Project Documentation
│   ├── SRS.md                        # Software Requirements Specification
│   ├── ERD.png                       # Database Diagram
│   ├── API.md                        # OpenAPI spec link or docs
│   ├── USER_MANUAL.md
│   └── ARCHITECTURE.md
│
├── scripts/                          # Helper scripts
│   ├── setup-dev.sh                  # One-click local setup
│   └── generate-env.sh
│
├── docker-compose.yml                # Local dev: backend + db + frontend
├── .gitignore
├── .env.example                      # Shared env variables
├── LICENSE                           # MIT / Apache 2.0
├── README.md                         # Main project overview
├── CONTRIBUTING.md
├── CHANGELOG.md
└── SECURITY.md                       # How to report vulnerabilities