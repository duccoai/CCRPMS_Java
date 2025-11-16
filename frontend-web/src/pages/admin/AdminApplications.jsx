import React, { useEffect, useState } from "react";
import {
  getAllApplications,
  updateApplicationFinalDecision,
} from "../../services/adminApi";
import "./AdminTable.css";

export default function AdminApplications() {
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchApplications = () => {
    setLoading(true);
    getAllApplications()
      .then((res) => setApplications(res.data || []))
      .catch((err) => console.error("Error:", err))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchApplications();
  }, []);

  const handleDecision = (id, result) => {
    updateApplicationFinalDecision(id, result)
      .then(() => fetchApplications())
      .catch((err) => console.error("Update failed:", err));
  };

  if (loading) return <p>Loading...</p>;
  if (!applications.length) return <p>Chưa có hồ sơ ứng tuyển</p>;

  return (
    <div className="admin-table-wrapper">
      <h1>📄 Hồ sơ ứng tuyển</h1>

      <table className="admin-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Ứng viên</th>
            <th>Email</th>
            <th>Công việc</th>
            <th>Trạng thái</th>
            <th>Duyệt</th>
          </tr>
        </thead>

        <tbody>
          {applications.map((app) => (
            <tr key={app.applicationId}>
              <td>{app.applicationId}</td>
              <td>{app.candidateFullName}</td>
              <td>{app.candidateEmail}</td>
              <td>{app.jobTitle}</td>

              <td>
                <span
                  className={`status-badge ${
                    app.status === "PASS"
                      ? "status-pass"
                      : app.status === "REJECT"
                      ? "status-reject"
                      : "status-pending"
                  }`}
                >
                  {app.status}
                </span>
              </td>

              <td>
                <button
                  className="btn-pass"
                  onClick={() =>
                    handleDecision(app.applicationId, "PASS")
                  }
                >
                  ✔ Duyệt
                </button>

                <button
                  className="btn-reject"
                  onClick={() =>
                    handleDecision(app.applicationId, "REJECT")
                  }
                >
                  ✖ Trượt
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
