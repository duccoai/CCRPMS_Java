import React from "react";
import { Navigate } from "react-router-dom";

export default function RequireRole({ children, role }) {
  const token = localStorage.getItem("token");
  const user = JSON.parse(localStorage.getItem("user") || "null");

  if (!token) return <Navigate to="/login" replace />;

  if (!user || user.role?.name !== role) {
    return <Navigate to="/" replace />; // nếu role không đúng, về candidate dashboard
  }

  return children;
}
