import './LearnMore.css';
import React from 'react';
import { Link } from 'react-router-dom';

import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';

export type IHomeProp = StateProps;

export const Home = (props: IHomeProp) => {
  const { account } = props;

  return (
    <React.Fragment>
      <Row className="MainMarging">
        <div className="home-desc">
          <div className="other-content">
            <p>Get 3.50% interest rate</p>
            <p>with our 3-Year term deposit.</p>
            <p>
              For a limited time, get a bonus 0.10% on the 3.40% 3-year non-redeemable term deposit when investing in a registered term.{' '}
            </p>
            <ul className="terms">
              <li>Only $500 to get started </li>
              <li>Guaranteed 3.50% interest rate on registered terms or 3.40% on Non-registered terms. </li>
              <li>Available in Non-Registered and Registered Accounts such as an RRSP, RRIF, RESP or TFSA </li>
            </ul>
            <span className="contact">Call us at 1.800.617.8763 to get started. </span>
          </div>
        </div>
      </Row>
    </React.Fragment>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Home);
