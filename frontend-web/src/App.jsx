import { BrowserRouter, Routes, Route, Navigate, Outlet } from "react-router-dom";

// Candidate
import Dashboard from "./pages/candidate/Dashboard";
import JobList from "./pages/candidate/JobList";
import JobDetail from "./pages/candidate/JobDetail";
import Applications from "./pages/candidate/Applications";
import Profile from "./pages/candidate/Profile";
import ExamPage from "./pages/candidate/ExamPage";
import Results from "./pages/candidate/Results";
import CandidatePromotion from "./pages/candidate/CandidatePromotion";
import CandidatePromotionStatus from "./pages/candidate/CandidatePromotionStatus";
import Notifications from "./pages/candidate/Notifications";



// Recruiter
import RecruiterDashboard from "./pages/recruiter/RecruiterDashboard";
import RecruiterJobs from "./pages/recruiter/RecruiterJobs";
import RecruiterApplications from "./pages/recruiter/RecruiterApplications";
import RecruiterPromotions from "./pages/recruiter/RecruiterPromotions.jsx";


// Admin
import AdminDashboard from "./pages/admin/AdminDashboard";
import AdminUsers from "./pages/admin/AdminUsers";
import AdminApplications from "./pages/admin/AdminApplications";
import AdminInterviews from "./pages/admin/AdminInterviews";
import AdminExams from "./pages/admin/AdminExams";
import AdminReports from "./pages/admin/AdminReports";
import AdminLayout from "./components/layout/AdminLayout";

// Auth
import Login from "./pages/auth/Login";
import Register from "./pages/auth/Register";

import RequireRole from "./components/RequireRole.jsx";

function PrivateRoute({ children }) {
  const token = localStorage.getItem("token");
  return token ? children : <Navigate to="/login" replace />;
}

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Auth */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* Candidate */}
        <Route path="/" element={<PrivateRoute><Dashboard /></PrivateRoute>} />
        <Route path="/jobs" element={<PrivateRoute><JobList /></PrivateRoute>} />
        <Route path="/jobs/:id" element={<PrivateRoute><JobDetail /></PrivateRoute>} />
        <Route path="/applications" element={<PrivateRoute><Applications /></PrivateRoute>} />
        <Route path="/exam" element={<Navigate to="/exam/1" replace />} />
        <Route path="/exam/:examId" element={<PrivateRoute><ExamPage /></PrivateRoute>} />
        <Route path="/results" element={<PrivateRoute><Results /></PrivateRoute>} />
        <Route path="/profile" element={<PrivateRoute><Profile /></PrivateRoute>} />
        <Route path="/candidate/promotion" element={<CandidatePromotion />} />
        <Route path="/candidate/promotion" element={<PrivateRoute><CandidatePromotion /></PrivateRoute>} />
        <Route path="/promotion/status" element={<CandidatePromotionStatus />} />
        <Route path="/promotion/status" element={<PrivateRoute><CandidatePromotionStatus /></PrivateRoute>} />
        <Route path="/candidate/notifications" element={<PrivateRoute><Notifications /></PrivateRoute>} />

        {/* Recruiter */}
        <Route path="/recruiter/dashboard" element={<RequireRole role="RECRUITER"><RecruiterDashboard /></RequireRole>} />
        <Route path="/recruiter/jobs" element={<RequireRole role="RECRUITER"><RecruiterJobs /></RequireRole>} />
        <Route path="/recruiter/applications" element={<RequireRole role="RECRUITER"><RecruiterApplications /></RequireRole>} />
        <Route path="/recruiter/promotions" element={<RecruiterPromotions />} />
        <Route path="/recruiter/promotions" element={<RequireRole role="RECRUITER"><RecruiterPromotions /></RequireRole>} />

        {/* Admin */}
        <Route path="/admin/*" element={<RequireRole role="ADMIN"><AdminLayout><Outlet /></AdminLayout></RequireRole>}>
          {/* Index route: trang chào mừng */}
          <Route index element={<p>Chào mừng Admin! Chọn tab bên trái để bắt đầu.</p>} />
          <Route path="dashboard" element={<AdminDashboard />} />
          <Route path="users" element={<AdminUsers />} />
          <Route path="applications" element={<AdminApplications />} />
          <Route path="interviews" element={<AdminInterviews />} />
          <Route path="exams" element={<AdminExams />} />
          <Route path="reports" element={<AdminReports />} />
        </Route>

        {/* fallback */}
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  );
}