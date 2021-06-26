export interface IFilterState {
  itemsPerPage: number;
  sort: string;
  order: string;
  activePage: number;
  accountId?: number;
  searchString?: string;
}
