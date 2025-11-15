import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import api from "../../api/api";

export default function Login() {
  const [role, setRole] = useState("CANDIDATE");
  const [form, setForm] = useState({ username: "", password: "" });
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      localStorage.removeItem("token");
      localStorage.removeItem("user");

      const res = await api.post("/auth/login", form);
      const data = res.data || {};

      const token =
        data.token ||
        data.accessToken ||
        data.jwt ||
        (data?.body && data.body.token);

      if (!token) {
        setError("Server không trả token.");
        setLoading(false);
        return;
      }

      let user = data.user || null;

      if (!user && (data.userId || data.username || data.role)) {
        user = {
          id: data.userId,
          username: data.username,
          fullName: data.fullName || data.username || "",
          role: { name: data.role }
        };
      }

      if (!user) {
        user = { username: data.username || form.username, role: { name: role } };
      }

      // 🔥 Chỉ giữ một biến backendRole duy nhất
      const backendRole = (user.role?.name || "").toUpperCase();
      const selectedRole = role.toUpperCase();

      if (backendRole !== selectedRole) {
        setError(`Vui lòng đăng nhập bằng tài khoản có role: ${selectedRole}`);
        setLoading(false);
        return;
      }

      localStorage.setItem("token", token);
      localStorage.setItem("user", JSON.stringify(user));
      localStorage.setItem("username", user.username || user.fullName || "");
      localStorage.setItem("userId", user.id ? String(user.id) : "");
      localStorage.setItem("role", backendRole);

      // 🔥 Redirect theo role chuẩn
      if (backendRole === "ADMIN") navigate("/admin");
      else if (backendRole === "RECRUITER") navigate("/recruiter/dashboard");
      else if (backendRole === "CANDIDATE") navigate("/candidate/jobs");
      else navigate("/");

    } catch (err) {
      console.error("Login error:", err);
      const msg =
        err.response?.data?.message ||
        err.response?.data?.error ||
        err.message ||
        "Đăng nhập thất bại";
      setError(msg);
    } finally {
      setLoading(false);
    }
  }


  return (
    <div style={styles.container}>
      <h2>Đăng nhập</h2>

      {/* Role selector */}
      <div style={{ marginBottom: 15 }}>
        <label>
          <input
            type="radio"
            value="CANDIDATE"
            checked={role === "CANDIDATE"}
            onChange={() => setRole("CANDIDATE")}
          />
          Candidate
        </label>

        <label style={{ marginLeft: 10 }}>
          <input
            type="radio"
            value="RECRUITER"
            checked={role === "RECRUITER"}
            onChange={() => setRole("RECRUITER")}
          />
          Recruiter
        </label>

        <label style={{ marginLeft: 10 }}>
          <input
            type="radio"
            value="ADMIN"
            checked={role === "ADMIN"}
            onChange={() => setRole("ADMIN")}
          />
          Admin
        </label>
      </div>

      <form onSubmit={handleSubmit} style={styles.form}>
        <input
          placeholder="Username"
          value={form.username}
          onChange={(e) => setForm({ ...form, username: e.target.value })}
        />
        <input
          type="password"
          placeholder="Password"
          value={form.password}
          onChange={(e) => setForm({ ...form, password: e.target.value })}
        />

        {error && <div style={{ color: "red" }}>{error}</div>}

        <button type="submit" disabled={loading}>
          {loading ? "Đang đăng nhập..." : "Đăng nhập"}
        </button>
      </form>

      <p>
        Chưa có tài khoản? <Link to="/register">Đăng ký</Link>
      </p>
    </div>
  );
}

const styles = {
  container: { padding: 20, maxWidth: 400, margin: "40px auto", textAlign: "center" },
  form: { display: "flex", flexDirection: "column", gap: 10 },
};
