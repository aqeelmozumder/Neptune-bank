import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { getEntity } from './customer.reducer';
import { ICustomer } from 'app/shared/model/customer.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT, AUTHORITIES } from 'app/config/constants';
import { getSession } from 'app/shared/reducers/authentication';
import { hasAnyAuthority } from 'app/shared/auth/private-route';

export interface ICustomerDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CustomerDetail extends React.Component<ICustomerDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
    this.props.getSession();
  }
  callGoBack = () => {
    this.props.history.goBack();
  };

  render() {
    const { customerEntity, isNotUser } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Customer [<b>{customerEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="firstName">First Name</span>
            </dt>
            <dd>{customerEntity.firstName}</dd>
            <dt>
              <span id="lastName">Last Name</span>
            </dt>
            <dd>{customerEntity.lastName}</dd>
            <dt>
              <span id="telephone">Telephone</span>
            </dt>
            <dd>{customerEntity.telephone}</dd>
            <dt>
              <span id="gender">Gender</span>
            </dt>
            <dd>{customerEntity.gender}</dd>
            <dt>
              <span id="address">Address</span>
            </dt>
            <dd>{customerEntity.address}</dd>
            <dt>
              <span id="city">City</span>
            </dt>
            <dd>{customerEntity.city}</dd>
            <dt>
              <span id="state">State</span>
            </dt>
            <dd>{customerEntity.state}</dd>
            <dt>
              <span id="zipCode">zipCode</span>
            </dt>
            <dd>{customerEntity.zipCode}</dd>
            <dt>User ID</dt>
            <dd>{customerEntity.userLogin ? customerEntity.userLogin : ''}</dd>
          </dl>
          {!isNotUser && (
            <Button onClick={this.callGoBack} color="info">
              <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
            </Button>
          )}
          &nbsp;
          {isNotUser && (
            <React.Fragment>
              <Button onClick={this.callGoBack} color="info">
                <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
              </Button>
              <Button tag={Link} to={`/entity/customer/${customerEntity.id}/edit`} replace color="primary">
                <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
              </Button>
            </React.Fragment>
          )}
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ customer, authentication }: IRootState) => ({
  customerEntity: customer.entity,
  isNotUser: hasAnyAuthority(authentication.account.authorities, [AUTHORITIES.ADMIN, AUTHORITIES.STAFF, AUTHORITIES.MANAGER])
});

const mapDispatchToProps = { getEntity, getSession };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CustomerDetail);
