import React from 'react';

export default class BucketActionsPanel extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        var publishEnabled = this.props.publishEnabled;
        var publishHandler = this.props.publishHandler;
        var fetchEnabled = this.props.fetchEnabled;
        var fetchHandler = this.props.fetchHandler;

        return (<div className="row buttonPanel">
                <button className="btn btn-primary"
                        onClick={publishHandler}
                        disabled={!publishEnabled} id="publishButton">Publish
                </button>
                <button className="btn btn-primary" disabled={!fetchEnabled} id="fetchButton"
                        onClick={fetchHandler}>Fetch
                </button>
                <img id="progress" src="/assets/images/progress.gif"/>
            </div>
        );
    }
}
