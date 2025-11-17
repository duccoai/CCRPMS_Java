import React, { useEffect, useState } from "react";
import RecruiterApi from "../../services/recruiterApi";
import "./RecruiterTable.css";

const statusOptions = ["PENDING", "INTERVIEWING", "APPROVED", "REJECTED", "HIRED"];

export default function RecruiterApplications() {
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadApplications();
  }, []);

  // Load danh sách applications
  async function loadApplications() {
    setLoading(true);
    try {
      const res = await RecruiterApi.getApplications();
      setApplications(res.data || []);
    } catch (e) {
      console.error("Load applications error:", e);
      alert("Lỗi khi tải applications. Kiểm tra console.");
    } finally {
      setLoading(false);
    }
  }

  // Helper functions
  const getAppId = (app) => app.applicationId || app.id;
  const getCandidateName = (app) =>
    app.candidateFullName ||
    app.candidateUsername ||
    (app.user && (app.user.fullName || app.user.username)) ||
    "—";
  const getCandidateEmail = (app) => app.candidateEmail || (app.user && app.user.email) || "—";
  const getJobTitle = (app) => app.jobTitle || (app.job && app.job.title) || "—";
  const getStatus = (app) => app.status || "PENDING";
  const getExamScore = (app) => (app.examScore !== null && app.examScore !== undefined ? app.examScore : "-");
  const getInterviewScore = (app) =>
    app.interviewScore !== null && app.interviewScore !== undefined ? app.interviewScore : "-";

  // Thay đổi trạng thái application
  async function changeStatus(app, status) {
    const appId = getAppId(app);
    if (!appId) return alert("Application ID không xác định");
    if (!window.confirm(`Đặt trạng thái ${status}?`)) return;

    try {
      await RecruiterApi.updateApplicationStatus(appId, status);
      await loadApplications();
    } catch (e) {
      console.error("Update status error:", e);
      alert("Cập nhật thất bại");
    }
  }

  // Lên lịch phỏng vấn
  async function scheduleInterview(app) {
    const appId = getAppId(app);
    if (!appId) return alert("Application ID không xác định");

    const iso = prompt("Nhập thời gian (ví dụ 2025-11-20T14:00:00Z)", "");
    const location = prompt("Nhập địa điểm", "");
    const note = prompt("Ghi chú", "");
    if (!iso || !location) return;

    try {
      await RecruiterApi.scheduleInterview({
        applicationId: appId,
        schedule: iso,
        location,
        note,
        status: "INTERVIEWING",
      });
      alert("Lên lịch thành công");
      await loadApplications();
    } catch (e) {
      console.error("Schedule interview error:", e);
      alert("Lên lịch thất bại: " + (e.response?.data?.message || e.message));
    }
  }

  // Chấm điểm phỏng vấn
  async function scoreInterview(app) {
    const interviewId = app.interviewId || app.interview?.id;
    if (!interviewId) return alert("Không tìm thấy interviewId (hãy schedule trước)");

    const s = prompt("Nhập điểm phỏng vấn (VD: 8.5)", "");
    const score = parseFloat(s);
    if (isNaN(score)) return alert("Điểm không hợp lệ");

    try {
      await RecruiterApi.scoreInterview(interviewId, score, "");
      alert("Chấm điểm phỏng vấn thành công");
      await loadApplications();
    } catch (e) {
      console.error("Score interview error:", e);
      alert("Chấm điểm thất bại: " + (e.response?.data?.message || e.message));
    }
  }

  // Chấm điểm bài thi
  async function scoreExam(app) {
    const appId = getAppId(app);
    if (!appId) return alert("Application ID không xác định");

    try {
      const res = await RecruiterApi.getSubmissionByApplication(appId);
      const submission = res.data;
      if (!submission) return alert("Không tìm thấy submission");

      const s = prompt("Nhập điểm cho bài thi (VD: 7.5)", submission.score ?? "");
      const score = parseFloat(s);
      if (isNaN(score)) return alert("Điểm không hợp lệ");

      await RecruiterApi.scoreSubmission(submission.id, score);
      alert("Chấm bài thành công");
      await loadApplications();
    } catch (e) {
      console.error("Score exam error:", e);
      alert("Không thể chấm bài: " + (e.response?.data?.message || e.message));
    }
  }

  if (loading) return <div>Loading...</div>;

  return (
    <div className="recruiter-table-wrapper">
      <h1>📄 Applications</h1>
      {applications.length === 0 ? (
        <div>Không có applications</div>
      ) : (
        <table className="recruiter-table">
          <thead>
            <tr>
              <th>Candidate</th>
              <th>Email</th>
              <th>Job</th>
              <th>Status</th>
              <th>Exam</th>
              <th>Interview</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {applications.map((app) => (
              <tr key={getAppId(app)}>
                <td>{getCandidateName(app)}</td>
                <td>{getCandidateEmail(app)}</td>
                <td>{getJobTitle(app)}</td>
                <td>
                  <span className={`status-badge status-${getStatus(app).toLowerCase()}`}>
                    {getStatus(app)}
                  </span>
                </td>
                <td>{getExamScore(app)}</td>
                <td>{getInterviewScore(app)}</td>
                <td className="action-buttons">
                  <select value={getStatus(app)} onChange={(e) => changeStatus(app, e.target.value)}>
                    {statusOptions.map((s) => (
                      <option key={s} value={s}>
                        {s}
                      </option>
                    ))}
                  </select>
                  <button onClick={() => scheduleInterview(app)}>Schedule</button>
                  <button onClick={() => scoreInterview(app)}>Score Interview</button>
                  <button onClick={() => scoreExam(app)}>Score Exam</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
