package com.fhss.shop.bean;

import com.zrq.base.model.BaseBean;

public class LogisticsInformationBean extends BaseBean {
    /**
     * data : {"message":"ok","nu":"0018345304860101","ischeck":"1","condition":"F00","com":"suning","status":"200","state":"3","data":[{"time":"2018-11-14 20:09:50","ftime":"2018-11-14 20:09:50","context":"您的订单已由本人签收。感谢您在苏宁易购官方旗舰店购物，欢迎再次光临"},{"time":"2018-11-14 16:50:58","ftime":"2018-11-14 16:50:58","context":"已安排送货，请您做好收货准备,送货员【张士勇13439880224】"},{"time":"2018-11-14 16:50:25","ftime":"2018-11-14 16:50:25","context":"您的商品已到达【北京昌平立汤路快递站】"},{"time":"2018-11-14 04:23:05","ftime":"2018-11-14 04:23:05","context":"您的商品已发货出库，下一站【北京昌平立汤路快递站】"},{"time":"2018-11-13 18:23:05","ftime":"2018-11-13 18:23:05","context":"您的商品已完成拣货"},{"time":"2018-11-13 18:23:05","ftime":"2018-11-13 18:23:05","context":"仓库作业即将完成，预计【2018-11-14】发货出库"},{"time":"2018-11-11 08:05:02","ftime":"2018-11-11 08:05:02","context":"已分配给出货仓库，您的发货清单已打印，苏宁北京通州中心仓作业准备中"}]}
     */

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
