import { ICourse } from './icourse';
import { IModule } from './imodule';

export interface ISuperWeeklyScheduleItem {
  id: number;
  klassId: number | undefined;
  weekDay: string;
  startTime: string;
  endTime: string;
  module: IModule;
  course: ICourse;
}

export class SuperWeeklyScheduleItem implements ISuperWeeklyScheduleItem {
  id!: number;
  klassId: number | undefined;
  weekDay!: string;
  startTime!: string;
  endTime!: string;
  module!: IModule;
  course!: ICourse;
}
