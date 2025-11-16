// src/pages/candidate/Profile.jsx
import { useEffect, useState } from "react";
import api from "../../api/api";
import "./Candidate.css";

export default function Profile() {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) { setUser(JSON.parse(storedUser)); setLoading(false); }
    else {
      api.get("/users/me").then(res => setUser(res.data)).catch(console.error).finally(() => setLoading(false));
    }
  }, []);

  if (loading) return <div className="candidate-container">Loading…</div>;
  if (!user) return <div className="candidate-container">Không tìm thấy user</div>;

  return (
    <div className="candidate-container">
      <h2>Hồ sơ cá nhân</h2>
      <div><b>Username:</b> {user.username}</div>
      <div><b>Họ và tên:</b> {user.fullName || "—"}</div>
      <div><b>Email:</b> {user.email || "—"}</div>
      <div><b>Bio:</b> {user.bio || ""}</div>
    </div>
  );
}
