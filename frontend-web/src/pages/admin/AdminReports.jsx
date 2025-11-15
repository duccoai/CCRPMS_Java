import { useEffect, useState } from "react";
import axios from "axios";

export default function AdminReports() {
  const [stats, setStats] = useState({ totalCandidates: 0, totalApplications: 0, passed: 0, failed: 0 });

  const fetchStats = async () => {
    try {
      const res = await axios.get("/api/admin/statistics");
      setStats(res.data || { totalCandidates: 0, totalApplications: 0, passed: 0, failed: 0 });
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => { fetchStats(); }, []);

  return (
    <div>
      <h2>Báo cáo</h2>
      <p>Tổng số ứng viên: {stats.totalCandidates}</p>
      <p>Tổng số hồ sơ: {stats.totalApplications}</p>
      <p>Số pass: {stats.passed}</p>
      <p>Số fail: {stats.failed}</p>
    </div>
  );
}
