import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import Customer from './customer';
import CustomerDetail from './customer-detail';
import CustomerUpdate from './customer-update';
import CustomerDeleteDialog from './customer-delete-dialog';
import { AUTHORITIES } from 'app/config/constants';
import PrivateRoute from 'app/shared/auth/private-route';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CustomerUpdate} />
      <PrivateRoute
        exact
        path={`${match.url}/:id/edit`}
        component={CustomerUpdate}
        hasAnyAuthorities={[AUTHORITIES.MANAGER, AUTHORITIES.STAFF]}
      />
      <PrivateRoute
        exact
        path={`${match.url}/:id`}
        component={CustomerDetail}
        hasAnyAuthorities={[AUTHORITIES.MANAGER, AUTHORITIES.STAFF]}
      />
      <ErrorBoundaryRoute path={match.url} component={Customer} />
    </Switch>
    <PrivateRoute
      path={`${match.url}/:id/delete`}
      component={CustomerDeleteDialog}
      hasAnyAuthorities={[AUTHORITIES.MANAGER, AUTHORITIES.STAFF]}
    />
  </>
);

export default Routes;
