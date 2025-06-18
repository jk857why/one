export interface Carrier {
    CarrierID: string
    CarrierType?: string
    CarrierDetailType?: string
    DurableSpecID?: string
    CarrierStatus?: string
    CleaningStatus?: string
    LockStatus?: string
    CapacityStatus?: string
    BatchNumber?: string
    BatchQuantity?: number
    eqp_id?: string
    port_id?: string
    LocationID?: string
    edit_time?: string // 日期时间类型，前端一般用 ISO 字符串或 Date
    createTime?: string
    MaxCleaningCount?: number
    CleaningCount?: number
  }