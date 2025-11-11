import { Link } from "react-router-dom";

export default function Dashboard() {
  const username = localStorage.getItem("username");

  return (
    <div style={styles.container}>
      <h2>Xin chào, {username} 👋</h2>
      <p>Chào mừng bạn đến với hệ thống tuyển dụng!</p>

      <div style={styles.grid}>
        <Link to="/jobs" style={styles.card}>📋 Danh sách công việc</Link>
        <Link to="/applications" style={styles.card}>📩 Hồ sơ đã nộp</Link>
        <Link to="/exam" style={styles.card}>🧠 Làm bài thi online</Link>
        <Link to="/results" style={styles.card}>📄 Xem kết quả</Link>
        <Link to="/profile" style={styles.card}>👤 Hồ sơ cá nhân</Link>
      </div>
    </div>
  );
}

const styles = {
  container: { padding: 40, textAlign: "center" },
  grid: {
    display: "grid",
    gridTemplateColumns: "repeat(auto-fit, minmax(250px, 1fr))",
    gap: 20,
    marginTop: 30,
  },
  card: {
    background: "#fff",
    padding: 20,
    borderRadius: 12,
    textDecoration: "none",
    color: "#333",
    boxShadow: "0 3px 10px rgba(0,0,0,0.1)",
    fontWeight: 600,
  },
};
