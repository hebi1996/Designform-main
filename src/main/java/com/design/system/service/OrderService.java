package com.design.system.service;

import com.design.system.domain.GoodsDO;
import com.design.system.domain.OrderDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface OrderService {
    OrderDO get(Long id);
    List<OrderDO> list(Map<String, Object> map);
    int count(Map<String, Object> map);
    int save(OrderDO order);
    int update(OrderDO order);
    int remove(Long id);
    int batchremove(Long[] ids);
}
