package cn.imiaomi.admin.api.service.impl;

import cn.imiaomi.admin.api.mapper.AuthUserMapper;
import cn.imiaomi.admin.api.pojo.AuthUser;
import cn.imiaomi.admin.api.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("all")
@Service("authUserService")
public class AuthUserServiceImpl implements AuthUserService {

    @Autowired
    AuthUserMapper authUserMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public AuthUser getUserByUsername(String username) {
        return authUserMapper.queryUserByUsername(username);
    }
}
