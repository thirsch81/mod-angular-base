var app = angular.module("vertxBase", []);

app.config(["$routeProvider", function($routeProvider) {
}]);

app.factory("eventBus", function($rootScope, $location, $q, $log) {

	$log.log("initializing eventBus");
	eb = new vertx.EventBus($location.protocol() + "://" + $location.host() + ":" + $location.port() + "/eventbus");

	var q_open = $q.defer();
	this.open = q_open.promise;
	eb.onopen = function() {
		$log.log("eventBus opened");
		$rootScope.$apply(q_open.resolve);
	}

	var q_closed = $q.defer();
	this.closed = q_closed.promise;
	eb.onclose = function() {
		$log.log("eventBus closed");
		$rootScope.$apply(q_closed.resolve);
	}

	function ready() {
		return eb.readyState() == 1;
	}

	this.send = function(address, message) {
		var response = $q.defer();
		if (ready()) {
			eb.send(address, message, function(reply) {
				$log.log("sending " + JSON.stringify(message) + " to address " + address);
				$rootScope.$apply(function() {
					$log.log("got reply " + JSON.stringify(reply));
					if ("ok" == reply.status) {
						response.resolve(reply);
					} else {
						response.reject(reply.message);
					}
				});
			});
		} else {
			var errorMsg = "EventBus not ready, please reload page!";
		}
		return response.promise;
	}

	return this;
});