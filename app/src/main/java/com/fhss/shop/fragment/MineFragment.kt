package com.fhss.shop.fragment

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.fhss.shop.R
import com.fhss.shop.activity.*
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.bean.User
import com.fhss.shop.event.RedPointsEvent
import com.fhss.shop.utils.ImageLoadUtils
import com.fhss.shop.utils.OnClickCheckIsLoginListener
import com.fhss.shop.utils.clickCheckIsLogin
import com.zrq.base.base.BaseActivity
import com.zrq.base.base.ReuseViewFragment
import com.zrq.base.model.BaseBean
import com.zrq.base.net.OnMyActivityRequestListener
import kotlinx.android.synthetic.main.fragment_mine.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 描述:我的
 *
 * @author leifeng
 *         2018/10/9 9:17
 */

class MineFragment : ReuseViewFragment() {

    override fun loadViewLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        getNetData()
        val loginUser = FhssApplication.getFhssApplication().getLoginUser()
        rootView.apply {
            // 用户头像地址
            val url = loginUser?.map?.user?.userImg ?: ""
            if (loginUser == null || TextUtils.isEmpty(url)) {
                // 没有登录
                mUserButton.setImageResource(R.mipmap.ic_defalut_avatar)
            } else {
                // 已经登录
                ImageLoadUtils.setImageBig(mContext, url, mUserButton)
            }

            mUserNameTextView.text = loginUser?.map?.user?.userName ?: "登录"
            val generalize = loginUser?.map?.user?.generalize ?: ""

            mPromoCodeTextView.text = if (TextUtils.isEmpty(generalize)) "" else "推广码:$generalize"
        }
    }

    override fun initView() {
        setTitleName("我的")
    }

    override fun setListener() {
        EventBus.getDefault().register(this)
        rootView.apply {
            // 个人信息点击事件
            mUserInfoRL.clickCheckIsLogin(mActivity, object : OnClickCheckIsLoginListener {
                override fun onClickCheckIsLogin(user: User) {
                    // 跳转到个人信息界面
                    startActivity(PersonInfoActivity.newIntent(mContext))
                }
            })
            // 我的积分点击事件
            mMyIntegralTextView.clickCheckIsLogin(mActivity, object : OnClickCheckIsLoginListener {
                override fun onClickCheckIsLogin(user: User) {
                    // 跳转到积分界面
                    startActivity(IntegralActivity.newIntent(mContext))
                }
            })
            // 我的收藏点击事件
            mMyCollectTextView.clickCheckIsLogin(mActivity, object : OnClickCheckIsLoginListener {
                override fun onClickCheckIsLogin(user: User) {
                    // 跳转到收藏中心
                    startActivity(CollectCenterActivity.newIntent(mContext))
                }

            })
            // 消息中心
            mMessageCenterTextView.clickCheckIsLogin(mActivity, object : OnClickCheckIsLoginListener {
                override fun onClickCheckIsLogin(user: User) {
                    // 跳转到消息中心
                    startActivity(MessageCenterActivity.newIntent(mContext))
                }

            })
            // 我的订单
            mPurchaseHistoryTextView.clickCheckIsLogin(mActivity, object : OnClickCheckIsLoginListener {
                override fun onClickCheckIsLogin(user: User) {
                    // 跳转到订单列表
                    startActivity(MyOrderActivity.newIntent(mContext))
                }

            })
            // 收获地址
            mDeliveryAddressTextView.clickCheckIsLogin(mActivity, object : OnClickCheckIsLoginListener {
                override fun onClickCheckIsLogin(user: User) {
                    // 跳转到地址列表
                    startActivity(AddressActivity.newIntent(mContext))
                }

            })
            // 银行卡
            mBankCardTextView.clickCheckIsLogin(mActivity, object : OnClickCheckIsLoginListener {
                override fun onClickCheckIsLogin(user: User) {
                    // 跳转到银行卡列表
                    startActivity(BankActivity.newIntent(mContext))
                }

            })
            // 设置点击事件
            mSettingTextView.clickCheckIsLogin(mActivity, object : OnClickCheckIsLoginListener {
                override fun onClickCheckIsLogin(user: User) {
                    // 跳转到设置界面
                    startActivity(SettingActivity.newIntent(mContext))
                }

            })
        }

    }

    override fun processLogic() {

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    private fun getNetData(){
        val loginUser = FhssApplication.getFhssApplication().getLoginUser()
        if (loginUser != null){
            FhssApplication.getFhssApplication().getFhssApi().selectUserInfo(loginUser.map.user.userId).enqueue(object : OnMyActivityRequestListener<BaseBean>(activity as BaseActivity?){
                override fun onSuccess(bean: BaseBean?) {
                    EventBus.getDefault().post(RedPointsEvent(true))
                }

                override fun onFailed(isServiceHintError: Boolean, code: Int, message: String?) {
                    EventBus.getDefault().post(RedPointsEvent(false))
                }

            })
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun setRedPointsVisible(event: RedPointsEvent) {
        rootView.mReadPointsButton.visibility = if (event.isShowRedPoints) VISIBLE else GONE
    }


}