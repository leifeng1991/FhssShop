package com.fhss.shop.bean;

public class WeChatUserInfoBean {

    /**
     * code : 200
     * user : {"address":null,"addressId":null,"alias":null,"birthday":null,"byGeneralize":"12122916","comboId":null,"email":null,"end":null,"generalize":null,"homeAddress":null,"identityCard":"100000000000000000","integral":0,"openid":"ouluJ1N8-WJmuxdfPihEWMcTgXPo","password":null,"rankId":1,"regTime":"2018-12-05 00:00:00","sex":null,"start":null,"userId":120,"userImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ6HVAP67T8ic20ZAWbs6Ql47fImut8OAldhzRq5BMe4Rc91GwF41gUfLXrenL7vfUG9nAAu93Dibgw/132","userName":"惜梦","userPhone":"10000000000"}
     * meg : 该用户已注册
     */

    private int code;
    private User.MapBean.UserBean user;
    private String meg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public User.MapBean.UserBean getUser() {
        return user;
    }

    public void setUser(User.MapBean.UserBean user) {
        this.user = user;
    }

    public String getMeg() {
        return meg;
    }

    public void setMeg(String meg) {
        this.meg = meg;
    }

}
