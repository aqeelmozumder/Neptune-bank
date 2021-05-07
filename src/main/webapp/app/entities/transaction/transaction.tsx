import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table, Label } from 'reactstrap';
import { AvGroup, AvField, AvForm } from 'availity-reactstrap-validation';
import { ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { getEntities } from './transaction.reducer';
import { ITransaction } from 'app/shared/model/transaction.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT, AUTHORITIES } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { getSession } from 'app/shared/reducers/authentication';
export interface ITransactionsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}
import { IPaginationBaseCusState } from './PaginationUtils';
export type ITransactionsState = IPaginationBaseCusState;
import { hasAnyAuthority } from 'app/shared/auth/private-route';

export class Transactions extends React.Component<ITransactionsProps, ITransactionsState> {
  state: ITransactionsState = {
    // ...getSortState(this.props.location, ITEMS_PER_PAGE),
    activePage: 1,
    itemsPerPage: ITEMS_PER_PAGE,
    order: 'asc',
    sort: 'id',
    id: 0,
    userRoll: '',
    filteredTranData: [],
    startDate: null,
    endDate: null
  };

  componentDidMount() {
    this.getEntities();
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

  filterTransactions = (event, errors, values) => {
    if (errors.length === 0) {
      // console.log("FILTER", event, errors, values);
      const filteredTranData = this.props.transactionList.filter(i => {
        if (i.createdDate > values.startDate && i.createdDate < values.endDate) {
          return i;
        }
      });
      this.setState({
        filteredTranData: filteredTranData as [],
        startDate: values.startDate,
        endDate: values.endDate
      });

      // console.log('filter: ', filteredTranData)
    }
  };

  render() {
    const { transactionList, match, totalItems, isNotUser } = this.props;
    let filteredTranData = [];
    filteredTranData = !this.state.startDate || !this.state.endDate ? (transactionList as []) : this.state.filteredTranData;
    const filteredTrantotalItems = filteredTranData.length;
    return (
      <div>
        <h2 id="transaction-heading">
          Transactions
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Transfer Money
          </Link>
        </h2>
        <h5>
          <AvForm onSubmit={this.filterTransactions}>
            <AvGroup>
              <Label id="dateLabel" for="start-date">
                Start Date
              </Label>
              <AvField
                id="start-date"
                type="date"
                name="startDate"
                validate={{
                  required: { value: true, errorMessage: 'This field is required.' }
                }}
              />
            </AvGroup>
            <AvGroup>
              <Label id="dateLabel" for="end-date">
                End Date
              </Label>
              <AvField
                id="end-date"
                type="date"
                name="endDate"
                validate={{
                  required: { value: true, errorMessage: 'This field is required.' }
                }}
              />
            </AvGroup>
            <Button color="primary" id="filter-tran" type="submit">
              Filter
            </Button>
          </AvForm>
        </h5>
        <div className="table-responsive">
          {filteredTranData && filteredTranData.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('tranID')}>
                    Tran ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('createdDate')}>
                    Date <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('amount')}>
                    Amount <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('type')}>
                    Type <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('toAccount')}>
                    To Account <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('fromAccount')}>
                    From Account <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {filteredTranData.map((transaction: any, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${transaction.id}`} color="link" size="sm">
                        {transaction.tranID}
                      </Button>
                    </td>
                    <td>
                      <TextFormat type="date" value={transaction.createdDate} format={APP_LOCAL_DATE_FORMAT} />
                    </td>
                    <td>{transaction.amount}</td>
                    <td>{transaction.type}</td>
                    <td>{transaction.toAccount}</td>
                    <td>
                      {transaction.fromAccount ? <Link to={`accounts/${transaction.fromAccount}`}>{transaction.fromAccount}</Link> : ''}
                    </td>
                    {isNotUser && (
                      <td className="text-right">
                        <div className="btn-group flex-btn-group-container">
                          <Button tag={Link} to={`${match.url}/${transaction.tranID}`} color="info" size="sm">
                            <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${transaction.tranID}/edit`} color="primary" size="sm">
                            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${transaction.tranID}/delete`} color="danger" size="sm">
                            <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                          </Button>
                        </div>
                      </td>
                    )}
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Transactions found</div>
          )}
        </div>
        <div className={transactionList && transactionList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={this.state.activePage} total={filteredTrantotalItems} itemsPerPage={this.state.itemsPerPage} />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={this.state.activePage}
              onSelect={this.handlePagination}
              maxButtons={5}
              itemsPerPage={this.state.itemsPerPage}
              totalItems={filteredTrantotalItems}
            />
          </Row>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ transaction, authentication }: IRootState) => ({
  transactionList: transaction.entities,
  totalItems: transaction.totalItems,
  authentication: authentication ? authentication : {},
  isNotUser: hasAnyAuthority(authentication.account.authorities, [AUTHORITIES.ADMIN, AUTHORITIES.STAFF, AUTHORITIES.MANAGER])
});

const mapDispatchToProps = {
  getEntities,
  getSession
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Transactions);
