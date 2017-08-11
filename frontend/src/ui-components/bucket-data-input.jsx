import React from 'react';

export default class BucketDataInput extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        var value = this.props.value;
        var changeHandler = this.props.changeHandler;

        return (<div className="form-group col-12">
            <textarea className="form-control" rows="6" placeholder="Your Data"
                      id="bucketDataInput"
                      value={state.data.bucketValue}
                      onChange={this._onBucketValueChanged}/>
            </div>);
    }
}
