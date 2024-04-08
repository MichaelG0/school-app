import { IUserResponse, UserResponse } from './iuser-response';

export interface IJwtResponse {
  token: string;
  type: string;
  user: IUserResponse;
}

export class JwtResponse implements IJwtResponse{
  token = '';
  type = '';
  user = new UserResponse();
}
