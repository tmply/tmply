import {dispatcher} from '../util/mini-flux';
import BucketResource from '../api/bucket-resource';

export default function fetchBucket(bucketName) {
    BucketResource.fetchBucket(bucketName,
        function (result) {
            dispatcher.dispatch({
                type: "bucketFetched",
                data: result
            })
        }, function (resp) {
            if( resp.statusCode === 404 ) {
                dispatcher.dispatch({
                    type: "bucketFetchFailed",
                    data: {
                        message: "No such bucket.",
                        messageType: "info"
                    }
                })
            } else {
                dispatcher.dispatch({
                    type: "bucketFetchFailed",
                    data: {
                        message: "Internal server error.",
                        messageType: "danger"
                    }
                })
            }
        });
};

