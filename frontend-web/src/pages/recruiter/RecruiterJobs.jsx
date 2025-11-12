// src/pages/recruiter/RecruiterJobs.jsx
import React, { useEffect, useState } from 'react';
import RecruiterApi from '../../services/recruiterApi';
import JobForm from '../../components/recruiter/JobForm';

const RecruiterJobs = () => {
  const [jobs, setJobs] = useState([]);
  const [editing, setEditing] = useState(null); // job to edit
  const [showForm, setShowForm] = useState(false);

  useEffect(() => { load(); }, []);

  async function load() {
    try {
      const res = await RecruiterApi.getMyJobs();
      setJobs(res.data || []);
    } catch (e) {
      console.error(e);
    }
  }

  function handleCreateClick() {
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
      load();
    } catch (e) {
      console.error(e);
      alert('Xóa thất bại');
    }
  }

  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h1>Manage Jobs</h1>
        <button onClick={handleCreateClick}>Create Job</button>
      </div>

      {showForm && (
        <JobForm
          initial={editing}
          onClose={() => { setShowForm(false); load(); }}
        />
      )}

      <div style={{ marginTop: 16 }}>
        {jobs.length === 0 && <p>No jobs yet.</p>}
        {jobs.map(job => (
          <div key={job.id} style={{ border: '1px solid #ddd', padding: 12, marginBottom: 8 }}>
            <h3>{job.title} <small>({job.status})</small></h3>
            <p>{job.description?.slice(0, 200)}</p>
            <div style={{ display: 'flex', gap: 8 }}>
              <button onClick={() => handleEdit(job)}>Edit</button>
              <button onClick={() => handleDelete(job.id)}>Delete</button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default RecruiterJobs;
