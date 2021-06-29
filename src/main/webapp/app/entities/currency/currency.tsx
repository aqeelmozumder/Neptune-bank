import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Table, Label, Button, Row, Col } from 'reactstrap';
import { IPaginationBaseState } from 'react-jhipster';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { IRootState } from 'app/shared/reducers';
import { getEntities } from './currency.reducer';

export interface IForeignCurrenciesProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IForeignCurrenciesState = IPaginationBaseState;

const ForeignCurrencies = (props: IForeignCurrenciesProps) => {
  const [calculatedExchange, setCalculatedExchange] = useState(undefined);
  const calculateExchange = (event, errors, values) => {
    const fromRate = values.fromCurrency === 'CND' ? 1 : props.currencyList.find(c => c.currencyName === values.fromCurrency).exchangeRate;
    const toRate = values.toCurrency === 'CND' ? 1 : props.currencyList.find(c => c.currencyName === values.toCurrency).exchangeRate;
    const fromInCND = values.amount / fromRate;
    const result = fromInCND * toRate;
    setCalculatedExchange(`${result.toFixed(2)} ${values.toCurrency}`);
  };

  useEffect(() => {
    props.getEntities();
  }, []);

  return (
    <div>
      <h2 id="calculator-heading">Foreign Exchange Calculator</h2>
      <>
        <AvForm model={{}} onSubmit={calculateExchange}>
          <Row className="align-items-center">
            <Col sm="1">
              <AvGroup>
                <AvField
                  id="conversion-amount"
                  type="string"
                  className="form-control"
                  name="amount"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    number: { value: true, errorMessage: 'This field should be a number.' }
                  }}
                  placeholder="Amount"
                />
              </AvGroup>
            </Col>
            <Col sm="1">
              <AvGroup>
                <AvInput id="fromCurrency" type="select" className="form-control" name="fromCurrency" defaultValue="CND" required>
                  <option>CND</option>
                  {props.currencyList.map((currency: any, i) => (
                    <option>{currency.currencyName}</option>
                  ))}
                </AvInput>
              </AvGroup>
            </Col>
            <Label>To</Label>
            <Col sm="1">
              <AvGroup>
                <AvInput id="toCurrency" type="select" className="form-control" name="toCurrency" defaultValue="CND" required>
                  <option>CND</option>
                  {props.currencyList.map((currency: any, i) => (
                    <option>{currency.currencyName}</option>
                  ))}
                </AvInput>
              </AvGroup>
            </Col>
            <Label>Is</Label>
            <Col xs="1">
              <Label>{!calculatedExchange ? '...' : calculatedExchange}</Label>
            </Col>
          </Row>
          <Button color="primary" id="calculate-exchange" type="submit">
            Calculate
          </Button>
        </AvForm>
      </>
      <h2 id="currencies-heading">Foreign Currencies</h2>
      <div className="table-responsive">
        {props.currencyList && props.currencyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>Currency</th>
                <th>Exchange Rate</th>
              </tr>
            </thead>
            <tbody>
              {props.currencyList.map((currency: any, i) => (
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
};

const mapStateToProps = ({ currency, authentication }: IRootState) => ({
  currencyList: currency.entities,
  totalItems: currency.totalItems
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
