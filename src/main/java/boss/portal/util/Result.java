package boss.portal.util;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;


public class Result<T> implements Serializable {
    private static final long serialVersionUID = -4009830701561952613L;
    private int code;
    private String msg;
    private T data;

    public Result() {
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static Result fail(String desc) {
        return new Result(MsgCode.PARAMETER_EMPTY.getCode(), desc);
    }

    /*public boolean isSuccess() {
        return this.code == MsgCode.SUCCESS.getCode();
    }*/
    public static Result success() {
        return new Result(MsgCode.SUCCESS.getCode(),
            MsgCode.SUCCESS.getDescription());
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("msg", msg);
        map.put("data", data);

        return map;
    }
}
