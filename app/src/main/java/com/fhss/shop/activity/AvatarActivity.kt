package com.fhss.shop.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.CoordinatorLayout
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import com.fhss.shop.R
import com.fhss.shop.base.FhssBaseActivity
import com.fhss.shop.bean.UploadBean
import com.fhss.shop.utils.FileUploadUtils
import com.fhss.shop.utils.PixelUtil
import com.squareup.picasso.Picasso
import com.zrq.base.net.OnMyActivityRequestListener
import com.zrq.base.util.LogUtil
import com.zrq.base.util.PermissionHelper
import com.zrq.base.util.ToastUtils
import kotlinx.android.synthetic.main.activity_avatar.*
import kotlinx.android.synthetic.main.dialog_select_avatar.view.*
import me.nereo.multi_image_selector.MultiImageSelector
import me.nereo.multi_image_selector.MultiImageSelectorActivity
import java.io.File
import java.util.ArrayList

/**
 * 描述:头像
 *
 * @author leifeng
 *         2018/10/8 14:54
 */

class AvatarActivity : FhssBaseActivity() {
    private var dialog: BottomSheetDialog? = null
    private lateinit var dialogView: View
    private lateinit var mHelper: PermissionHelper
    private lateinit var imageList: ArrayList<String>
    private var imageUrl = ""

    companion object {
        const val REQUEST_IMAGE = 456
        const val IMAGE_URL = "imageUrl"
        fun newIntent(context: Context): Intent {
            return Intent(context, AvatarActivity::class.java)
        }

        fun getIntentImageUrl(intent: Intent): String {
            return intent.getStringExtra(IMAGE_URL)
        }
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_avatar)
    }

    override fun initView() {
        setTitleName("头像")
        setTitleLeftBack()
        setTitleRightImage(R.mipmap.ic_more)

        val width = PixelUtil.getScreenWidth(mContext)
        mUserButton.layoutParams.height = width
    }

    override fun setListener() {

    }

    override fun processLogic() {
        mUserButton.setOnClickListener { showBottomDialog() }
    }

    override fun onTitleRightClick(view: View?) {
        super.onTitleRightClick(view)
        showBottomDialog()
    }

    override fun onBackPressed() {
        if (TextUtils.isEmpty(imageUrl)) {
            super.onBackPressed()
        } else {
            FileUploadUtils.upload(this,imageUrl,object : OnMyActivityRequestListener<UploadBean>(this) {
                override fun onSuccess(bean: UploadBean?) {
                    ToastUtils.showShort(mContext,"上传成功")
                    val intent = Intent()
                    intent.putExtra(IMAGE_URL, bean?.imgurl)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }

                override fun onFailed(isServiceHintError: Boolean, code: Int, message: String?) {
                    super.onFailed(isServiceHintError, code, message)
                    ToastUtils.showShort(mContext,"上传失败")
                    finish()
                }

            })

        }
    }

    /**
     * 展示底部选中图片对话框
     */
    private fun showBottomDialog() {
        if (dialog == null) {
            dialogView = layoutInflater.inflate(R.layout.dialog_select_avatar, null)

            dialog = BottomSheetDialog(this)
            dialog!!.setContentView(dialogView)
            // 解决显示不全
            val parent = dialogView.parent as View
            val behavior = BottomSheetBehavior.from(parent)
            dialogView.measure(0, 0)
            behavior.peekHeight = dialogView.measuredHeight
            val params = parent.layoutParams as CoordinatorLayout.LayoutParams
            params.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            parent.layoutParams = params
            // 展示对话框
            dialog!!.show()
            // 取消选择
            dialogView.mCancelTextView.setOnClickListener {
                dialog!!.dismiss()
            }
            // 从相册选择
            dialogView.mPhotoAlbumTextView.setOnClickListener {
                dialog!!.dismiss()
                selectImage()
            }
        } else {
            // 正在展示时不再进行展示
            if (!dialog!!.isShowing) {
                dialog!!.show()
            }
        }

    }

    /**
     * 选择图片
     */
    private fun selectImage() {
        //        授权处理
        mHelper = PermissionHelper(this)
        mHelper.requestPermissions("请授予[读写][拍照]权限，否则无法拍照和读取本地图片", object : PermissionHelper.PermissionListener {
            override fun doAfterGrand(vararg permission: String) {
                // 请求权限成功
                MultiImageSelector.create()
                        .showCamera(true) // show camera or not. true by default
                        .single() // multi mode, default mode;
                        .start(this@AvatarActivity, REQUEST_IMAGE)
            }

            override fun doAfterDenied(vararg permission: String) {

            }
        }, "android.permission.READ_EXTERNAL_STORAGE", android.Manifest.permission.CAMERA)
    }

    //直接把参数交给mHelper就行了
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        mHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            // 选择的图片
            if (data != null) {
                imageList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT)
                if (imageList.size > 0) {
                    LogUtil.e("uri", imageList[0])
                    //                    PicassoUtils.setImageSmall(PersonInfoActivity.this,imageList.get(0),mUserIcon);
                    imageUrl = imageList[0]
                    Picasso.with(mContext).load(File(imageUrl)).into(mUserButton)
                }
            }

        }

    }
}