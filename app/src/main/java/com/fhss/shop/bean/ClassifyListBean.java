package com.fhss.shop.bean;

import com.zrq.base.model.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class ClassifyListBean extends BaseBean {

    private List<DataBean> data = new ArrayList<>();

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * classifyId : 6
         * classifyName : HIFI音响
         * classifyParentId : null
         * isShow : 1
         * sortOrder : 50
         */

        private String classifyId;
        private String classifyName;
        private String classifyParentId;
        private int isShow;
        private int sortOrder;

        public String getClassifyId() {
            return classifyId;
        }

        public void setClassifyId(String classifyId) {
            this.classifyId = classifyId;
        }

        public String getClassifyName() {
            return classifyName;
        }

        public void setClassifyName(String classifyName) {
            this.classifyName = classifyName;
        }

        public String getClassifyParentId() {
            return classifyParentId;
        }

        public void setClassifyParentId(String classifyParentId) {
            this.classifyParentId = classifyParentId;
        }

        public int getIsShow() {
            return isShow;
        }

        public void setIsShow(int isShow) {
            this.isShow = isShow;
        }

        public int getSortOrder() {
            return sortOrder;
        }

        public void setSortOrder(int sortOrder) {
            this.sortOrder = sortOrder;
        }
    }
}
