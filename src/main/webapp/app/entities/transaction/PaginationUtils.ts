export interface IPaginationBaseCusState {
  itemsPerPage: number;
  sort: string;
  order: string;
  activePage: number;
  id?: number;
  userRoll: string;
  filteredTranData?: [];
  startDate?: Date;
  endDate?: Date;
}
// export declare const getSortState: (location: any, itemsPerPage: any) => IPaginationBaseCusState;
