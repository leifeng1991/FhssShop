package com.fhss.shop.bean;

import java.util.List;

public class OrderInfoBean {
    private String userId;
    private String consignee;
    private String address;
    private String mobile;
    private String postcode;
    private String remark;
    private String total;
    private String usingIntegral;
    private String pointIntegral;
    private List<OrderGoods> oGoodsList;

    public OrderInfoBean(String userId, String consignee, String address, String mobile, String postcode, String remark, String total,String usingIntegral,String pointIntegral,List<OrderGoods> oGoodsList) {
        this.userId = userId;
        this.consignee = consignee;
        this.address = address;
        this.mobile = mobile;
        this.postcode = postcode;
        this.remark = remark;
        this.total = total;
        this.usingIntegral = usingIntegral;
        this.pointIntegral = pointIntegral;
        this.oGoodsList = oGoodsList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<OrderGoods> getoGoodsList() {
        return oGoodsList;
    }

    public void setoGoodsList(List<OrderGoods> oGoodsList) {
        this.oGoodsList = oGoodsList;
    }
}
