import request from '@/utils/request'
import type { DurableGoods } from '@/types/durableGoods'


export const getDurables = (specId = 'All'): Promise<DurableGoods[]> =>
  request.get('/vehicles/durables', { params: { specId } })

export const createCarrier = (durableSpecID: string, locationID = ''): Promise<any> =>
  request.post('/vehicles/carriers/create', null, {
    params: { durableSpecID, locationID }
  })
