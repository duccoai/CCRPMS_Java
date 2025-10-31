@echo off
setlocal enabledelayedexpansion

REM Root
mkdir ccrpms
cd ccrpms

REM ===== .github =====
mkdir .github\workflows
mkdir .github\ISSUE_TEMPLATE
type nul > .github\workflows\ci-backend.yml
type nul > .github\workflows\ci-frontend.yml
type nul > .github\workflows\deploy.yml
type nul > .github\ISSUE_TEMPLATE\bug_report.md
type nul > .github\ISSUE_TEMPLATE\feature_request.md
type nul > .github\PULL_REQUEST_TEMPLATE.md

REM ===== backend =====
mkdir backend

REM ===== frontend-web =====
mkdir frontend-web
mkdir frontend-web\public
mkdir frontend-web\src
mkdir frontend-web\src\assets
mkdir frontend-web\src\components
mkdir frontend-web\src\components\layout
mkdir frontend-web\src\components\forms
mkdir frontend-web\src\components\dashboard
mkdir frontend-web\src\pages
mkdir frontend-web\src\pages\auth
mkdir frontend-web\src\pages\candidate
mkdir frontend-web\src\pages\recruiter
mkdir frontend-web\src\pages\admin
mkdir frontend-web\src\services
mkdir frontend-web\src\context

type nul > frontend-web\public\index.html
type nul > frontend-web\public\favicon.ico
type nul > frontend-web\src\services\api.js
type nul > frontend-web\src\context\AuthContext.js
type nul > frontend-web\src\App.jsx
type nul > frontend-web\src\index.js
type nul > frontend-web\Dockerfile
type nul > frontend-web\package.json
type nul > frontend-web\vite.config.js
type nul > frontend-web\.env.example
type nul > frontend-web\.dockerignore

REM ===== mobile =====
mkdir mobile
mkdir mobile\android
mkdir mobile\ios
mkdir mobile\src
mkdir mobile\src\screens
mkdir mobile\src\components
mkdir mobile\src\navigation
mkdir mobile\src\services

type nul > mobile\App.js
type nul > mobile\package.json
type nul > mobile\metro.config.js
type nul > mobile\.env.example

REM ===== docs =====
mkdir docs
type nul > docs\SRS.md
type nul > docs\ERD.png
type nul > docs\API.md
type nul > docs\USER_MANUAL.md
type nul > docs\ARCHITECTURE.md

REM ===== scripts =====
mkdir scripts
type nul > scripts\setup-dev.sh
type nul > scripts\generate-env.sh

REM ===== root files =====
type nul > docker-compose.yml
type nul > .gitignore
type nul > .env.example
type nul > LICENSE
type nul > README.md
type nul > CONTRIBUTING.md
type nul > CHANGELOG.md
type nul > SECURITY.md

echo ✅ CCRPMS fullstack folder structure created successfully!
pause
