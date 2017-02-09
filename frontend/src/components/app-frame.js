import React from 'react';
import PublishForm from './publish-form';
import ReceiveForm from './receive-form';
import {Tab, Tabs, TabList, TabPanel} from 'react-tabs';

import AppStore from '../app-store';

const AppFrame = React.createClass({
    _onDataChanged: function () {
        this.forceUpdate();
    },
    componentDidMount: function () {
        AppStore.addChangeListener(this._onDataChanged);
    },
    componentWillUnmount: function () {
        AppStore.removeChangeListener(this._onDataChanged);
    },
    getInitialState: function () {
        return {selectedTab: 0};
    },
    __onTabSelected: function (index) {
        this.state.selectedTab = index;
        this.forceUpdate();
    },
    render: function () {
        var receiverData = AppStore.getData().receiverData;
        var publisherData = AppStore.getData().publisherData;

        return <div className="row">
            <h1 className="col-12">Tmply</h1>
            <h4 className="col-12">Temporary Variable as a Service</h4>
            <Tabs className="col-12" selectedIndex={this.state.selectedTab}
                  onSelect={this.__onTabSelected}>
                <TabList>
                    <Tab>Publish</Tab>
                    <Tab>Fetch</Tab>
                </TabList>
                <TabPanel>
                    <PublishForm message={publisherData.message}/>
                </TabPanel>
                <TabPanel>
                    <ReceiveForm bucketName={receiverData.bucketName}
                                 bucketData={receiverData.bucketData}
                                 message={receiverData.message}/>
                </TabPanel>
            </Tabs>
        </div>
    }
});

export default AppFrame;
