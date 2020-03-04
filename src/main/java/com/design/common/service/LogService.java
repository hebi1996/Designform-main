package com.design.common.service;

import com.design.common.domain.LogDO;
import com.design.common.domain.PageDO;
import com.design.common.utils.Query;
import org.springframework.stereotype.Service;
@Service
public interface LogService {
	void save(LogDO logDO);
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
