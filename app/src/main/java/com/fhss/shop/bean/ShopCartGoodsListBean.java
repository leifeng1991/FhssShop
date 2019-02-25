package com.fhss.shop.bean;

import com.zrq.base.model.BaseBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShopCartGoodsListBean extends BaseBean {

    /**
     * map : {"arrayList":[{"address":"霍营小区","addressId":3,"alias":"111","birthday":"2016-12-12 00:00:00","byGeneralize":"1","email":"22","generalize":"12122156","homeAddress":"231","identityCard":"110","integral":1,"password":"321","regTime":null,"sex":1,"userId":16,"userImg":"22","userName":"user","userPhone":"555"},{"address":"复兴小区","addressId":4,"alias":"123","birthday":"2033-05-05 00:00:00","byGeneralize":"12","email":"123","generalize":"2619","homeAddress":"123","identityCard":"140411199612122619","integral":12,"password":"123","regTime":"2018-06-06 00:00:00","sex":1,"userId":15,"userImg":"12","userName":"zhangsan","userPhone":"12011033"},{"address":"闪现小区","addressId":5,"alias":"","birthday":null,"byGeneralize":null,"email":"","generalize":null,"homeAddress":"","identityCard":"","integral":null,"password":"admin","regTime":null,"sex":1,"userId":14,"userImg":"","userName":"admin","userPhone":"15835562159"}],"list":[{"cartId":4,"goodsAgent":null,"goodsId":17,"goodsMarket":null,"goodsMembers":null,"goodsName":null,"goodsNumber":null,"goodsSn":null,"userId":17},{"cartId":5,"goodsAgent":null,"goodsId":17,"goodsMarket":null,"goodsMembers":null,"goodsName":null,"goodsNumber":null,"goodsSn":null,"userId":17},{"cartId":6,"goodsAgent":null,"goodsId":18,"goodsMarket":null,"goodsMembers":null,"goodsName":null,"goodsNumber":null,"goodsSn":null,"userId":17}]}
     * meg : OK
     */

    private MapBean map = new MapBean();
    private String meg;

    public MapBean getMap() {
        return map;
    }

    public void setMap(MapBean map) {
        this.map = map;
    }

    public String getMeg() {
        return meg;
    }

    public void setMeg(String meg) {
        this.meg = meg;
    }

    public static class MapBean {
        private List<ArrayListBean> arrayList = new ArrayList<>();
        private List<ListBean> list = new ArrayList<>();

        public List<ArrayListBean> getArrayList() {
            return arrayList;
        }

        public void setArrayList(List<ArrayListBean> arrayList) {
            this.arrayList = arrayList;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ArrayListBean {
            /**
             * address : 霍营小区
             * addressId : 3
             * alias : 111
             * birthday : 2016-12-12 00:00:00
             * byGeneralize : 1
             * email : 22
             * generalize : 12122156
             * homeAddress : 231
             * identityCard : 110
             * integral : 1
             * password : 321
             * regTime : null
             * sex : 1
             * userId : 16
             * userImg : 22
             * userName : user
             * userPhone : 555
             */

            private String address;
            private int addressId;
            private String alias;
            private String birthday;
            private String byGeneralize;
            private String email;
            private String generalize;
            private String homeAddress;
            private String identityCard;
            private int integral;
            private String password;
            private String regTime;
            private int sex;
            private int userId;
            private String userImg;
            private String userName;
            private String userPhone;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getAddressId() {
                return addressId;
            }

            public void setAddressId(int addressId) {
                this.addressId = addressId;
            }

            public String getAlias() {
                return alias;
            }

            public void setAlias(String alias) {
                this.alias = alias;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getByGeneralize() {
                return byGeneralize;
            }

            public void setByGeneralize(String byGeneralize) {
                this.byGeneralize = byGeneralize;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getGeneralize() {
                return generalize;
            }

            public void setGeneralize(String generalize) {
                this.generalize = generalize;
            }

            public String getHomeAddress() {
                return homeAddress;
            }

            public void setHomeAddress(String homeAddress) {
                this.homeAddress = homeAddress;
            }

            public String getIdentityCard() {
                return identityCard;
            }

            public void setIdentityCard(String identityCard) {
                this.identityCard = identityCard;
            }

            public int getIntegral() {
                return integral;
            }

            public void setIntegral(int integral) {
                this.integral = integral;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getRegTime() {
                return regTime;
            }

            public void setRegTime(String regTime) {
                this.regTime = regTime;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getUserImg() {
                return userImg;
            }

            public void setUserImg(String userImg) {
                this.userImg = userImg;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserPhone() {
                return userPhone;
            }

            public void setUserPhone(String userPhone) {
                this.userPhone = userPhone;
            }
        }

        public static class ListBean implements Serializable {
            /**
             * cartId : 4
             * goodsAgent : null
             * goodsId : 17
             * goodsMarket : null
             * goodsMembers : null
             * goodsName : null
             * goodsNumber : null
             * goodsSn : null
             * userId : 17
             */

            private int cartId;
            private String goodsAgent;
            private int goodsId;
            private String goodsMarket;
            private String goodsMembers;
            private String goodsName;
            private String goodsNumber;
            private String goodsSn;
            private String goodsImg;
            private int userId;
            private boolean checked;
            private int goodsIntegral;
            private String goodsColor;

            public String getGoodsColor() {
                return goodsColor;
            }

            public void setGoodsColor(String goodsColor) {
                this.goodsColor = goodsColor;
            }

            public ListBean(String goodsAgent, int goodsId, String goodsMarket, String goodsMembers, String goodsName, String goodsNumber, String goodsSn, String goodsImg, int userId, int goodsIntegral, String goodsColor) {
                this.goodsAgent = goodsAgent;
                this.goodsId = goodsId;
                this.goodsMarket = goodsMarket;
                this.goodsMembers = goodsMembers;
                this.goodsName = goodsName;
                this.goodsNumber = goodsNumber;
                this.goodsSn = goodsSn;
                this.goodsImg = goodsImg;
                this.userId = userId;
                this.goodsIntegral = goodsIntegral;
                this.goodsColor = goodsColor;
            }

            public int getGoodsIntegral() {
                return goodsIntegral;
            }

            public void setGoodsIntegral(int goodsIntegral) {
                this.goodsIntegral = goodsIntegral;
            }

            public String getGoodsImg() {
                return goodsImg;
            }

            public void setGoodsImg(String goodsImg) {
                this.goodsImg = goodsImg;
            }

            public boolean isChecked() {
                return checked;
            }

            public void setChecked(boolean checked) {
                this.checked = checked;
            }

            public int getCartId() {
                return cartId;
            }

            public void setCartId(int cartId) {
                this.cartId = cartId;
            }

            public String getGoodsAgent() {
                return goodsAgent;
            }

            public void setGoodsAgent(String goodsAgent) {
                this.goodsAgent = goodsAgent;
            }

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public String getGoodsMarket() {
                return goodsMarket;
            }

            public void setGoodsMarket(String goodsMarket) {
                this.goodsMarket = goodsMarket;
            }

            public String getGoodsMembers() {
                return goodsMembers;
            }

            public void setGoodsMembers(String goodsMembers) {
                this.goodsMembers = goodsMembers;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsNumber() {
                return goodsNumber;
            }

            public void setGoodsNumber(String goodsNumber) {
                this.goodsNumber = goodsNumber;
            }

            public String getGoodsSn() {
                return goodsSn;
            }

            public void setGoodsSn(String goodsSn) {
                this.goodsSn = goodsSn;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
    }
}
