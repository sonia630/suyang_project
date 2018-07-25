package com.o2o.nm.sys.mapper;

import cn.jeeweb.modules.sys.entity.Log;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogMapper extends BaseMapper<Log> {
	
	List<Log> selectLogPage(Page<Log> page, @Param("ew") Wrapper<Log> wrapper);
}