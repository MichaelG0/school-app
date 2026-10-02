export interface ISnackBarData {
  type: string;
  message: string;
  icon: string;
}

export class SnackBarData implements ISnackBarData {
  type = '';
  message = '';
  icon = '';
}
