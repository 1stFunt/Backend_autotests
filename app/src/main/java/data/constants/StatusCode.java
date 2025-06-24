package data.constants;

public class StatusCode {

    private StatusCode() {
    }

    public static final int SUCCESS_STATUS_CODE = 200;
    public static final int CREATED_STATUS_CODE = 201;
    public static final int NO_CONTENT_CODE = 204;
    public static final int BAD_REQUEST_STATUS_CODE = 400;
    public static final int UNAUTHORIZED_STATUS_CODE = 401;
    public static final int FORBIDDEN_STATUS_CODE = 403;
    public static final int NOT_FOUND_STATUS_CODE = 404;
    public static final int NOT_ACCEPTABLE_STATUS_CODE = 406;
    public static final int INTERNAL_SERVER_ERROR = 500;
}