$(function () {
    var Config = {
        getUrlFor: function getBaseUrl(path) {
            if (typeof window === 'undefined') {
                return undefined;
            }

            var loc = window.location;
            if (loc.port == 8081) {
                return loc.protocol + "//" + loc.hostname + ":" + 8080 + "/api" + path;
            }

            return loc.protocol + "//" + loc.host + "/api" + path;
        }
    };

    var BucketResource = (function () {
        var noop = function () {
        }

        return {
            publishBucket: function publichBucket(request, successCallback, errorCallback) {
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
    })();

    (function () {
        var $progressIndicator = $('#progress');
        var $document = $(document);

        $document.ajaxStart(function () {
            $progressIndicator.addClass('active');
        });
        $document.ajaxComplete(function () {
            $progressIndicator.removeClass('active');
        });
    })();

    (function () {
        var $freeBuckets = $('#freeBuckets');
        var $maxBuckets = $('#maxBuckets');

        function updateStats(free, max) {
            $freeBuckets.text(free);
            $maxBuckets.text(max);
        }

        function reloadStats() {
            $.ajax({
                type: "GET",
                url: Config.getUrlFor('/stats'),
                success: function (response) {
                    updateStats(response.maxBucketsCount - response.usedBucketsCount, response.maxBucketsCount);
                },
                dataType: 'json'
            });
        }

        window.setInterval(reloadStats, 5000);
        reloadStats();
    })();

    (function () {
        var $bucketNameInput = $('#bucketNameInput');
        var $bucketDataInput = $('#bucketDataInput');
        var $messagePanel = $('#messagePanel');
        var $publishButton = $('#publishButton');
        var $fetchButton = $('#fetchButton');

        function updateButtonStates() {
            var publishEnabled = $bucketNameInput.val().length > 7 && $bucketDataInput.val().length > 0;
            $publishButton.prop('disabled', !publishEnabled);

            var fetchEnabled = $bucketNameInput.val().length > 7;
            $fetchButton.prop('disabled', !fetchEnabled);
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

        function onPublishClicked(ev) {
            ev.preventDefault();
            ev.stopPropagation();

            var bucketName = $bucketNameInput.val();
            var bucketData = $bucketDataInput.val();
            BucketResource.publishBucket({bucketName: bucketName, data: bucketData}, function (response) {
                setMessage("Published as " + bucketName + ".", "success");
            }, function (response) {
                setMessage("Publishing failed.", "danger");
            });
        }

        function onFetchClicked(ev) {
            ev.preventDefault();
            ev.stopPropagation();

            var bucketName = $bucketNameInput.val();
            BucketResource.fetchBucket(bucketName, function (response) {
                $bucketNameInput.val(response.bucketName);
                $bucketDataInput.val(response.data);
                clearMessage();
            }, function (response) {
                if (response.status === 404) {
                    setMessage("Nothing found.", "info");
                } else {
                    setMessage("Fetching failed.", "danger");
                }
            });
        }

        $publishButton.on('click', onPublishClicked);
        $fetchButton.on('click', onFetchClicked);
        $bucketNameInput.on('keyup blur change', updateButtonStates);
        $bucketDataInput.on('keyup blur change', updateButtonStates);

        updateButtonStates();
    })();
});