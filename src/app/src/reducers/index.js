import {combineReducers} from 'redux';
import subscription from './subscriptionReducer';
import ajaxCallsInProgress from './ajaxStatusReducer';

const rootReducer = combineReducers({
	subscription,
	ajaxCallsInProgress
});


export default rootReducer;
