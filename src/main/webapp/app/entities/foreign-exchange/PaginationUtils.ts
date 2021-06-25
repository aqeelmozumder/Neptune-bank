export interface IPaginationBaseCusState {
  itemsPerPage: number;
  sort: string;
  order: string;
  activePage: number;
  id?: number;
  userRoll: string;
  filteredCurrencyData?: [];
  currencyName?: Date;
  exchangeRate?: Date;
}
// export declare const getSortState: (location: any, itemsPerPage: any) => IPaginationBaseCusState;
