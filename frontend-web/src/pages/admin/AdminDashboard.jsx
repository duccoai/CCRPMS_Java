import React, { useEffect, useState } from "react";
import { getAdminStats } from "../../services/adminApi";
import "./AdminDashboard.css";

export default function AdminDashboard() {
  const [stats, setStats] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    getAdminStats()
      .then(res => setStats(res.data))
      .catch(err => {
        console.error("Failed to load admin stats:", err);
        setError("Không thể tải thống kê. Vui lòng thử lại sau.");
      });
  }, []);

  if (error) return <p className="error-message">{error}</p>;
  if (!stats) return <p>Loading...</p>;

  return (
    <div className="admin-dashboard">
      <h1>📊 Admin Dashboard</h1>

      <div className="stats-grid">
        <div className="stat-card">
          <h3>Tổng ứng viên</h3>
          <p>{stats.totalCandidates}</p>
        </div>

        <div className="stat-card">
          <h3>Tổng hồ sơ ứng tuyển</h3>
          <p>{stats.totalApplications}</p>
        </div>

        <div className="stat-card">
          <h3>Đỗ</h3>
          <p className="passed">{stats.passed}</p>
        </div>

        <div className="stat-card">
          <h3>Trượt</h3>
          <p className="failed">{stats.failed}</p>
        </div>
      </div>
    </div>
  );
}
