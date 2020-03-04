package com.design.system.service.impl;

import com.design.common.config.BootdoConfig;
import com.design.common.domain.FileDO;
import com.design.common.service.FileService;
import com.design.common.utils.FileType;
import com.design.common.utils.FileUtil;
import com.design.common.utils.ImageUtils;
import com.design.system.dao.GoodsDao;
import com.design.system.domain.GoodsDO;
import com.design.system.domain.UserDO;
import com.design.system.service.GoodsService;
import com.design.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsDao goodsDao;
    @Autowired
    private FileService sysFileService;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Override
    public GoodsDO get(Long id) {
        return goodsDao.get(id);
    }

    @Override
    public List<GoodsDO> list(Map<String, Object> map) {
        List<GoodsDO> list = goodsDao.list(map);
        for (GoodsDO goodsDO : list) {
            if (null != goodsDO.getImages()) {
                FileDO fileDO = sysFileService.get(goodsDO.getImages());
                goodsDO.setImagesUrl(fileDO.getUrl());
            }
        }
        return list;
    }

    @Override
    public int count(Map<String, Object> map) {
        return goodsDao.count(map);
    }

    @Override
    public int save(GoodsDO goods) {
        return goodsDao.save(goods);
    }

    @Override
    public int update(GoodsDO goods) {
        return goodsDao.update(goods);
    }

    @Override
    public int remove(Long id) {
        return goodsDao.remove(id);
    }

    @Override
    public int batchremove(Long[] ids) {
        return goodsDao.batchRemove(ids);
    }

    @Override
    public boolean exit(Map<String, Object> params) {
        return false;
    }
    @Autowired
    private BootdoConfig bootdoConfig;
    @Override
    public Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long id) throws Exception {
        String fileName = file.getOriginalFilename();
        fileName = FileUtil.renameToUUID(fileName);
        FileDO sysFile = new FileDO(FileType.fileType(fileName), "/files/" + fileName, new Date());
        //获取图片后缀
        String prefix = fileName.substring((fileName.lastIndexOf(".") + 1));
        String[] str = avatar_data.split(",");
        //获取截取的x坐标
        int x = (int) Math.floor(Double.parseDouble(str[0].split(":")[1]));
        //获取截取的y坐标
        int y = (int) Math.floor(Double.parseDouble(str[1].split(":")[1]));
        //获取截取的高度
        int h = (int) Math.floor(Double.parseDouble(str[2].split(":")[1]));
        //获取截取的宽度
        int w = (int) Math.floor(Double.parseDouble(str[3].split(":")[1]));
        //获取旋转的角度
        int r = Integer.parseInt(str[4].split(":")[1].replaceAll("}", ""));
        try {
            BufferedImage cutImage = ImageUtils.cutImage(file, x, y, w, h, prefix);
            BufferedImage rotateImage = ImageUtils.rotateImage(cutImage, r);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(rotateImage, prefix, out);
            //转换后存入数据库
            byte[] b = out.toByteArray();
            FileUtil.uploadFile(b, bootdoConfig.getUploadPath(), fileName);
        } catch (Exception e) {
            throw new Exception("图片裁剪错误！！");
        }
        Map<String, Object> result = new HashMap<>();
        if (sysFileService.save(sysFile) > 0) {
            GoodsDO goodsDO = new GoodsDO();
            goodsDO.setId(id);
            goodsDO.setImages(sysFile.getId());
            goodsDO.setStatus(0); //上架
            if (goodsDao.update(goodsDO) > 0 ) {
                result.put("url", sysFile.getUrl());
            }
        }
        return result;
    }
}
