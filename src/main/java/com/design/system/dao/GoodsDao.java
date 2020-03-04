package com.design.system.dao;

import com.design.system.domain.GoodsDO;

import java.util.List;
import java.util.Map;

/**
 * @author yangzhi
 * @create 2020/1/21
 */
public interface GoodsDao {
    GoodsDO get(Long id);
    List<GoodsDO> list(Map<String, Object> map);
    int count(Map<String, Object> map);
    int save(GoodsDO goods);
    int update(GoodsDO goods);
    int remove(Long id);
    int batchRemove(Long[] ids);
}
