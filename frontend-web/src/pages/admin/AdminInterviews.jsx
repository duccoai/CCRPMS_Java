import { useEffect, useState } from "react";
import axios from "axios";

export default function AdminInterviews() {
  const [interviews, setInterviews] = useState([]);
  const [exams, setExams] = useState([]);

  const fetchData = async () => {
    try {
      const [interviewRes, examRes] = await Promise.all([
        axios.get("/api/admin/interviews"),
        axios.get("/api/admin/exams")
      ]);
      setInterviews(Array.isArray(interviewRes.data) ? interviewRes.data : []);
      setExams(Array.isArray(examRes.data) ? examRes.data : []);
    } catch (err) {
      console.error(err);
    }
  };

  const toggleExam = async (id) => {
    try {
      await axios.put(`/api/admin/exams/${id}/toggle`);
      fetchData();
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => { fetchData(); }, []);

  return (
    <div>
      <h2>Lịch phỏng vấn</h2>
      {interviews.length === 0 ? <p>Chưa có lịch phỏng vấn</p> :
        <ul>
          {interviews.map(i => (
            <li key={i.id}>{i.candidate?.fullName || "N/A"} - {i.job?.title || "N/A"} - {i.schedule}</li>
          ))}
        </ul>
      }

      <h2>Bài thi</h2>
      {exams.length === 0 ? <p>Chưa có bài thi</p> :
        <ul>
          {exams.map(e => (
            <li key={e.id}>
              {e.title} - {e.active ? "Active" : "Inactive"}
              <button onClick={() => toggleExam(e.id)}>Toggle</button>
            </li>
          ))}
        </ul>
      }
    </div>
  );
}
