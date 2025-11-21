import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../../api/api";
import "./Candidate.css";

export default function ExamPage() {
  const { examId } = useParams();
  const [questions, setQuestions] = useState([]);
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [answers, setAnswers] = useState({});
  const [loading, setLoading] = useState(true);
  const [score, setScore] = useState(null);
  const [canTakeExam, setCanTakeExam] = useState(false);

  const userId = localStorage.getItem("userId");

  // Load exam
  useEffect(() => {
    if (!examId) return;

    api.get(`/exams/start/${examId}`)
      .then(res => {
        setCanTakeExam(true); // backend đã APPROVED
        setTitle(res.data.title);
        setDescription(res.data.description);
        const qList = res.data.questions.map(q => ({
          id: q.id,
          text: q.content,
          options: [
            { label: "A", text: q.optionA },
            { label: "B", text: q.optionB },
            { label: "C", text: q.optionC },
            { label: "D", text: q.optionD }
          ]
        }));
        setQuestions(qList);
      })
      .catch(err => {
        console.error("Failed to load exam:", err);
        setCanTakeExam(false);
        alert(err.response?.data || "Bạn chưa thể làm bài thi online.");
      })
      .finally(() => setLoading(false));
  }, [examId]);

  // Chọn đáp án
  const handleChange = (qid, value) =>
    setAnswers(prev => ({ ...prev, [qid]: value }));

  // Nộp bài
  const handleSubmit = () => {
    if (!canTakeExam) return alert("Bạn chưa được phép làm bài thi.");

    const unanswered = questions.filter(q => !answers[q.id]);
    if (unanswered.length > 0) {
      alert("Bạn phải trả lời tất cả câu hỏi!");
      return;
    }

    const payload = { answers: {} };
    questions.forEach(q => {
      payload.answers[q.id.toString()] = answers[q.id];
    });

    api.post(`/exams/submit/${userId}/${examId}`, payload)
      .then(res => {
        const submittedScore = res.data.score;
        setScore(submittedScore);
        alert(`🎉 Bạn đã nộp bài!\nĐiểm của bạn: ${submittedScore}`);
      })
      .catch(err => {
        console.error("Submit failed:", err);
        alert(err.response?.data || "Không thể nộp bài!");
      });
  };

  if (loading) return <div className="candidate-container">Đang tải bài thi…</div>;

  return (
    <div className="candidate-container">
      <h2>{title}</h2>
      <p>{description}</p>

      {!canTakeExam && (
        <p>Bạn chưa được phép làm bài thi online. Hãy nộp hồ sơ nâng bậc và chờ duyệt.</p>
      )}

      {canTakeExam && questions.map(q => (
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

      {canTakeExam && (
        <button className="candidate-btn" onClick={handleSubmit}>
          Nộp bài
        </button>
      )}

      {score !== null && <p>Điểm của bạn: <b>{score}</b></p>}
    </div>
  );
}
