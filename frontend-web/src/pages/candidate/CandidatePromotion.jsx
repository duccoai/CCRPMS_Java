import { useState } from "react";
import api from "../../api/api";
import "./Candidate.css";

export default function CandidatePromotion() {
  const [flightHours, setFlightHours] = useState("");
  const [performanceResult, setPerformanceResult] = useState("");
  const [certificates, setCertificates] = useState("");
  const [teamLeadSuggestion, setTeamLeadSuggestion] = useState("");
  const [loading, setLoading] = useState(false);

  const userId = localStorage.getItem("userId");

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!flightHours || !performanceResult || !certificates || !teamLeadSuggestion) {
      return alert("Vui lòng điền đầy đủ thông tin!");
    }

    const payload = {
      userId: userId,
      flightHours: parseInt(flightHours),
      performanceResult,
      certificates,
      teamLeadSuggestion
    };

    setLoading(true);
    try {
      const res = await api.post("/promotion/submit", payload);
      alert("🎉 Hồ sơ nâng bậc đã được gửi thành công!");
      // Reset form
      setFlightHours("");
      setPerformanceResult("");
      setCertificates("");
      setTeamLeadSuggestion("");
    } catch (err) {
      console.error(err);
      alert("Không thể gửi hồ sơ. Vui lòng thử lại.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="candidate-container">
      <h2>📈 Nộp hồ sơ nâng bậc</h2>
      <form onSubmit={handleSubmit} className="promotion-form">
        <label>
          Số giờ bay tích lũy:
          <input
            type="number"
            value={flightHours}
            onChange={(e) => setFlightHours(e.target.value)}
            min="0"
          />
        </label>

        <label>
          Kết quả đánh giá hiệu suất:
          <textarea
            value={performanceResult}
            onChange={(e) => setPerformanceResult(e.target.value)}
          />
        </label>

        <label>
          Chứng chỉ liên quan:
          <textarea
            value={certificates}
            onChange={(e) => setCertificates(e.target.value)}
          />
        </label>

        <label>
          Đề xuất từ trưởng nhóm:
          <textarea
            value={teamLeadSuggestion}
            onChange={(e) => setTeamLeadSuggestion(e.target.value)}
          />
        </label>

        <button type="submit" disabled={loading}>
          {loading ? "Đang gửi..." : "Nộp"}
        </button>
      </form>
    </div>
  );
}
