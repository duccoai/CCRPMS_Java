// src/pages/recruiter/RecruiterDashboard.jsx
import React, { useEffect, useState } from "react";
import RecruiterApi from "../../services/recruiterApi";

export default function RecruiterDashboard() {
  const [jobs, setJobs] = useState([]);           // ensure default is array
  const [meta, setMeta] = useState(null);         // optional metadata from API
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    load();
  }, []);

  // inside RecruiterDashboard.jsx (replace load function with this)
  async function load() {
    setLoading(true);
    setError("");
    try {
      const res = await RecruiterApi.getMyJobs();
      console.log("getMyJobs response:", res.data);

      // If backend returned a JSON string, parse it
      let data = res.data;
      if (typeof data === "string") {
        // trim to be safe and try parse
        const s = data.trim();
        try {
          // some backends double-encode => attempt parse repeatedly until object/array
          let parsed = JSON.parse(s);
          // if parsed is still a string, try parse again
          if (typeof parsed === "string") {
            parsed = JSON.parse(parsed);
          }
          data = parsed;
        } catch (e) {
          console.error("Failed to parse jobs JSON string:", e, data);
          setError("Unexpected response shape from server (see console).");
          setJobs([]);
          setMeta({ raw: data });
          return;
        }
      }

      // Normalize to array (support common shapes)
      let arr = [];
      let metadata = null;
      if (Array.isArray(data)) {
        arr = data;
      } else if (data == null) {
        arr = [];
      } else if (Array.isArray(data.jobs)) {
        arr = data.jobs;
        metadata = { ...data };
        delete metadata.jobs;
      } else if (Array.isArray(data.data)) {
        arr = data.data;
        metadata = { ...data };
        delete metadata.data;
      } else {
        // find first array value
        const arrayProp = Object.keys(data).find(k => Array.isArray(data[k]));
        if (arrayProp) {
          arr = data[arrayProp];
          metadata = { ...data };
          delete metadata[arrayProp];
        } else {
          // fallback: unknown shape
          console.warn("Unknown jobs response shape:", data);
          setError("Unexpected response shape from server (see console).");
          arr = [];
          metadata = { raw: data };
        }
      }

      setJobs(arr);
      setMeta(metadata);
    } catch (e) {
      console.error("Load recruiter dashboard error", e);
      setError("Lỗi khi tải data. Kiểm tra console.");
      setJobs([]);
    } finally {
      setLoading(false);
    }
  }


  // safe getters
  const myJobsCount = Array.isArray(jobs) ? jobs.length : 0;
  // applications: if each job has applications array, sum them; else try meta.totalApplications
  const applicationsCount = Array.isArray(jobs)
    ? jobs.reduce((acc, j) => acc + (Array.isArray(j.applications) ? j.applications.length : 0), 0)
    : (meta?.totalApplications ?? 0);

  // open positions: count jobs with status OPEN (adapt to your job.status path)
  const openPositions = Array.isArray(jobs)
    ? jobs.filter(j => (j.status || "").toString().toUpperCase() === "OPEN").length
    : (meta?.openPositions ?? 0);

  if (loading) return <div>Loading...</div>;

  return (
    <div style={{ padding: 16 }}>
      <h1>Recruiter Dashboard</h1>

      {error && <div style={{ color: "red" }}>{error}</div>}

      <div style={{ display: "grid", gridTemplateColumns: "repeat(4, 1fr)", gap: 12, marginTop: 12 }}>
        <div style={{ padding: 12, border: "1px solid #ddd" }}>
          <h3>My Jobs</h3>
          <div style={{ fontSize: 24 }}>{myJobsCount}</div>
        </div>

        <div style={{ padding: 12, border: "1px solid #ddd" }}>
          <h3>Applications</h3>
          <div style={{ fontSize: 24 }}>{applicationsCount}</div>
        </div>

        <div style={{ padding: 12, border: "1px solid #ddd" }}>
          <h3>Open Positions</h3>
          <div style={{ fontSize: 24 }}>{openPositions}</div>
        </div>

        <div style={{ padding: 12, border: "1px solid #ddd" }}>
          <h3>Recent Jobs</h3>
          <div style={{ fontSize: 14 }}>
            {Array.isArray(jobs) && jobs.length > 0 ? (
              jobs.slice(0, 5).map(j => <div key={j.id || j.title}>{j.title}</div>)
            ) : (
              <div>No recent jobs</div>
            )}
          </div>
        </div>
      </div>

      {/* optional: show raw meta for debugging */}
      {meta && (
        <div style={{ marginTop: 16, fontSize: 12, color: "#666" }}>
          <pre style={{ whiteSpace: "pre-wrap" }}>{JSON.stringify(meta, null, 2)}</pre>
        </div>
      )}
    </div>
  );
}
