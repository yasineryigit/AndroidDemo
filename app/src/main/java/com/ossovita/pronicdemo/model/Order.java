package com.ossovita.pronicdemo.model;

import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("orderPk")
    private long orderPk;
    @SerializedName("orderNo")
    private long orderNo;
    @SerializedName("orderTime")
    private String orderTime;
    @SerializedName("clientInfo")
    private String clientInfo;

    @SerializedName("orderPrice")
    private int orderPrice;

    public long getOrderPk() {
        return orderPk;
    }

    public void setOrderPk(long orderPk) {
        this.orderPk = orderPk;
    }

    public long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(long orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }
}
