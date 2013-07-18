package thhi.vertx.mods

import org.vertx.groovy.platform.Verticle

class Starter extends Verticle {

	def start() {

		def webServerConfig = container.config.web_server

		container.deployModule("io.vertx~mod-web-server~2.0.0-final", webServerConfig) { result ->
			container.logger.info("Deployed WebServer ${result.result()}")
		}

		container.deployVerticle("io.vertx~mod-mongo-persistor~2.0.0-final", mongoPersistorConfig) { result ->
			container.logger.info("Deployed MongoPersistor ${result.result()}")
		}
	}
}