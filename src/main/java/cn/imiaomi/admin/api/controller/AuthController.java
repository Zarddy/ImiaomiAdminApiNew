package cn.imiaomi.admin.api.controller;

import cn.imiaomi.admin.api.dto.AuthTokenDTO;
import cn.imiaomi.admin.api.exception.ImiaoException;
import cn.imiaomi.admin.api.http.HttpResult;
import cn.imiaomi.admin.api.pojo.AuthUser;
import cn.imiaomi.admin.api.service.AuthUserService;
import cn.imiaomi.admin.api.service.RedisService;
import cn.imiaomi.admin.api.util.AuthWebTokenUtil;
import cn.imiaomi.admin.api.util.CalendarUtil;
import cn.imiaomi.admin.api.util.MD5Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("all")
@RestController
public class AuthController {

    @Autowired
    AuthUserService authUserService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/login")
    public HttpResult login(@RequestBody Map<String, String> reqMap) throws ImiaoException {
        String account = reqMap.getOrDefault("account", "");
        String password = reqMap.getOrDefault("password", "");

        String key = "retryCache_" + account;
        String route = "routeCache_" + account;

        AtomicInteger retryCount = new AtomicInteger(0);
        Object obj = redisService.get(key);
        if (null != obj) {
            retryCount.set(Integer.parseInt(String.valueOf(obj)));
        }

        // 当用户连续输入密码错误5次以上禁止用户登录一段时间
        // TODO 暂时取消登录失败次数限制
//        if (retryCount.incrementAndGet() > 5) {
//            throw new ExcessiveAttemptsException();
//        }

        AuthUser authUser = authUserService.getUserByUsername(account);
        HttpResult httpResult = new HttpResult();


        if (authUser == null) {
            redisService.set(key, retryCount, 300);
            throw new ImiaoException("账号不存在");
        }

        password = MD5Util.encrypt(password + authUser.getSalt(), "utf-8");
        if (!password.equals(authUser.getPassword())) {
            redisService.set(key, retryCount, 300);
            throw new ImiaoException("账号或密码有误");
        }

        // 密码正确移除限制
        redisService.del(account);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        // 生成token
        AuthWebTokenUtil tokenUtil = new AuthWebTokenUtil();

        AuthTokenDTO tokenDTO = new AuthTokenDTO();
        tokenDTO.setId(String.valueOf(authUser.getId()));
        tokenDTO.setUsername(authUser.getUsername());
        tokenDTO.setExpirationDate(CalendarUtil.addDays(new Date(), 7));

        String token = tokenUtil.createJsonWebToken(tokenDTO);

        node.put("username", authUser.getUsername());
        node.put("token", token);

        httpResult.setResult(node);

        return httpResult;
    }
}
