package com.design.system.dao;

import com.design.system.domain.GoodsDO;
import com.design.system.domain.ShopingCertDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ShopingCertDao {
    ShopingCertDO get(Long id);
    List<ShopingCertDO> list(Map<String, Object> map);
    int count(Map<String, Object> map);
    int save(ShopingCertDO certDO);
    int update(ShopingCertDO certDO);
    int remove(Long id);
    int batchRemove(Long[] ids);
    ShopingCertDO selectByGoodsIdAndUserIdCreate(@Param("goodsId") Long goodsId, @Param("userIdCreate") Long userIdCreate);
}
