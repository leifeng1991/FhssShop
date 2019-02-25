package com.fhss.shop.bean;

import java.util.ArrayList;
import java.util.List;

public class AddressListBean {

    /**
     * name : 北京
     * child : [{"name":"北京","child":[{"value":"东城区"},{"value":"西城区"},{"value":"崇文区"},{"value":"宣武区"},{"value":"朝阳区"},{"value":"丰台区"},{"value":"石景山区"},{"value":"海淀区"},{"value":"门头沟区"},{"value":"房山区"},{"value":"通州区"},{"value":"顺义区"},{"value":"昌平区"},{"value":"大兴区"},{"value":"平谷区"},{"value":"怀柔区"},{"value":"密云县"},{"value":"延庆县"}]}]
     */

    private String name;
    private List<ChildBeanX> child = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChildBeanX> getChild() {
        return child;
    }

    public void setChild(List<ChildBeanX> child) {
        this.child = child;
    }

    public static class ChildBeanX {
        /**
         * name : 北京
         * child : [{"value":"东城区"},{"value":"西城区"},{"value":"崇文区"},{"value":"宣武区"},{"value":"朝阳区"},{"value":"丰台区"},{"value":"石景山区"},{"value":"海淀区"},{"value":"门头沟区"},{"value":"房山区"},{"value":"通州区"},{"value":"顺义区"},{"value":"昌平区"},{"value":"大兴区"},{"value":"平谷区"},{"value":"怀柔区"},{"value":"密云县"},{"value":"延庆县"}]
         */

        private String name;
        private List<ChildBean> child = new ArrayList<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ChildBean> getChild() {
            return child;
        }

        public void setChild(List<ChildBean> child) {
            this.child = child;
        }

        public static class ChildBean {
            /**
             * value : 东城区
             */

            private String value;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}
