import React from 'react';
import PropTypes from 'prop-types';
import SubscriptionItem from './SubscriptionItem';
import toastr from 'toastr';

class SubscriptionList extends React.Component{
	constructor(props, context){
		super(props, context);

	}

	render(){
		const {subscriptions} = this.props;

		return (
			<table className="table">
				<thead>
					<tr>
						<th>Subscription ID</th>
						<th>Product ID</th>
						<th>Sku ID</th>
						<th>License Count</th>
						<th>Beneficiary ID</th>
						<th>Purchaser ID</th>
						<th>Create Time</th>
						<th>Status</th>
					</tr>
				</thead>
				<tbody>
					 {subscriptions.map(subscription => 
					 	<SubscriptionItem key={subscription.subscriptionId} subscription={subscription}/>
					 )}
				</tbody>
			</table>
		);
	};
}


export default SubscriptionList;