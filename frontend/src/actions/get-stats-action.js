import {dispatcher} from '../util/mini-flux';
import StatsResource from '../api/stats-resource';

export default function getStats() {
    StatsResource.getStats(function (result) {
        dispatcher.dispatch({
            type: "statsReceived",
            data: result
        })
    });
}

