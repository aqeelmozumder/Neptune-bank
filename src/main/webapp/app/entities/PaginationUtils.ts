export interface IPaginationBaseCusState {
  itemsPerPage: number;
  sort: string;
  order: string;
  activePage: number;
  id?: number;
  userRoll: string;
  filteredTranData?: [];
}
// export declare const getSortCusState: (location: any, itemsPerPage: any) => IPaginationBaseCusState;
