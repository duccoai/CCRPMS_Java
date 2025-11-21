import api from "./api";

const safeParse = (data) => {
  if (typeof data !== "string") return data;
  try {
    let parsed = JSON.parse(data);
    if (typeof parsed === "string") parsed = JSON.parse(parsed);
    return parsed;
  } catch (e) {
    console.warn("safeParse: failed to parse response", e);
    return data;
  }
};

const RecruiterApi = {
  /** ---------------- JOBS ---------------- */
  getMyJobs: async () => {
    const res = await api.get("/recruiter/jobs");
    return { ...res, data: safeParse(res.data) };
  },
  createJob: async (jobRequest) => {
    const res = await api.post("/recruiter/jobs", jobRequest);
    return { ...res, data: safeParse(res.data) };
  },
  updateJob: async (id, jobRequest) => {
    const res = await api.put(`/recruiter/jobs/${id}`, jobRequest);
    return { ...res, data: safeParse(res.data) };
  },
  deleteJob: async (id) => {
    const res = await api.delete(`/recruiter/jobs/${id}`);
    return { ...res, data: safeParse(res.data) };
  },

  /** ---------------- APPLICATIONS ---------------- */
  getApplications: async () => {
    const res = await api.get("/recruiter/applications");
    return { ...res, data: safeParse(res.data) };
  },
  updateApplicationStatus: async (id, status) => {
    const res = await api.put(`/recruiter/applications/${id}/status`, null, {
      params: { status },
    });
    return { ...res, data: safeParse(res.data) };
  },

  /** ---------------- INTERVIEWS ---------------- */
  scheduleInterview: async (dto) => {
    const body = {
      applicationId: dto.applicationId,
      schedule: dto.schedule,
      status: dto.status,
      scoreInterview: dto.scoreInterview,
      scoreExam: dto.scoreExam,
      comment: dto.comment,
      location: dto.location,
      note: dto.note,
    };
    const res = await api.post("/recruiter/interviews/schedule", body);
    return { ...res, data: safeParse(res.data) };
  },
  scoreInterview: async (interviewId, score, comment) => {
    const res = await api.post(
      `/recruiter/interviews/${interviewId}/score`,
      null,
      { params: { score, comment } }
    );
    return { ...res, data: safeParse(res.data) };
  },

  /** ---------------- SUBMISSIONS ---------------- */
  scoreSubmission: async (submissionId, score) => {
    const res = await api.post(
      `/recruiter/submissions/${submissionId}/score`,
      null,
      { params: { score } }
    );
    return { ...res, data: safeParse(res.data) };
  },
  getSubmissionByApplication: async (applicationId) => {
    const res = await api.get(`/recruiter/applications/${applicationId}/submission`);
    return { ...res, data: safeParse(res.data) };
  },

  /** ---------------- USERS ---------------- */
  getCandidateById: async (id) => {
    const res = await api.get(`/users/${id}`);
    return { ...res, data: safeParse(res.data) };
  },

/** ---------------- PROMOTIONS ---------------- */

getPromotionApplications: async () => {
  const res = await api.get("/recruiter/promotions"); // ✔ singular
  return { ...res, data: safeParse(res.data) };
},

updatePromotionStatus: async (id, status) => {
  const res = await api.put(`/recruiter/promotions/${id}/status`, { status }); // ✔ gửi body
  return { ...res, data: safeParse(res.data) };
},

};

export default RecruiterApi;
