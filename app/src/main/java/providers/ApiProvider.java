package providers;

import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.TEXT;

public abstract class ApiProvider {

    private static final RequestSpecification BASE_SPEC = given().contentType(JSON);

    private static RequestSpecification authSpec(String token) {
        return given()
                .contentType(JSON)
                .headers("Authorization", "Bearer " + token);
    }

    protected ValidatableResponse postRequest(String url, Object body) {
        return BASE_SPEC
                .body(body)
                .baseUri(url)
                .post()
                .then();
    }

    protected ValidatableResponse postWithAuthHeadRequest(String url, String token, Object body) {
        return authSpec(token)
                .body(body)
                .baseUri(url)
                .post()
                .then();
    }

    protected ValidatableResponse postWithFileRequest(String url, String token, File body) {
        return authSpec(token)
                .config(RestAssuredConfig.config()
                        .httpClient(HttpClientConfig.httpClientConfig()
                                .setParam("http.connection.timeout", 12000)))
                .contentType(TEXT)
                .body(body)
                .baseUri(url)
                .post()
                .then();
    }

    protected ValidatableResponse getRequest(String url, String token) {
        return authSpec(token)
                .baseUri(url)
                .get()
                .then();
    }

    protected ValidatableResponse getNoAuthRequest(String url) {
        return BASE_SPEC
                .baseUri(url)
                .get()
                .then();
    }

    protected ValidatableResponse getRequestWithParam(String url, String token, String paramKey, String paramVal) {
        return authSpec(token)
                .queryParam(paramKey, paramVal)
                .baseUri(url)
                .get()
                .then();
    }

    protected ValidatableResponse getNoAuthRequestWithParams(String url, String paramKey, String paramVal) {
        return BASE_SPEC
                .queryParams(paramKey, paramVal)
                .baseUri(url)
                .get()
                .then();
    }

    protected ValidatableResponse getRequestWithParams(String url, String token, HashMap<String, String> params) {
        return authSpec(token)
                .queryParams(params)
                .baseUri(url)
                .get()
                .then();
    }

    protected ValidatableResponse getNoAuthRequestWithParams(String url, HashMap<String, String> params) {
        return BASE_SPEC
                .queryParams(params)
                .baseUri(url)
                .get()
                .then();
    }

    protected ValidatableResponse patchRequest(String url, String token, Object body) {
        return authSpec(token)
                .body(body)
                .baseUri(url)
                .patch()
                .then();
    }

    protected ValidatableResponse patchRequestNoAuth(String url, Object body) {
        return BASE_SPEC
                .body(body)
                .baseUri(url)
                .patch()
                .then();
    }

    protected ValidatableResponse putRequest(String url, String token, Object body) {
        return authSpec(token)
                .body(body)
                .baseUri(url)
                .put()
                .then();
    }

    protected ValidatableResponse putRequestNoAuth(String url, Object body) {
        return BASE_SPEC
                .body(body)
                .baseUri(url)
                .put()
                .then();
    }
}