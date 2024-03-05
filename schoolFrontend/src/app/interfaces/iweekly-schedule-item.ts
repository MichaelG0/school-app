import { IKlassBasicResponse, KlassBasicResponse } from './iklass-basic-response';
import { IModule } from './imodule';

export interface IWeeklyScheduleItem {
  id: number;
  klass: IKlassBasicResponse;
  weekDay: string;
  startTime: string;
  endTime: string;
  module: IModule;
}

export class WeeklyScheduleItem implements IWeeklyScheduleItem {
  id!: number;
  klass: IKlassBasicResponse = new KlassBasicResponse();
  weekDay!: string;
  startTime!: string;
  endTime!: string;
  module!: IModule;
}
