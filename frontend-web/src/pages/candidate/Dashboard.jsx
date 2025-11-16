// src/pages/candidate/Dashboard.jsx
import { Link } from "react-router-dom";
import "./Candidate.css";

export default function Dashboard() {
  const username = localStorage.getItem("username");

  return (
    <div className="candidate-container">
      <h2>Xin chào, {username} 👋</h2>
      <p>Chào mừng bạn đến với hệ thống tuyển dụng!</p>

      <div className="candidate-grid">
        <Link to="/jobs" className="candidate-card">📋 Danh sách công việc</Link>
        <Link to="/applications" className="candidate-card">📩 Hồ sơ đã nộp</Link>
        <Link to="/exam" className="candidate-card">🧠 Làm bài thi online</Link>
        <Link to="/results" className="candidate-card">📄 Xem kết quả</Link>
        <Link to="/profile" className="candidate-card">👤 Hồ sơ cá nhân</Link>
      </div>
    </div>
  );
}
