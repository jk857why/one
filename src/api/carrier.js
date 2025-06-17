import axios from 'axios'

const BASE_URL = 'http://localhost:8080/api/carriers'

export function fetchDistinct(field) {
  return axios.get(`${BASE_URL}/distinct?field=${field}`)
}

export function searchCarriers(params) {
  return axios.get(`${BASE_URL}/search`, { params })
}
