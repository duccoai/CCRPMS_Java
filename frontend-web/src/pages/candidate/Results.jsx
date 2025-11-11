import { useEffect, useState } from "react";
import api from "../../api/api";

export default function Results() {
  const [results, setResults] = useState([]);
  const userId = localStorage.getItem("userId");

  useEffect(() => {
    api.get(`/applications/result/${userId}`).then(res => setResults(res.data));
  }, []);

  return (
    <div style={{ padding: 20 }}>
      <h2>Kết quả tuyển dụng</h2>
      <table style={styles.table}>
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

const styles = {
  table: {
    width: "100%",
    borderCollapse: "collapse",
  },
};
