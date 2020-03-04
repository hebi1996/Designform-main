package com.design.system.service;

import com.design.system.domain.GoodsDO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public interface GoodsService {
    GoodsDO get(Long id);
    List<GoodsDO> list(Map<String, Object> map);
    int count(Map<String, Object> map);
    int save(GoodsDO goods);
    int update(GoodsDO goods);
    int remove(Long id);
    int batchremove(Long[] ids);
    boolean exit(Map<String, Object> params);
    Map<String,Object> updatePersonalImg(MultipartFile file, String avatar_data,Long id) throws Exception;
}
