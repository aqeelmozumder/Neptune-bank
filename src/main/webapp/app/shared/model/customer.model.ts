export interface ICustomer {
  id?: number;
  firstName?: string;
  lastName?: string;
  telephone?: string;
  gender?: string;
  address?: string;
  city?: string;
  state?: string;
  zipCode?: string;
  pin?: number;
  userLogin?: string;
  userID?: number;
  document?: FormData;
}

export const defaultValue: Readonly<ICustomer> = {};
