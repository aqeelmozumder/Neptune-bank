import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { getEntity } from './transaction.reducer';
import { ITransaction } from 'app/shared/model/transaction.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITransactionsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TransactionsDetail extends React.Component<ITransactionsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  callGoBack = () => {
    this.props.history.goBack();
  };
  render() {
    const { transactionsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Transactions [<b>{transactionsEntity.tranID}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="createdDate">Date</span>
            </dt>
            <dd>
              <TextFormat value={transactionsEntity.createdDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="type">Type</span>
            </dt>
            <dd>{transactionsEntity.type}</dd>
            <dt>
              <span id="amount">Amount</span>
            </dt>
            <dd>{transactionsEntity.amount}</dd>
            <dt>
              <span id="toAccountNumber">To Account</span>
            </dt>
            <dd>{transactionsEntity.toAccount}</dd>
            <dt>From Account</dt>
            <dd>{transactionsEntity.fromAccount ? transactionsEntity.fromAccount : ''}</dd>
          </dl>
          <Button onClick={this.callGoBack} color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/transaction/${transactionsEntity.tranID}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ transaction }: IRootState) => ({
  transactionsEntity: transaction.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TransactionsDetail);
