import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { ICustomer } from 'app/shared/model/customer.model';
import { getEntities as getCustomers } from 'app/entities/customer/customer.reducer';
import { IBranch } from 'app/shared/model/branch.model';
import { getEntities as getBranches } from 'app/entities/branch/branch.reducer';
import { getEntity, updateEntity, createEntity, reset } from './accounts.reducer';
import { IAccounts } from 'app/shared/model/accounts.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { getSession } from 'app/shared/reducers/authentication';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import { AUTHORITIES } from 'app/config/constants';

export interface IAccountsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IAccountsUpdateState {
  isNew: boolean;
  customerID: string;
  branchID: string;
}

export class AccountsUpdate extends React.Component<IAccountsUpdateProps, IAccountsUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      customerID: '0',
      branchID: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getCustomers();
    this.props.getBranches();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { accountsEntity } = this.props;
      const entity = {
        ...accountsEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.goBack();
  };

  callGoBack = () => {
    this.props.history.goBack();
  };

  render() {
    const { accountsEntity, customers, branches, loading, updating, isNotUser } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            {isNotUser ? (
              <h2 id="neptunebank.accounts.home.createOrEditLabel">Create or edit an Account</h2>
            ) : (
              <h2 id="neptunebank.accounts.home.createOrEditLabel">Request For a New Account</h2>
            )}
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : accountsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="accounts-accountID">Account ID</Label>
                    <AvInput id="accounts-accountID" type="text" className="form-control" name="accountID" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="accountTypeLabel" for="accounts-accountType">
                    {' '}
                    Account Type{' '}
                  </Label>
                  <AvInput
                    id="accounts-accountType"
                    value={this.state.isNew ? 'Savings' : null}
                    type="select"
                    name="accountType"
                    className="form-control"
                    required
                  >
                    <option value="Savings">Savings</option>
                    <option value="Checking">Checking</option>
                    <option value="Credit">Credit</option>
                    <option value="Loan">Loan</option>
                  </AvInput>
                  <AvFeedback>This field is required.</AvFeedback>
                </AvGroup>
                <AvGroup>
                  {isNotUser ? (
                    <Label id="balanceLabel" for="accounts-balance">
                      {' '}
                      Balance{' '}
                    </Label>
                  ) : (
                    <Label id="balanceLabel" for="accounts-balance">
                      {' '}
                      Amount{' '}
                    </Label>
                  )}

                  <AvField
                    id="accounts-balance"
                    type="string"
                    className="form-control"
                    name="balance"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="accounts-customerID">Customer</Label>
                  <AvInput id="accounts-customerID" type="select" className="form-control" name="customerID" required>
                    {customers ? <option value="">-- Select --</option> : null}
                    {customers
                      ? customers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.userLogin}
                          </option>
                        ))
                      : null}
                  </AvInput>
                  <AvFeedback>This field is required.</AvFeedback>
                </AvGroup>
                <AvGroup>
                  <Label for="accounts-branchID"> Branch </Label>
                  <AvInput id="accounts-branchID" type="select" className="form-control" name="branchID" required>
                    {branches ? <option value="">-- Select --</option> : null}
                    {branches
                      ? branches.map(otherEntity => (
                          <option value={otherEntity.branchID} key={otherEntity.branchID}>
                            {otherEntity.address}
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
  customers: storeState.customer.entities,
  branches: storeState.branch.entities,
  accountsEntity: storeState.accounts.entity,
  loading: storeState.accounts.loading,
  updating: storeState.accounts.updating,
  updateSuccess: storeState.accounts.updateSuccess,
  isNotUser: hasAnyAuthority(storeState.authentication.account.authorities, [AUTHORITIES.ADMIN, AUTHORITIES.STAFF, AUTHORITIES.MANAGER])
});

const mapDispatchToProps = {
  getCustomers,
  getBranches,
  getEntity,
  updateEntity,
  createEntity,
  reset,
  getSession
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AccountsUpdate);
