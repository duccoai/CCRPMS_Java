// src/services/candidateApi.js
import api from "./api";

const CandidateApi = {
  // Lấy danh sách hồ sơ nâng bậc của candidate hiện tại
  getMyPromotionApplications: async () => {
    const res = await api.get("/promotion/user/me"); // endpoint backend
    return { ...res, data: res.data };
  },

  // Nếu muốn sau này thêm jobs, applications... có thể mở rộng ở đây
};

export default CandidateApi;
