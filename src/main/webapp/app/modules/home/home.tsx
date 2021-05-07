import './home.scss';
import './home.css';
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
      <Row>
        <Col md="9">
          <div className="home-desc">
            <div className="titleMain">
              <h2>Save a little more. Win a little&nbsp;more.</h2>
              <span> Get a great interest rate of&nbsp;2.75%*&nbsp;by becoming a Client today.</span>
            </div>
            <div className="light-up-saving">
              <p>Light up your savings.</p>
              <div className="center-align">
                <span className="percent">3.50%</span>
              </div>
            </div>
            <div className="other-content">
              <Link className="strong" to="/LearnMore">
                Learn more{' '}
              </Link>
            </div>
          </div>
        </Col>
        <Col md="3" className="pad">
          <span className="hipster rounded" />
        </Col>
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
