import React from 'react';
import PropTypes from 'prop-types';
import {Route} from 'react-router-dom';

import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import * as subscriptionActions from '../../actions/subscriptionActions';
import SubscriptionList from './SubscriptionList';


class SubscriptionManagementPage extends React.Component{

	constructor(props, context){
		super(props, context);	
	}
	componentDidUpdate(){
		this.props.actions.loadSubscriptions();
	}
	render(){
		const {subscriptions} = this.props;
		return (
			<div className="body-container">
				{subscriptions && subscriptions.length>0
					?<SubscriptionList subscriptions={subscriptions}/>
					:<p>No Subscription</p>
				}
			</div>
		);
	};

}

function mapStateToProps(state, ownProps){
	const subscriptions = state.subscription.subscriptions;
	return{
		subscriptions: subscriptions,
	};
}

function mapDispatchToProps(dispatch){
	return {
		actions: bindActionCreators(subscriptionActions, dispatch)
	};
}


export default connect(mapStateToProps, mapDispatchToProps)(SubscriptionManagementPage);