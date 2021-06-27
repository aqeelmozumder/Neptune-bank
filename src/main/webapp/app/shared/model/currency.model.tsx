export interface ICurrency {
  currencyName?: string;
  exchangeRate?: number;
  filteredCurrencyData?: [];
}

export const defaultValue: Readonly<ICurrency> = {};
