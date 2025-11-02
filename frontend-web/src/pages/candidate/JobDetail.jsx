import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../../api/api";

export default function JobDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [job, setJob] = useState(null);
  const [loading, setLoading] = useState(true);
  const userId = Number(localStorage.getItem("userId") || 1); // default 1

  useEffect(() => {
    api.get(`/jobs/${id}`)
      .then(res => setJob(res.data))
      .catch(console.error)
      .finally(() => setLoading(false));
  }, [id]);

  function handleApply() {
    // controller hiện tại dùng POST /api/applications/submit/{userId}/{jobId}
    api.post(`/applications/submit/${userId}/${id}`)
      .then(res => {
        alert("Nộp hồ sơ thành công!");
        navigate("/applications");
      })
      .catch(err => {
        console.error(err);
        alert("Nộp hồ sơ thất bại: " + (err?.response?.data?.message || err.message));
      });
  }

  if (loading) return <div style={{padding:20}}>Loading…</div>;
  if (!job) return <div style={{padding:20}}>Job not found</div>;

  return (
    <div style={{ padding: 20 }}>
      <h2>{job.title}</h2>
      <p>{job.description}</p>
      <p><b>Location:</b> {job.location || "—"}</p>
      <p><b>Salary:</b> {job.salaryRange || "—"}</p>
      <button onClick={handleApply}>Nộp hồ sơ</button>
    </div>
  );
}
