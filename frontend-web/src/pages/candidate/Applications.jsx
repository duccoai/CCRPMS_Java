// src/pages/candidate/Applications.jsx
import { useEffect, useState } from "react";
import api from "../../api/api";
import "./Candidate.css";

export default function ApplicationsPage() {
  const [apps, setApps] = useState([]);
  const [loading, setLoading] = useState(true);
  const [err, setErr] = useState(null);

  useEffect(() => {
    setLoading(true);
    api.get("/applications/user/me")
      .then(res => {
        const data = res.data || [];
        setApps(Array.isArray(data) ? data : (data.items || []));
      })
      .catch(e => {
        console.error("Lỗi tải applications:", e);
        setErr(e);
      })
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <div className="candidate-container">Đang tải…</div>;
  if (err) return <div className="candidate-container error">Lỗi khi tải hồ sơ (xem console)</div>;
  if (!apps.length) return <div className="candidate-container">Bạn chưa nộp hồ sơ nào.</div>;

  return (
    <div className="candidate-container">
      <h2>Hồ sơ đã nộp</h2>
      <table className="candidate-table">
        <thead>
          <tr>
            <th>Job</th>
            <th>Trạng thái</th>
            <th>Ngày nộp</th>
          </tr>
        </thead>
        <tbody>
          {apps.map(a => (
            <tr key={a.id || a.applicationId}>
              <td>{a.job?.title || a.jobTitle || "—"}</td>
              <td>{a.status || a.statusCode || "—"}</td>
              <td>{a.createdAt ? new Date(a.createdAt).toLocaleString() : (a.submittedAt ? new Date(a.submittedAt).toLocaleString() : "—")}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
