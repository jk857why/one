// vehicle.ts
import request from '@/utils/request'
import type { DurableGoods } from '@/types/durableGoods'

export const getDurables = (specId = 'All'): Promise<DurableGoods[]> =>
  request.get('/vehicles/durables', { params: { specId } })

export const createCarrier = (data: { durableSpecID: string; locationID?: string }): Promise<any> =>
  request.post('/vehicles/carriers/create', data) // 注意，不再传 params，而是直接放 body 中
