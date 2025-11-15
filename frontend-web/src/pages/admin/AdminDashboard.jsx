import { Link, Outlet } from "react-router-dom";

export default function AdminDashboard() {
  return (
    <div className="admin-dashboard">
      <h1>Chào mừng bạn đến khu vực quản trị</h1>

      <div className="tabs" style={{ marginBottom: 20 }}>
        <Link to="users">
          <button>Quản lý người dùng</button>
        </Link>
        <Link to="applications">
          <button>Hồ sơ ứng tuyển</button>
        </Link>
        <Link to="interviews">
          <button>Phỏng vấn & Bài thi</button>
        </Link>
        <Link to="jobs">
          <button>Quản lý bài đăng</button>
        </Link>
        <Link to="reports">
          <button>Báo cáo</button>
        </Link>
      </div>

      <div className="tab-content">
        <Outlet />
      </div>
    </div>
  );
}
