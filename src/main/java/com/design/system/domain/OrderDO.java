package com.design.system.domain;

import java.io.Serializable;

/**
 * @author yangzhi
 * @create 2020/1/21
 */
public class OrderDO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id; //订单id
    private String goodsIds; // 商品id的信息 1,2,3,4,5,6
    private Double price; // 订单价格
    private Long userIdCreate;
    private Long gmtCreate; // 创建订单时间
    private Long payTime; // 支付订单时间
    private Integer payType; //支付方式
    private Integer payStatus; // 支付状态  0 未支付 1已经支付

    public OrderDO(Long id, String goodsIds, Double price, Long userIdCreate, Long gmtCreate, Long payTime, Integer payType, Integer payStatus) {
        this.id = id;
        this.goodsIds = goodsIds;
        this.price = price;
        this.userIdCreate = userIdCreate;
        this.gmtCreate = gmtCreate;
        this.payTime = payTime;
        this.payType = payType;
        this.payStatus = payStatus;
    }

    public OrderDO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodsIds() {
        return goodsIds;
    }

    public void setGoodsIds(String goodsIds) {
        this.goodsIds = goodsIds;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public Long getPayTime() {
        return payTime;
    }

    public void setPayTime(Long payTime) {
        this.payTime = payTime;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }
}
