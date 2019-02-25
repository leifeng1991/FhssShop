package com.fhss.shop.bean;

import com.zrq.base.model.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class GoodsNewListBean extends BaseBean {

    /**
     * data : {"rows":[{"attributeId":null,"brandId":0,"classifyId":0,"goodsAgent":null,"goodsDesc":null,"goodsId":12,"goodsImg":null,"goodsIntegral":null,"goodsMarket":null,"goodsMembers":null,"goodsName":"你好","goodsSn":"","goodsTime":null,"goodsTypeId":0,"isBest":0,"isDelete":0,"isHot":0,"isNew":1,"specifId":0}],"total":1}
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
         * rows : [{"attributeId":null,"brandId":0,"classifyId":0,"goodsAgent":null,"goodsDesc":null,"goodsId":12,"goodsImg":null,"goodsIntegral":null,"goodsMarket":null,"goodsMembers":null,"goodsName":"你好","goodsSn":"","goodsTime":null,"goodsTypeId":0,"isBest":0,"isDelete":0,"isHot":0,"isNew":1,"specifId":0}]
         * total : 1
         */

        private int total;
        private ArrayList<RowsBean> rows = new ArrayList<>();

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public ArrayList<RowsBean> getRows() {
            return rows;
        }

        public void setRows(ArrayList<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            /**
             * attributeId : null
             * brandId : 0
             * classifyId : 0
             * goodsAgent : null
             * goodsDesc : null
             * goodsId : 12
             * goodsImg : null
             * goodsIntegral : null
             * goodsMarket : null
             * goodsMembers : null
             * goodsName : 你好
             * goodsSn :
             * goodsTime : null
             * goodsTypeId : 0
             * isBest : 0
             * isDelete : 0
             * isHot : 0
             * isNew : 1
             * specifId : 0
             */

            private String attributeId;
            private int brandId;
            private int classifyId;
            private String goodsAgent;
            private String goodsDesc;
            private String goodsId;
            private String goodsImg;
            private String goodsIntegral;
            private String goodsMarket;
            private String goodsMembers;
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
            private ArrayList<String> imgUrl = new ArrayList<>();

            public String getGoodsColor() {
                return goodsColor;
            }

            public void setGoodsColor(String goodsColor) {
                this.goodsColor = goodsColor;
            }

            public ArrayList<String> getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(ArrayList<String> imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getAttributeId() {
                return attributeId;
            }

            public void setAttributeId(String attributeId) {
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

            public String getGoodsAgent() {
                return goodsAgent;
            }

            public void setGoodsAgent(String goodsAgent) {
                this.goodsAgent = goodsAgent;
            }

            public String getGoodsDesc() {
                return goodsDesc;
            }

            public void setGoodsDesc(String goodsDesc) {
                this.goodsDesc = goodsDesc;
            }

            public String getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(String goodsId) {
                this.goodsId = goodsId;
            }

            public String getGoodsImg() {
                return goodsImg;
            }

            public void setGoodsImg(String goodsImg) {
                this.goodsImg = goodsImg;
            }

            public String getGoodsIntegral() {
                return goodsIntegral;
            }

            public void setGoodsIntegral(String goodsIntegral) {
                this.goodsIntegral = goodsIntegral;
            }

            public String getGoodsMarket() {
                return goodsMarket;
            }

            public void setGoodsMarket(String goodsMarket) {
                this.goodsMarket = goodsMarket;
            }

            public String getGoodsMembers() {
                return goodsMembers;
            }

            public void setGoodsMembers(String goodsMembers) {
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
}
