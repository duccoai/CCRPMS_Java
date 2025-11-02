import { useEffect, useState } from "react";
import api from "../../api/api";

export default function Profile() {
  const userId = Number(localStorage.getItem("userId") || 1);
  const [user, setUser] = useState(null);
  const [editing, setEditing] = useState(false);
  const [form, setForm] = useState({});
  const [avatarFile, setAvatarFile] = useState(null);
  const [cvFile, setCvFile] = useState(null);

  useEffect(() => {
    api.get(`/users/profile/${userId}`)
      .then(res => {
        setUser(res.data);
        setForm({
          fullName: res.data.fullName || "",
          email: res.data.email || "",
          bio: res.data.bio || "",
        });
      })
      .catch(console.error);
  }, [userId]);

  function handleSave() {
    api.put(`/users/profile/${userId}`, form)
      .then(res => {
        setUser(res.data);
        setEditing(false);
        alert("Cập nhật thành công");
      })
      .catch(err => {
        console.error(err);
        alert("Cập nhật thất bại");
      });
  }

  function uploadFile(endpoint, file) {
    if (!file) return;
    const fd = new FormData();
    fd.append("file", file);
    api.post(`/users/${endpoint}/${userId}`, fd, {
      headers: { "Content-Type": "multipart/form-data" }
    })
      .then(res => {
        alert(res.data.message || "Upload success");
        // refresh profile
        return api.get(`/users/profile/${userId}`);
      })
      .then(res => setUser(res.data))
      .catch(err => {
        console.error(err);
        alert("Upload thất bại");
      });
  }

  if (!user) return <div style={{padding:20}}>Loading profile…</div>;

  return (
    <div style={{ padding: 20 }}>
      <h2>Hồ sơ cá nhân</h2>
      <div style={{ display: "flex", gap: 20 }}>
        <div style={{ minWidth: 220 }}>
          <div style={{ marginBottom: 8 }}>
            <b>Username:</b> {user.username}
          </div>
          <div style={{ marginBottom: 8 }}>
            <img src={user.avatarUrl || "/placeholder-avatar.png"} alt="avatar" style={{width:120, height:120, objectFit:"cover", borderRadius:8}} />
          </div>
          <div>
            <input type="file" onChange={e => setAvatarFile(e.target.files?.[0])} />
            <button onClick={() => uploadFile("upload/avatar", avatarFile)}>Upload avatar</button>
          </div>
          <div style={{ marginTop: 12 }}>
            <input type="file" onChange={e => setCvFile(e.target.files?.[0])} />
            <button onClick={() => uploadFile("upload/cv", cvFile)}>Upload CV</button>
          </div>
        </div>

        <div style={{ flex: 1 }}>
          {!editing && (
            <>
              <div><b>Họ và tên:</b> {user.fullName}</div>
              <div><b>Email:</b> {user.email}</div>
              <div style={{ marginTop: 8 }}><b>Bio:</b> <div>{user.bio}</div></div>
              <div style={{ marginTop: 12 }}>
                <button onClick={() => setEditing(true)}>Sửa hồ sơ</button>
              </div>
            </>
          )}

          {editing && (
            <>
              <div style={{ marginBottom: 8 }}>
                <label>Full name</label><br/>
                <input value={form.fullName} onChange={e => setForm({...form, fullName: e.target.value })} />
              </div>
              <div style={{ marginBottom: 8 }}>
                <label>Email</label><br/>
                <input value={form.email} onChange={e => setForm({...form, email: e.target.value })} />
              </div>
              <div style={{ marginBottom: 8 }}>
                <label>Bio</label><br/>
                <textarea value={form.bio} onChange={e => setForm({...form, bio: e.target.value })} rows={4} />
              </div>
              <div>
                <button onClick={handleSave}>Lưu</button>
                <button onClick={() => setEditing(false)}>Hủy</button>
              </div>
            </>
          )}
        </div>
      </div>
    </div>
  );
}
