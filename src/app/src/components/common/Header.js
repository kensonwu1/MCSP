import React from 'react';
import PropTypes from 'prop-types';
import {Link} from 'react-router-dom';
import styles from './HeaderStyles';
import LoadingDots from './LoadingDots';
import NavRouteLink from './NavRouteLink';

class Header extends React.Component{

	render(){
		const {loading} = this.props;
		return (
			<div style={styles.header}>
				<nav style={styles.nav}>
					<NavRouteLink to="/app/" label="Home" activeOnlyWhenExact={true}/>
					{" | "}
					<NavRouteLink to="/app/subscriptions" label="Subscriptions"/>
					{" | "}
					<NavRouteLink to="/app/about" label="About"/>
					{loading && <LoadingDots interval={100} dots={20}/>}
				</nav>
			</div>
		);
	}
}

Header.propTypes ={
	loading: PropTypes.bool.isRequired
}

export default Header;