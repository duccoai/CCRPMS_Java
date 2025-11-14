import React, { useEffect, useState } from 'react';
import RecruiterApi from '../../services/recruiterApi';

const statusOptions = ['PENDING', 'INTERVIEWING', 'APPROVED', 'REJECTED', 'HIRED'];

export default function RecruiterApplications() {
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    load();
  }, []);

  async function load() {
    setLoading(true);
    try {
      const res = await RecruiterApi.getApplications();
      setApplications(res.data || []);
    } catch (e) {
      console.error('load applications error', e);
      alert('Lỗi khi tải applications. Kiểm tra console.');
    } finally {
      setLoading(false);
    }
  }

  function getAppId(app) { return app.applicationId || app.id; }
  function getCandidateName(app) { return app.candidateFullName || app.candidateUsername || (app.user && (app.user.fullName || app.user.username)) || '—'; }
  function getCandidateEmail(app) { return app.candidateEmail || (app.user && app.user.email) || '—'; }
  function getJobTitle(app) { return app.jobTitle || (app.job && app.job.title) || '—'; }
  function getStatus(app) { return app.status || 'PENDING'; }
  function getExamScore(app) { return (app.examScore !== undefined && app.examScore !== null) ? app.examScore : '-'; }
  function getInterviewScore(app) { return (app.interviewScore !== undefined && app.interviewScore !== null) ? app.interviewScore : '-'; }

  async function changeStatus(app, status) {
    const appId = getAppId(app);
    if (!appId) return alert('Application ID không xác định');
    if (!window.confirm(`Đặt trạng thái ${status}?`)) return;
    try {
      await RecruiterApi.updateApplicationStatus(appId, status);
      await load();
    } catch (e) {
      console.error('Update status error:', e);
      alert('Cập nhật thất bại');
    }
  }

  async function scheduleInterview(app) {
    const appId = getAppId(app);
    if (!appId) return alert('Application ID không xác định');

    const iso = prompt('Nhập thời gian (ví dụ 2025-11-20T14:00:00Z)', '');
    const location = prompt('Nhập địa điểm', '');
    const note = prompt('Ghi chú', '');
    if (!iso || !location) return;

    try {
      await RecruiterApi.scheduleInterview({
        applicationId: appId,
        schedule: iso,
        location,
        note,
        status: 'INTERVIEWING'
      });
      alert('Lên lịch thành công');
      await load();
    } catch (e) {
      console.error('Schedule interview error:', e);
      // show server message if available
      const msg = e.response?.data?.message || e.message || e.toString();
      alert('Lên lịch thất bại: ' + msg);
    }
  }

  async function scoreInterview(app) {
    // need interviewId
    const interviewId = app.interviewId || app.interview?.id;
    if (!interviewId) return alert('Không tìm thấy interviewId cho application này (hãy schedule trước)');

    const s = prompt('Nhập điểm phỏng vấn (VD: 8.5)', '');
    const score = parseFloat(s);
    if (isNaN(score)) return alert('Điểm không hợp lệ');

    try {
      await RecruiterApi.scoreInterview(interviewId, score, '');
      alert('Chấm điểm phỏng vấn thành công');
      await load();
    } catch (e) {
      console.error('Score interview error:', e);
      const msg = e.response?.data?.message || e.message || e.toString();
      alert('Chấm điểm thất bại: ' + msg);
    }
  }

  async function viewSubmissionAndScore(app) {
    const appId = getAppId(app);
    if (!appId) return alert('Application ID không xác định');

    try {
      const res = await RecruiterApi.getSubmissionByApplication(appId);
      const submission = res.data;
      if (!submission) {
        return alert('Không tìm thấy submission cho application này.');
      }

      // show answers if any
      if (submission.answers) {
        try {
          // pretty print if answers is an object
          const text = typeof submission.answers === 'string' ? submission.answers : JSON.stringify(submission.answers, null, 2);
          // show in window (simple) — you can replace by modal
          alert('Answers:\n' + text);
        } catch (e) {
          console.warn('Cannot show answers:', e);
        }
      } else {
        alert('Không có đáp án (answers) lưu trong submission. Bạn vẫn có thể chấm điểm.');
      }

      // prompt score
      const s = prompt('Nhập điểm cho bài thi (VD: 7.5)', submission.score ?? '');
      const score = parseFloat(s);
      if (isNaN(score)) return alert('Điểm không hợp lệ');

      // call score API
      await RecruiterApi.scoreSubmission(submission.id, score);
      alert('Chấm bài thành công');
      await load();
    } catch (e) {
      console.error('Get/score submission error:', e);
      const msg = e.response?.data?.message || e.message || e.toString();
      alert('Không tìm thấy submission cho application này hoặc lỗi server: ' + msg);
    }
  }

  if (loading) return <div>Loading...</div>;

  return (
    <div style={{ padding: 16 }}>
      <h1>Applications</h1>
      {applications.length === 0 ? <div>Không có applications</div> :
        <table style={{ width: '100%', borderCollapse: 'collapse' }}>
          <thead style={{ background: '#fafafa' }}>
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
            {applications.map(app => (
              <tr key={getAppId(app)}>
                <td>{getCandidateName(app)}</td>
                <td>{getCandidateEmail(app)}</td>
                <td>{getJobTitle(app)}</td>
                <td>{getStatus(app)}</td>
                <td>{getExamScore(app)}</td>
                <td>{getInterviewScore(app)}</td>
                <td style={{ display: 'flex', gap: 8 }}>
                  <select value={getStatus(app)} onChange={e => changeStatus(app, e.target.value)}>
                    {statusOptions.map(s => <option key={s} value={s}>{s}</option>)}
                  </select>
                  <button onClick={() => scheduleInterview(app)}>Schedule</button>
                  <button onClick={() => scoreInterview(app)}>Score Interview</button>
                  <button onClick={() => viewSubmissionAndScore(app)}>Score Exam</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      }
    </div>
  );
}
