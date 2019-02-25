package com.fhss.shop.bean;

import com.zrq.base.model.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class BrandListBean extends BaseBean {

    private List<DataBean> data = new ArrayList<>();

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * brandDescribe : 好用
         * brandId : 186
         * brandIsshow : 1
         * brandLetter : null
         * brandLogo : https://gss1.bdstatic.com/-vo3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268%3Bg%3D0/sign=c5a8fe6fc9fdfc03e578e4beec04e0a9/242dd42a2834349b0e6f6614c3ea15ce36d3be31.jpg
         * brandName : bos
         * brandSort : 50
         * brandUrl : www.lol.qq.com/
         */

        private String brandDescribe;
        private int brandId;
        private int brandIsshow;
        private Object brandLetter;
        private String brandLogo;
        private String brandName;
        private int brandSort;
        private String brandUrl;

        public String getBrandDescribe() {
            return brandDescribe;
        }

        public void setBrandDescribe(String brandDescribe) {
            this.brandDescribe = brandDescribe;
        }

        public int getBrandId() {
            return brandId;
        }

        public void setBrandId(int brandId) {
            this.brandId = brandId;
        }

        public int getBrandIsshow() {
            return brandIsshow;
        }

        public void setBrandIsshow(int brandIsshow) {
            this.brandIsshow = brandIsshow;
        }

        public Object getBrandLetter() {
            return brandLetter;
        }

        public void setBrandLetter(Object brandLetter) {
            this.brandLetter = brandLetter;
        }

        public String getBrandLogo() {
            return brandLogo;
        }

        public void setBrandLogo(String brandLogo) {
            this.brandLogo = brandLogo;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public int getBrandSort() {
            return brandSort;
        }

        public void setBrandSort(int brandSort) {
            this.brandSort = brandSort;
        }

        public String getBrandUrl() {
            return brandUrl;
        }

        public void setBrandUrl(String brandUrl) {
            this.brandUrl = brandUrl;
        }
    }
}
