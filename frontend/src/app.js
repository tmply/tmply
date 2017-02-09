require('./styles')
require('bootstrap/dist/css/bootstrap.css')

import React from 'react';
import ReactDOM from 'react-dom';
import UserService from './services/bucket-service';

import AppFrame from './components/app-frame';

ReactDOM.render(
    <AppFrame/>,
    document.getElementById("viewport")
);
