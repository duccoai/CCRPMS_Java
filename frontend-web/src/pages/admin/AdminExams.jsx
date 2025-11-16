import React, { useEffect, useState } from "react";
import { getAllExams, toggleExam } from "../../services/adminApi";
import "./AdminTable.css";

export default function AdminExams() {
  const [exams, setExams] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchExams = () => {
    setLoading(true);
    getAllExams()
      .then((res) => setExams(res.data || []))
      .catch((err) => console.error("Fetch exams error:", err))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchExams();
  }, []);

  const handleToggle = (id) => {
    toggleExam(id)
      .then(() => fetchExams())
      .catch((err) => console.error("Toggle exam error:", err));
  };

  if (loading) return <p>Loading...</p>;
  if (!exams.length) return <p>Chưa có bài thi</p>;

  return (
    <div className="admin-table-wrapper">
      <h1>📝 Quản lý bài thi</h1>
      <table className="admin-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Tiêu đề</th>
            <th>Trạng thái</th>
            <th>Hành động</th>
          </tr>
        </thead>
        <tbody>
          {exams.map((ex) => (
            <tr key={ex.id}>
              <td>{ex.id}</td>
              <td>{ex.title}</td>
              <td>
                {ex.active ? (
                  <span className="status-badge status-pass">Đang bật</span>
                ) : (
                  <span className="status-badge status-reject">Đang tắt</span>
                )}
              </td>
              <td>
                <button
                  className={`btn-pass`}
                  onClick={() => handleToggle(ex.id)}
                >
                  {ex.active ? "Tắt" : "Bật"}
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
