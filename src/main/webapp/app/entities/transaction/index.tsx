import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import Transactions from './transaction';
import TransactionsDetail from './transaction-detail';
import TransactionsUpdate from './transaction-update';
import TransactionsDeleteDialog from './transaction-delete-dialog';
import PrivateRoute from 'app/shared/auth/private-route';
import { AUTHORITIES } from 'app/config/constants';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TransactionsUpdate} />
      <PrivateRoute
        exact
        path={`${match.url}/:id/edit`}
        component={TransactionsUpdate}
        hasAnyAuthorities={[AUTHORITIES.MANAGER, AUTHORITIES.STAFF]}
      />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TransactionsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Transactions} />
    </Switch>
    <PrivateRoute path={`${match.url}/:id/delete`} component={TransactionsDeleteDialog} hasAnyAuthorities={[AUTHORITIES.MANAGER]} />
  </>
);

export default Routes;
