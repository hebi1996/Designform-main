package com.design.system.domain;

import java.io.Serializable;

public class ShopingCertDO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id; // 购物id
    private Long goodsId; //商品id
    private String goodsName;
    private String imageUrl;
    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private Integer number; //商品数量
    private Long userIdCreate; //购物人
    private Long gmtCreate; //购物时间

    public ShopingCertDO() {
    }

    public ShopingCertDO(Long id, Long goodsId, Integer number, Long userIdCreate, Long gmtCreate) {
        this.id = id;
        this.goodsId = goodsId;
        this.number = number;
        this.userIdCreate = userIdCreate;
        this.gmtCreate = gmtCreate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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
}
