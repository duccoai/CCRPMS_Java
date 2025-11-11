import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Dashboard from "./pages/candidate/Dashboard";
import JobList from "./pages/candidate/JobList";
import JobDetail from "./pages/candidate/JobDetail";
import Applications from "./pages/candidate/Applications";
import Profile from "./pages/candidate/Profile";
import ExamPage from "./pages/candidate/ExamPage";
import Results from "./pages/candidate/Results";
import Login from "./pages/auth/Login";
import Register from "./pages/auth/Register";

function PrivateRoute({ children }) {
  const token = localStorage.getItem("token");
  return token ? children : <Navigate to="/login" replace />;
}

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        <Route path="/" element={<PrivateRoute><Dashboard /></PrivateRoute>} />
        <Route path="/jobs" element={<PrivateRoute><JobList /></PrivateRoute>} />
        <Route path="/jobs/:id" element={<PrivateRoute><JobDetail /></PrivateRoute>} />
        <Route path="/applications" element={<PrivateRoute><Applications /></PrivateRoute>} />
        <Route path="/exam" element={<PrivateRoute><ExamPage /></PrivateRoute>} />
        <Route path="/results" element={<PrivateRoute><Results /></PrivateRoute>} />
        <Route path="/profile" element={<PrivateRoute><Profile /></PrivateRoute>} />
      </Routes>
    </BrowserRouter>
  );
}
