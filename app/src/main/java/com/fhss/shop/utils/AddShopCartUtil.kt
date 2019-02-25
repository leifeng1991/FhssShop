package com.fhss.shop.utils

import com.fhss.shop.base.FhssApplication
import com.fhss.shop.bean.User
import com.zrq.base.base.BaseActivity
import com.zrq.base.model.BaseBean
import com.zrq.base.net.OnMyActivityRequestListener
import com.zrq.base.util.ToastUtils

import java.util.HashMap

object AddShopCartUtil {
    /**
     * @param goodsInfo 商品信息 必须一一对应 可以传空（必传）
     */
    fun addShopCart(activity: BaseActivity, vararg goodsInfo: String) {
        val loginUserDoLogin = FhssApplication.getFhssApplication().getLoginUserDoLogin(activity)
                ?: return
        val map = HashMap<String, String>()
        map["userId"] = loginUserDoLogin.map.user.userId
        map["goodsId"] = goodsInfo[0]
        map["goodsName"] = goodsInfo[1]
        map["goodsMarket"] = goodsInfo[2]
        map["goodsMembers"] = goodsInfo[3]
        map["goodsAgent"] = goodsInfo[4]
        map["goodsNumber"] = goodsInfo[5]
        map["goodsImg"] = goodsInfo[6]
        map["goodsIntegral"] = goodsInfo[7]
        map["goodsColor"] = goodsInfo[8]
        FhssApplication.getFhssApplication().getFhssApi().insertCart(map).enqueue(object : OnMyActivityRequestListener<BaseBean>(activity) {
            override fun onSuccess(bean: BaseBean) {
                ToastUtils.showShort(activity.applicationContext, "加入购物车成功")
            }
        })
    }
}
