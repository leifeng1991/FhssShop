package com.fhss.shop.bean;

import com.zrq.base.model.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class BannerBean extends BaseBean {

    private List<DataBean> data = new ArrayList<>();

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * imgUrl : http://47.93.116.213:8088/FuhessApp_goodsModule/bannerImg/123.jpg
         */

        private int id;
        private String imgUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
