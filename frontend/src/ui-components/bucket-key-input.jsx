import React from 'react';

export default class BucketKeyInput extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        var value = this.props.value;
        var changeHandler = this.props.changeHandler;

        return (<div className="form-group col-12">
                <input className="form-control" type="text"
                       placeholder="Choose a Bucket Name"
                       value={value}
                       id="bucketNameInput" onChange={changeHandler}/>
            </div>
        );
    }
}
