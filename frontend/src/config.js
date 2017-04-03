var Config = {
    server: window.location.host,
    getUrlFor: function (path) {
        return "http://" + this.server + "/api" + path;
    },
    getWsUrl: function () {
        return "ws://" + this.server + "/api/ws";
    }
}

if (/.*localhost:8081.*/.test(window.location.href)) {
    Config.server = "localhost:8080"
}

export default Config;
