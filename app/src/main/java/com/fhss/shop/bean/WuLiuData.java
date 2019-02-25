package com.fhss.shop.bean;

import java.util.List;

public class WuLiuData {

    /**
     * message : ok
     * nu : 0018345304860101
     * ischeck : 1
     * condition : F00
     * com : suning
     * status : 200
     * state : 3
     * data : [{"time":"2018-11-14 20:09:50","ftime":"2018-11-14 20:09:50","context":"您的订单已由本人签收。感谢您在苏宁易购官方旗舰店购物，欢迎再次光临"},{"time":"2018-11-14 16:50:58","ftime":"2018-11-14 16:50:58","context":"已安排送货，请您做好收货准备,送货员【张士勇13439880224】"},{"time":"2018-11-14 16:50:25","ftime":"2018-11-14 16:50:25","context":"您的商品已到达【北京昌平立汤路快递站】"},{"time":"2018-11-14 04:23:05","ftime":"2018-11-14 04:23:05","context":"您的商品已发货出库，下一站【北京昌平立汤路快递站】"},{"time":"2018-11-13 18:23:05","ftime":"2018-11-13 18:23:05","context":"您的商品已完成拣货"},{"time":"2018-11-13 18:23:05","ftime":"2018-11-13 18:23:05","context":"仓库作业即将完成，预计【2018-11-14】发货出库"},{"time":"2018-11-11 08:05:02","ftime":"2018-11-11 08:05:02","context":"已分配给出货仓库，您的发货清单已打印，苏宁北京通州中心仓作业准备中"}]
     */

    private String message;
    private String nu;
    private String ischeck;
    private String condition;
    private String com;
    private String status;
    private String state;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 2018-11-14 20:09:50
         * ftime : 2018-11-14 20:09:50
         * context : 您的订单已由本人签收。感谢您在苏宁易购官方旗舰店购物，欢迎再次光临
         */

        private String time;
        private String ftime;
        private String context;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }
    }
}
