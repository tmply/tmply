function getBaseUrl() {
    if (typeof window === 'undefined') {
        return undefined;
    }

    var loc = window.location;
    if (loc.port == 8081) {
        return loc.protocol + "//" + loc.hostname + ":" + 8080 + "/api";
    }

    return loc.protocol + "//" + loc.host + "/api";
}

export default {
    apiUrl: function (path) {
        return getBaseUrl() + path;
    }
};
