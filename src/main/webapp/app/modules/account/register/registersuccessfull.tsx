import React from 'react';
import { connect } from 'react-redux';
import { Row, Col } from 'reactstrap';
const divStyle = {
  'text-align': 'center'
};

export const registersuccessfull = () => (
  <div>
    <Row className="justify-content-center" style={divStyle}>
      <Col md="8">
        <h3 id="register-title">Your account has successfully been created.</h3>
        <h4 id="register-title1">Please login with your Credentials.</h4>
      </Col>
    </Row>
  </div>
);

export default connect(null)(registersuccessfull);
