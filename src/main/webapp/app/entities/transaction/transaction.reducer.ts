import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';
import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { ITransaction, defaultValue } from 'app/shared/model/transaction.model';

export const ACTION_TYPES = {
  FETCH_TRANSACTIONS_LIST: 'transaction/FETCH_TRANSACTIONS_LIST',
  FETCH_TRANSACTIONS: 'transaction/FETCH_TRANSACTIONS',
  CREATE_TRANSACTIONS: 'transaction/CREATE_TRANSACTIONS',
  UPDATE_TRANSACTIONS: 'transaction/UPDATE_TRANSACTIONS',
  DELETE_TRANSACTIONS: 'transaction/DELETE_TRANSACTIONS',
  RESET: 'transaction/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITransaction>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type TransactionsState = Readonly<typeof initialState>;

// Reducer

export default (state: TransactionsState = initialState, action): TransactionsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TRANSACTIONS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TRANSACTIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TRANSACTIONS):
    case REQUEST(ACTION_TYPES.UPDATE_TRANSACTIONS):
    case REQUEST(ACTION_TYPES.DELETE_TRANSACTIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TRANSACTIONS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TRANSACTIONS):
    case FAILURE(ACTION_TYPES.CREATE_TRANSACTIONS):
    case FAILURE(ACTION_TYPES.UPDATE_TRANSACTIONS):
    case FAILURE(ACTION_TYPES.DELETE_TRANSACTIONS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TRANSACTIONS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_TRANSACTIONS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TRANSACTIONS):
    case SUCCESS(ACTION_TYPES.UPDATE_TRANSACTIONS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TRANSACTIONS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/transactions';
const payeeT = 'api/transactions/payeeTransfer';
// Actions

export const getEntities: ICrudGetAllAction<ITransaction> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TRANSACTIONS_LIST,
    payload: axios.get<ITransaction>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITransaction> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TRANSACTIONS,
    payload: axios.get<ITransaction>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITransaction> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TRANSACTIONS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const payeeTransfer: ICrudPutAction<ITransaction> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TRANSACTIONS,
    payload: axios.post(payeeT, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITransaction> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TRANSACTIONS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITransaction> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TRANSACTIONS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
