import { ICourse } from './icourse';

export interface IKlassBasicResponse {
  id: number;
  course: ICourse;
  renderColor: string;
}

export class KlassBasicResponse implements IKlassBasicResponse {
  id!: number;
  course!: ICourse;
  renderColor!: string;
}
