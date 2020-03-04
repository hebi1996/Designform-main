package com.design.system.domain;

import com.design.common.domain.FileDO;

import java.io.Serializable;
import java.util.List;

public class GoodsDO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id; //商品id
    private String name; // 商品名称
    private Double price; // 商品单价
    private String remark; // 商品描述信息
    private Integer status; // 商品状态  上架  0 下架 1 缺货 2
    private Long images; // 商品图片
    private String imagesUrl; //商品图片链接
    private Long userIdCreate; // 商品所属店铺
    private Long gmtCreate;
    private List<FileDO> files; //商品图片

    public GoodsDO() {
    }

    public GoodsDO(Long id, String name, Double price, String remark, Integer status, Long images, Long userIdCreate, Long gmtCreate, List<FileDO> files) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.remark = remark;
        this.status = status;
        this.images = images;
        this.userIdCreate = userIdCreate;
        this.gmtCreate = gmtCreate;
        this.files = files;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(String imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getImages() {
        return images;
    }

    public void setImages(Long images) {
        this.images = images;
    }

    public Long getUserIdCreate() {
        return userIdCreate;
    }

    public void setUserIdCreate(Long userIdCreate) {
        this.userIdCreate = userIdCreate;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public List<FileDO> getFiles() {
        return files;
    }

    public void setFiles(List<FileDO> files) {
        this.files = files;
    }
}
