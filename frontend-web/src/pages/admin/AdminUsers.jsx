import { useEffect, useState } from "react";
import axios from "axios";

export default function AdminUsers() {
  const [users, setUsers] = useState([]);
  const [newRecruiter, setNewRecruiter] = useState({ username: "", fullName: "", email: "", password: "" });

  const fetchUsers = async () => {
    try {
      const res = await axios.get("/api/users");
      setUsers(Array.isArray(res.data) ? res.data : []);
    } catch (err) {
      console.error(err);
    }
  };

  const handleCreateRecruiter = async () => {
    try {
      await axios.post("/api/users", { ...newRecruiter });
      setNewRecruiter({ username: "", fullName: "", email: "", password: "" });
      fetchUsers();
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => { fetchUsers(); }, []);

  return (
    <div>
      <h2>Danh sách người dùng</h2>
      {users.length === 0 ? <p>Chưa có người dùng</p> :
      <table border="1" cellPadding="5">
        <thead>
          <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Họ tên</th>
            <th>Email</th>
            <th>Vai trò</th>
          </tr>
        </thead>
        <tbody>
          {users.map(u => (
            <tr key={u.id}>
              <td>{u.id}</td>
              <td>{u.username}</td>
              <td>{u.fullName}</td>
              <td>{u.email}</td>
              <td>{u.role?.name || "N/A"}</td>
            </tr>
          ))}
        </tbody>
      </table>}

      <h3>Tạo tài khoản Recruiter mới</h3>
      <input placeholder="Username" value={newRecruiter.username} onChange={e => setNewRecruiter({...newRecruiter, username: e.target.value})} />
      <input placeholder="Full Name" value={newRecruiter.fullName} onChange={e => setNewRecruiter({...newRecruiter, fullName: e.target.value})} />
      <input placeholder="Email" value={newRecruiter.email} onChange={e => setNewRecruiter({...newRecruiter, email: e.target.value})} />
      <input type="password" placeholder="Password" value={newRecruiter.password} onChange={e => setNewRecruiter({...newRecruiter, password: e.target.value})} />
      <button onClick={handleCreateRecruiter}>Tạo Recruiter</button>
    </div>
  );
}
