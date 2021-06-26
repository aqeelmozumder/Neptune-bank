export interface IAccounts {
  accountID?: number;
  searchString?: string;
  accountType?: string;
  balance?: number;
  activated?: boolean;
  customerID?: number;
  branchID?: number;
  userLogin?: String;
  branchAddress?: String;
}

export const defaultValue: Readonly<IAccounts> = {
  activated: false
};
