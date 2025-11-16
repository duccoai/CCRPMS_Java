import React, { useEffect, useState } from "react";
import { getAllInterviews } from "../../services/adminApi";
import "./AdminTable.css";

export default function AdminInterviews() {
  const [interviews, setInterviews] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchInterviews = () => {
    setLoading(true);
    getAllInterviews()
      .then((res) => setInterviews(res.data || []))
      .catch((err) => console.error("Fetch interviews error:", err))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchInterviews();
  }, []);

  if (loading) return <p>Loading...</p>;
  if (!interviews.length) return <p>Chưa có lịch phỏng vấn</p>;

  return (
    <div className="admin-table-wrapper">
      <h1>📅 Lịch phỏng vấn</h1>
      <table className="admin-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Ứng viên</th>
            <th>Email</th>
            <th>Job</th>
            <th>Ngày phỏng vấn</th>
            <th>Điểm</th>
          </tr>
        </thead>
        <tbody>
          {interviews.map((iv) => (
            <tr key={iv.id}>
              <td>{iv.id}</td>
              <td>{iv.candidateFullName}</td>
              <td>{iv.candidateEmail}</td>
              <td>{iv.jobTitle}</td>
              <td>{new Date(iv.interviewDate).toLocaleString()}</td>
              <td>
                {iv.score !== null && iv.score !== undefined ? (
                  <span className="status-badge status-pass">{iv.score}</span>
                ) : (
                  <span className="status-badge status-pending">Chưa chấm</span>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
