import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { getEntity } from './accounts.reducer';
import { IAccounts } from 'app/shared/model/accounts.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAccountsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class AccountsDetail extends React.Component<IAccountsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  callGoBack = () => {
    this.props.history.goBack();
  };

  render() {
    const { accountsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Accounts [<b>{accountsEntity.accountID}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="accountType">Account Type</span>
            </dt>
            <dd>{accountsEntity.accountType}</dd>
            <dt>
              <span id="balance">Balance</span>
            </dt>
            <dd>{accountsEntity.balance}</dd>
            <dt>
              <span id="activated">Activated</span>
            </dt>
            <dd>{accountsEntity.activated ? 'true' : 'false'}</dd>
            <dt>Customer</dt>
            <dd>{accountsEntity.userLogin ? accountsEntity.userLogin : ''}</dd>
            <dt>Branch</dt>
            <dd>{accountsEntity.branchAddress ? accountsEntity.branchAddress : ''}</dd>
          </dl>
          <Button onClick={this.callGoBack} color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/accounts/${accountsEntity.accountID}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ accounts }: IRootState) => ({
  accountsEntity: accounts.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AccountsDetail);
