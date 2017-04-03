import {dispatcher} from '../util/mini-flux'

export default function changeBucketKey(newValue) {
    dispatcher.dispatch({
        type: "bucketKeyChanged",
        value: newValue
    })
}

