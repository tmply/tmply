import xhr from 'xhr';
import Config from '../config';

function pre() {
    window.document.body.classList.add("loading");
}

function post() {
    window.document.body.classList.remove("loading")
}

export default {
    publishBucket: function publishBucket(request, successCallback, errorCallback) {
        pre();
        xhr({
            method: 'POST',
            uri: Config.getUrlFor('/buckets'),
            json: true,
            body: request
        }, function (err, resp, body) {
            post();
            if( resp.statusCode === 200 ) {
                successCallback(body);
            } else {
                errorCallback(resp);
            }
        });
    },
    fetchBucket: function fetchBucket(bucketName, successCallback, errorCallback) {
        pre();
        xhr({
            uri: Config.getUrlFor('/buckets/' + encodeURIComponent(bucketName)),
            json: true
        }, function (err, resp, body) {
            post();
            if( resp.statusCode === 200 ) {
                successCallback(body);
            } else {
                errorCallback(resp);
            }
        });
    }
};
