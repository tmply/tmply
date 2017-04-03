import xhr from 'xhr';
import Config from '../config';

function pre() {
    window.document.body.classList.add("loading");
}

function post() {
    window.document.body.classList.remove("loading")
}

export default {
    getStats: function getStats(successCallback, errorCallback) {
        pre();
        xhr({
            uri: Config.getUrlFor('/stats'),
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
