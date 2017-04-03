import xhr from 'xhr';
import Config from '../config';
import Encryption from '../util/encryption';

function pre() {
    window.document.body.classList.add("loading");
}

function post() {
    window.document.body.classList.remove("loading")
}

export default {
    publishBucket: function publishBucket(request, successCallback, errorCallback) {
        pre();
        var newRequest = {
            bucketName: Encryption.hash(request.bucketName),
            data: Encryption.encrypt(request.data, request.bucketName)
        };

        xhr({
            method: 'POST',
            uri: Config.getUrlFor('/buckets'),
            json: true,
            body: newRequest
        }, function (err, resp, body) {
            post();
            if (resp.statusCode === 200) {
                successCallback(body);
            } else {
                errorCallback(resp);
            }
        });
    },
    fetchBucket: function fetchBucket(bucketName, successCallback, errorCallback) {
        pre();
        xhr({
            uri: Config.getUrlFor('/buckets/' + Encryption.hash(bucketName)),
            json: true
        }, function (err, resp, body) {
            post();
            if (resp.statusCode === 200) {
                var response = {
                    bucketName: bucketName,
                    data: Encryption.decrypt(body.data, bucketName)
                };

                successCallback(response);
            } else {
                errorCallback(resp);
            }
        });
    }
};
