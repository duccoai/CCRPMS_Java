// src/pages/candidate/JobList.jsx
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api from "../../api/api";
import "./Candidate.css";

export default function JobList() {
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);

  // Lấy danh sách công việc từ API
  useEffect(() => {
    api.get("/jobs")
      .then(res => setJobs(res.data || []))
      .catch(err => {
        console.error(err);
        setJobs([]);
      })
      .finally(() => setLoading(false));
  }, []);

  if (loading) {
    return <div className="candidate-container">Loading jobs…</div>;
  }

  if (!jobs.length) {
    return <div className="candidate-container">Không có công việc nào.</div>;
  }

  return (
    <div className="candidate-container">
      <h2>Danh sách việc làm</h2>
      <div className="job-grid">
        {jobs.map(job => (
          <div key={job.id} className="job-card">
            <h3>{job.title}</h3>
            <p>
              {job.description?.slice(0, 200)}
              {job.description?.length > 200 ? "…" : ""}
            </p>
            <div className="job-meta">
              <span>{job.location || "—"}</span>
              <span>{job.salaryRange || "—"}</span>
              <Link to={`/jobs/${job.id}`}>
                <button className="candidate-btn">Chi tiết</button>
              </Link>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
