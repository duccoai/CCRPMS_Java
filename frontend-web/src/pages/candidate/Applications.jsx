// src/pages/candidate/Applications.jsx
import { useEffect, useState } from "react";
import api from "../../api/api";

export default function ApplicationsPage() {
  const [apps, setApps] = useState([]);
  const [loading, setLoading] = useState(true);
  const [err, setErr] = useState(null);

  useEffect(() => {
    setLoading(true);
    api.get("/applications/user/me")
      .then(res => {
        const data = res.data || [];
        // nếu backend trả object wrap: adjust here
        setApps(Array.isArray(data) ? data : (data.items || []));
      })
      .catch(e => {
        console.error("Lỗi tải applications:", e);
        setErr(e);
      })
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <div style={{padding:20}}>Đang tải…</div>;
  if (err) return <div style={{padding:20,color:'red'}}>Lỗi khi tải hồ sơ (xem console)</div>;
  if (!apps.length) return <div style={{padding:20}}>Bạn chưa nộp hồ sơ nào.</div>;

  return (
    <div style={{padding:20}}>
      <h2>Hồ sơ đã nộp</h2>
      <table style={{width:'100%',borderCollapse:'collapse'}}>
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
              <td>{a.status || (a.statusCode) || "—"}</td>
              <td>{a.createdAt ? new Date(a.createdAt).toLocaleString() : (a.submittedAt ? new Date(a.submittedAt).toLocaleString() : "—")}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
