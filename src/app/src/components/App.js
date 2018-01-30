//This compoment handles the App template used on every page
import React from 'react';
import PropTypes from 'prop-types';
import {Route} from 'react-router-dom';
import Header from './common/Header';
import HomePage from './home/HomePage';
import AboutPage from './about/AboutPage';
import SubscriptionManagementPage from './subscription/SubscriptionManagementPage';
import styles from './AppStyles.js';
import {connect} from  'react-redux';

class App extends React.Component {
  render () {
    return (
    	<div className="container-fluid">
    		<Header
    			loading={this.props.loading}
    		/>
            <div style={styles.body}>
        		<Route exact path="/app" component={HomePage}/>
                <Route path="/app/subscriptions" component={SubscriptionManagementPage}/>
                <Route path="/app/about" component={AboutPage}/>
            </div>
    	</div>
    );
  }

  componentDidMount(){
        this.props.history.push('/app');
  };
}

App.propTypes = {
	loading: PropTypes.bool.isRequired
};

function mapStateToProps(state, ownProps){
    // debugger;
    return {
        loading: state.ajaxCallsInProgress > 0
    };
}

export default connect(mapStateToProps)(App);