package com.fhss.shop.bean;

import com.zrq.base.model.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class ClassifyChildListBean extends BaseBean {
    /**
     * data : {"parent":[{"classifyParentId":745,"classifyId":756,"sortOrder":50,"classifyName":"书架式音箱","isShow":1},{"classifyParentId":745,"classifyId":755,"sortOrder":50,"classifyName":"入墙式音箱","isShow":1},{"classifyParentId":745,"classifyId":754,"sortOrder":50,"classifyName":"环绕音响","isShow":1},{"classifyParentId":745,"classifyId":753,"sortOrder":50,"classifyName":"中置音箱","isShow":1},{"classifyParentId":745,"classifyId":752,"sortOrder":50,"classifyName":"落地式音响","isShow":1},{"classifyParentId":745,"classifyId":751,"sortOrder":50,"classifyName":"功放","isShow":1}],"child":[{"classifyParentId":752,"classifyId":757,"sortOrder":50,"classifyName":"M&K Sound","isShow":1},{"classifyParentId":752,"classifyId":758,"sortOrder":50,"classifyName":"JBL","isShow":1}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ParentBean> parent = new ArrayList<>();
        private List<ChildBean> child = new ArrayList<>();

        public List<ParentBean> getParent() {
            return parent;
        }

        public void setParent(List<ParentBean> parent) {
            this.parent = parent;
        }

        public List<ChildBean> getChild() {
            return child;
        }

        public void setChild(List<ChildBean> child) {
            this.child = child;
        }

        public static class ParentBean {
            /**
             * classifyParentId : 745
             * classifyId : 756
             * sortOrder : 50
             * classifyName : 书架式音箱
             * isShow : 1
             */

            private int classifyParentId;
            private int classifyId;
            private int sortOrder;
            private String classifyName;
            private int isShow;
            private List<ChildBean> list = new ArrayList<>();

            public List<ChildBean> getList() {
                return list;
            }

            public void setList(List<ChildBean> list) {
                this.list = list;
            }

            public int getClassifyParentId() {
                return classifyParentId;
            }

            public void setClassifyParentId(int classifyParentId) {
                this.classifyParentId = classifyParentId;
            }

            public int getClassifyId() {
                return classifyId;
            }

            public void setClassifyId(int classifyId) {
                this.classifyId = classifyId;
            }

            public int getSortOrder() {
                return sortOrder;
            }

            public void setSortOrder(int sortOrder) {
                this.sortOrder = sortOrder;
            }

            public String getClassifyName() {
                return classifyName;
            }

            public void setClassifyName(String classifyName) {
                this.classifyName = classifyName;
            }

            public int getIsShow() {
                return isShow;
            }

            public void setIsShow(int isShow) {
                this.isShow = isShow;
            }
        }

        public static class ChildBean {
            /**
             * classifyParentId : 752
             * classifyId : 757
             * sortOrder : 50
             * classifyName : M&K Sound
             * isShow : 1
             */

            private int classifyParentId;
            private int classifyId;
            private int sortOrder;
            private String classifyName;
            private int isShow;

            public int getClassifyParentId() {
                return classifyParentId;
            }

            public void setClassifyParentId(int classifyParentId) {
                this.classifyParentId = classifyParentId;
            }

            public int getClassifyId() {
                return classifyId;
            }

            public void setClassifyId(int classifyId) {
                this.classifyId = classifyId;
            }

            public int getSortOrder() {
                return sortOrder;
            }

            public void setSortOrder(int sortOrder) {
                this.sortOrder = sortOrder;
            }

            public String getClassifyName() {
                return classifyName;
            }

            public void setClassifyName(String classifyName) {
                this.classifyName = classifyName;
            }

            public int getIsShow() {
                return isShow;
            }

            public void setIsShow(int isShow) {
                this.isShow = isShow;
            }
        }
    }
}
