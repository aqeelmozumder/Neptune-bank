import React from 'react';
import { Switch } from 'react-router-dom';
import ForeignCurrencies from './currency';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute path={match.url} component={ForeignCurrencies} />
    </Switch>
  </>
);

export default Routes;
