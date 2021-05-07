import { Moment } from 'moment';

export interface IFileUpload {
  id?: number;
  fileName?: string;
  fileContentType?: string;
  file?: any;
  lastModifiedDate?: Moment;
  size?: number;
}

export const defaultValue: Readonly<IFileUpload> = {};
