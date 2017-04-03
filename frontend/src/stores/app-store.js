import {dispatcher, initStore} from '../util/mini-flux';

const MESSAGE_CLEAR_TIMEOUT = 3000;

class AppStore {
    constructor() {
        this.data = {
            loading: false,
            bucketKey: "",
            bucketValue: "",
            subscribeEnabled: true,
            publishEnabled: false,
            fetchEnabled: false,
            freeBuckets: "??",
            maxBuckets: "??",
            message: "",
            messageUpdateTime: 0,
            messageType: "info" // info, success, danger
        };
    }

    handleStatsReceived(ev) {
        this.data.maxBuckets = ev.data.maxBucketsCount;
        this.data.freeBuckets = ev.data.maxBucketsCount - ev.data.usedBucketsCount;
        this.update({});
    }

    handleMessageSet(ev) {
        this.data.message = ev.data.message || "";
        this.data.messageType = ev.data.type || "info";
        this.data.messageUpdateTime = new Date().getTime();
        this.update({});
        this.scheduleClearMessage();
    }

    handleBucketFetched(ev) {
        this.data.bucketKey = ev.data.bucketName || "";
        this.data.bucketValue = ev.data.data || "";
        this.data.message = "Bucket fetched.";
        this.data.messageType = "info";
        this.data.messageUpdateTime = new Date().getTime();
        this.update({});
        this.scheduleClearMessage();
    }

    handleBucketFetchFailed(ev) {
        this.data.message = ev.data.message || "Bucket fetch failed.";
        this.data.messageType = ev.data.messageType || "danger";
        this.data.messageUpdateTime = new Date().getTime();
        this.update({});
        this.scheduleClearMessage();
    }

    handlePublishBucketFailed(ev) {
        this.data.message = "Publish failed.";
        this.data.messageType = "danger";
        this.data.messageUpdateTime = new Date().getTime();
        this.update({});
        this.scheduleClearMessage();
    }

    handleBucketPublished(ev) {
        this.data.message = "Bucket published.";
        this.data.messageType = "info";
        this.data.messageUpdateTime = new Date().getTime();
        this.update({});
        this.scheduleClearMessage();
    }

    handleBucketKeyChanged(ev) {
        this.data.bucketKey = ev.value;
        this.update({});
    }

    handleBucketValueChanged(ev) {
        this.data.bucketValue = ev.value;
        this.update({});
    }

    handleDataChanged(ev) {
        this.data = ev.data;
        this.update({});
    }

    update(ev) {
        this.data.publishEnabled = this.data.bucketKey.length > 8 && this.data.bucketValue.length > 0;
        this.data.fetchEnabled = this.data.bucketKey.length > 8;
        this.notifyListeners(ev);
    }

    scheduleClearMessage() {
        window.setTimeout(function () {
            if (this.data.messageUpdateTime + MESSAGE_CLEAR_TIMEOUT <= new Date().getTime()) {
                this.data.message = "";
                this.data.messageType = "info";
                this.update({});
            }
        }.bind(this), MESSAGE_CLEAR_TIMEOUT);
    }
}

var appStore = initStore(new AppStore());

dispatcher.addStore(appStore);

export default appStore;
