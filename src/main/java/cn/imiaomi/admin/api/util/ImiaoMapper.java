package cn.imiaomi.admin.api.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface ImiaoMapper<T> extends Mapper, MySqlMapper<T> {
}
