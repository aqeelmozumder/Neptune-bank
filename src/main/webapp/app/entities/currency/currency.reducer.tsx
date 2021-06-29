import axios, { AxiosResponse } from 'axios';
import { ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { ICurrency, defaultValue } from 'app/shared/model/currency.model';

export const ACTION_TYPES = {
  FETCH_CURRENCIES_LIST: 'currency/FETCH_CURRENCIES_LIST',
  FETCH_CURRENCIES: 'currency/FETCH_CURRENCIES'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICurrency>,
  entity: defaultValue,
  totalItems: 0
};

export type CurrenciesState = Readonly<typeof initialState>;

// Reducer

export default (state: CurrenciesState = initialState, action): CurrenciesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CURRENCIES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CURRENCIES):
      return {
        ...state,
        errorMessage: null,
        loading: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CURRENCIES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CURRENCIES):
      return {
        ...state,
        loading: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CURRENCIES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_CURRENCIES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    default:
      return state;
  }
};

// Actions

export const getEntities: ICrudGetAllAction<ICurrency> = () => ({
  type: ACTION_TYPES.FETCH_CURRENCIES_LIST,
  payload: new Promise((resolve, reject) => {
    const c1: ICurrency = { currencyName: 'USD', exchangeRate: 0.81 };
    const c2: ICurrency = { currencyName: 'CNY', exchangeRate: 5.27 };
    const c3: ICurrency = { currencyName: 'JPY', exchangeRate: 90.23 };
    const c4: ICurrency = { currencyName: 'EUR', exchangeRate: 0.68 };
    const c5: ICurrency = { currencyName: 'INR', exchangeRate: 60.29 };
    const c6: ICurrency = { currencyName: 'GBP', exchangeRate: 0.58 };
    const res: AxiosResponse = {
      status: 200,
      statusText: 'Success',
      config: {},
      headers: { 'content-type': 'application/json' },
      data: [c1, c2, c3, c4, c5, c6]
    };
    resolve(res);
  })
});
