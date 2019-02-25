package com.fhss.shop

import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.support.v4.app.FragmentTabHost
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TabHost
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.base.FhssBaseActivity
import com.fhss.shop.event.RedPointsEvent
import com.fhss.shop.fragment.CategoryFragment
import com.fhss.shop.fragment.HomeFragment
import com.fhss.shop.fragment.MineFragment
import com.fhss.shop.fragment.ShopCartFragment
import com.zrq.base.model.BaseBean
import com.zrq.base.net.OnMyActivityRequestListener
import com.zrq.base.util.ToastUtils
import kotlinx.android.synthetic.main.layout_tab_item_view.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : FhssBaseActivity() {
    // 定义数组来存放Fragment界面
    private val fragmentArray = arrayOf<Class<*>>(HomeFragment::class.java, CategoryFragment::class.java, ShopCartFragment::class.java, MineFragment::class.java)
    // 定义数组来存放按钮图片
    private val mImageViewArray = intArrayOf(R.drawable.selector_main_tab_iv1, R.drawable.selector_main_tab_iv2, R.drawable.selector_main_tab_iv3, R.drawable.selector_main_tab_iv4)
    // 数组长度 N 就代表几击事件
    private var mHits = LongArray(2)
    // Tab选项卡的文字
    private val mTextViewArray = arrayOf("首页", "分类", "购物车", "我的")
    private var fragmentTabHost: FragmentTabHost? = null
    private lateinit var mMineView: View

    companion object {
        const val CURRENT_TAB = "currentTab"

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }

        // 中间按钮点击事件处理标记
        fun newIntent(context: Context, currentTab: Int): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(CURRENT_TAB, currentTab)
            return intent
        }

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val index = intent.getIntExtra(CURRENT_TAB, 0)
        if (fragmentTabHost != null) {
            fragmentTabHost!!.currentTab = index
        }
    }

    override fun onResume() {
        super.onResume()
        getNetData()
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_main)
    }

    override fun initView() {
        EventBus.getDefault().register(this)
        // 实例化TabHost对象，得到TabHost
        fragmentTabHost = findViewById(R.id.fth_tab)
        fragmentTabHost!!.setup(this, supportFragmentManager, R.id.fl_content)
        fragmentTabHost!!.tabWidget.dividerDrawable = null

        // 得到fragment的个数
        val count = fragmentArray.size
        for (i in 0 until count) {
            // 为每一个Tab按钮设置图标、文字和内容
            var tabSpec: TabHost.TabSpec
            if (i == 3) {
                mMineView = getTabItemView(i)
                tabSpec = fragmentTabHost!!.newTabSpec(mTextViewArray[i]).setIndicator(mMineView)
                // 设置Tab点击事件
                mMineView.id = i
                mMineView.setOnClickListener(this)// 此事件会消费，原生的点击事件，所以得自己处理
            } else {
                val tabItemView = getTabItemView(i)
                tabSpec = fragmentTabHost!!.newTabSpec(mTextViewArray[i]).setIndicator(tabItemView)

                // 设置Tab点击事件
                tabItemView.id = i
                tabItemView.setOnClickListener(this)// 此事件会消费，原生的点击事件，所以得自己处理
            }
            // 将Tab按钮添加进Tab选项卡中
            fragmentTabHost!!.addTab(tabSpec, fragmentArray[i], null)

        }
    }

    override fun setListener() {
    }

    override fun processLogic() {
        val index = intent.getIntExtra(CURRENT_TAB, 0)
        if (fragmentTabHost != null) {
            fragmentTabHost!!.currentTab = index
        }
    }

    override fun onBackPressed() {
        exitApp()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onClick(view: View) {
        val id = view.id
        if (id == 0 || id == 1 || id == 3) {
            fragmentTabHost?.currentTab = id
        } else if (id == 2) {
            val user = FhssApplication.getFhssApplication().getLoginUserDoLogin(this)
            if (user != null)
                fragmentTabHost?.currentTab = id
        }
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private fun getTabItemView(index: Int): View {
        val view = View.inflate(mContext, R.layout.layout_tab_item_view, null)
        // 设置图片
        view.iv_tab_icon.setImageResource(mImageViewArray[index])
        // 设置文字
        view.tv_tab_title.text = mTextViewArray[index]
        return view
    }

    // 判断是否退出app
    private fun exitApp() {
        // 数组向左移位操作
        System.arraycopy(mHits, 1, mHits, 0, mHits.size - 1)
        mHits[mHits.size - 1] = SystemClock.uptimeMillis()
        if (mHits[0] >= SystemClock.uptimeMillis() - 2000) {// 1000代表多击事件的限定时间
            // 处理多击事件的代码
            this.finish()
        } else {
            ToastUtils.showShort(mContext, "再按一次退出")
        }
    }

    fun setCurrentItem(position: Int) {
        if (position == 2) {
            val user = FhssApplication.getFhssApplication().getLoginUserDoLogin(this)
            if (user != null)
                fragmentTabHost?.currentTab = position
        } else {

            fragmentTabHost?.currentTab = position
        }
    }

    private fun getNetData() {
        val loginUser = FhssApplication.getFhssApplication().getLoginUser()
        if (loginUser != null) {
            FhssApplication.getFhssApplication().getFhssApi().selectUserInfo(loginUser.map?.user?.userId
                    ?: "").enqueue(object : OnMyActivityRequestListener<BaseBean>(this) {
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
        // 检查消息
        mMineView?.apply {
            mReadPointsButton.visibility = if (event.isShowRedPoints) VISIBLE else GONE
        }
    }
}
