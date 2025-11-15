import React, { useEffect, useState } from "react";
import { getAdminStats } from "../../services/adminApi";

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

  if (error) return <p>{error}</p>;
  if (!stats) return <p>Loading...</p>;

  return (
    <div>
      <h1>Admin Dashboard</h1>
      <div className="cards">
        <div className="card">Tổng ứng viên: {stats.totalCandidates}</div>
        <div className="card">Tổng hồ sơ: {stats.totalApplications}</div>
        <div className="card">Được duyệt: {stats.passed}</div>
        <div className="card">Trượt: {stats.failed}</div>
      </div>
    </div>
  );
}
