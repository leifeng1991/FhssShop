package com.fhss.shop.bean;

import com.zrq.base.model.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class MessageListBean extends BaseBean {

    private List<DataBean> data = new ArrayList<>();

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createTime : 2018-11-20 14:14:07
         * infoCentent : 尊敬的用户，您已成功提交订单，订单号为3e9798774455
         * infoId : 10
         * infoStatus : 1
         * infoType : null
         * orderId : acbcdc652
         * userId : 66
         */

        private String createTime;
        private String infoCentent;
        private int infoId;
        private int infoStatus;
        private Object infoType;
        private String orderId;
        private int userId;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getInfoCentent() {
            return infoCentent;
        }

        public void setInfoCentent(String infoCentent) {
            this.infoCentent = infoCentent;
        }

        public int getInfoId() {
            return infoId;
        }

        public void setInfoId(int infoId) {
            this.infoId = infoId;
        }

        public int getInfoStatus() {
            return infoStatus;
        }

        public void setInfoStatus(int infoStatus) {
            this.infoStatus = infoStatus;
        }

        public Object getInfoType() {
            return infoType;
        }

        public void setInfoType(Object infoType) {
            this.infoType = infoType;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
