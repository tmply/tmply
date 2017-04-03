import React from 'react';
import {render} from 'react-dom';
import {AppContainer} from 'react-hot-loader';
import App from './ui-components/app';
import '../styles/index.scss';
import getStats from './actions/get-stats-action';

render(<AppContainer><App/></AppContainer>, document.querySelector("#app"));

window.setInterval(function() {
	getStats()
}, 30*1000);
getStats()

if (module && module.hot) {
	module.hot.accept('./ui-components/app.jsx', () => {
		const App = require('./ui-components/app.jsx').default;
		render(
			<AppContainer>
				<App/>
			</AppContainer>,
			document.querySelector("#app")
		);
	});
}
