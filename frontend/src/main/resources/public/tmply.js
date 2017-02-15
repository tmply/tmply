var context = {};

(function (context) {
    context.Config = {
        getUrlFor: function getUrlFor(path) {
            var loc = window.location;
            if (loc.port == 8081) {
                return loc.protocol + "//" + loc.hostname + ":" + 8080 + "/api" + path;
            }

            return loc.protocol + "//" + loc.host + "/api" + path;
        },
        getWsUrl: function getWsUrl() {
            var loc = window.location;
            if (loc.port == 8081 || loc.port == 8080) {
                return "ws://" + loc.hostname + ":" + 8080 + "/api/ws";
            }
            else if (loc.port == 80) {
                return "ws://" + loc.hostname + "/api/ws";
            }

            return "wss://" + loc.host + "/api/ws";
        }
    };
})(context);

(function (Config, $, context) {

    var noop = function () {
    }

    context.BucketResource = {
        publishBucket: function publishBucket(request, successCallback, errorCallback) {
            $.ajax({
                headers: {
                    'Content-Type': 'application/json'
                },
                type: "POST",
                url: Config.getUrlFor('/buckets'),
                data: JSON.stringify(request),
                success: successCallback || noop,
                error: errorCallback || noop,
                dataType: 'json'
            });
        },
        fetchBucket: function fetchBucket(bucketName, successCallback, errorCallback) {
            $.ajax({
                type: "GET",
                url: Config.getUrlFor('/buckets/' + bucketName),
                success: successCallback || noop,
                error: errorCallback || noop,
                dataType: 'json'
            });
        }
    };

})(context.Config, jQuery, context);

(function ($) {
    var $document = $(document);
    var $progressIndicator = $('#progress');

    $document.ajaxStart(function () {
        $progressIndicator.addClass('active');
    });
    $document.ajaxComplete(function () {
        $progressIndicator.removeClass('active');
    });
})(jQuery);

(function (window, BucketResource, Config, context) {
    var wsEnabled = (typeof window.WebSocket !== 'undefined');

    function reloadStats(successCallback) {
        $.ajax({
            type: "GET",
            url: Config.getUrlFor('/stats'),
            success: successCallback,
            dataType: 'json'
        });
    }

    function scheduleReloadStats(successCallback) {
        window.setInterval(function () {
            reloadStats(successCallback);
        }, 5000);
        reloadStats(successCallback);
    }

    function App() {
        var ws = wsEnabled ? new WebSocket(Config.getWsUrl()) : undefined;
        var self = this;

        ws.onopen = function (ev) {
        }
        ws.onclose = function (ev) {
        }
        ws.onerror = function (ev) {
        }
        ws.onmessage = function (ev) {
            var message = JSON.parse(ev.data);
            switch (message.type) {
                case "deliver":
                    self.deliverCallback(message);
                    break;
                case "stats":
                    self.statsCallback(message);
                    break;
                default:
                    console.error("ignored %o", message);
                    break;
            }
        }

        this.init = function (options) {
            this.fetchErrorCallback = options.fetchErrorCallback;
            this.fetchSuccessCallback = options.fetchSuccessCallback;
            this.publishErrorCallback = options.publishErrorCallback;
            this.publishSuccessCallback = options.publishSuccessCallback;
            this.subscribeSuccessCallback = options.subscribeSuccessCallback;
            this.subscribeErrorCallback = options.subscribeErrorCallback;
            this.deliverCallback = options.deliverCallback;
            this.statsCallback = options.statsCallback;

            if (!wsEnabled) {
                scheduleReloadStats(this.statsCallback);
            }
        }
        this.isSubscribeEnabled = function () {
            return true
        }
        this.publishBucket = function (bucketName, bucketData) {
            BucketResource.publishBucket({bucketName: bucketName, data: bucketData},
                this.publishSuccessCallback, this.publishErrorCallback);
        }
        this.fetchBucket = function (bucketName) {
            BucketResource.fetchBucket(bucketName, this.fetchSuccessCallback, this.fetchErrorCallback);
        }
        this.subscribe = function (bucketName) {
            if (!ws)
                return;

            ws.send(JSON.stringify({"type": "subscribe", bucketName: bucketName}));
        }
    }

    context.App = new App()
})(this, context.BucketResource, context.Config, context);

