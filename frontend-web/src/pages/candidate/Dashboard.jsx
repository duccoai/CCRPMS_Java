// src/pages/candidate/Dashboard.jsx
import { Link } from "react-router-dom";
import "./Candidate.css";

export default function Dashboard() {
  const username = localStorage.getItem("username");

  return (
    <div className="candidate-container">
      <h2>Xin chào, {username}</h2>
      <p>Hệ thống quản lý tuyển dụng và nâng bậc tiếp viên của Học viện Hàng không</p>

      <div className="candidate-grid">
        <Link to="/jobs" className="candidate-card">📋 Danh sách công việc</Link>
        <Link to="/applications" className="candidate-card">📩 Hồ sơ đã nộp</Link>
        <Link to="/candidate/promotion" className="candidate-card">📈 Nâng bậc</Link>
        <Link to="/promotion/status" className="candidate-card">📊 Trạng thái nâng bậc</Link>
        <Link to="/exam" className="candidate-card">🧠 Làm bài thi nâng bậc</Link>
        <Link to="/results" className="candidate-card">📄 Xem kết quả</Link>
        <Link to="/profile" className="candidate-card">👤 Hồ sơ cá nhân</Link>
        <Link to="/candidate/notifications" className="candidate-card">🔔 Thông báo</Link>
      </div>
    </div>
  );
}
