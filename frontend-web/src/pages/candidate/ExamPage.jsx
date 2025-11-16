// src/pages/candidate/ExamPage.jsx
import { useEffect, useState } from "react";
import api from "../../api/api";
import "./Candidate.css";

export default function ExamPage() {
  const [questions, setQuestions] = useState([]);
  const [answers, setAnswers] = useState({});

  useEffect(() => {
    // Fake data nếu backend chưa có
    const fakeData = [
      { id: 1, text: "1. Java là gì?", options: ["Ngôn ngữ lập trình", "Cà phê", "Cả hai", "Không phải"] },
      { id: 2, text: "2. Từ khóa nào để kế thừa class?", options: ["import", "extends", "super", "implements"] },
    ];
    setQuestions(fakeData);

    // Khi backend có API
    // api.get("/exams/start/1").then(res => setQuestions(res.data.questions));
  }, []);

  const handleChange = (qId, value) => setAnswers(prev => ({ ...prev, [qId]: value }));

  const handleSubmit = () => {
    console.log("Answers:", answers);
    alert("Bài thi đã nộp!");
    // có thể gọi api.post("/exams/submit", { answers }) khi backend sẵn sàng
  };

  if (!questions.length) return <div className="candidate-container">Đang tải bài thi…</div>;

  return (
    <div className="candidate-container">
      <h2>Bài thi tuyển dụng</h2>
      {questions.map(q => (
        <div key={q.id} className="exam-question">
          <p><b>{q.text}</b></p>
          {q.options.map(opt => (
            <label key={opt} className="exam-option">
              <input type="radio" name={`q${q.id}`} value={opt} onChange={() => handleChange(q.id, opt)} /> {opt}
            </label>
          ))}
        </div>
      ))}
      <button className="candidate-btn" onClick={handleSubmit}>Nộp bài</button>
    </div>
  );
}
