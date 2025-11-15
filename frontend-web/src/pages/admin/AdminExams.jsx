import React, { useEffect, useState } from "react";
import { getAllExams, toggleExam } from "../../services/adminApi";

export default function AdminExams() {
  const [exams, setExams] = useState([]);

  const fetchExams = () => {
    getAllExams()
      .then(res => setExams(res.data))
      .catch(err => console.error(err));
  };

  useEffect(() => {
    fetchExams();
  }, []);

  const handleToggle = (id) => {
    toggleExam(id)
      .then(() => fetchExams())
      .catch(err => console.error(err));
  };

  if (!exams || exams.length === 0) return <p>Chưa có bài thi</p>;

  return (
    <div>
      <h1>Bài thi</h1>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Tiêu đề</th>
            <th>Trạng thái</th>
            <th>Hành động</th>
          </tr>
        </thead>
        <tbody>
          {exams.map(ex => (
            <tr key={ex.id}>
              <td>{ex.id}</td>
              <td>{ex.title}</td>
              <td>{ex.active ? "Đang kích hoạt" : "Đang tắt"}</td>
              <td>
                <button onClick={() => handleToggle(ex.id)}>
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
