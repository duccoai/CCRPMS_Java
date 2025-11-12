import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

// Candidate
import Dashboard from "./pages/candidate/Dashboard";
import JobList from "./pages/candidate/JobList";
import JobDetail from "./pages/candidate/JobDetail";
import Applications from "./pages/candidate/Applications";
import Profile from "./pages/candidate/Profile";
import ExamPage from "./pages/candidate/ExamPage";
import Results from "./pages/candidate/Results";

// Recruiter
import RecruiterDashboard from "./pages/recruiter/RecruiterDashboard";
import RecruiterJobs from "./pages/recruiter/RecruiterJobs";
import RecruiterApplications from "./pages/recruiter/RecruiterApplications";

// Auth
import Login from "./pages/auth/Login";
import Register from "./pages/auth/Register";

// Components
import RequireRole from "./components/RequireRole.jsx";

// Private route cho candidate
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
        <Route path="/exam" element={<PrivateRoute><ExamPage /></PrivateRoute>} />
        <Route path="/results" element={<PrivateRoute><Results /></PrivateRoute>} />
        <Route path="/profile" element={<PrivateRoute><Profile /></PrivateRoute>} />

        {/* Recruiter */}
        <Route path="/recruiter/dashboard" element={<RequireRole role="RECRUITER"><RecruiterDashboard /></RequireRole>} />
        <Route path="/recruiter/jobs" element={<RequireRole role="RECRUITER"><RecruiterJobs /></RequireRole>} />
        <Route path="/recruiter/applications" element={<RequireRole role="RECRUITER"><RecruiterApplications /></RequireRole>} />

        {/* fallback */}
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  );
}
