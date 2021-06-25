import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Button, Row, Table, Label } from 'reactstrap';
import { AvGroup, AvField, AvForm } from 'availity-reactstrap-validation';
import { JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { getEntities } from './currency.reducer';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { getSession } from 'app/shared/reducers/authentication';
export interface IForeignCurrenciesProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}
import { IPaginationBaseCusState } from './PaginationUtils';
export type IForeignCurrenciesState = IPaginationBaseCusState;

export class ForeignCurrencies extends React.Component<IForeignCurrenciesProps, IForeignCurrenciesState> {
  state: IForeignCurrenciesState = {
    // ...getSortState(this.props.location, ITEMS_PER_PAGE),
    activePage: 1,
    itemsPerPage: ITEMS_PER_PAGE,
    order: 'asc',
    sort: 'id',
    id: 0,
    userRoll: '',
    filteredCurrencyData: [],
    currencyName: null,
    exchangeRate: null
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

  filterCurrencies = (event, errors, values) => {
    if (errors.length === 0) {
      const filteredCurrencyData = this.props.currencyList.filter(i => {
        if (i.currencyName > values.currencyName && i.exchangeRate < values.exchangeRate) {
          return i;
        }
      });
      this.setState({
        filteredCurrencyData: filteredCurrencyData as [],
        currencyName: values.currencyName,
        exchangeRate: values.exchangeRate
      });
    }
  };

  render() {
    const { currencyList, match, totalItems } = this.props;
    let filteredCurrencyData = [];
    filteredCurrencyData = !this.state.currencyName || !this.state.exchangeRate ? (currencyList as []) : this.state.filteredCurrencyData;
    const filteredCurrencytotalItems = filteredCurrencyData.length;
    return (
      <div>
        <h2 id="currency-heading">Foreign Currencies</h2>
        <h5>
          <AvForm onSubmit={this.filterCurrencies}>
            <AvGroup>
              <Label id="currencyLabel" for="currency-name">
                Currency
              </Label>
              <AvField
                id="currency-name"
                name="currencyName"
                validate={{
                  required: { value: true, errorMessage: 'This field is required.' }
                }}
              />
            </AvGroup>
            <AvGroup>
              <Label id="rateLabel" for="rate">
                End Date
              </Label>
              <AvField
                id="rate"
                name="endDate"
                validate={{
                  required: { value: true, errorMessage: 'This field is required.' }
                }}
              />
            </AvGroup>
            <Button color="primary" id="filter-currency" type="submit">
              Filter
            </Button>
          </AvForm>
        </h5>
        <div className="table-responsive">
          {filteredCurrencyData && filteredCurrencyData.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('currencyName')}>
                    Currency <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('exchangeRate')}>
                    Exchange Rate <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {filteredCurrencyData.map((currency: any, i) => (
                  <tr key={`entity-${i}`}>
                    <td>{currency.name}</td>
                    <td>{currency.rate}</td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Currencies found</div>
          )}
        </div>
        <div className={currencyList && currencyList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={this.state.activePage} total={filteredCurrencytotalItems} itemsPerPage={this.state.itemsPerPage} />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={this.state.activePage}
              onSelect={this.handlePagination}
              maxButtons={5}
              itemsPerPage={this.state.itemsPerPage}
              totalItems={filteredCurrencytotalItems}
            />
          </Row>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ currency, authentication }: IRootState) => ({
  currencyList: currency.entities,
  totalItems: currency.totalItems
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
)(ForeignCurrencies);
