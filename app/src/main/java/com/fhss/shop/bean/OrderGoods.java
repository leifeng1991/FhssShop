package com.fhss.shop.bean;

public class OrderGoods {


    /**
     * checked : true
     * goodsId : 17
     * goodsName : JBL ARENA 130 HI-FI书架音箱 环绕无源音响
     * goodsSn : 20181112001
     * goodsNumber : 1
     * goodsPrice : 1500.0
     * goodsImg : http://www.fuhess.cn/images/201708/goods_img/584_P_1501810265607.jpg
     */

    private int goodsId;
    private String goodsName;
    private String goodsSn;
    private String goodsNumber;
    private String goodsPrice;
    private String goodsImg;
    private String getIntegral;

    public OrderGoods(int goodsId, String goodsName, String goodsSn, String goodsNumber, String goodsPrice, String goodsImg,String getIntegral) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsSn = goodsSn;
        this.goodsNumber = goodsNumber;
        this.goodsPrice = goodsPrice;
        this.goodsImg = goodsImg;
        this.getIntegral = getIntegral;
    }

    public String getGetIntegral() {
        return getIntegral;
    }

    public void setGetIntegral(String getIntegral) {
        this.getIntegral = getIntegral;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public String getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }
}
