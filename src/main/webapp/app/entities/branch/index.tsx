import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import Branch from './branch';
import BranchDetail from './branch-detail';
import BranchUpdate from './branch-update';
import BranchDeleteDialog from './branch-delete-dialog';
import { AUTHORITIES } from 'app/config/constants';
import PrivateRoute from 'app/shared/auth/private-route';

const Routes = ({ match }) => (
  <>
    <Switch>
      <PrivateRoute exact path={`${match.url}/new`} component={BranchUpdate} hasAnyAuthorities={[AUTHORITIES.MANAGER]} />
      <PrivateRoute exact path={`${match.url}/:id/edit`} component={BranchUpdate} hasAnyAuthorities={[AUTHORITIES.MANAGER]} />
      <PrivateRoute exact path={`${match.url}/:id`} component={BranchDetail} hasAnyAuthorities={[AUTHORITIES.MANAGER]} />
      <ErrorBoundaryRoute path={match.url} component={Branch} />
    </Switch>
    <PrivateRoute path={`${match.url}/:id/delete`} component={BranchDeleteDialog} hasAnyAuthorities={[AUTHORITIES.MANAGER]} />
  </>
);

export default Routes;
