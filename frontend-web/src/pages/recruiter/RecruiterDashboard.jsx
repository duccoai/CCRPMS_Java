// src/pages/recruiter/RecruiterDashboard.jsx
import React, { useEffect, useState } from 'react';
import RecruiterApi from '../../services/recruiterApi';
import CardJob from '../../components/dashboard/CardJob';
import CardExam from '../../components/dashboard/CardExam';
import CardStatus from '../../components/dashboard/CardStatus';

const RecruiterDashboard = () => {
  const [jobs, setJobs] = useState([]);
  const [applications, setApplications] = useState([]);

  useEffect(() => {
    loadData();
  }, []);

  async function loadData() {
    try {
      const [jobsRes, appsRes] = await Promise.all([
        RecruiterApi.getMyJobs(),
        RecruiterApi.getApplications()
      ]);
      setJobs(jobsRes.data || []);
      setApplications(appsRes.data || []);
    } catch (err) {
      console.error('Load recruiter dashboard error', err);
    }
  }

  return (
    <div>
      <h1>Recruiter Dashboard</h1>
      <div style={{ display: 'flex', gap: 12, marginBottom: 16 }}>
        <CardJob title="My Jobs" value={jobs.length} />
        <CardExam title="Applications" value={applications.length} />
        <CardStatus title="Open Positions" value={jobs.filter(j => j.status === 'OPEN').length} />
      </div>

      <section>
        <h2>Recent Jobs</h2>
        <div>
          {jobs.map(job => (
            <div key={job.id} style={{ border: '1px solid #eee', padding: 12, marginBottom: 8 }}>
              <h3>{job.title}</h3>
              <p>{job.description?.slice(0, 180)}...</p>
              <small>{job.location} • {job.salaryRange}</small>
            </div>
          ))}
        </div>
      </section>
    </div>
  );
};

export default RecruiterDashboard;
