import AppDispatcher from './app-dispatcher';
import {EventEmitter} from 'events';
import assign from 'object-assign';
import ActionTypes from './action-types';

const CHANGE_EVENT = 'change';

const AppStore = assign({}, EventEmitter.prototype, {

    data: {
        publisherData: {
            message: ''
        },
        receiverData: {
            bucketName: '',
            bucketData: '',
            message: ''
        }
    },

    getData: function() {
        return this.data;
    },

    emitChange: function () {
        this.emit(CHANGE_EVENT);
    },

    addChangeListener: function (callback) {
        this.on(CHANGE_EVENT, callback);
    },

    removeChangeListener: function (callback) {
        this.removeListener(CHANGE_EVENT, callback);
    },
});

AppStore.dispatchToken = AppDispatcher.register(function(action) {

    switch (action.type) {
        case ActionTypes.BUCKET_RECEIVED:
            AppStore.data.receiverData = {
                bucketName: action.data.bucketName,
                bucketData: action.data.data,
                message: ''
            };
            AppStore.emitChange();
            break;
        case ActionTypes.BUCKET_PUBLISHED:
            AppStore.data.publisherData.message = "Bucket " + action.data.bucketName + " published."
            AppStore.emitChange();
            break;
        case ActionTypes.BUCKET_NOT_FOUND:
            AppStore.data.receiverData.bucketData = '';
            AppStore.data.receiverData.message = "Bucket not found."
            AppStore.emitChange();
            break;
        default:
            break;
    }
});

export default AppStore;