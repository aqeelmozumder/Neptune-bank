import './footer.scss';

import React from 'react';

import { Col, Row } from 'reactstrap';

const Footer = props => (
  <div className="footer page-content">
    <Row>
      <Col md="12">
        <p className="footer-content">All Rights Reserved Neptune Bank@2019</p>
      </Col>
    </Row>
  </div>
);

export default Footer;
