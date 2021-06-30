import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { getEntities } from './customer.reducer';
import { ICustomer } from 'app/shared/model/customer.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT, AUTHORITIES } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { getSession } from 'app/shared/reducers/authentication';
import { hasAnyAuthority } from 'app/shared/auth/private-route';

export interface ICustomerProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type ICustomerState = IPaginationBaseState;

export class Customer extends React.Component<ICustomerProps, ICustomerState> {
  state: ICustomerState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.getEntities();
    this.props.getSession();
  }
  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => this.sortEntities()
    );
  };

  sortEntities() {
    this.getEntities();
    this.props.history.push(`${this.props.location.pathname}?page=${this.state.activePage}&sort=${this.state.sort},${this.state.order}`);
  }

  handlePagination = activePage => this.setState({ activePage }, () => this.sortEntities());

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { customerList, match, totalItems, isNotUser } = this.props;
    return (
      <div>
        <h2 id="customer-heading">
          Customer Details
          {customerList && customerList.length === 0 && (
            <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
              <FontAwesomeIcon icon="plus" />
              &nbsp; Request for a new Customer Account
            </Link>
          )}
        </h2>
        <div className="table-responsive">
          {customerList && customerList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('firstName')}>
                    First Name <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('lastName')}>
                    Last Name <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('telephone')}>
                    Telephone <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('gender')}>
                    Gender <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('address')}>
                    Address <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('city')}>
                    City <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('state')}>
                    State <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('zipCode')}>
                    zipCode <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    User ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {customerList.map((customer, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${customer.id}`} color="link" size="sm">
                        {customer.id}
                      </Button>
                    </td>
                    <td>{customer.firstName}</td>
                    <td>{customer.lastName}</td>
                    <td>{customer.telephone}</td>
                    <td>{customer.gender}</td>
                    <td>{customer.address}</td>
                    <td>{customer.city}</td>
                    <td>{customer.state}</td>
                    <td>{customer.zipCode}</td>
                    <td>{customer.userLogin ? customer.userLogin : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${customer.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        {isNotUser && (
                          <Button tag={Link} to={`${match.url}/${customer.id}/edit`} color="primary" size="sm">
                            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                          </Button>
                        )}
                        {isNotUser && (
                          <Button tag={Link} to={`${match.url}/${customer.id}/delete`} color="danger" size="sm">
                            <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                          </Button>
                        )}
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Customer Detail found</div>
          )}
        </div>
        <div className={customerList && customerList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={this.state.activePage} total={totalItems} itemsPerPage={this.state.itemsPerPage} />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={this.state.activePage}
              onSelect={this.handlePagination}
              maxButtons={5}
              itemsPerPage={this.state.itemsPerPage}
              totalItems={this.props.totalItems}
            />
          </Row>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ customer, authentication }: IRootState) => ({
  customerList: customer.entities,
  totalItems: customer.totalItems,
  isNotUser: hasAnyAuthority(authentication.account.authorities, [AUTHORITIES.ADMIN, AUTHORITIES.STAFF, AUTHORITIES.MANAGER])
});

const mapDispatchToProps = { getEntities, getSession };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Customer);
