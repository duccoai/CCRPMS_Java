import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import api from "../../api/api";
import "./Auth.css";

export default function Register() {
  const [form, setForm] = useState({ username: "", password: "", email: "" });
  const [error, setError] = useState("");
  const navigate = useNavigate();

  async function handleSubmit(e) {
    e.preventDefault();
    try {
      await api.post("/auth/register", form);
      alert("Đăng ký thành công! Vui lòng đăng nhập.");
      navigate("/login");
    } catch (err) {
      console.error(err);
      setError(err.response?.data?.message || "Đăng ký thất bại");
    }
  }

  return (
    <div className="auth-container">
      <div className="auth-card">
        <h2 className="auth-title">Đăng ký</h2>
        <p className="auth-subtitle">Create your account</p>

        <form onSubmit={handleSubmit}>
          <div className="auth-input">
            <label>Username</label>
            <input
              placeholder="Username"
              value={form.username}
              onChange={e => setForm({ ...form, username: e.target.value })}
            />
          </div>

          <div className="auth-input">
            <label>Email</label>
            <input
              type="email"
              placeholder="Email"
              value={form.email}
              onChange={e => setForm({ ...form, email: e.target.value })}
            />
          </div>

          <div className="auth-input">
            <label>Password</label>
            <input
              type="password"
              placeholder="Password"
              value={form.password}
              onChange={e => setForm({ ...form, password: e.target.value })}
            />
          </div>

          {error && <div className="auth-error">{error}</div>}

          <button className="auth-btn" type="submit">Đăng ký</button>
        </form>

        <p className="auth-switch">
          Đã có tài khoản?
          <Link to="/login"> Đăng nhập</Link>
        </p>
      </div>
    </div>
  );
}
