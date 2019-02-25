package com.fhss.shop.bean;

import com.google.gson.annotations.SerializedName;
import com.zrq.base.model.BaseBean;

import java.util.List;

/**
 * 描述:
 *
 * @author leifeng
 * 2018/10/9 11:02
 */

public class User extends BaseBean {

    /**
     * map : {"msg":"用户名可用","list":[{"addressId":"1","alias":"111","birthday":"2016-12-12 00:00:00","byGeneralize":"1","email":"22","generalize":"12122156","homeAddress":"231","identityCard":"110","integral":1,"password":"321","regTime":null,"sex":1,"userId":16,"userImg":"22","userName":"user","userPhone":"555"},{"addressId":"1","alias":"123","birthday":"2033-05-05 00:00:00","byGeneralize":"12","email":"123","generalize":"2619","homeAddress":"123","identityCard":"140411199612122619","integral":12,"password":"123","regTime":"2018-06-06 00:00:00","sex":1,"userId":15,"userImg":"12","userName":"zhangsan","userPhone":"12011033"},{"addressId":null,"alias":"","birthday":null,"byGeneralize":null,"email":"","generalize":null,"homeAddress":"","identityCard":"","integral":null,"password":"admin","regTime":null,"sex":1,"userId":14,"userImg":"","userName":"admin","userPhone":"15835562159"},{"addressId":null,"alias":null,"birthday":null,"byGeneralize":null,"email":null,"generalize":null,"homeAddress":null,"identityCard":null,"integral":null,"password":"12350","regTime":null,"sex":null,"userId":7,"userImg":null,"userName":"wanghaiyun1","userPhone":"12345678910"},{"addressId":null,"alias":null,"birthday":null,"byGeneralize":null,"email":null,"generalize":null,"homeAddress":null,"identityCard":null,"integral":null,"password":"1235","regTime":null,"sex":null,"userId":13,"userImg":null,"userName":"why12","userPhone":"12345678910"}],"user":{"addressId":null,"alias":null,"birthday":null,"byGeneralize":null,"email":null,"generalize":"11111111","homeAddress":null,"identityCard":"111111111111111111","integral":null,"password":null,"regTime":null,"sex":null,"userId":17,"userImg":null,"userName":"leifeng","userPhone":"15088888888"},"meg":"电话号已存在可以登录","vcode":"验证码正确"}
     * meg : OK
     */

    private MapBean map;
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
        /**
         * msg : 用户名可用
         * list : [{"addressId":"1","alias":"111","birthday":"2016-12-12 00:00:00","byGeneralize":"1","email":"22","generalize":"12122156","homeAddress":"231","identityCard":"110","integral":1,"password":"321","regTime":null,"sex":1,"userId":16,"userImg":"22","userName":"user","userPhone":"555"},{"addressId":"1","alias":"123","birthday":"2033-05-05 00:00:00","byGeneralize":"12","email":"123","generalize":"2619","homeAddress":"123","identityCard":"140411199612122619","integral":12,"password":"123","regTime":"2018-06-06 00:00:00","sex":1,"userId":15,"userImg":"12","userName":"zhangsan","userPhone":"12011033"},{"addressId":null,"alias":"","birthday":null,"byGeneralize":null,"email":"","generalize":null,"homeAddress":"","identityCard":"","integral":null,"password":"admin","regTime":null,"sex":1,"userId":14,"userImg":"","userName":"admin","userPhone":"15835562159"},{"addressId":null,"alias":null,"birthday":null,"byGeneralize":null,"email":null,"generalize":null,"homeAddress":null,"identityCard":null,"integral":null,"password":"12350","regTime":null,"sex":null,"userId":7,"userImg":null,"userName":"wanghaiyun1","userPhone":"12345678910"},{"addressId":null,"alias":null,"birthday":null,"byGeneralize":null,"email":null,"generalize":null,"homeAddress":null,"identityCard":null,"integral":null,"password":"1235","regTime":null,"sex":null,"userId":13,"userImg":null,"userName":"why12","userPhone":"12345678910"}]
         * user : {"addressId":null,"alias":null,"birthday":null,"byGeneralize":null,"email":null,"generalize":"11111111","homeAddress":null,"identityCard":"111111111111111111","integral":null,"password":null,"regTime":null,"sex":null,"userId":17,"userImg":null,"userName":"leifeng","userPhone":"15088888888"}
         * meg : 电话号已存在可以登录
         * vcode : 验证码正确
         */

        @SerializedName("msg")
        private String msgX;
        private UserBean user;
        private String meg;
        private String vcode;
        private List<ListBean> list;

        public String getMsgX() {
            return msgX;
        }

        public void setMsgX(String msgX) {
            this.msgX = msgX;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public String getMeg() {
            return meg;
        }

        public void setMeg(String meg) {
            this.meg = meg;
        }

        public String getVcode() {
            return vcode;
        }

        public void setVcode(String vcode) {
            this.vcode = vcode;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class UserBean {
            /**
             * addressId : null
             * alias : null
             * birthday : null
             * byGeneralize : null
             * email : null
             * generalize : 11111111
             * homeAddress : null
             * identityCard : 111111111111111111
             * integral : null
             * password : null
             * regTime : null
             * sex : null
             * userId : 17
             * userImg : null
             * userName : leifeng
             * userPhone : 15088888888
             */

            private String addressId;
            private String alias;
            private String birthday;
            private String byGeneralize;
            private String email;
            private String generalize;
            private String homeAddress;
            private String identityCard;
            private String integral;
            private String password;
            private String regTime;
            private String sex;
            private String userId;
            private String userImg;
            private String userName;
            private String userPhone;

            public String getAddressId() {
                return addressId;
            }

            public void setAddressId(String addressId) {
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

            public String getIntegral() {
                return integral;
            }

            public void setIntegral(String integral) {
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

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
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

        public static class ListBean {
            /**
             * addressId : 1
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

            private String addressId;
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

            public String getAddressId() {
                return addressId;
            }

            public void setAddressId(String addressId) {
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
    }
}
