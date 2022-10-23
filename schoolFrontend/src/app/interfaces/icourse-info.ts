import { IModule } from './imodule';

export interface ICourseInfo {
  id: number;
  name: string;
  description: string;
  image: string;
  type: string;
  modules: IModule[];
}
