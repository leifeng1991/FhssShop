package com.fhss.shop.bean;


import com.zrq.base.model.BaseBean;

import java.util.List;

/**
 * 描述:
 *
 * @author zhangrq
 * 2017/12/5 11:20
 */
public class HomeBean extends BaseBean {

    private DataBean data;

    public static class DataBean {

        /**
         * banner : [{"simg":"http://zhaolin-10029121.image.myqcloud.com/sample1527660347259742","type":"0","to":"0"},{"simg":"http://zhaolin-10029121.image.myqcloud.com/sample1527660087814090","type":"0","to":"0"},{"simg":"http://zhaolin-10029121.image.myqcloud.com/sample1527660015291252","type":"0","to":"0"}]
         * menu : [{"id":"131","title":"户门维修","simg":"http://zhaolin-10029121.image.myqcloud.com/sample1527659271680945","type":"2","to":"1"},{"id":"132","title":"卫浴设备","simg":"http://zhaolin-10029121.image.myqcloud.com/sample1527659932915937","type":"2","to":"5"},{"id":"133","title":"墙面地砖","simg":"http://zhaolin-10029121.image.myqcloud.com/sample1527660482497366","type":"5","to":"10"},{"id":"134","title":"燃气维修","simg":"http://zhaolin-10029121.image.myqcloud.com/sample1527660572839696","type":"5","to":"3"},{"id":"135","title":"门窗维修","simg":"http://zhaolin-10029121.image.myqcloud.com/sample1527660606726138","type":"5","to":"3"},{"id":"136","title":"防水维修","simg":"http://zhaolin-10029121.image.myqcloud.com/sample1527660626205182","type":"5","to":"10"},{"id":"137","title":"壁挂炉","simg":"http://zhaolin-10029121.image.myqcloud.com/sample1527660639140834","type":"5","to":"3"},{"id":"138","title":"全部维修","simg":"http://zhaolin-10029121.image.myqcloud.com/sample1527660706253008","type":"2","to":"0"}]
         * serviceAdd : {"simg":"","title":"","button":"申请入驻"}
         */

        private ServiceAddBean serviceAdd;
        private List<BannerBean> banner;
        private List<MenuBean> menu;

        public ServiceAddBean getServiceAdd() {
            return serviceAdd;
        }

        public void setServiceAdd(ServiceAddBean serviceAdd) {
            this.serviceAdd = serviceAdd;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<MenuBean> getMenu() {
            return menu;
        }

        public void setMenu(List<MenuBean> menu) {
            this.menu = menu;
        }

        public static class ServiceAddBean {
            /**
             * simg :
             * title :
             * button : 申请入驻
             */

            private String simg;
            private String title;
            private String button;

            public String getSimg() {
                return simg;
            }

            public void setSimg(String simg) {
                this.simg = simg;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getButton() {
                return button;
            }

            public void setButton(String button) {
                this.button = button;
            }
        }

        public static class BannerBean {
            /**
             * simg : http://zhaolin-10029121.image.myqcloud.com/sample1527660347259742
             * type : 0
             * to : 0
             */

            private String simg;
            private String type;
            private String to;

            public String getSimg() {
                return simg;
            }

            public void setSimg(String simg) {
                this.simg = simg;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTo() {
                return to;
            }

            public void setTo(String to) {
                this.to = to;
            }
        }

        public static class MenuBean {
            /**
             * id : 131
             * title : 户门维修
             * simg : http://zhaolin-10029121.image.myqcloud.com/sample1527659271680945
             * type : 2
             * to : 1
             */
            private String title;
            private String simg;
            private String type;
            private String to;
            private String project_title;
            private String project_pid;

            public String getProject_pid() {
                return project_pid;
            }

            public void setProject_pid(String project_pid) {
                this.project_pid = project_pid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSimg() {
                return simg;
            }

            public void setSimg(String simg) {
                this.simg = simg;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTo() {
                return to;
            }

            public void setTo(String to) {
                this.to = to;
            }

            public String getProject_title() {
                return project_title;
            }

            public void setProject_title(String project_title) {
                this.project_title = project_title;
            }
        }
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }
}
