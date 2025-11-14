import { useEffect, useState } from "react";
import api from "../../api/api";

export default function Applications() {
  const [apps, setApps] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api.get("/applications/user/me")
      .then(res => setApps(Array.isArray(res.data) ? res.data : []))
      .catch(err => {
        console.error(err);
        setApps([]);
      })
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <div style={{ padding: 20 }}>Loading…</div>;

  return (
    <div style={{ padding: 20 }}>
      <h2>Hồ sơ đã nộp</h2>
      {apps.length === 0 ? <div>Chưa có hồ sơ</div> :
        apps.map(a => (
          <div key={a.id} style={{ border: "1px solid #eee", padding: 10, marginBottom: 8 }}>
            <div><b>Vị trí:</b> {a.jobTitle ?? "—"}</div>
            <div><b>Ngày nộp:</b> {new Date(a.createdAt).toLocaleString()}</div>
            <div><b>Trạng thái:</b> {a.status ?? "PENDING"}</div>
          </div>
        ))
      }
    </div>
  );
}
