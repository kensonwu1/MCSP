import React from 'react';

class AboutPage extends React.Component{
	render(){
		return (
			<div className="body-container">
				<h1>About</h1>
				<h3>Flow:</h3>
					<ul>
						<li>Microsoft will send events(provision/update/deprovision) to this App.</li>
						<li>This App will call corresponding Charon Api to create/amend/cancel subscription.</li>
					</ul>
				<h3>Architecture:</h3>
					<ul>
						<li>Front-end: React+Redux</li>
						<li>Back-end: Grails+Groovy</li>
					</ul>
			</div>
		);
	}
}

export default AboutPage;