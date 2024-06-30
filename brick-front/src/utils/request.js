import axios, {HttpStatusCode} from "axios"
import {useUserStore} from "@/stores/index.js"
import router from "@/router/index.js"

const instance = axios.create({
  baseURL: 'http://localhost:5173',
  timeout: 10000
})

instance.interceptors.request.use(
  config => {
    const userStore = useUserStore()
    config.headers.setContentType("application/json")
    if (userStore.token) {
      config.headers.Authorization = 'Bearer ' + userStore.token
    }
    return config
  },
  error => {
    Promise.reject(error)
  }
)

instance.interceptors.response.use(
  res => {
    if (res.status === 200) {
      return res.data
    }
  },
  error => {
    if (error.response.status === 401) {
      router.push('/login')
    }
    // Promise.reject(error)
    return error.response.data
  }
)

export default instance

