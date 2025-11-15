import React, { useEffect, useState } from "react";
import { getAllUsers, createRecruiter } from "../../services/adminApi";

export default function AdminUsers() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [newRecruiter, setNewRecruiter] = useState({ username: "", email: "", password: "" });
  const [error, setError] = useState("");

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      const res = await getAllUsers();
      setUsers(Array.isArray(res.data) ? res.data : []);
    } catch (err) {
      console.error(err);
      setUsers([]);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateRecruiter = async () => {
    try {
      if (!newRecruiter.username || !newRecruiter.email || !newRecruiter.password) {
        setError("Vui lòng điền đầy đủ thông tin");
        return;
      }
      await createRecruiter(newRecruiter);
      setNewRecruiter({ username: "", email: "", password: "" });
      setError("");
      fetchUsers();
    } catch (err) {
      console.error(err);
      setError("Tạo recruiter thất bại");
    }
  };

  if (loading) return <p>Đang tải người dùng...</p>;
  if (!users.length) return <p>Chưa có người dùng</p>;

  return (
    <div>
      <h1>Danh sách người dùng</h1>

      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Email</th>
            <th>Role</th>
          </tr>
        </thead>
        <tbody>
          {users.map(u => (
            <tr key={u.id}>
              <td>{u.id}</td>
              <td>{u.username}</td>
              <td>{u.email}</td>
              <td>{u.role?.name}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <h2>Tạo tài khoản Recruiter</h2>
      <div style={{ marginTop: 10 }}>
        <input
          type="text"
          placeholder="Username"
          value={newRecruiter.username}
          onChange={e => setNewRecruiter({ ...newRecruiter, username: e.target.value })}
        />
        <input
          type="email"
          placeholder="Email"
          value={newRecruiter.email}
          onChange={e => setNewRecruiter({ ...newRecruiter, email: e.target.value })}
        />
        <input
          type="password"
          placeholder="Password"
          value={newRecruiter.password}
          onChange={e => setNewRecruiter({ ...newRecruiter, password: e.target.value })}
        />
        <button onClick={handleCreateRecruiter}>Tạo Recruiter</button>
        {error && <p style={{ color: "red" }}>{error}</p>}
      </div>
    </div>
  );
}
