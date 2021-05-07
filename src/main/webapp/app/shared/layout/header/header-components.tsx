import React from 'react';
import { NavItem, NavLink, NavbarBrand } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import appConfig from 'app/config/constants';

export const BrandIcon = props => (
  <div {...props} className="brand-icon">
    <img src="content/images/logo-jhipster.png" alt="Logo" />
  </div>
);

export const Brand = props => (
  <NavbarBrand tag={Link} to="/" className="brand-logo">
    <span className="brand-title">Neptune Bank</span>
  </NavbarBrand>
);

export const Home = props => (
  <NavItem>
    <NavLink tag={Link} to="/" className="d-flex align-items-center">
      <FontAwesomeIcon icon="home" />
      <span>Home</span>
    </NavLink>
  </NavItem>
);

export const Login = props => (
  <NavItem>
    <NavLink id="login-item" tag={Link} to="/login" className="d-flex align-items-center highlight-nav-item">
      <FontAwesomeIcon icon="sign-in-alt" />
      <span>Sign in</span>
    </NavLink>
  </NavItem>
);

export const Register = props => (
  <NavItem>
    <NavLink tag={Link} to="/register" className="d-flex align-items-center">
      <FontAwesomeIcon icon="sign-in-alt" />
      <span>Register</span>
    </NavLink>
  </NavItem>
);

export const AboutUs = props => (
  <NavItem>
    <NavLink tag={Link} to="/AboutUs" className="d-flex align-items-center">
      <span>About Us</span>
    </NavLink>
  </NavItem>
);

export const ContactUs = props => (
  <NavItem>
    <NavLink tag={Link} to="/ContactUs" className="d-flex align-items-center">
      <span>Contact Us</span>
    </NavLink>
  </NavItem>
);

export const LoanApproval = props => (
  <NavItem>
    <NavLink tag={Link} to="/LCNonApproved" className="d-flex align-items-center">
      <span>Account Approval</span>
    </NavLink>
  </NavItem>
);

export const OurBranches = props => (
  <NavItem>
    <NavLink tag={Link} to="/entity/branch" className="d-flex align-items-center">
      <span>Our Branches</span>
    </NavLink>
  </NavItem>
);

export const ManagePayees = props => (
  <NavItem>
    <NavLink tag={Link} to="/entity/payee" className="d-flex align-items-center">
      <span>Manage Payees</span>
    </NavLink>
  </NavItem>
);

export const NewsUpdates = props => (
  <NavItem>
    <NavLink tag={Link} to="/news" className="d-flex align-items-center">
      <span>News & Updates</span>
    </NavLink>
  </NavItem>
);

export const DocVerification = props => (
  <NavItem>
    <NavLink tag={Link} to="/entity/file-upload" className="d-flex align-items-center">
      <span>Document Verification</span>
    </NavLink>
  </NavItem>
);
