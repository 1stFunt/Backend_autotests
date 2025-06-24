package utilities;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.ValidatableResponse;

import java.util.ArrayList;
import java.util.List;

public class HeadersUtils {

    private HeadersUtils() {
    }

    public static String getResponseHeaderByName(ValidatableResponse response, String headerName) {
        return response.extract().header(headerName);
    }

    public static List<Header> getAuthorizationHeader(String token) {
        List<Header> headers = new ArrayList<>();
        headers.add(new Header("authorization", token));
        return headers;
    }

    public static Headers getHeadersFromHeaderList(List<Header> header) {
        return new Headers(header);
    }

    public static String getAuthorizationToken(ValidatableResponse response) {
        return getResponseHeaderByName(response, "authorization");
    }
}