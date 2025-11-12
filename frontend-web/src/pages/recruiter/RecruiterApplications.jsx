// src/pages/recruiter/RecruiterApplications.jsx
import React, { useEffect, useState } from 'react';
import RecruiterApi from '../../services/recruiterApi';

const statusOptions = ['PENDING', 'INTERVIEWING', 'APPROVED', 'REJECTED', 'HIRED'];

const RecruiterApplications = () => {
  const [applications, setApplications] = useState([]);

  useEffect(() => { load(); }, []);

  async function load() {
    try {
      const res = await RecruiterApi.getApplications();
      setApplications(res.data || []);
    } catch (e) {
      console.error(e);
    }
  }

  async function updateStatus(appId, status) {
    try {
      await RecruiterApi.updateApplicationStatus(appId, { status });
      load();
    } catch (e) {
      console.error(e);
      alert('Cập nhật thất bại');
    }
  }

  return (
    <div>
      <h1>Applications</h1>
      <table style={{ width: '100%', borderCollapse: 'collapse' }}>
        <thead>
          <tr>
            <th>Candidate</th>
            <th>Job</th>
            <th>Status</th>
            <th>Score</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {applications.map(app => (
            <tr key={app.id} style={{ borderTop: '1px solid #eee' }}>
              <td>{app.user?.fullName || app.user?.username}</td>
              <td>{app.job?.title}</td>
              <td>{app.status}</td>
              <td>{app.interviewScore ?? app.examScore ?? '-'}</td>
              <td>
                <select
                  defaultValue={app.status}
                  onChange={(e) => updateStatus(app.id, e.target.value)}
                >
                  {statusOptions.map(s => <option value={s} key={s}>{s}</option>)}
                </select>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default RecruiterApplications;
