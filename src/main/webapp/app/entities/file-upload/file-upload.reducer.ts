import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';
import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { IFileUpload, defaultValue } from 'app/shared/model/file-upload.model';

export const ACTION_TYPES = {
  FETCH_FILEUPLOAD_LIST: 'fileUpload/FETCH_FILEUPLOAD_LIST',
  FETCH_FILEUPLOAD: 'fileUpload/FETCH_FILEUPLOAD',
  CREATE_FILEUPLOAD: 'fileUpload/CREATE_FILEUPLOAD',
  UPDATE_FILEUPLOAD: 'fileUpload/UPDATE_FILEUPLOAD',
  DELETE_FILEUPLOAD: 'fileUpload/DELETE_FILEUPLOAD',
  SET_BLOB: 'fileUpload/SET_BLOB',
  RESET: 'fileUpload/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFileUpload>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type FileUploadState = Readonly<typeof initialState>;

// Reducer

export default (state: FileUploadState = initialState, action): FileUploadState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FILEUPLOAD_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FILEUPLOAD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_FILEUPLOAD):
    case REQUEST(ACTION_TYPES.UPDATE_FILEUPLOAD):
    case REQUEST(ACTION_TYPES.DELETE_FILEUPLOAD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_FILEUPLOAD_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FILEUPLOAD):
    case FAILURE(ACTION_TYPES.CREATE_FILEUPLOAD):
    case FAILURE(ACTION_TYPES.UPDATE_FILEUPLOAD):
    case FAILURE(ACTION_TYPES.DELETE_FILEUPLOAD):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_FILEUPLOAD_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_FILEUPLOAD):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_FILEUPLOAD):
    case SUCCESS(ACTION_TYPES.UPDATE_FILEUPLOAD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_FILEUPLOAD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/mediaFiles';

export const getEntities: ICrudGetAllAction<IFileUpload> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_FILEUPLOAD_LIST,
  payload: axios.get<IFileUpload>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<any> = fileName => {
  const requestUrl = `${apiUrl}/${fileName}`;
  fetch(requestUrl)
    .then(res => res.blob())
    .then(blob => {
      const url = window.URL.createObjectURL(new Blob([blob], { type: 'octet/stream' }));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `${fileName}`);
      document.body.appendChild(link);
      link.click();
      link.parentNode.removeChild(link);
      window.open();
    });
  return {
    type: ACTION_TYPES.FETCH_FILEUPLOAD,
    payload: axios.get<any>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<any> = entity => async dispatch => {
  const formData = new FormData();
  formData.append('file', entity);
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FILEUPLOAD,
    payload: axios.post(apiUrl, formData)
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFileUpload> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FILEUPLOAD,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFileUpload> = fileName => async dispatch => {
  const requestUrl = `${apiUrl}/${fileName}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FILEUPLOAD,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
