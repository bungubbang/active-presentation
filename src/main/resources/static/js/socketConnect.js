var WEBSOCKET_TARGET_URL = "/join";

var socketConnect = function() {
    socket = new SockJS(WEBSOCKET_TARGET_URL);
    stompClient = Stomp.over(socket);

    stompClient.connect(' ', ' ', function(frame) {
        status = true;
        statusConnect();
    }, function (error) {
        // TODO error 처리
        status = false;
        setTimeout(function() {
            socketConnect();
        }, timeout);
        statusDisconnect();
    });
}

var statusConnect = function() {
    $('.control-navbar-status-fail').hide();
    $('.control-navbar-status-connect').show();

}

var statusDisconnect = function() {
    $('.control-navbar-status-connect').hide();
    $('.control-navbar-status-fail').show();

}