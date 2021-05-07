import { IAccounts } from 'app/shared/model/accounts.model';

export interface IBranch {
  branchID?: number;
  address?: string;
  city?: string;
  state?: string;
  pinCode?: string;
  accounts?: IAccounts[];
}

export const defaultValue: Readonly<IBranch> = {};
