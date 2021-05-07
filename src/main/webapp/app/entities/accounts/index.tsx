import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import Accounts from './accounts';
import AccountsDetail from './accounts-detail';
import AccountsUpdate from './accounts-update';
import AccountsDeleteDialog from './accounts-delete-dialog';
import { AUTHORITIES } from 'app/config/constants';
import PrivateRoute from 'app/shared/auth/private-route';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AccountsUpdate} />
      <PrivateRoute
        exact
        path={`${match.url}/:id/edit`}
        component={AccountsUpdate}
        hasAnyAuthorities={[AUTHORITIES.MANAGER, AUTHORITIES.STAFF]}
      />
      <PrivateRoute
        exact
        path={`${match.url}/:id`}
        component={AccountsDetail}
        hasAnyAuthorities={[AUTHORITIES.MANAGER, AUTHORITIES.STAFF]}
      />
      <ErrorBoundaryRoute path={match.url} component={Accounts} />
    </Switch>
    <PrivateRoute
      path={`${match.url}/:id/delete`}
      component={AccountsDeleteDialog}
      hasAnyAuthorities={[AUTHORITIES.MANAGER, AUTHORITIES.STAFF]}
    />
  </>
);

export default Routes;
