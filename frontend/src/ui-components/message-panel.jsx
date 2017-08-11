import React from 'react';

export default class MessagePanel extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        var message = this.props.message;
        var messageType = this.props.messageType;

        return (<div className="row">
                <div className="col-12">
                    <span id="messagePanel"
                          className={"col-12 text-" + messageType}>{message}</span>
                </div>
            </div>);
    }
}
