import * as types from '../actions/actionTypes';
import initialState from './initialState';

export default function courseReducer(state = initialState.subscription, action){
	switch(action.type){
		case types.LOAD_SUBSCRIPTIONS_SUCCESS:
			return Object.assign({},state,{'subscriptions':action.subscriptions});
		case types.LOAD_RATEPLANS_SUCCESS:
			return Object.assign({},state,{'rateplans':action.rateplans});
		case types.GET_SIGNATURE_SUCCESS:
			return Object.assign({},state,{'signature':action.signature});
		case types.SUBSCRIBE_SUCCESS:
			return Object.assign({},state,{'subscribeResult':action.subscribeResult});
		default:
			return state;
	}
}