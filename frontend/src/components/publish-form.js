import React from 'react';
import ReactDOM from 'react-dom';
import extend from 'extend';
import BucketService from '../services/bucket-service';

const PublishForm = React.createClass({
    getInitialState: function () {
        return {bucketName: '', bucketData: ''};
    },
    __onBucketNameChanged: function (ev) {
        var newState = extend({}, this.state, {
            bucketName: ev.target.value
        });
        this.setState(newState);
    },
    __onBucketDataChanged: function (ev) {
        var newState = extend({}, this.state, {
            bucketData: ev.target.value
        });
        this.setState(newState);
    },
    __onPublishClicked: function (ev) {
        ev.preventDefault();
        ev.stopPropagation();
        BucketService.publishBucket({
            bucketName: this.state.bucketName,
            bucketData: this.state.bucketData
        });
    },
    __isPublishEnabled: function() {
        return this.state.bucketName.length >= 8;
    },
    render: function () {

        return <form>
            <div className="form-group">
                <input className="form-control" type="text" placeholder="Choose a Bucket Name (at least 8 characters long)" value={this.state.bucketName}
                       onChange={this.__onBucketNameChanged}/>
            </div>
            <div className="form-group">
                <input className="form-control" type="text" placeholder="Your Data" value={this.state.bucketData}
                       onChange={this.__onBucketDataChanged}/>
            </div>
            <div>
                {this.props.message}
            </div>
            <button className="btn btn-primary" disabled={!this.__isPublishEnabled()} onClick={this.__onPublishClicked}>Publish</button>
        </form>
    }
});

export default PublishForm;