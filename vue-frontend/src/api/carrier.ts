// src/api/carrier.ts
import request from '@/utils/request'

// 获取所有载具
export const getAllCarriers = () => {
  return request.get('/carriers')
}

// 根据ID获取载具
export const getCarrierById = (carrierID: string) => {
  return request.get(`/carriers/${carrierID}`)
}

// 根据多个条件查询载具（对应 findCarriersByConditions）
export interface CarrierSearchParams {
  carrierDetailType?: string
  cleaningStatus?: string
  durableSpecID?: string
  carrierStatus?: string
  capacityStatus?: string
}
export const findCarriersByConditions = (params: CarrierSearchParams) => {
  return request.get('/carriers/search', { params })
}

// 新增或更新载具
export const saveCarrier = (carrier: any) => {
  // carrier对象字段对应后端Carrier实体
  return request.post('/carriers', carrier)
}

// 清洗载具（对应 cleanCarrier）
export const cleanCarrier = (carrierID: string) => {
  return request.put(`/carriers/${carrierID}/clean`)
}

// 更新锁定状态（对应 updateLockStatus）
export const updateLockStatus = (carrierID: string, lockStatus: string) => {
  return request.put(`/carriers/${carrierID}/lockStatus`, null, {
    params: { lockStatus }
  })
}

// 更新载具状态（对应 updateCarrierStatus）
export const updateCarrierStatus = (carrierID: string, carrierStatus: string) => {
  return request.put(`/carriers/${carrierID}/status`, null, {
    params: { carrierStatus }
  })
}

// 获取某字段的去重值（对应 getDistinctValuesByField）
export type CarrierFieldName = 
  | 'carrierDetailType' 
  | 'cleaningStatus' 
  | 'durableSpecID' 
  | 'carrierStatus' 
  | 'capacityStatus'

export const getDistinctValuesByField = (fieldName: CarrierFieldName) => {
  return request.get(`/carriers/distinct/${fieldName}`)
}
