package utilities;

import io.restassured.response.ValidatableResponse;
import providers.clientInfoService.GetClientsProvider;
import providers.creditService.GetCredProdsProvider;
import providers.depositService.GetDepoProdsProvider;
import providers.creditService.NewCredProdProvider;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.IntStream;

import static data.constants.creditService.DeleteCreditConstants.CONSTANT_RATE;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;

public class ResponseUtils {

    private ResponseUtils() {
    }

    public static void checkResponseStatusCodeAndSchema(ValidatableResponse response, int code,
                                                        String schema) {
        response
                .statusCode(code)
                .body(matchesJsonSchemaInClasspath(schema));
    }

    public static void checkResponseStatusCode(ValidatableResponse response, int code) {
        response
                .statusCode(code);
    }

    public static void validateResponseByJsonSchema(ValidatableResponse response, String schema) {
        response
                .body(matchesJsonSchemaInClasspath(schema));
    }

    public static void checkResponseBody(ValidatableResponse response, String path, String message) {
        response
                .assertThat().body(path, equalTo(message));
    }

    public static String getResponseValToString(ValidatableResponse response, String path) {
        return response
                .extract().body().path(path).toString();
    }

    public static int getResponseValToInt(ValidatableResponse response, String path) {
        return Integer.parseInt(getResponseValToString(response, path));
    }

    public static String getClientVal(String value, int index) {
        GetClientsProvider provider = new GetClientsProvider();
        String indexPath = "clients." + value + ResponseUtils.squareFramed(Integer.toString(index));
        return ResponseUtils.getResponseValToString(provider.postRequestGetClients(),
                indexPath);
    }

    public static String searchClientID(String mobilePhone, int pageNumber) {
        ValidatableResponse response = new GetClientsProvider().postRequestGetClients(pageNumber);
        int clientSize = ResponseUtils.getResponseValToInt(response, "clients.size()");
        return IntStream.range(0, clientSize)
                .filter(i -> Objects.equals(mobilePhone, ResponseUtils.getResponseValToString(response, "clients.mobilePhone[" + i + "]")))
                .mapToObj(i -> ResponseUtils.getResponseValToString(response, "clients.id[" + i + "]"))
                .findFirst()
                .orElse("!");
    }

    public static String createAndGetCreditId() {
        new NewCredProdProvider()
                .postNewCredProdRequest("PERSONAL", "Test credit to delete", "12",
                        "75", "111.0", "111000000.0",
                        false, CONSTANT_RATE, "RUB",
                        false, false, "picName",
                        false, false, "2023-01-07");
        return ResponseUtils.getCreditId(CONSTANT_RATE);
    }

    public static boolean checkCredProd(String constantRate) {
        ValidatableResponse response = new GetCredProdsProvider().getPositiveRequestGetCredProds();
        int creditsSize = ResponseUtils.getResponseValToInt(response, "credits.size()");
        return IntStream.range(0, creditsSize)
                .anyMatch(i -> ResponseUtils.getResponseValToString(response, "credits[" + i + "].constantRate").equals(constantRate));
    }

    public static boolean checkCredProd(String productId, String constantRate) {
        ValidatableResponse response = new GetCredProdsProvider().getPositiveRequestGetCredProds();
        int creditsSize = ResponseUtils.getResponseValToInt(response, "credits.size()");
        int testCreditId = IntStream.range(0, creditsSize)
                .filter(i -> ResponseUtils.getResponseValToString(response, "credits[" + i + "].id").equals(productId))
                .findFirst()
                .orElse(-1);
        return (testCreditId != -1) &&
                (ResponseUtils.getResponseValToString(response, "credits[" + testCreditId + "].constantRate").equals(constantRate));
    }

    public static String getCreditId(String constantRate) {
        ValidatableResponse response = new GetCredProdsProvider().getPositiveRequestGetCredProds();
        int creditProdsSize = ResponseUtils.getResponseValToInt(response, "credits.size()");
        return IntStream.range(0, creditProdsSize)
                .filter(i -> Objects.equals(constantRate, ResponseUtils.getResponseValToString(response, "credits[" + i + "].constantRate")))
                .mapToObj(i -> ResponseUtils.getResponseValToString(response, "credits[" + i + "].id"))
                .findFirst()
                .orElse("!");
    }

    public static String getEndpointFromUrl(String url) {
        int lastSlashIndex = url.lastIndexOf("/");
        return url.substring(lastSlashIndex + 1);
    }

    public static String getDepositVal(String value, int index, String type, String category) {
        GetDepoProdsProvider provider = new GetDepoProdsProvider();
        String indexPath = "deposit-products." + value + ResponseUtils.squareFramed(Integer.toString(index));
        return ResponseUtils.getResponseValToString(provider.postPositiveRequestGetDepoProds(type, category),
                indexPath);
    }

    public static boolean getAndCheckDepositVal(String key, int index, String val, String type, String category) {
        GetDepoProdsProvider provider = new GetDepoProdsProvider();
        String indexPath = "deposit-products." + key + ResponseUtils.squareFramed(Integer.toString(index));
        String extractedVal = ResponseUtils.getResponseValToString(provider.postPositiveRequestGetDepoProds(type, category),
                indexPath);
        return extractedVal.equals(String.valueOf(val));
    }

    public static boolean getAndCheckDepositVals(ArrayList<String> keys, int index, ArrayList<String> vals, String type, String category) {
        GetDepoProdsProvider provider = new GetDepoProdsProvider();
        String indexStr = ResponseUtils.squareFramed(Integer.toString(index));
        return IntStream.range(0, keys.size())
                .allMatch(i -> {
                    String indexPath = "deposit-products." + keys.get(i) + indexStr;
                    String extractedVal = ResponseUtils.getResponseValToString(provider.postPositiveRequestGetDepoProds(type, category), indexPath);
                    return extractedVal.equals(String.valueOf(vals.get(i)));
                });
    }

    public static String squareFramed(String string) {
        return "[" + string + "]";
    }
}