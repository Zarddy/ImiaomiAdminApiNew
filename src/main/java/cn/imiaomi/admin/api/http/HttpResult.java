package cn.imiaomi.admin.api.http;

public class HttpResult {

    private int code = HttpStatusCode.SUCCESS.value(); // 响应业务状态，响应码
    private String msg; // 响应消息
    private long total; // 总记录数
    private Object result; // 返回值

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

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
