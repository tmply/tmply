import React from 'react';
import ReactDOM from 'react-dom';
import BucketService from '../services/bucket-service';
import extend from 'extend';

import AppStore from '../app-store';

const ReceiveForm = React.createClass({
    getInitialState: function () {
        return {
            bucketName: this.props.bucketName
        };
    },
    __onBucketNameChanged: function (ev) {
        var newState = extend({}, this.state, {
            bucketName: ev.target.value
        });
        this.setState(newState);
    },
    __onReceiveClicked: function (ev) {
        ev.preventDefault();
        ev.stopPropagation();
        BucketService.receiveBucket(this.state.bucketName);
    },
    __isReceiveEnabled: function() {
        return this.state.bucketName.length > 0;
    },
    render: function () {
        return <form>
            <div className="form-group">
                <input className="form-control" type="text" placeholder="Bucket Name" value={this.state.bucketName}
                       onChange={this.__onBucketNameChanged}/>
            </div>
            <div className="form-group">
                <input className="form-control" type="text" value={this.props.bucketData}
                       readOnly={true}/>
            </div>
            <div>
                {this.props.message}
            </div>
            <button className="btn btn-primary" disabled={!this.__isReceiveEnabled()}
                    onClick={this.__onReceiveClicked}>Fetch</button>
        </form>
    }
});

export default ReceiveForm;