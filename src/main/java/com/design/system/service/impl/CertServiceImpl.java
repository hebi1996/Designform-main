package com.design.system.service.impl;

import com.design.common.dao.FileDao;
import com.design.common.domain.FileDO;
import com.design.system.dao.GoodsDao;
import com.design.system.dao.ShopingCertDao;
import com.design.system.domain.GoodsDO;
import com.design.system.domain.ShopingCertDO;
import com.design.system.service.CertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author yangzhi
 * @create 2020/1/21
 */
@Service
public class CertServiceImpl implements CertService {
    @Autowired ShopingCertDao certDao;
    @Autowired
    GoodsDao goodsDao;
    @Autowired
    FileDao fileDao;

    @Override
    public ShopingCertDO get(Long id) {
        return certDao.get(id);
    }

    @Override
    public List<ShopingCertDO> list(Map<String, Object> map) {
        List<ShopingCertDO> list = certDao.list(map);
        for (ShopingCertDO shopingCertDO : list) {
            Long goodsId = shopingCertDO.getGoodsId();
            if (null != goodsId) {
                GoodsDO goodsDO = goodsDao.get(goodsId);
                Long images = goodsDO.getImages();
                shopingCertDO.setGoodsName(goodsDO.getName());
                if (null != images) {
                    FileDO fileDO = fileDao.get(images);
                    shopingCertDO.setImageUrl(fileDO.getUrl());
                }
                shopingCertDO.setPrice(String.valueOf(goodsDO.getPrice() * shopingCertDO.getNumber()));
            }
        }
        return list;
    }

    @Override
    public int count(Map<String, Object> map) {
        return certDao.count(map);
    }

    @Override
    public int save(ShopingCertDO certDO) {
        return certDao.save(certDO);
    }

    @Override
    public int update(ShopingCertDO certDO) {
        return certDao.update(certDO);
    }

    @Override
    public int remove(Long id) {
        return certDao.remove(id);
    }

    @Override
    public int batchremove(Long[] ids) {
        return certDao.batchRemove(ids);
    }
}