(function (window, App) {
    var $body = $('body');
    var $bucketNameInput = $('#bucketNameInput');
    var $bucketDataInput = $('#bucketDataInput');
    var $messagePanel = $('#messagePanel');
    var $publishButton = $('#publishButton');
    var $fetchButton = $('#fetchButton');
    var $subscribeButton = $('#subscribeButton');
    var $freeBuckets = $('#freeBuckets');
    var $maxBuckets = $('#maxBuckets');

    function updateStats(message) {
        $freeBuckets.text(message.free);
        $maxBuckets.text(message.max);
    }

    function updateButtonStates() {
        var publishEnabled = $bucketNameInput.val().length > 7 && $bucketDataInput.val().length > 0;
        $publishButton.prop('disabled', !publishEnabled);

        var fetchEnabled = $bucketNameInput.val().length > 7;
        $fetchButton.prop('disabled', !fetchEnabled);
        $subscribeButton.prop('disabled', !fetchEnabled);
    }

    var timer;

    function clearMessage() {
        window.clearTimeout(timer)
        $messagePanel.text("");
        $messagePanel.removeClass('text-info');
        $messagePanel.removeClass('text-success');
        $messagePanel.removeClass('text-danger');
    }

    function setMessage(message, clazz) {
        $messagePanel.text(message);
        if (clazz) {
            $messagePanel.addClass("text-" + clazz);
        }
        window.clearTimeout(timer)
        timer = window.setTimeout(clearMessage, 3000);
    }

    function onSubscribeClicked(ev) {
        ev.preventDefault();
        ev.stopPropagation();

        var bucketName = $bucketNameInput.val();
        App.subscribe(bucketName);
    }

    function onPublishClicked(ev) {
        ev.preventDefault();
        ev.stopPropagation();

        var bucketName = $bucketNameInput.val();
        var bucketData = $bucketDataInput.val();
        App.publishBucket(bucketName, bucketData);
    }

    function onFetchClicked(ev) {
        ev.preventDefault();
        ev.stopPropagation();

        var bucketName = $bucketNameInput.val();
        App.fetchBucket(bucketName)
    }

    App.init({
        publishSuccessCallback: function (response) {
            setMessage("Published as " + response.bucketName + ".", "success");
        },
        publishErrorCallback: function (response) {
            setMessage("Publishing failed.", "danger");
        },
        fetchSuccessCallback: function (response) {
            $bucketNameInput.val(response.bucketName);
            $bucketDataInput.val(response.data);
        },
        fetchErrorCallback: function (response) {
            if (response.status === 404) {
                setMessage("Nothing found.", "info");
            } else {
                setMessage("Fetching failed.", "danger");
            }
        },
        subscribeSuccessCallback: function () {
            setMessage("Subscribed.", "info");
        },
        subscribeErrorCallback: function () {
            setMessage("Subscribe failed.", "danger");
        },
        deliverCallback: function (message) {
            $bucketNameInput.val(message.bucketName);
            $bucketDataInput.val(message.data);
            setMessage("Received " + message.bucketName + ".", "info");
        },
        statsCallback: function (message) {
            updateStats(message);
        }
    });

    $publishButton.on('click', onPublishClicked);
    $fetchButton.on('click', onFetchClicked);
    $bucketNameInput.on('keyup blur change', updateButtonStates);
    $bucketDataInput.on('keyup blur change', updateButtonStates);
    $subscribeButton.on('click', onSubscribeClicked);

    context.UI = {
        init: function () {
            updateButtonStates();
            if (App.isSubscribeEnabled()) {
                $body.removeClass("noSubscriber");
            }
        }
    };
})(this, context.App);


$(function () {
    context.UI.init();
})