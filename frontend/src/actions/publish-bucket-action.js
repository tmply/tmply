import {dispatcher} from '../util/mini-flux';
import BucketResource from '../api/bucket-resource';

export default function publishBucket(bucket) {
    BucketResource.publishBucket(bucket,
        function (result) {
            dispatcher.dispatch({
                type: "bucketPublished"
            })
        }, function () {
            dispatcher.dispatch({
                type: "publishBucketFailed"
            })

        });
}

