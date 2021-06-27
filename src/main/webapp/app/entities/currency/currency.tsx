import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Table } from 'reactstrap';
import { getSortState, IPaginationBaseState } from 'react-jhipster';
import { IRootState } from 'app/shared/reducers';
import { getEntities } from './currency.reducer';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IForeignCurrenciesProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IForeignCurrenciesState = IPaginationBaseState;

export class ForeignCurrencies extends React.Component<IForeignCurrenciesProps, IForeignCurrenciesState> {
  state: IForeignCurrenciesState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.getEntities();
  }

  getEntities = () => {
    this.props.getEntities();
  };

  render() {
    const { currencyList, match } = this.props;
    return (
      <div>
        <h2 id="currencies-heading">Foreign Currencies</h2>
        <div className="table-responsive">
          {currencyList && currencyList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>Currency</th>
                  <th>Exchange Rate</th>
                </tr>
              </thead>
              <tbody>
                {currencyList.map((currency: any, i) => (
                  <tr key={`entity-${i}`}>
                    <td>{currency.currencyName}</td>
                    <td>{currency.exchangeRate}</td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No currencies found</div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ currency, authentication }: IRootState) => ({
  currencyList: currency.entities,
  totalItems: currency.totalItems,
  entity: currency.entity
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ForeignCurrencies);
