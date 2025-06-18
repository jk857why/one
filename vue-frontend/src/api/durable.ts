// src/api/durable.ts
import request from '@/utils/request'

// 获取所有耐用品
export const getAllDurables = () => {
  return request.get('/durable/all')
}

// 根据规格ID查询耐用品
export const getDurablesBySpecID = (durableSpecID: string) => {
  return request.get(`/durable/by-spec-id/${durableSpecID}`)
}

// 获取所有去重的耐用品规格ID
export const getDistinctDurableSpecIDs = () => {
  return request.get('/durable/distinct-ids')
}

// 获取耐用品及其对应载具信息（联合查询结果）
export const getDurablesWithCarrier = () => {
  return request.get('/durable/with-carrier')
}
