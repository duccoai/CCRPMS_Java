import React from "react";
import Sidebar from "./Sidebar";
import "./AdminLayout.css";

export default function AdminLayout({ children }) {
  return (
    <div className="admin-layout">
      
      <Sidebar />

      <div className="admin-main">
        <header className="admin-navbar">
          <h2>Administrator Panel</h2>
        </header>

        <main className="admin-content">
          {children}
        </main>
      </div>

    </div>
  );
}
