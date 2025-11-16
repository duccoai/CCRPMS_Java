// src/pages/candidate/Results.jsx
import { useEffect, useState } from "react";
import api from "../../api/api";
import "./Candidate.css";

export default function Results() {
  const [results, setResults] = useState([]);
  const userId = localStorage.getItem("userId");

  useEffect(() => {
    api.get(`/applications/result/${userId}`).then(res => setResults(res.data || []));
  }, [userId]);

  if (!results.length) return <div className="candidate-container">Chưa có kết quả.</div>;

  return (
    <div className="candidate-container">
      <h2>Kết quả tuyển dụng</h2>
      <table className="candidate-table">
        <thead>
          <tr>
            <th>Vị trí</th>
            <th>Điểm thi</th>
            <th>Trạng thái</th>
          </tr>
        </thead>
        <tbody>
          {results.map((r, i) => (
            <tr key={i}>
              <td>{r.testTitle}</td>
              <td>{r.score}</td>
              <td>{r.applicationStatus}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
