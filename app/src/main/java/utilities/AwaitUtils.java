package utilities;

import io.restassured.response.ValidatableResponse;
import org.awaitility.core.ConditionFactory;

import java.time.Duration;

import static org.awaitility.Awaitility.await;

public class AwaitUtils {

    private AwaitUtils() {
    }

    public static void awaitedCheckResponseStatusCodeAndSchema(ValidatableResponse response,
                                                               int code, String schema,
                                                               int timeout) {
        final ConditionFactory WAIT = await()
                .atMost(Duration.ofSeconds(timeout))
                .pollInterval(Duration.ofSeconds(1))
                .pollDelay(Duration.ofSeconds(1));
        WAIT.untilAsserted(() -> ResponseUtils.checkResponseStatusCodeAndSchema(response, code, schema));
    }

    public static void awaitedCheckResponseStatusCode(ValidatableResponse response, int code,
                                                      int timeout) {
        final ConditionFactory WAIT = await()
                .atMost(Duration.ofSeconds(timeout))
                .pollInterval(Duration.ofSeconds(1))
                .pollDelay(Duration.ofSeconds(1));
        WAIT.untilAsserted(() -> ResponseUtils.checkResponseStatusCode(response, code));
    }

    public static void awaitedCheckResponseSchema(ValidatableResponse response, String schema,
                                                  int timeout) {
        final ConditionFactory WAIT = await()
                .atMost(Duration.ofSeconds(timeout))
                .pollInterval(Duration.ofSeconds(1))
                .pollDelay(Duration.ofSeconds(1));
        WAIT.untilAsserted(() -> ResponseUtils.validateResponseByJsonSchema(response, schema));
    }

    public static void awaitedCheckResponseBody(ValidatableResponse response, String path,
                                                String message, int timeout) {
        final ConditionFactory WAIT = await()
                .atMost(Duration.ofSeconds(timeout))
                .pollInterval(Duration.ofSeconds(1))
                .pollDelay(Duration.ofSeconds(1));
        WAIT.untilAsserted(() -> ResponseUtils.checkResponseBody(response, path, message));
    }
}