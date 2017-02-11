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

    var BucketResource = {
        publishBucket: function publichBucket(request, successCallback, errorCallback) {
            $.ajax({
                headers: {
                    'Content-Type': 'application/json'
                },
                type: "POST",
                url: Config.getUrlFor('/buckets'),
                data: JSON.stringify(request),
                success: successCallback,
                error: errorCallback,
                dataType: 'json'
            });
        },
        fetchBucket: function fetchBucket(bucketName, successCallback, errorCallback) {
            $.ajax({
                type: "GET",
                url: Config.getUrlFor('/buckets/' + bucketName),
                success: successCallback,
                error: errorCallback,
                dataType: 'json'
            });
        }
    };

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

    function clearMessage() {
        $messagePanel.text("");
    }

    function setMessage(message) {
        $messagePanel.text(message);
        window.setTimeout(clearMessage, 3000);
    }

    function onPublishClicked(ev) {
        ev.preventDefault();
        ev.stopPropagation();

        var bucketName = $bucketNameInput.val();
        var bucketData = $bucketDataInput.val();
        BucketResource.publishBucket({bucketName: bucketName, data: bucketData}, function (response) {
            setMessage("Published as " + bucketName + ".");
        }, function (response) {
            setMessage("Publishing failed.");
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
                setMessage("Nothing found.");
            } else {
                setMessage("Fetching failed.");
            }
        });
    }

    $publishButton.on('click', onPublishClicked);
    $fetchButton.on('click', onFetchClicked);
    $bucketNameInput.on('keyup blur change', updateButtonStates);
    $bucketDataInput.on('keyup blur change', updateButtonStates);

    updateButtonStates();
});