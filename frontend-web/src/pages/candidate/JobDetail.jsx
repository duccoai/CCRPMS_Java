// src/pages/candidate/JobDetail.jsx
import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../../api/api";
import "./Candidate.css";

export default function JobDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [job, setJob] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api.get(`/jobs/${id}`)
      .then(res => setJob(res.data))
      .catch(console.error)
      .finally(() => setLoading(false));
  }, [id]);

  function handleApply() {
    api.post(`/applications/submit/${id}`)
      .then(() => {
        alert("Nộp hồ sơ thành công!");
        navigate("/applications");
      })
      .catch(err => {
        console.error(err);
        alert("Nộp hồ sơ thất bại: " + (err?.response?.data?.message || err.message));
      });
  }

  if (loading) return <div className="candidate-container">Loading…</div>;
  if (!job) return <div className="candidate-container">Job not found</div>;

  return (
    <div className="candidate-container">
      <h2>{job.title}</h2>
      <p>{job.description}</p>
      <p><b>Location:</b> {job.location || "—"}</p>
      <p><b>Salary:</b> {job.salaryRange || "—"}</p>
      <button className="candidate-btn" onClick={handleApply}>Nộp hồ sơ</button>
    </div>
  );
}
