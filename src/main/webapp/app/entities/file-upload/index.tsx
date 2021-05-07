import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FileUpload from './file-upload';
import FileUploadDetail from './file-upload-detail';
import FileUploadUpdate from './file-upload-update';
import FileUploadDeleteDialog from './file-upload-delete-dialog';
import { AUTHORITIES } from 'app/config/constants';
import PrivateRoute from 'app/shared/auth/private-route';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FileUploadUpdate} />
      <PrivateRoute exact path={`${match.url}/:id/edit`} component={FileUploadUpdate} hasAnyAuthorities={[AUTHORITIES.MANAGER]} />
      <PrivateRoute exact path={`${match.url}/:id`} component={FileUploadDetail} hasAnyAuthorities={[AUTHORITIES.MANAGER]} />
      <PrivateRoute path={match.url} component={FileUpload} hasAnyAuthorities={[AUTHORITIES.MANAGER]} />
    </Switch>
    <PrivateRoute path={`${match.url}/:id/delete`} component={FileUploadDeleteDialog} hasAnyAuthorities={[AUTHORITIES.MANAGER]} />
  </>
);

export default Routes;
