import * as types from './actionTypes';
// import subscriptionApi from '../api/mockSubscriptionApi.js';
import subscriptionApi from '../api/subscriptionApi.js';
import {beginAjaxCall, ajaxCallError} from './ajaxStatusActions';


export function loadSubscriptionsSuccess(subscriptions){
	// debugger;
	return {type: types.LOAD_SUBSCRIPTIONS_SUCCESS, subscriptions};
}

export function loadSubscriptions(){
	return function(dispatch){
		dispatch(beginAjaxCall());
		return subscriptionApi.getAllSubscriptions().then(subscriptions => {
			dispatch(loadSubscriptionsSuccess(subscriptions));
		}).catch(error => {
			throw(error);
		});
	};
}








