import { Moment } from 'moment';
import { IPayee } from 'app/shared/model/payee.model';

export interface ITransaction {
  tranID?: number;
  createdDate?: Moment;
  type?: string;
  amount?: number;
  fromAccount?: string;
  toAccount?: string;
  customerID?: string;
  filteredTranData?: [];
}

export const defaultValue: Readonly<ITransaction> = {};
