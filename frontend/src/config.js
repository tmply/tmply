var Config = {
    server: window.location.host,
    getUrlFor: function (path) {
        return window.location.protocol + "//" + this.server + "/api" + path;
    }
}

if (/.*localhost:8081.*/.test(window.location.href)) {
    Config.server = "localhost:8080"
}

export default Config;
