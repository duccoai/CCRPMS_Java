// src/components/recruiter/JobForm.jsx
import React, { useState, useEffect } from 'react';
import RecruiterApi from '../../services/recruiterApi';

const JobForm = ({ initial = null, onClose }) => {
  const [form, setForm] = useState({
    title: '',
    description: '',
    location: '',
    salaryRange: '',
    status: 'OPEN'
  });
  const isEdit = !!initial;

  useEffect(() => {
    if (initial) {
      setForm({
        title: initial.title || '',
        description: initial.description || '',
        location: initial.location || '',
        salaryRange: initial.salaryRange || '',
        status: initial.status || 'OPEN'
      });
    }
  }, [initial]);

  function onChange(e) {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  }

  async function submit(e) {
    e.preventDefault();
    try {
      // include recruiterId from localStorage user object if not set
      const user = JSON.parse(localStorage.getItem('user') || 'null');
      const payload = {
        title: form.title,
        description: form.description,
        location: form.location,
        salaryRange: form.salaryRange,
        status: form.status,
        recruiterId: form.recruiterId || user?.id
      };

      if (isEdit) {
        await RecruiterApi.updateJob(initial.id, payload);
      } else {
        await RecruiterApi.createJob(payload);
      }
      onClose();
    } catch (err) {
      console.error(err);
      alert('Lỗi khi lưu job: ' + (err?.response?.data?.message || err.message));
    }
  }


  return (
    <div style={{ border: '1px solid #ddd', padding: 12, marginBottom: 12 }}>
      <h3>{isEdit ? 'Edit Job' : 'Create Job'}</h3>
      <form onSubmit={submit}>
        <div>
          <label>Title</label><br />
          <input name="title" value={form.title} onChange={onChange} required />
        </div>
        <div>
          <label>Description</label><br />
          <textarea name="description" value={form.description} onChange={onChange} rows={6} />
        </div>
        <div>
          <label>Location</label><br />
          <input name="location" value={form.location} onChange={onChange} />
        </div>
        <div>
          <label>Salary</label><br />
          <input name="salaryRange" value={form.salaryRange} onChange={onChange} />
        </div>
        <div>
          <label>Status</label><br />
          <select name="status" value={form.status} onChange={onChange}>
            <option value="OPEN">OPEN</option>
            <option value="CLOSED">CLOSED</option>
          </select>
        </div>
        <div style={{ marginTop: 8 }}>
          <button type="submit">Save</button>
          <button type="button" onClick={() => onClose()} style={{ marginLeft: 8 }}>Cancel</button>
        </div>
      </form>
    </div>
  );
};

export default JobForm;
