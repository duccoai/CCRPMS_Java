import { useEffect, useState } from "react";
import api from "../../api/api";
import { Link } from "react-router-dom";

export default function JobList() {
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api.get("/jobs")
      .then(res => setJobs(res.data || []))
      .catch(err => {
        console.error(err);
        setJobs([]);
      })
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <div style={{padding:20}}>Loading jobs…</div>;
  if (jobs.length === 0) return <div style={{padding:20}}>No jobs found.</div>;

  return (
    <div style={{ padding: 20 }}>
      <h2>Danh sách việc làm</h2>
      <div style={{ display: "grid", gap: 12, marginTop: 12 }}>
        {jobs.map(job => (
          <div key={job.id} style={{ border: "1px solid #e6e6e6", padding: 12, borderRadius: 8 }}>
            <h3 style={{ margin: 0 }}>{job.title}</h3>
            <p style={{ margin: "6px 0" }}>{job.description?.slice(0, 200)}{job.description?.length > 200 ? "…" : ""}</p>
            <div style={{ display: "flex", gap: 8, alignItems: "center" }}>
              <div style={{ color: "#666" }}>{job.location || "—"}</div>
              <div style={{ color: "#666" }}>{job.salaryRange || "—"}</div>
              <Link to={`/jobs/${job.id}`} style={{ marginLeft: "auto", textDecoration: "none" }}>
                <button>Chi tiết</button>
              </Link>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
