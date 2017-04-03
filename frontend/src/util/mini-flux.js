import upperFirst from './upper-first'

// a small dispatcher that delegates actions to stores;
// for action Abc implement handleAbc(action) for store class
class Dispatcher {
	constructor() {
		this.stores = []
	}

	addStore(store) {
		this.stores.push(store)
	}

	dispatch(action) {
		var handlerName = "handle" + upperFirst(action.type)
		this.stores.forEach(function (store) {
			var handler = store[handlerName]
			if (handler) {
				handler.call(store, action)
			}
		});
	}
}

var dispatcher = new Dispatcher()

// add event emitting to store object
function initStore(store) {
	store.listeners = []
	store.addListener = function (l) {
		if (!l) return;
		this.listeners.push(l)
	}
	store.removeListener = function (l) {
		var newListeners = []
		this.listeners.forEach(function (curr) {
			if (curr != l) {
				newListeners.push(curr);
			}
		})
		this.listeners = newListeners
	}
	store.notifyListeners = function (ev) {
		this.listeners.forEach(function (l) {
			l(ev)
		})
	}

	return store;
}

export {dispatcher, initStore}
