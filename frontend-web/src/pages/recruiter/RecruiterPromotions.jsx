import React, { useEffect, useState } from "react";
import RecruiterApi from "../../services/recruiterApi";
import "./RecruiterTable.css";

const statusOptions = ["PENDING", "APPROVED", "REJECTED"];

export default function RecruiterPromotions() {
  const [promotions, setPromotions] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadPromotions();
  }, []);

  async function loadPromotions() {
    setLoading(true);
    try {
      const res = await RecruiterApi.getPromotionApplications();
      setPromotions(res.data || []);
    } catch (e) {
      console.error("Load promotions error:", e);
      alert("Lỗi khi tải hồ sơ nâng bậc.");
    } finally {
      setLoading(false);
    }
  }

  const getId = (app) => app.id;
  const getCandidateName = (app) =>
    app.candidateFullName || app.candidate?.fullName || "—";
  const getCandidateEmail = (app) =>
    app.candidateEmail || app.candidate?.email || "—";
  const getStatus = (app) => app.status || "PENDING";

  /**
   * 🔥 Hàm cập nhật trạng thái theo đúng backend:
   * PUT /api/recruiter/promotion/{id}/status
   * Body: { status: "APPROVED" }
   */
  const changeStatus = async (id, newStatus) => {
    if (!window.confirm(`Xác nhận đặt trạng thái: ${newStatus}?`)) return;

    try {
      await RecruiterApi.updatePromotionStatus(id, newStatus);
      await loadPromotions();
    } catch (err) {
      console.error("Update status error:", err);
      alert("Cập nhật trạng thái thất bại.");
    }
  };

  if (loading) return <div>Loading...</div>;

  return (
    <div className="recruiter-table-wrapper">
      <h1>📈 Promotion Applications</h1>

      {promotions.length === 0 ? (
        <div>Không có hồ sơ nâng bậc</div>
      ) : (
        <table className="recruiter-table">
          <thead>
            <tr>
              <th>Candidate</th>
              <th>Email</th>
              <th>Flight Hours</th>
              <th>Performance</th>
              <th>Certificates</th>
              <th>Team Lead Suggestion</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>

          <tbody>
            {promotions.map((app) => (
              <tr key={getId(app)}>
                <td>{getCandidateName(app)}</td>
                <td>{getCandidateEmail(app)}</td>
                <td>{app.flightHours}</td>
                <td>{app.performanceResult || "-"}</td>
                <td>{app.certificates || "-"}</td>
                <td>{app.teamLeadSuggestion || "-"}</td>

                <td>
                  <span className={`status-badge status-${getStatus(app).toLowerCase()}`}>
                    {getStatus(app)}
                  </span>
                </td>

                <td className="action-buttons">
                  <select
                    value={getStatus(app)}
                    onChange={(e) => changeStatus(getId(app), e.target.value)}
                  >
                    {statusOptions.map((s) => (
                      <option key={s} value={s}>
                        {s}
                      </option>
                    ))}
                  </select>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
