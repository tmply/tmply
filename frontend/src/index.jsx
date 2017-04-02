import React from 'react';
import {render} from 'react-dom';
import {AppContainer} from 'react-hot-loader';
import App from './ui-components/app';
import '../styles/index.scss';

render(<AppContainer><App/></AppContainer>, document.querySelector("#app"));

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
