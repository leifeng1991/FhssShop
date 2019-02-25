package com.fhss.shop.bean;

import com.zrq.base.model.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class ComBoChoiceListBean extends BaseBean {

    private List<ListBean> list = new ArrayList<>();

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * comboId : 1
         * comboName : 青铜套餐
         * price : 300
         * timeCycle : 三个月
         */

        private int comboId;
        private String comboName;
        private int price;
        private String timeCycle;

        public int getComboId() {
            return comboId;
        }

        public void setComboId(int comboId) {
            this.comboId = comboId;
        }

        public String getComboName() {
            return comboName;
        }

        public void setComboName(String comboName) {
            this.comboName = comboName;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getTimeCycle() {
            return timeCycle;
        }

        public void setTimeCycle(String timeCycle) {
            this.timeCycle = timeCycle;
        }
    }
}
