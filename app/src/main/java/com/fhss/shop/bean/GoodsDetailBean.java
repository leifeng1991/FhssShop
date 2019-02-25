package com.fhss.shop.bean;

import com.zrq.base.model.BaseBean;

import java.util.ArrayList;

public class GoodsDetailBean extends BaseBean {

    /**
     * data : {"attributeId":0,"brandId":188,"classifyId":758,"goodsAgent":215000,"goodsDesc":"http://47.93.116.213/bdimages/upload1/20170719/1500441935575066.jpg","goodsId":18,"goodsImg":"http://www.fuhess.cn/images/201707/goods_img/31_P_1500441739201.jpg","goodsIntegral":10000,"goodsMarket":229800,"goodsMembers":220000,"goodsName":"JBL 67000","goodsSn":"20181112002","goodsTime":null,"goodsTypeId":0,"isBest":1,"isDelete":0,"isHot":1,"isNew":1,"specifId":0}
     */

    private DataBean data = new DataBean();

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * attributeId : 0
         * brandId : 188
         * classifyId : 758
         * goodsAgent : 215000.0
         * goodsDesc : http://47.93.116.213/bdimages/upload1/20170719/1500441935575066.jpg
         * goodsId : 18
         * goodsImg : http://www.fuhess.cn/images/201707/goods_img/31_P_1500441739201.jpg
         * goodsIntegral : 10000
         * goodsMarket : 229800.0
         * goodsMembers : 220000.0
         * goodsName : JBL 67000
         * goodsSn : 20181112002
         * goodsTime : null
         * goodsTypeId : 0
         * isBest : 1
         * isDelete : 0
         * isHot : 1
         * isNew : 1
         * specifId : 0
         */

        private int attributeId;
        private int brandId;
        private int classifyId;
        private double goodsAgent;
        private String goodsDesc;
        private String goodsLocation;
        private int goodsId;
        private String goodsImg;
        private int goodsIntegral;
        private double goodsMarket;
        private double goodsMembers;
        private String goodsName;
        private String goodsSn;
        private String goodsTime;
        private int goodsTypeId;
        private int isBest;
        private int isDelete;
        private int isHot;
        private int isNew;
        private int specifId;
        private String goodsColor;

        public String getGoodsColor() {
            return goodsColor;
        }

        public void setGoodsColor(String goodsColor) {
            this.goodsColor = goodsColor;
        }

        private ArrayList<String> imgUrl = new ArrayList<>();

        public String getGoodsLocation() {
            return goodsLocation;
        }

        public void setGoodsLocation(String goodsLocation) {
            this.goodsLocation = goodsLocation;
        }

        public ArrayList<String> getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(ArrayList<String> imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getAttributeId() {
            return attributeId;
        }

        public void setAttributeId(int attributeId) {
            this.attributeId = attributeId;
        }

        public int getBrandId() {
            return brandId;
        }

        public void setBrandId(int brandId) {
            this.brandId = brandId;
        }

        public int getClassifyId() {
            return classifyId;
        }

        public void setClassifyId(int classifyId) {
            this.classifyId = classifyId;
        }

        public double getGoodsAgent() {
            return goodsAgent;
        }

        public void setGoodsAgent(double goodsAgent) {
            this.goodsAgent = goodsAgent;
        }

        public String getGoodsDesc() {
            return goodsDesc;
        }

        public void setGoodsDesc(String goodsDesc) {
            this.goodsDesc = goodsDesc;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsImg() {
            return goodsImg;
        }

        public void setGoodsImg(String goodsImg) {
            this.goodsImg = goodsImg;
        }

        public int getGoodsIntegral() {
            return goodsIntegral;
        }

        public void setGoodsIntegral(int goodsIntegral) {
            this.goodsIntegral = goodsIntegral;
        }

        public double getGoodsMarket() {
            return goodsMarket;
        }

        public void setGoodsMarket(double goodsMarket) {
            this.goodsMarket = goodsMarket;
        }

        public double getGoodsMembers() {
            return goodsMembers;
        }

        public void setGoodsMembers(double goodsMembers) {
            this.goodsMembers = goodsMembers;
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

        public String getGoodsTime() {
            return goodsTime;
        }

        public void setGoodsTime(String goodsTime) {
            this.goodsTime = goodsTime;
        }

        public int getGoodsTypeId() {
            return goodsTypeId;
        }

        public void setGoodsTypeId(int goodsTypeId) {
            this.goodsTypeId = goodsTypeId;
        }

        public int getIsBest() {
            return isBest;
        }

        public void setIsBest(int isBest) {
            this.isBest = isBest;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public int getIsHot() {
            return isHot;
        }

        public void setIsHot(int isHot) {
            this.isHot = isHot;
        }

        public int getIsNew() {
            return isNew;
        }

        public void setIsNew(int isNew) {
            this.isNew = isNew;
        }

        public int getSpecifId() {
            return specifId;
        }

        public void setSpecifId(int specifId) {
            this.specifId = specifId;
        }
    }
}
