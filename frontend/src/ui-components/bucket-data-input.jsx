import React from 'react';

export default class BucketDataInput extends React.Component {
    render() {
        var value = this.props.value;
        var changeHandler = this.props.changeHandler;

        return (<div className="form-group col-12">
            <textarea className="form-control" rows="6" placeholder="Your Data"
                      id="bucketDataInput"
                      value={value}
                      onChange={changeHandler}/>
            </div>);
    }
}
