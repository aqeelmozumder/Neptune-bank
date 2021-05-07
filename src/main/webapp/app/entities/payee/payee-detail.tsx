import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { getEntity } from './payee.reducer';
import { IPayee } from 'app/shared/model/payee.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPayeeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PayeeDetail extends React.Component<IPayeeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }
  callGoBack = () => {
    this.props.history.goBack();
  };
  render() {
    const { payeeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Payees [<b>{payeeEntity.payeeID}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="emailID">Email ID</span>
            </dt>
            <dd>{payeeEntity.emailID}</dd>
            <dt>
              <span id="firstName">First Name</span>
            </dt>
            <dd>{payeeEntity.firstName}</dd>
            <dt>
              <span id="lastName">Last Name</span>
            </dt>
            <dd>{payeeEntity.lastName}</dd>
            <dt>
              <span id="telephone">Telephone</span>
            </dt>
            <dd>{payeeEntity.telephone}</dd>
          </dl>
          <Button onClick={this.callGoBack} color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/payee/${payeeEntity.payeeID}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ payee }: IRootState) => ({
  payeeEntity: payee.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PayeeDetail);
