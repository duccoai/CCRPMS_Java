import { useEffect, useState } from "react";
import api from "../../api/api";

export default function Profile() {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Lấy user từ localStorage
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      setUser(JSON.parse(storedUser));
      setLoading(false);
    } else {
      // fallback: call backend /me
      api.get("/users/me")
        .then(res => setUser(res.data))
        .catch(console.error)
        .finally(() => setLoading(false));
    }
  }, []);

  if (loading) return <div>Loading…</div>;
  if (!user) return <div>Không tìm thấy user</div>;

  return (
    <div style={{ padding: 20 }}>
      <h2>Hồ sơ cá nhân</h2>
      <div><b>Username:</b> {user.username}</div>
      <div><b>Họ và tên:</b> {user.fullName || "—"}</div>
      <div><b>Email:</b> {user.email || "—"}</div>
      <div><b>Bio:</b> {user.bio || ""}</div>
      {/* Avatar / CV logic */}
    </div>
  );
}
