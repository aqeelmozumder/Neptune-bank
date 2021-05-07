import { Moment } from 'moment';

export interface INews {
  id?: number;
  title?: string;
  date?: Moment;
  content?: string;
  location?: string;
}

export const defaultValue: Readonly<INews> = {};
