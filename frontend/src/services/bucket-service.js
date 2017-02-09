import AppDispatcher from '../app-dispatcher';
import ActionTypes from '../action-types';
import xhr from 'xhr-request';
import Config from '../config';

class BucketService {
    publishBucket(request) {
        xhr(Config.apiUrl('/buckets'), {
            body: {bucketName: request.bucketName, data: request.bucketData},
            method: 'POST',
            json: true
        }, function (err, body, resp) {
            if (resp.statusCode === 200) {
                AppDispatcher.dispatch({
                    type: ActionTypes.BUCKET_PUBLISHED,
                    data: body
                });
            }
        });
    }

    receiveBucket(bucketName) {
        xhr(Config.apiUrl('/buckets/' + bucketName), {
            json: true
        }, function (err, body, resp) {
            if (resp.statusCode === 200) {
                AppDispatcher.dispatch({
                    type: ActionTypes.BUCKET_RECEIVED,
                    data: body
                });
            } else if ( resp.statusCode === 404 ) {
                AppDispatcher.dispatch({
                    type: ActionTypes.BUCKET_NOT_FOUND
                });
            }
        });
    }
}

export default new BucketService();