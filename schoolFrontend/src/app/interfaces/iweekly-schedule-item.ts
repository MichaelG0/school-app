import { IModule } from "./imodule"

export interface IWeeklyScheduleItem {
    id: number
    weekDay: string
    startTime: string
    endTime: string
    module: IModule
}
