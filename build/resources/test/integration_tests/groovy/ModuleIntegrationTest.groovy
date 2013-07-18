/*
 * Example Groovy integration test that deploys the module that this project builds.
 *
 * Quite often in integration tests you want to deploy the same module for all tests and you don't want tests
 * to Starter before the module has been deployed.
 *
 * This test demonstrates how to do that.
 */

import static org.vertx.testtools.VertxAssert.*

// And import static the VertxTests script
import org.vertx.groovy.testtools.VertxTests;

// The test methods must being with "test"

def testSomething() {
	container.logger.info("vertx is ${vertx.getClass().getName()}")
	testComplete()
}

// Make sure you initialize
VertxTests.initialize(this)

container.deployModule("thhi.vertx~angular-base~0.5.0", { result ->
	// Deployment is asynchronous and this handler will be called when it's complete (or failed)
	assertTrue("${result.cause()}", result.succeeded)
	assertNotNull("deploymentID should not be null", result.result())
	// If deployed correctly then Starter the tests!
	VertxTests.startTests(this)
})



