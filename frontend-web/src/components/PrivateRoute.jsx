import React from "react";
import { Navigate } from "react-router-dom";

export default function PrivateRoute({ children, allowedRole }) {
  const token = localStorage.getItem("token");
  const user = JSON.parse(localStorage.getItem("user") || "{}");

  if (!token) return <Navigate to="/login" replace />;

  if (allowedRole && user.role?.name !== allowedRole) {
    if (user.role?.name === "RECRUITER") return <Navigate to="/recruiter/dashboard" replace />;
    return <Navigate to="/" replace />;
  }

  return children;
}
