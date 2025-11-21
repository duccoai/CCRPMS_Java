// src/pages/candidate/CandidatePromotionStatus.jsx
import React, { useEffect, useState } from "react";
import CandidateApi from "../../services/candidateApi"; // giả sử bạn đã có api tương tự như jobs/applications
import "./Promotion.css"; // bạn có thể dùng CSS riêng hoặc dùng chung

export default function CandidatePromotionStatus() {
  const [promotions, setPromotions] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadPromotions();
  }, []);

  async function loadPromotions() {
    setLoading(true);
    try {
      const res = await CandidateApi.getMyPromotionApplications();
      setPromotions(res.data || []);
    } catch (e) {
      console.error("Load promotion status error:", e);
      alert("Lỗi khi tải trạng thái hồ sơ nâng bậc. Kiểm tra console.");
    } finally {
      setLoading(false);
    }
  }

  const getStatusText = (status) => {
    switch (status) {
      case "PENDING":
        return "Đang chờ duyệt";
      case "APPROVED":
        return "Được duyệt";
      case "HIRED":
        return "Đỗ";
      case "REJECTED":
        return "Trượt";
      default:
        return "Không rõ trạng thái";
    }
  };

  if (loading) return <div>Loading...</div>;

  return (
    <div className="candidate-promotion-status-wrapper">
      <h1>📈 Hồ sơ nâng bậc</h1>
      {promotions.length === 0 ? (
        <div>Không có hồ sơ nâng bậc</div>
      ) : (
        <table className="candidate-promotion-table">
          <thead>
            <tr>
              <th>Ngày nộp</th>
              <th>Số giờ bay</th>
              <th>Kết quả đánh giá</th>
              <th>Chứng chỉ</th>
              <th>Đề xuất trưởng nhóm</th>
              <th>Trạng thái</th>
            </tr>
          </thead>
          <tbody>
            {promotions.map((app) => (
              <tr key={app.id}>
                <td>{new Date(app.createdAt).toLocaleString()}</td>
                <td>{app.flightHours}</td>
                <td>{app.performanceResult || "-"}</td>
                <td>{app.certificates || "-"}</td>
                <td>{app.teamLeadSuggestion || "-"}</td>
                <td>{getStatusText(app.status)}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
