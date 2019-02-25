package com.fhss.shop.bean;

import com.zrq.base.model.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class MyOrderListBean extends BaseBean {

    private List<DataBean> data = new ArrayList<>();

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * address : 北京市顺义区石门地铁站
         * cancelTime : null
         * checkTime : null
         * consignee : 雷锋
         * creatTime : 2018-11-20 14:14:07
         * finishTime : null
         * mobile : 15088888888
         * oGoodsList : [{"getIntegral":null,"goodsId":19,"goodsImg":"http://www.fuhess.cn/images/201707/source_img/24_P_1500369903793.jpg","goodsName":"M&K Sound S150T","goodsNumber":1,"goodsPrice":21500,"goodsSn":"20181112003","orderId":"acbcdc652","recId":159},{"getIntegral":null,"goodsId":54,"goodsImg":"http://www.fuhess.cn/images/201707/goods_img/31_P_1500441739201.jpg","goodsName":"bos 67000","goodsNumber":1,"goodsPrice":220000,"goodsSn":"20181112008","orderId":"acbcdc652","recId":160}]
         * orderId : acbcdc652
         * orderSn : 3e9798774455
         * orderStatus : 1
         * pointIntegral : 0
         * postcode : 1000000
         * remark : 
         * total : 241500.0
         * userId : 66
         * usingIntegral : 0
         */

        private String address;
        private String cancelTime;
        private String checkTime;
        private String consignee;
        private String creatTime;
        private String finishTime;
        private String mobile;
        private String orderId;
        private String orderSn;
        private int orderStatus;
        private int pointIntegral;
        private int postcode;
        private String remark;
        private double total;
        private int userId;
        private int usingIntegral;
        private List<OGoodsListBean> oGoodsList = new ArrayList<>();

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCancelTime() {
            return cancelTime;
        }

        public void setCancelTime(String cancelTime) {
            this.cancelTime = cancelTime;
        }

        public String getCheckTime() {
            return checkTime;
        }

        public void setCheckTime(String checkTime) {
            this.checkTime = checkTime;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getCreatTime() {
            return creatTime;
        }

        public void setCreatTime(String creatTime) {
            this.creatTime = creatTime;
        }

        public String getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(String finishTime) {
            this.finishTime = finishTime;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public int getPointIntegral() {
            return pointIntegral;
        }

        public void setPointIntegral(int pointIntegral) {
            this.pointIntegral = pointIntegral;
        }

        public int getPostcode() {
            return postcode;
        }

        public void setPostcode(int postcode) {
            this.postcode = postcode;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getUsingIntegral() {
            return usingIntegral;
        }

        public void setUsingIntegral(int usingIntegral) {
            this.usingIntegral = usingIntegral;
        }

        public List<OGoodsListBean> getOGoodsList() {
            return oGoodsList;
        }

        public void setOGoodsList(List<OGoodsListBean> oGoodsList) {
            this.oGoodsList = oGoodsList;
        }

        public static class OGoodsListBean {
            /**
             * getIntegral : null
             * goodsId : 19
             * goodsImg : http://www.fuhess.cn/images/201707/source_img/24_P_1500369903793.jpg
             * goodsName : M&K Sound S150T
             * goodsNumber : 1
             * goodsPrice : 21500.0
             * goodsSn : 20181112003
             * orderId : acbcdc652
             * recId : 159
             */

            private String getIntegral;
            private int goodsId;
            private String goodsImg;
            private String goodsName;
            private int goodsNumber;
            private double goodsPrice;
            private String goodsSn;
            private String orderId;
            private int recId;

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

            public String getGoodsImg() {
                return goodsImg;
            }

            public void setGoodsImg(String goodsImg) {
                this.goodsImg = goodsImg;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public int getGoodsNumber() {
                return goodsNumber;
            }

            public void setGoodsNumber(int goodsNumber) {
                this.goodsNumber = goodsNumber;
            }

            public double getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(double goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public String getGoodsSn() {
                return goodsSn;
            }

            public void setGoodsSn(String goodsSn) {
                this.goodsSn = goodsSn;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public int getRecId() {
                return recId;
            }

            public void setRecId(int recId) {
                this.recId = recId;
            }
        }
    }
}
