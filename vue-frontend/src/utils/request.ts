import axios, { AxiosResponse, InternalAxiosRequestConfig, AxiosHeaders } from 'axios'
import { ElMessage } from 'element-plus'

const request = axios.create({
  baseURL: '/api',
  timeout: 10000
})

request.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = localStorage.getItem('token')
    if (token) {
      if (!config.headers) {
        // 用 AxiosHeaders 创建一个新的 headers 对象
        config.headers = new AxiosHeaders()
      }
      config.headers.set('Authorization', `Bearer ${token}`)
    }
    return config
  },
  error => Promise.reject(error)
)

request.interceptors.response.use(
  (response: AxiosResponse) => response.data,
  error => {
    const msg =
      error.response?.data?.message ||
      error.message ||
      '服务器异常，请稍后再试'
    ElMessage.error(msg)
    return Promise.reject(error)
  }
)

export default request
