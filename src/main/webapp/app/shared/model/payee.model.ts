export interface IPayee {
  payeeID?: number;
  emailID?: string;
  firstName?: string;
  lastName?: string;
  telephone?: string;
  customerID?: string;
}

export const defaultValue: Readonly<IPayee> = {};
