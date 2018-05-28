package boss.portal.util;

public enum MsgCode {SUCCESS(0, "成功"),
    FAILED(1, "服务器内部错误"),
    IP_FAILED(2, "IP限制"),
    BUSI_FAILED(3, "业务异常"),
    PARAMETER_EMPTY(4, "参数不能为空"),
    TOKEN_FAILED(5, "获取token异常"),
    TOKEN_ILLEGAL(6, "非法的token"),
    TOKEN_EXPIRE(7, "token已经过期"),
    NO_LIMIT(-10, "无权限操作"),
    APIEXCEPTION(-99, "系统异常"),
    SESSION_EXCEPTION(-1, "未登陆"),
    NOT_GRANT_AUTH(401, "服务接口没有授权"),
    NOT_FOUND(404, "未找到相应服务接口"),
    EXCEPTION(500, "系统服务异常");

    private int code;
    private String description;

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    MsgCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static MsgCode getByCode(int code) {
        for (MsgCode codeEnum : MsgCode.values()) {
            if (codeEnum.getCode() == code) {
                return codeEnum;
            }
        }

        return null;
    }
}
