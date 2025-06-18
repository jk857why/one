export interface DurableGoods {
    durableSpecID: string
    durableName: string
    durableType: string
    durableDetailType?: string
    durableColor?: string
    durableCapacity?: string
    maxUseCount?: number
    maxUseDays?: number
    maxUseCountAfterClean?: number
    maxUseDaysAfterClean?: number
    alarmWhenExceedCount?: boolean
    alarmWhenExceedDays?: boolean
    alarmWhenExceedCountAfterClean?: boolean
    alarmWhenExceedCountAfterInspection?: boolean
    alarmWhenExceedDaysAfterInspection?: boolean
    factoryId?: string
    editStatus?: string
    editTime?: string
    editUser?: string
    event?: string
    eventCode?: string
    createTime?: string
  }