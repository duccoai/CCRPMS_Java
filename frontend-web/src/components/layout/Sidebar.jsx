// src/components/layout/Sidebar.jsx
import React from "react";
import { Link, useLocation } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";

export default function Sidebar() {
  const auth = useAuth();
  const user = auth?.user;
  const location = useLocation();

  if (!user) return null; // tránh crash khi chưa load user

  const activeClass = (path) => location.pathname.startsWith(path) ? "active" : "";

  return (
    <aside className="sidebar" style={{ width: "200px", background: "#f5f5f5", padding: "10px" }}>
      <ul style={{ listStyle: "none", padding: 0 }}>
        <li><Link to="/admin/dashboard">Dashboard</Link></li>
        <li><Link to="/admin/users">Người dùng</Link></li>
        <li><Link to="/admin/applications">Hồ sơ ứng tuyển</Link></li>
        <li><Link to="/admin/interviews">Phỏng vấn</Link></li>
        <li><Link to="/admin/exams">Bài thi</Link></li>
        <li><Link to="/admin/reports">Báo cáo</Link></li>
      </ul>
    </aside>
  );
}