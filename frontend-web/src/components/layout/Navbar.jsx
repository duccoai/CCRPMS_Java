import { useNavigate, Link } from "react-router-dom";

export default function Navbar() {
  const navigate = useNavigate();
  const raw = localStorage.getItem("user");
  let user = null;
  try {
    user = raw ? JSON.parse(raw) : null;
  } catch (e) {
    user = null;
  }

  const username = user?.fullName || user?.username || localStorage.getItem("username") || "";

  function handleLogout() {
    localStorage.clear();
    navigate("/login");
  }

  return (
    <nav className="bg-blue-600 text-white flex justify-between px-8 py-3 shadow-md">
      <div style={{ display: 'flex', alignItems: 'center', gap: 16 }}>
        <h1 className="text-xl font-semibold cursor-pointer" onClick={() => navigate("/")}>
          💼 CCRPMS Candidate Portal
        </h1>

        {/* show recruiter links when role is RECRUITER */}
        { (user?.role?.name === 'RECRUITER') || (user?.role === 'RECRUITER') ? (
          <>
            <Link to="/recruiter/dashboard" style={{ color: 'white', marginLeft: 8 }}>Recruiter</Link>
            <Link to="/recruiter/jobs" style={{ color: 'white', marginLeft: 8 }}>Manage Jobs</Link>
            <Link to="/recruiter/applications" style={{ color: 'white', marginLeft: 8 }}>Applications</Link>
          </>
        ) : null }

        {/* candidate links */}
        <Link to="/jobs" style={{ color: 'white', marginLeft: 8 }}>Jobs</Link>
      </div>

      <div className="flex items-center gap-4">
        <span>Xin chào, <b>{username}</b></span>
        <button onClick={handleLogout} className="bg-white text-blue-600 px-3 py-1 rounded hover:bg-gray-100">
          Đăng xuất
        </button>
      </div>
    </nav>
  );
}
