import React from 'react';
import '../../styles/index.scss';
import changeBucketKey from '../actions/change-bucket-key-action';
import changeBucketValue from '../actions/change-bucket-value-action';
import publishBucket from '../actions/publish-bucket-action';
import fetchBucket from '../actions/fetch-bucket-action';
import ifDefined from '../util/if-defined';
import appStore from '../stores/app-store';

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
                <div className="form-group col-12">
                    <input className="form-control" type="text"
                           placeholder="Choose a Bucket Name (at least 8 characters long)"
                           value={state.data.bucketKey}
                           id="bucketNameInput" onChange={this._onBucketKeyChanged}/>
                </div>
                <div className="form-group col-12">
            <textarea className="form-control" rows="6" placeholder="Your Data"
                      id="bucketDataInput"
                      value={state.data.bucketValue}
                      onChange={this._onBucketValueChanged}/>
                </div>
            </div>
            <div className="row">
                <div className="col-12">
                    <span id="messagePanel"
                          className={"col-12 text-" + state.data.messageType}>{state.data.message}</span>
                </div>
            </div>
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
            <div className="row">
                <div className="col-12 statsPanel">
                    <span id="freeBuckets">{state.data.freeBuckets}</span> of <span
                    id="maxBuckets">{state.data.maxBuckets}</span> bucket(s) free.
                </div>
            </div>
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
