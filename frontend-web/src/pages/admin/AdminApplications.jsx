import { useEffect, useState } from "react";
import axios from "axios";

export default function AdminApplications() {
  const [applications, setApplications] = useState([]);

  const fetchApplications = async () => {
    try {
      const res = await axios.get("/api/admin/applications");
      // Nếu backend trả object { data: [...] } thì chọn data
      const apps = Array.isArray(res.data) ? res.data : res.data.data || [];
      setApplications(apps);
    } catch (err) {
      console.error(err);
    }
  };

  const handleFinalDecision = async (id, result) => {
    try {
      await axios.put(`/api/admin/applications/${id}/final?result=${result}`);
      fetchApplications();
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => { fetchApplications(); }, []);

  if (!applications.length) return <p>Chưa có hồ sơ ứng tuyển</p>;

  return (
    <div>
      <h2>Danh sách hồ sơ ứng tuyển</h2>
      <table border="1" cellPadding="5">
        <thead>
          <tr>
            <th>ID</th>
            <th>Ứng viên</th>
            <th>Job</th>
            <th>Trạng thái</th>
            <th>Hành động</th>
          </tr>
        </thead>
        <tbody>
          {applications.map(app => (
            <tr key={app.id}>
              <td>{app.id}</td>
              <td>{app.candidate?.fullName || "N/A"}</td>
              <td>{app.job?.title || "N/A"}</td>
              <td>{app.status}</td>
              <td>
                <button onClick={() => handleFinalDecision(app.id, "PASS")}>Pass</button>
                <button onClick={() => handleFinalDecision(app.id, "FAIL")}>Fail</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
