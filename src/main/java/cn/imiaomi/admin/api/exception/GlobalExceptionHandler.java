package cn.imiaomi.admin.api.exception;

import cn.imiaomi.admin.api.http.HttpResult;
import cn.imiaomi.admin.api.http.HttpStatusCode;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 捕捉shiro异常
     * @param exception
     * @return
     */
    @ExceptionHandler(ShiroException.class)
    @ResponseStatus(HttpStatus.OK)
    public HttpResult handle401(ShiroException exception) {
        HttpResult result = new HttpResult();
        result.setCode(HttpStatusCode.NO_PERMISSION.value());
        result.setMsg(exception.getMessage());
        return result;
    }

    /**
     * 捕捉未授权异常
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.OK)
    public HttpResult handle401() {
        HttpResult result = new HttpResult();
        result.setCode(HttpStatusCode.NO_PERMISSION.value());
        result.setMsg("Unauthorized");
        return result;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public HttpResult globalException(HttpServletRequest request, Throwable throwable) {
        HttpResult result = new HttpResult();
        result.setCode(getStatus(request).value());
        result.setMsg(throwable.getMessage());
        return result;
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
