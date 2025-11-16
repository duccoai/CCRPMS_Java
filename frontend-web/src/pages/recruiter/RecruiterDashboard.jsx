import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import RecruiterApi from "../../services/recruiterApi";
import "./RecruiterDashboard.css";

export default function RecruiterDashboard() {
  const [jobs, setJobs] = useState([]);
  const [meta, setMeta] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    loadJobs();
  }, []);

  async function loadJobs() {
    setLoading(true);
    setError("");
    try {
      let res = await RecruiterApi.getMyJobs();
      let data = res.data;

      if (typeof data === "string") {
        try { data = JSON.parse(data); } 
        catch (e) { setError("Unexpected server response"); setJobs([]); setMeta({ raw: data }); return; }
      }

      let arr = [], metadata = null;
      if (Array.isArray(data)) arr = data;
      else if (Array.isArray(data.jobs)) { arr = data.jobs; metadata = { ...data }; delete metadata.jobs; }
      else { arr = []; metadata = { raw: data }; }

      setJobs(arr);
      setMeta(metadata);
    } catch (e) {
      console.error("Load recruiter dashboard error", e);
      setError("Lỗi khi tải data");
      setJobs([]);
    } finally { setLoading(false); }
  }

  const myJobsCount = jobs.length;
  const applicationsCount = jobs.reduce((acc, j) => acc + (Array.isArray(j.applications) ? j.applications.length : 0), 0);
  const openPositions = jobs.filter(j => (j.status || "").toUpperCase() === "OPEN").length;

  if (loading) return <div className="loading">Loading...</div>;

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
        <h1>Recruiter Dashboard</h1>
        {error && <div className="error">{error}</div>}

        <div className="cards-grid">
          <div className="card">
            <h3>My Jobs</h3>
            <div className="card-value">{myJobsCount}</div>
          </div>
          <div className="card">
            <h3>Applications</h3>
            <div className="card-value">{applicationsCount}</div>
          </div>
          <div className="card">
            <h3>Open Positions</h3>
            <div className="card-value">{openPositions}</div>
          </div>
          <div className="card">
            <h3>Recent Jobs</h3>
            <div className="card-list">
              {jobs.slice(0, 5).map(j => <div key={j.id || j.title}>{j.title}</div>)}
              {jobs.length === 0 && <div>No recent jobs</div>}
            </div>
          </div>
        </div>

        {meta && (
          <div className="meta-debug">
            <pre>{JSON.stringify(meta, null, 2)}</pre>
          </div>
        )}
      </main>
    </div>
  );
}
