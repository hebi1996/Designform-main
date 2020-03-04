package com.design.system.service.impl;

import com.design.system.dao.OrderDao;
import com.design.system.domain.OrderDO;
import com.design.system.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author yangzhi
 * @create 2020/1/21
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;
    @Override
    public OrderDO get(Long id) {
        return orderDao.get(id);
    }

    @Override
    public List<OrderDO> list(Map<String, Object> map) {
        return orderDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return orderDao.count(map);
    }

    @Override
    public int save(OrderDO order) {
        return orderDao.save(order);
    }

    @Override
    public int update(OrderDO order) {
        return orderDao.update(order);
    }

    @Override
    public int remove(Long id) {
        return orderDao.remove(id);
    }

    @Override
    public int batchremove(Long[] ids) {
        return orderDao.batchRemove(ids);
    }
}
