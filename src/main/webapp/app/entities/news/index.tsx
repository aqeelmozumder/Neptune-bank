import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import PrivateRoute from 'app/shared/auth/private-route';
import News from './news';
import NewsDetail from './news-detail';
import NewsUpdate from './news-update';
import NewsDeleteDialog from './news-delete-dialog';
import { AUTHORITIES } from 'app/config/constants';

const Routes = ({ match }) => (
  <>
    <Switch>
      <PrivateRoute exact path={`${match.url}/new`} component={NewsUpdate} hasAnyAuthorities={[AUTHORITIES.MANAGER]} />
      <PrivateRoute exact path={`${match.url}/:id/edit`} component={NewsUpdate} hasAnyAuthorities={[AUTHORITIES.MANAGER]} />
      <PrivateRoute exact path={`${match.url}/:id`} component={NewsDetail} hasAnyAuthorities={[AUTHORITIES.MANAGER]} />
      <ErrorBoundaryRoute path={match.url} component={News} />
    </Switch>
    <PrivateRoute path={`${match.url}/:id/delete`} component={NewsDeleteDialog} hasAnyAuthorities={[AUTHORITIES.MANAGER]} />
  </>
);

export default Routes;
