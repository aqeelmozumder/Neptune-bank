import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField, AvCheckboxGroup, AvCheckbox } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { IAccounts } from 'app/shared/model/accounts.model';
import { getEntities as getAccounts } from 'app/entities/accounts/accounts.reducer';
import { getEntities as getPayees } from 'app/entities/payee/payee.reducer';
import { getEntity, updateEntity, createEntity, reset, payeeTransfer } from './transaction.reducer';
import { ITransaction } from 'app/shared/model/transaction.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITransactionsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITransactionsUpdateState {
  isNew: boolean;
  toAccountsId: string;
  isPayee: boolean;
}

export class TransactionsUpdate extends React.Component<ITransactionsUpdateProps, ITransactionsUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      toAccountsId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id,
      isPayee: false
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  callGoBack = () => {
    this.props.history.goBack();
  };

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getAccounts();
    this.props.getPayees();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { transactionsEntity } = this.props;
      values.toAccount = values.payeeCheckBox.length === 0 ? values.toAccount1 : values.toAccount;
      const entity = {
        ...transactionsEntity,
        ...values
      };
      if (this.state.isNew) {
        if (this.state.isPayee) {
          this.props.payeeTransfer(entity);
        } else {
          this.props.createEntity(entity);
        }
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleCheckboxChange = event => this.setState({ isPayee: !this.state.isPayee });

  handleClose = () => {
    this.props.history.push('/entity/transaction');
  };

  render() {
    const { transactionsEntity, accounts, loading, updating, payee } = this.props;
    const { isNew, isPayee } = this.state;
    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="neptunebank.transaction.home.createOrEditLabel">Transfer Money</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : transactionsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label id="tranIDLabel" for="transaction-tranID">
                      Tran ID
                    </Label>
                    <AvField
                      id="transaction-tranID"
                      type="text"
                      name="tranID"
                      validate={{
                        required: { value: true, errorMessage: 'This field is required.' }
                      }}
                      readOnly
                    />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="amountLabel" for="transaction-amount">
                    Transaction Amount
                  </Label>
                  <AvField
                    id="transaction-amount"
                    type="string"
                    className="form-control"
                    name="amount"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <AvCheckboxGroup inline name="payeeCheckBox" label="Transfer to ?">
                    <AvCheckbox label="Payee" value="P" onChange={this.handleCheckboxChange} />
                  </AvCheckboxGroup>
                </AvGroup>
                {!isPayee ? (
                  <AvGroup>
                    <Label id="toAccountId" for="transaction-toAccount">
                      To Account
                    </Label>
                    <AvField
                      id="transaction-toAccount1"
                      type="text"
                      name="toAccount1"
                      validate={{
                        required: { value: true, errorMessage: 'This field is required.' },
                        number: { value: true, errorMessage: 'This field should be a number.' }
                      }}
                    />
                  </AvGroup>
                ) : (
                  <AvGroup>
                    <Label for="transaction-toAccount">Select Payee</Label>
                    <AvInput id="transaction-toAccount" type="select" className="form-control" name="toAccount" required>
                      {payee ? <option value="">-- Select --</option> : null}
                      {payee
                        ? payee.map(otherEntity => (
                            <option value={otherEntity.emailID} key={otherEntity.emailID}>
                              {otherEntity.emailID}
                            </option>
                          ))
                        : null}
                    </AvInput>
                    <AvFeedback>This field is required.</AvFeedback>
                  </AvGroup>
                )}
                <AvGroup>
                  <Label for="transaction-fromAccount">Transaction from Account</Label>
                  <AvInput id="transaction-fromAccount" type="select" className="form-control" name="fromAccount" required>
                    <option value="">-- Select --</option>
                    {accounts
                      ? accounts.map(otherEntity => (
                          <option value={otherEntity.accountID} key={otherEntity.accountID}>
                            {otherEntity.accountID}
                          </option>
                        ))
                      : null}
                  </AvInput>
                  <AvFeedback>This field is required.</AvFeedback>
                </AvGroup>
                <Button onClick={this.callGoBack} color="info">
                  <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  accounts: storeState.accounts.entities,
  payee: storeState.payee.entities,
  transactionsEntity: storeState.transaction.entity,
  loading: storeState.transaction.loading,
  updating: storeState.transaction.updating,
  updateSuccess: storeState.transaction.updateSuccess
});

const mapDispatchToProps = {
  getAccounts,
  getEntity,
  updateEntity,
  createEntity,
  payeeTransfer,
  getPayees,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TransactionsUpdate);
