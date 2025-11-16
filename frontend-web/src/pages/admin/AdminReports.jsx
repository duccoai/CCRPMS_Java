import React, { useEffect, useState } from "react";
import { getAdminStats } from "../../services/adminApi";
import "./AdminTable.css";

export default function AdminReports() {
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getAdminStats()
      .then((res) => setStats(res.data))
      .catch((err) => console.error("Fetch stats error:", err))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <p>Loading...</p>;
  if (!stats) return <p>Không có dữ liệu thống kê</p>;

  return (
    <div className="admin-table-wrapper">
      <h1>📊 Báo cáo tổng quan</h1>
      <table className="admin-table">
        <tbody>
          <tr>
            <td>Tổng số ứng viên</td>
            <td>{stats.totalCandidates}</td>
          </tr>
          <tr>
            <td>Tổng hồ sơ</td>
            <td>{stats.totalApplications}</td>
          </tr>
          <tr>
            <td>Số hồ sơ được duyệt</td>
            <td>{stats.passed}</td>
          </tr>
          <tr>
            <td>Số hồ sơ trượt</td>
            <td>{stats.failed}</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}
