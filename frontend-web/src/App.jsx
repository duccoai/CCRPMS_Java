import { BrowserRouter, Routes, Route } from "react-router-dom";
import JobList from "./pages/candidate/JobList";
import JobDetail from "./pages/candidate/JobDetail";
import Profile from "./pages/candidate/Profile";
import Applications from "./pages/candidate/Applications";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<JobList />} />
        <Route path="/jobs/:id" element={<JobDetail />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/applications" element={<Applications />} />
      </Routes>
    </BrowserRouter>
  );
}
