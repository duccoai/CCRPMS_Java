import axios from "./api";  // giữ dòng này, bỏ dòng kia

// ==========================
//  Người dùng (Admin)
// ==========================
export const getAllUsers = () => axios.get("/admin/users");
export const createRecruiter = (data) => axios.post("/admin/users/recruiter", data);

// ==========================
//  Hồ sơ ứng tuyển
// ==========================
export const getAllApplications = () =>
  axios.get("/admin/applications").then(res => ({
    data: Array.isArray(res.data) ? res.data : [],
  }));

export const updateApplicationFinalDecision = (id, result) =>
  axios.put(`/admin/applications/${id}/final?result=${result}`);

// ==========================
//  Phỏng vấn
// ==========================
export const getAllInterviews = () =>
  axios.get("/admin/interviews").then(res => ({
    data: Array.isArray(res.data) ? res.data : [],
  }));

// ==========================
//  Bài thi
// ==========================
export const getAllExams = () =>
  axios.get("/admin/exams").then(res => ({
    data: Array.isArray(res.data) ? res.data : [],
  }));

export const toggleExam = (id) => axios.put(`/admin/exams/${id}/toggle`);

// ==========================
//  Thống kê
// ==========================
export const getAdminStats = () => axios.get("/admin/statistics");
