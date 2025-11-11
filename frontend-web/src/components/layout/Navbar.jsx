import { useNavigate } from "react-router-dom";

export default function Navbar() {
  const navigate = useNavigate();
  const username = localStorage.getItem("username");

  function handleLogout() {
    localStorage.clear();
    navigate("/login");
  }

  return (
    <nav className="bg-blue-600 text-white flex justify-between px-8 py-3 shadow-md">
      <h1 className="text-xl font-semibold cursor-pointer" onClick={() => navigate("/")}>
        💼 CCRPMS Candidate Portal
      </h1>
      <div className="flex items-center gap-4">
        <span>Xin chào, <b>{username}</b></span>
        <button onClick={handleLogout} className="bg-white text-blue-600 px-3 py-1 rounded hover:bg-gray-100">
          Đăng xuất
        </button>
      </div>
    </nav>
  );
}
