import React from 'react';

export default class StatusPanel extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        var freeBuckets = this.props.freeBuckets;
        var maxBuckets = this.props.maxBuckets;

        return (<div className="row">
                <div className="col-12 statsPanel">
                    <span id="freeBuckets">{freeBuckets}</span> of <span
                    id="maxBuckets">{maxBuckets}</span> bucket(s) free.
                </div>
            </div>);
    }
}
