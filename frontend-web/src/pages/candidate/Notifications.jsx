import { useEffect, useState } from "react";
import { getNotifications } from "../../services/notificationApi";
import "./Candidate.css";

export default function Notifications() {
  const userId = localStorage.getItem("userId");
  const [notis, setNotis] = useState([]);

  useEffect(() => {
    getNotifications(userId).then(res => setNotis(res.data));
  }, []);

  return (
    <div className="notifications-container">
      <h2>Thông báo</h2>

      {notis.length === 0 && <p>Không có thông báo.</p>}

      {notis.map(n => (
        <div key={n.id} className="notification-item">
          <h4>{n.title}</h4>
          <p>{n.message}</p>
          <small>{new Date(n.createdAt).toLocaleString()}</small>
        </div>
      ))}
    </div>
  );
}
