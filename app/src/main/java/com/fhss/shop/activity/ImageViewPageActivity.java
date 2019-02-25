package com.fhss.shop.activity;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;


import com.fhss.shop.R;
import com.fhss.shop.fragment.ImageFragment;
import com.fhss.shop.utils.SystemBarUtils;
import com.zrq.base.base.BaseActivity;
import com.zrq.base.base.BaseFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 描述:图片预览详情
 *
 * @author zhangrq
 *         2017/3/21 14:48
 */
public class ImageViewPageActivity extends BaseActivity {
    public static final String CURRENT_INDEX = "currentIndex";
    private ViewPager viewPager;
    public static final String LIST = "list";
    private TextView mNumberTextView;
    private ArrayList<String> urlList;
    private List<Fragment> fragments = new ArrayList<>();


    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_image_view_page);
        SystemBarUtils.setStatusBarTranslucent(this);
        SystemBarUtils.setPaddingTopStatusBarHeight(this, getView(R.id.rl_title));
    }

    @Override
    protected void initView() {
        viewPager = getView(R.id.viewPager);
        mNumberTextView = getView(R.id.id_number);
    }

    @Override
    public void setListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mNumberTextView.setText(String.format("%s/%s", String.valueOf((position + 1)), String.valueOf(fragments.size())));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void processLogic() {
        setTitleLeftBack();
        // 获取值
        urlList = getIntent().getStringArrayListExtra(LIST);
        int currentIndex = getIntent().getIntExtra(CURRENT_INDEX, 0);
        // 设置值
        for (String url : urlList)
            fragments.add(ImageFragment.newInstance(url));
        viewPager.setOffscreenPageLimit(urlList.size());//预加载
        viewPager.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments));
        // 设置当前选中的位置
        if (currentIndex < urlList.size())
            viewPager.setCurrentItem(currentIndex);
        // 设置底部提示
        mNumberTextView.setText(String.format("%s/%s", String.valueOf((viewPager.getCurrentItem() + 1)), String.valueOf(urlList.size())));
    }

    public static Intent newIntent(Context context, ArrayList<String> list, int currentIndex) {
        Intent intent = new Intent(context, ImageViewPageActivity.class);
        intent.putStringArrayListExtra(LIST, list);
        intent.putExtra(CURRENT_INDEX, currentIndex);
        return intent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (urlList != null) {
            urlList.clear();
            urlList = null;
        }
        if (fragments != null) {
            fragments.clear();
            fragments = null;
        }
    }
}
