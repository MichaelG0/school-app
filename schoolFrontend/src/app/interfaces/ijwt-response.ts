export interface IJwtResponse {
  token: string;
  type: string;
  id: number;
  email: string;
  name: string;
  surname: string;
  roles: string[];
}
