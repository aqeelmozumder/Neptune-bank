import './contactus.scss';
import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';
import { IRootState } from 'app/shared/reducers';

export type IContactUsProp = StateProps;

export const ContactUs = (props: IContactUsProp) => {
  const { account } = props;

  return (
    <Row>
      <Col md="9">
        <h2>We're Here for You!</h2>
        <p className="custom">By Phone</p>
        <p className="custom">Give us a call 7 days a week at </p>
        <div>
          <Alert color="warning">1-800-876-4370</Alert>
          <p className="custom">Saving & Chequing </p>
          <Alert color="warning">Weekdays 8am - 8pm ET</Alert>
          <Alert color="warning">Weekends 9am - 5pm ET</Alert>
          <p className="custom">Investing </p>
          <Alert color="warning">Weekdays 8am - 8pm ET</Alert>
          <p className="custom">By Mail </p>
          <Alert color="warning">4390 Zeta Avenue East Toronto, Ontario</Alert>
        </div>
      </Col>
      <Col md="3" className="pad">
        <span className="hipster rounded" />
      </Col>
    </Row>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(ContactUs);
