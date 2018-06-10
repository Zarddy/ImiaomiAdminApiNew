package cn.imiaomi.admin.api.mapper;

import cn.imiaomi.admin.api.pojo.AuthUser;
import cn.imiaomi.admin.api.util.ImiaoMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AuthUserMapper extends ImiaoMapper<AuthUser> {

    String TABLE_NAME = "`imiao_db`.`auth_user`";
    String MAO_FIELDS = "*";

    @Select({"SELECT " + MAO_FIELDS + " FROM " + TABLE_NAME + " WHERE username=#{username} limit 1"})
    AuthUser queryUserByUsername(@Param("username") String username);
}