// src/components/layout/Sidebar.jsx
import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';

const Sidebar = () => {
  const { user } = useAuth();

  return (
    <aside>
      <nav>
        <ul>
          <li><Link to="/">Home</Link></li>
          {/* Common links for candidate */}
          <li><Link to="/jobs">Jobs</Link></li>

          {/* Recruiter section */}
          {user?.role?.name === 'RECRUITER' && (
            <>
              <li><strong>Recruiter</strong></li>
              <li><Link to="/recruiter/dashboard">Dashboard</Link></li>
              <li><Link to="/recruiter/jobs">Manage Jobs</Link></li>
              <li><Link to="/recruiter/applications">Applications</Link></li>
            </>
          )}
        </ul>
      </nav>
    </aside>
  );
};

export default Sidebar;
