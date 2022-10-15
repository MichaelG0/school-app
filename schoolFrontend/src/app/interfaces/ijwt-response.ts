import { IUserResponse } from "./iuser-response";

export interface IJwtResponse {
  token: string;
  type: string;
  user: IUserResponse
}
