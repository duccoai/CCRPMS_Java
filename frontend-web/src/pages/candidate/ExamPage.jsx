import { useEffect, useState } from "react";
import api from "../../api/api";

export default function ExamPage() {
  const [questions, setQuestions] = useState([]);

  useEffect(() => {
    // Nếu backend chưa có, tạo dữ liệu giả:
    const fakeData = [
      {
        id: 1,
        text: "1. Java là gì?",
        options: ["Ngôn ngữ lập trình", "Cà phê", "Cả hai", "Không phải"],
      },
      {
        id: 2,
        text: "2. Từ khóa nào để kế thừa class?",
        options: ["import", "extends", "super", "implements"],
      },
    ];
    setQuestions(fakeData);

    // Khi backend có, bạn bật lại dòng này:
    // api.get("/exams/start/1").then(res => setQuestions(res.data.questions));
  }, []);

  const [answers, setAnswers] = useState({});

  const handleChange = (qId, value) => {
    setAnswers(prev => ({ ...prev, [qId]: value }));
  };

  const handleSubmit = () => {
    console.log("Answers:", answers);
    alert("Bài thi đã nộp!");
  };

  return (
    <div style={{ padding: 20 }}>
      <h2>Bài thi tuyển dụng</h2>
      {questions.map(q => (
        <div key={q.id} style={{ marginBottom: 20 }}>
          <p><b>{q.text}</b></p>
          {q.options.map(opt => (
            <label key={opt} style={{ display: "block", marginLeft: 10 }}>
              <input
                type="radio"
                name={`q${q.id}`}
                value={opt}
                onChange={() => handleChange(q.id, opt)}
              />{" "}
              {opt}
            </label>
          ))}
        </div>
      ))}
      <button onClick={handleSubmit} style={styles.button}>Nộp bài</button>
    </div>
  );
}

const styles = {
  button: { padding: "10px 20px", background: "#007bff", color: "white", border: "none", borderRadius: 5 },
};
