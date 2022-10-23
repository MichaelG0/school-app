import { ICourse } from "./icourse";
import { IUserBasicResponse } from "./iuser-basic-response";
import { IWeeklyScheduleItem } from "./iweekly-schedule-item";

export interface IKlass {
    id: string;
    course: ICourse;
    students: IUserBasicResponse[]
    weeklySchedule: IWeeklyScheduleItem[]
}
