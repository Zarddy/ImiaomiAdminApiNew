package cn.imiaomi.admin.api.http;

/**
 * 响应码
 */
public enum HttpStatusCode {

    /**
     * 成功
     */
    SUCCESS(200),

    /**
     * 错误
     */
    ERROR(400),

    /**
     * 缺少权限
     */
    NO_PERMISSION(401);

    private int code;

    private HttpStatusCode(int code) {
        this.code = code;
    }

    public static HttpStatusCode valueOf(int code) {
        switch (code) {
            case 200:
                return SUCCESS;

            case 400:
                return ERROR;

            case 401:
                return NO_PERMISSION;

            default:
                return null;
        }
    }

    public int value() {
        return code;
    }
}
