import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import toastr from 'toastr';
import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';
import * as subscriptionActions from '../../actions/subscriptionActions';

class SubscriptionItem extends React.Component{

	render(){
		const {subscription} = this.props;

		return (
			<tr>
				<td>{subscription.subscriptionId}</td>
				<td>{subscription.productId}</td>
				<td>{subscription.skuId}</td>
				<td>{subscription.quantity}</td>
				<td>{subscription.beneficiaryId}</td>
				<td>{subscription.purchaserId}</td>
				<td>{subscription.dateCreated}</td>
				<td>{subscription.status.name}</td>
			</tr>
		);
	};
};


function mapStateToProps(state, ownProps){
	// debugger;
	return{
	};
}

function mapDispatchToProps(dispatch){
	return {
		actions: bindActionCreators(subscriptionActions, dispatch)
	};
}

const connectedStateAndProps = connect(mapStateToProps, mapDispatchToProps);
export default connectedStateAndProps(SubscriptionItem);
