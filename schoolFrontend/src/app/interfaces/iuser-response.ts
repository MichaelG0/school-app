export interface IUserResponse {
  id: number;
  name: string;
  surname: string;
  email: string;
  avatar: string;
  gender: string;
  address: string;
  phone: string;
  bio: string;
  roles: string[];
}

export class UserResponse implements IUserResponse {
  id = 0;
  name = '';
  surname = '';
  email = '';
  avatar = '';
  gender = '';
  address = '';
  phone = '';
  bio = '';
  roles = [];
}
