import React, { useEffect, useState } from "react";
import { getAllApplications, updateApplicationFinalDecision } from "../../services/adminApi";

export default function AdminApplications() {
  const [applications, setApplications] = useState([]);

  const fetchApplications = () => {
    getAllApplications()
      .then(res => setApplications(res.data || []))
      .catch(err => console.error(err));
  };

  useEffect(() => { fetchApplications(); }, []);

  const handleDecision = (id, result) => {
    updateApplicationFinalDecision(id, result)
      .then(() => fetchApplications())
      .catch(err => console.error(err));
  };

  if (!applications.length) return <p>Chưa có hồ sơ ứng tuyển</p>;

  return (
    <div>
      <h1>Hồ sơ ứng tuyển</h1>
      <table border="1" cellPadding="5">
        <thead>
          <tr>
            <th>ID</th>
            <th>Ứng viên</th>
            <th>Email</th>
            <th>Job</th>
            <th>Status</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {applications.map(a => (
            <tr key={a.applicationId}>
              <td>{a.applicationId}</td>
              <td>{a.candidateFullName}</td>
              <td>{a.candidateEmail}</td>
              <td>{a.jobTitle}</td>
              <td>{a.status}</td>
              <td>
                <button onClick={() => handleDecision(a.applicationId, "PASS")}>Được duyệt</button>
                <button onClick={() => handleDecision(a.applicationId, "REJECT")}>Trượt</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
