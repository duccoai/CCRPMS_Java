import { Navigate } from "react-router-dom";

export default function RequireRole({ children, role }) {
  const token = localStorage.getItem("token");
  const userRole = (localStorage.getItem("role") || "").toUpperCase();
  const requiredRole = role.toUpperCase();

  // Nếu chưa đăng nhập
  if (!token) return <Navigate to="/login" replace />;

  // Nếu role sai → chặn truy cập
  if (userRole !== requiredRole) {
    return (
      <div style={{ padding: 20, textAlign: "center" }}>
        <h2>⚠ Không có quyền truy cập</h2>
        <p>Bạn không có quyền truy cập vào trang này.</p>
      </div>
    );
  }

  return children;
}
