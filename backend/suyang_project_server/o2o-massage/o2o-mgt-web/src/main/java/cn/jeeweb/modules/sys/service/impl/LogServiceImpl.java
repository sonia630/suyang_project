package cn.jeeweb.modules.sys.service.impl;

import cn.jeeweb.core.common.service.impl.CommonServiceImpl;
import cn.jeeweb.modules.sys.entity.Log;
import cn.jeeweb.modules.sys.service.ILogService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.o2o.nm.sys.mapper.LogMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("logService")
public class LogServiceImpl extends CommonServiceImpl<LogMapper,Log> implements ILogService {
	@Override
	public Page<Log> selectPage(Page<Log> page, Wrapper<Log> wrapper) {
		wrapper.eq("1", "1");
		page.setRecords(baseMapper.selectLogPage(page, wrapper));
		return page;
	}
}
