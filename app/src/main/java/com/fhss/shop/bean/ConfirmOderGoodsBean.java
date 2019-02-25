package com.fhss.shop.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConfirmOderGoodsBean implements Serializable {
    private List<ShopCartGoodsListBean.MapBean.ListBean> list = new ArrayList<>();

    public List<ShopCartGoodsListBean.MapBean.ListBean> getList() {
        return list;
    }

    public void setList(List<ShopCartGoodsListBean.MapBean.ListBean> list) {
        this.list = list;
    }
}
