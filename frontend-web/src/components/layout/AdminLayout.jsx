import React from "react";
import Sidebar from "./Sidebar";

export default function AdminLayout({ children }) {
  return (
    <div className="admin-layout" style={{ display: "flex", minHeight: "100vh" }}>
      <Sidebar />
      <main style={{ flex: 1, padding: "20px" }}>
        {children}
      </main>
    </div>
  );
}
