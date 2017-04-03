import {dispatcher} from '../util/mini-flux'

export default function changeBucketValue(newValue) {
    dispatcher.dispatch({
        type: "bucketValueChanged",
        value: newValue
    })
}

