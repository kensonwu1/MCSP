import React from 'react';
import { Link } from 'react-router-dom';
import NavRouteLink from '../common/NavRouteLink';

class HomePage extends React.Component{
	render(){
		let notifyEndpoint = location.protocol+'//'+location.host+'/cloud/providers/microsoft/event/notify';
		return (
			<div className="jumbotron">
				<h1>MCSP integration Demo</h1>
				<p>Microsfot Cloud Solution integration</p>
				<p>Triger microsoft event:</p>
				<ul>
					<li>Open <a href="https://notifyemulator.azurewebsites.net/" target="_blank">Microsfot Notification Emulator</a></li>
					<li>Click 'Load Test Data' button</li>
					<li>Replay Notify Url with:{notifyEndpoint}</li>
					<li>Click 'Notify' button</li>
				</ul>
				<p></p>
				<Link to="/app/about" className="btn btn-primary btn-lg">Learn More</Link>
			</div>

		);
	}
}

export default HomePage;