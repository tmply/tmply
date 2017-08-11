import React from 'react';
import '../../styles/index.scss';
import changeBucketKey from '../actions/change-bucket-key-action';
import changeBucketValue from '../actions/change-bucket-value-action';
import publishBucket from '../actions/publish-bucket-action';
import fetchBucket from '../actions/fetch-bucket-action';
import appStore from '../stores/app-store';
import StatusPanel from './status-panel';
import MessagePanel from './message-panel';
import BucketKeyInput from './bucket-key-input';
import BucketDataInput from './bucket-data-input';

export default class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            data: appStore.data
        };
        this._onDataChanged = this._onDataChanged.bind(this);
        this._onBucketKeyChanged = this._onBucketKeyChanged.bind(this);
        this._onBucketValueChanged = this._onBucketValueChanged.bind(this);
        this._onPublishClicked = this._onPublishClicked.bind(this);
        this._onFetchClicked = this._onFetchClicked.bind(this);
    }

    _onPublishClicked(ev) {
        ev.stopPropagation();
        ev.preventDefault();
        publishBucket({
            bucketName: appStore.data.bucketKey,
            data: appStore.data.bucketValue
        });
    }

    _onFetchClicked(ev) {
        ev.stopPropagation();
        ev.preventDefault();
        fetchBucket(appStore.data.bucketKey);
    }

    _onBucketKeyChanged(ev) {
        ev.stopPropagation();
        ev.preventDefault();
        changeBucketKey(ev.target.value);
    }

    _onBucketValueChanged(ev) {
        ev.stopPropagation();
        ev.preventDefault();
        changeBucketValue(ev.target.value);
    }

    _onDataChanged() {
        this.setState({data: appStore.data})
    }

    componentDidMount() {
        appStore.addListener(this._onDataChanged);
    }

    componentWillUnmount() {
        appStore.removeListener(this._onDataChanged);
    }

    render() {
        var state = this.state;

        return <div className="container">
            <div className="row">
                <h1 className="col-12">Tmply</h1>
                <h4 className="col-12">Temporary Variable as a Service</h4>
                <BucketKeyInput value={state.data.bucketKey}
                             changeHandler={this._onBucketKeyChanged}/>
                <BucketDataInput value={state.data.bucketValue}
                                changeHandler={this._onBucketValueChanged}/>
            </div>
            <MessagePanel message={state.data.message}
                          messageType={state.data.messageType}/>
            <div className="row buttonPanel">
                <button className="btn btn-primary"
                        onClick={this._onPublishClicked}
                        disabled={!state.data.publishEnabled} id="publishButton">Publish
                </button>
                <button className="btn btn-primary" disabled={!state.data.fetchEnabled} id="fetchButton"
                        onClick={this._onFetchClicked}>Fetch
                </button>
                <img id="progress" src="/assets/images/progress.gif"/>
            </div>
            <StatusPanel maxBuckets="state.data.maxBuckets" freeBuckets="state.data.freeBuckets" />
            <div className="row" id="app">
            </div>
            <footer className="row">
                For more infos visit <a target="_blank" href="https://github.com/cbuschka/tmply" id="github-link">tmply
                on
                github</a>.
            </footer>
        </div>;
    }
}
