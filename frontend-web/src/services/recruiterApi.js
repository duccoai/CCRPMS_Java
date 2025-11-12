// src/services/recruiterApi.js
import api from './api'; // dùng axios instance của project (nếu bạn có file khác thì chỉnh đường dẫn)

const RecruiterApi = {
  // Lấy job của recruiter (nếu backend cung cấp /api/recruiter/jobs)
  getMyJobs: () => api.get('/api/recruiter/jobs'),

  // Tạo job (jobRequest: { title, description, location, salaryRange, status, recruiterId })
  createJob: (jobRequest) => api.post('/api/jobs', jobRequest),

  // Cập nhật job
  updateJob: (id, jobRequest) => api.put(`/api/jobs/${id}`, jobRequest),

  // Xoá job
  deleteJob: (id) => api.delete(`/api/jobs/${id}`),

  // Lấy tất cả ứng tuyển liên quan recruiter
  getApplications: () => api.get('/api/recruiter/applications'),

  // Cập nhật trạng thái application (ví dụ endpoint: PUT /api/applications/{id}/status)
  updateApplicationStatus: (id, payload) => api.put(`/api/applications/${id}/status`, payload),

  // Lấy thông tin candidate (nếu cần)
  getCandidateById: (id) => api.get(`/api/users/${id}`)
};

export default RecruiterApi;
