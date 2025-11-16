import React, { useEffect, useState } from "react";
import { getAllUsers } from "../../services/adminApi";
import "./AdminTable.css";

export default function AdminUsers() {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    getAllUsers()
      .then((res) => setUsers(res.data || []))
      .catch((err) => console.error("Failed to load users:", err));
  }, []);

  if (!users.length) return <p>Không có dữ liệu người dùng</p>;

  return (
    <div className="admin-table-wrapper">
      <h1>👤 Danh sách người dùng</h1>

      <table className="admin-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Email</th>
            <th>Quyền</th>
          </tr>
        </thead>

        <tbody>
          {users.map((u) => (
            <tr key={u.id}>
              <td>{u.id}</td>
              <td>{u.username}</td>
              <td>{u.email}</td>
              <td>
                <span className="role-badge">{u.roleName}</span>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
