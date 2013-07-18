container.with {

	deployModule("io.vertx~mod-web-server~2.0.0-final", config.web_server) { result ->
		logger.info("Deployed WebServer ${result.result()}")
	}

	deployModule("io.vertx~mod-mongo-persistor~2.0.0-final", config.mongo_persistor) { result ->
		logger.info("Deployed MongoPersistor ${result.result()}")
	}
}