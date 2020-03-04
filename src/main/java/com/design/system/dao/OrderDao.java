package com.design.system.dao;

import com.design.system.domain.GoodsDO;
import com.design.system.domain.OrderDO;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    OrderDO get(Long id);
    List<OrderDO> list(Map<String, Object> map);
    int count(Map<String, Object> map);
    int save(OrderDO orderDO);
    int update(OrderDO orderDO);
    int remove(Long id);
    int batchRemove(Long[] ids);
}
