import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Customer from './customer';
import Accounts from './accounts';
import Payee from './payee';
import Transaction from './transaction';
import ForeignCurrencies from './currency';
import Branch from './branch';
import News from './news';
import FileUpload from './file-upload';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/customer`} component={Customer} />
      <ErrorBoundaryRoute path={`${match.url}/accounts`} component={Accounts} />
      <ErrorBoundaryRoute path={`${match.url}/payee`} component={Payee} />
      <ErrorBoundaryRoute path={`${match.url}/transaction`} component={Transaction} />
      <ErrorBoundaryRoute path={`${match.url}/currency`} component={ForeignCurrencies} />
      <ErrorBoundaryRoute path={`${match.url}/branch`} component={Branch} />
      <ErrorBoundaryRoute path={`${match.url}/file-upload`} component={FileUpload} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
