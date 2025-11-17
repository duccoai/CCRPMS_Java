// src/pages/candidate/ExamPage.jsx
import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../../api/api";
import "./Candidate.css";

export default function ExamPage() {
  const { examId } = useParams();      
  const navigate = useNavigate();
  const [questions, setQuestions] = useState([]);
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [answers, setAnswers] = useState({});
  const [loading, setLoading] = useState(true);

  const userId = localStorage.getItem("userId");

  // 1️⃣ Load đề thi từ backend
  useEffect(() => {
    if (!examId) return;

    setLoading(true);
    api.get(`/exams/start/${examId}`)
      .then(res => {
        setTitle(res.data.title);
        setDescription(res.data.description);

        // map câu hỏi về format dễ dùng cho frontend
        const qList = res.data.questions.map(q => ({
          id: q.id,
          text: q.content,
          options: [
            { label: "A", text: q.optionA },
            { label: "B", text: q.optionB },
            { label: "C", text: q.optionC },
            { label: "D", text: q.optionD },
          ],
          correctAnswer: q.correctAnswer
        }));
        setQuestions(qList);
      })
      .catch(err => {
        console.error("Failed to load exam:", err);
        alert("Không thể tải bài thi!");
      })
      .finally(() => setLoading(false));
  }, [examId]);

  // 2️⃣ Xử lý chọn đáp án
  const handleChange = (qid, value) =>
    setAnswers(prev => ({ ...prev, [qid]: value }));

  // 3️⃣ Nộp bài
  const handleSubmit = () => {
    // kiểm tra câu chưa trả lời
    const unanswered = questions.filter(q => !answers[q.id]);
    if (unanswered.length > 0) {
      alert("Bạn phải trả lời tất cả câu hỏi!");
      return;
    }

    // chuẩn dữ liệu gửi lên backend: { questionId: "A/B/C/D" }
    const payload = {};
    questions.forEach(q => {
      payload[q.id] = answers[q.id];
    });

    api.post(`/exams/submit/${userId}/${examId}`, payload)
      .then(res => {
        alert(`Nộp bài thành công! Điểm của bạn: ${res.data.score}`);
        navigate("/results");
      })
      .catch(err => {
        console.error("Submit failed:", err);
        alert("Không thể nộp bài!");
      });
  };

  if (loading) return <div className="candidate-container">Đang tải bài thi…</div>;

  return (
    <div className="candidate-container">
      <h2>{title}</h2>
      <p>{description}</p>

      {questions.map(q => (
        <div key={q.id} className="exam-question">
          <p><b>{q.text}</b></p>
          {q.options.map(opt => (
            <label key={opt.label} className="exam-option">
              <input
                type="radio"
                name={`q-${q.id}`}
                value={opt.label}
                checked={answers[q.id] === opt.label}
                onChange={() => handleChange(q.id, opt.label)}
              />
              {opt.label}. {opt.text}
            </label>
          ))}
        </div>
      ))}

      <button className="candidate-btn" onClick={handleSubmit}>
        Nộp bài
      </button>
    </div>
  );
}
