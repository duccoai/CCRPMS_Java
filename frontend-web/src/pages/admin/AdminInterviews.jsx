import React, { useEffect, useState } from "react";
import { getAllInterviews } from "../../services/adminApi";

export default function AdminInterviews() {
  const [interviews, setInterviews] = useState([]);

  const fetchInterviews = () => {
    getAllInterviews()
      .then(res => setInterviews(res.data))
      .catch(err => console.error(err));
  };

  useEffect(() => {
    fetchInterviews();
  }, []);

  if (!interviews || interviews.length === 0) return <p>Chưa có lịch phỏng vấn</p>;

  return (
    <div>
      <h1>Phỏng vấn</h1>
      <table>
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
          {interviews.map(iv => (
            <tr key={iv.id}>
              <td>{iv.id}</td>
              <td>{iv.candidateFullName}</td>
              <td>{iv.candidateEmail}</td>
              <td>{iv.jobTitle}</td>
              <td>{new Date(iv.interviewDate).toLocaleString()}</td>
              <td>{iv.score ?? "Chưa chấm"}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
