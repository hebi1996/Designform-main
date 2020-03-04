package com.design.system.service;

import com.design.system.domain.GoodsDO;
import com.design.system.domain.ShopingCertDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CertService {
    ShopingCertDO get(Long id);
    List<ShopingCertDO> list(Map<String, Object> map);
    int count(Map<String, Object> map);
    int save(ShopingCertDO certDO);
    int update(ShopingCertDO certDO);
    int remove(Long id);
    int batchremove(Long[] ids);
}
