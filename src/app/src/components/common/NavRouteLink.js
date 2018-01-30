import React from 'react';
import PropTypes from 'prop-types';
import {Route,Link} from 'react-router-dom';

const NavRouteLink = ({ label, to, activeOnlyWhenExact }) => (
  <Route path={to} exact={activeOnlyWhenExact} children={({ match }) => (
    <span className={match ? 'nav-link-active' : 'nav-link'}>
      	<Link to={to}>{label}</Link>
    </span>
  )}/>
)

export default NavRouteLink;