package com.fhss.shop.bean;

import com.zrq.base.model.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class BankListBean extends BaseBean {

    /**
     * lists : [{"bankId":7,"bankName":"工商银行","cardNum":"**** **** **** 4000","realName":"雷锋","userId":66}]
     * meg : 成功
     */

    private String meg;
    private List<ListsBean> lists = new ArrayList<>();

    public String getMeg() {
        return meg;
    }

    public void setMeg(String meg) {
        this.meg = meg;
    }

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    public static class ListsBean {
        /**
         * bankId : 7
         * bankName : 工商银行
         * cardNum : **** **** **** 4000
         * realName : 雷锋
         * userId : 66
         */

        private int bankId;
        private String bankName;
        private String cardNum;
        private String realName;
        private int userId;

        public int getBankId() {
            return bankId;
        }

        public void setBankId(int bankId) {
            this.bankId = bankId;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getCardNum() {
            return cardNum;
        }

        public void setCardNum(String cardNum) {
            this.cardNum = cardNum;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
