import './aboutus.scss';
import './aboutUs.css';
import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';
import { IRootState } from 'app/shared/reducers';

export type IAboutUsProp = StateProps;

export const AboutUs = (props: IAboutUsProp) => {
  const { account } = props;

  return (
    <React.Fragment>
      <Row className="MainMarging">
        <h2 className="sheader">Neptune Bank at a glance</h2>
        <div>
          <div className="abt-us-desc">
            <p>
              Neptune Bank is a wholly on-line banking institution.It is a{' '}
              <span className="strong">values-based financial cooperative</span>
              serving the needs of its <span className="strong">more than 14,986 member-owners</span> and their communities.
            </p>
            <p>
              Neptune isn't like a typical bank. For one thing, we provide most of our services online, which means we can pass those
              savings on to you. One way we do this is by paying you more interest on your money without charging you unfair fees. But
              that's just the start.
            </p>
            <p>
              Life is busy and complicated enough, right? No one wants their banking to be one more complicated thing to worry about. Your
              money should work as hard as you do, and you shouldn’t have to work hard to make that happen. It’s why we make it simple to
              save, simple to understand, and simple to do all of your everyday banking with us. It’s what we call Forward Banking.
            </p>
          </div>
          <div className="exec-team">
            <h2 className="exec-team-head">Executive leadership team</h2>
            <div className="display-table">
              <div>
                <span>Edith Cressvan</span>
                <span>President and Chief Executive Officer</span>
              </div>
              <div>
                <span>Paula Windsor</span>
                <span>Chief Financial Officer</span>
              </div>
              <div>
                <span>Kamal Awad</span>
                <span>Senior Vice President, Digital Solutions & Business Technology</span>
              </div>
              <div>
                <span>Moussa Konate</span>
                <span>Senior Vice President, Business Transformation</span>
              </div>
              <div>
                <span>Josiane Hayes</span>
                <span>Chief Member Services Officer, Member Experience & Community Engagement</span>
              </div>
              <div>
                <span>Angela Wu</span>
                <span>Chief Marketing Officer</span>
              </div>
              <div>
                <span>Leslie Chang</span>
                <span>Senior Vice President, Enterprise Risk and Chief Risk</span>
              </div>
              <div>
                <span>Paulo Costa</span>
                <span>Chief Technology Officer</span>
              </div>
            </div>
          </div>

          <div className="vis-val">
            <h2 className="vis-val-head">Vision and values</h2>
            <div>
              <div className="vis-val-pts">
                <span>Vision</span>
                <p>
                  At Neptune, our vision is to redefine wealth in a way that furthers the financial, social and environmental well-being of
                  our members and their communities.
                </p>
              </div>
              <div className="vis-val-pts">
                <span>Make Good Money</span>
                <p>
                  Good Money is about making your money work harder for you while doing good things in your community and the world. It's
                  connecting members to an innovative investment model that supports a stronger&nbsp;local economy, a healthier population,
                  and a more vibrant, prosperous community.
                </p>
              </div>
              <div className="vis-val-pts">
                <span>Values-based banking</span>
                <p>
                  Our commitment to building an accountable, sustainable, supportive financial model guides everything we do, from our work
                  with the&nbsp;Global Alliance for Banking on Values&nbsp;to our&nbsp;Living Wage&nbsp;and&nbsp;ethical principles to
                  our&nbsp;environmental practices.
                </p>
              </div>
            </div>
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

export default connect(mapStateToProps)(AboutUs);
