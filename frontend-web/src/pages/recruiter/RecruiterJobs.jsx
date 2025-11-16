import React, { useEffect, useState } from 'react';
import RecruiterApi from '../../services/recruiterApi';
import JobForm from '../../components/recruiter/JobForm';
import { Link } from 'react-router-dom';
import './RecruiterDashboard.css'; // reuse CSS for sidebar and layout

export default function RecruiterJobs() {
  const [jobs, setJobs] = useState([]);
  const [editing, setEditing] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [loading, setLoading] = useState(false);

  useEffect(() => { loadJobs(); }, []);

  async function loadJobs() {
    setLoading(true);
    try {
      const res = await RecruiterApi.getMyJobs();
      setJobs(res.data || []);
    } catch (e) {
      console.error('Load jobs error:', e);
      alert('Lỗi khi tải jobs. Kiểm tra console.');
    } finally {
      setLoading(false);
    }
  }

  function handleCreate() {
    setEditing(null);
    setShowForm(true);
  }

  function handleEdit(job) {
    setEditing(job);
    setShowForm(true);
  }

  async function handleDelete(id) {
    if (!window.confirm('Bạn có chắc muốn xóa job này?')) return;
    try {
      await RecruiterApi.deleteJob(id);
      loadJobs();
    } catch (e) {
      console.error('Delete job error:', e);
      alert('Xóa thất bại');
    }
  }

  return (
    <div className="recruiter-dashboard">
      {/* Sidebar */}
      <aside className="sidebar">
        <h2>Menu</h2>
        <ul>
          <li><Link to="/recruiter/dashboard">Dashboard</Link></li>
          <li><Link to="/recruiter/applications">Applications</Link></li>
          <li><Link to="/recruiter/jobs">Jobs</Link></li>
        </ul>
      </aside>

      {/* Main content */}
      <main className="dashboard-main">
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <h1>Manage Jobs</h1>
          <button onClick={handleCreate}>Create Job</button>
        </div>

        {showForm && (
          <JobForm
            initial={editing}
            onClose={() => { setShowForm(false); loadJobs(); }}
          />
        )}

        {loading ? (
          <div>Loading jobs...</div>
        ) : jobs.length === 0 ? (
          <p>No jobs yet.</p>
        ) : (
          <div className="jobs-list">
            {jobs.map(job => (
              <div key={job.id} className="job-card">
                <h3>{job.title} <small>({job.status})</small></h3>
                <p>{job.description?.slice(0, 200)}</p>
                <div className="job-actions">
                  <button onClick={() => handleEdit(job)}>Edit</button>
                  <button onClick={() => handleDelete(job.id)}>Delete</button>
                </div>
              </div>
            ))}
          </div>
        )}
      </main>
    </div>
  );
}
