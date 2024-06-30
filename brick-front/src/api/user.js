import request from "@/utils/request.js";

export const userLoginRequest = (email, password) => request.post('/api/web/login', {email, password})
