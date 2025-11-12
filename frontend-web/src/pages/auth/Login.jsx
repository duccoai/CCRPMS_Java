// src/pages/auth/Login.jsx
import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import api from "../../api/api";

export default function Login() {
  const [form, setForm] = useState({ username: "", password: "" });
  const [error, setError] = useState("");
  const navigate = useNavigate();

async function handleSubmit(e) {
  e.preventDefault();
  try {
    const res = await api.post("/auth/login", form);
    const { token, user } = res.data; // nhấn mạnh: user là object
    localStorage.setItem("token", token);
    localStorage.setItem("user", JSON.stringify(user)); // ✅ lưu cả object
    // tự động redirect dựa trên role
    if (user.role?.name === "RECRUITER") {
      navigate("/recruiter/dashboard");
    } else {
      navigate("/");
    }
  } catch (err) {
    console.error(err);
    setError(err.response?.data?.message || "Đăng nhập thất bại");
  }
}



  return (
    <div style={styles.container}>
      <h2>Đăng nhập</h2>
      <form onSubmit={handleSubmit} style={styles.form}>
        <input
          placeholder="Username"
          value={form.username}
          onChange={e => setForm({ ...form, username: e.target.value })}
        />
        <input
          type="password"
          placeholder="Password"
          value={form.password}
          onChange={e => setForm({ ...form, password: e.target.value })}
        />
        {error && <div style={{ color: "red" }}>{error}</div>}
        <button type="submit">Đăng nhập</button>
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
