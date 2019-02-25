package com.fhss.shop.utils

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView

import com.fhss.shop.base.FhssApplication
import com.zrq.base.util.ToastUtils

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * 检查内容
 *
 * @author zhangrq
 */
object CheckUtils {
    /**
     * 正则表达式：验证手机号
     */
    val REGEX_MOBILE = "1\\d{10}"
    /**
     * 正则表达式：验证纯数字
     */
    val REGEX_NUM = "\\d+"
    /**
     * 正则表达式：验证身份证号
     */
    val REGEX_ID_CARD = "\\d{17}(\\d|x|X)"
    /**
     * 正则表达式：验证密码
     */
    val PASS_WORD = "[a-zA-Z0-9]{6,12}"

    private val context: Context
        get() = FhssApplication.getFhssApplication().applicationContext

    /**
     * 检查手机号
     */

    private fun checkPhotoNumber(photoNumber: String): Boolean {

        if (TextUtils.isEmpty(photoNumber)) {
            ToastUtils.showLong(context, "手机号不能为空")
            return false
        } else if (!Pattern.matches(REGEX_MOBILE, photoNumber)) {
            ToastUtils.showLong(context, "请输入正确手机号")
            return false
        }
        return true
    }

    /**
     * 检查注册密码
     */
    private fun checkPassWord(password: String): Boolean {

        if (TextUtils.isEmpty(password)) {
            ToastUtils.showLong(context, "密码不能为空")
            return false
        } else if (!Pattern.matches(PASS_WORD, password)) {
            ToastUtils.showLong(context, "密码为6-12位，数字和字母组合，不可含有符号")
            return false
        }
        return true
    }

    /**
     * 检查手机号和密码
     */
    fun checkPhotoNumberAndPassword(photoNumber: String, password: String): Boolean {
        if (!checkPhotoNumber(photoNumber)) {
            // 手机号有问题
            return false
        } else if (!checkPassWord(password)) {
            return false
        }
        return true
    }

    /**
     * 检查身份证号
     *
     * @param idCard 身份证号
     */
    private fun checkIdCard(idCard: String): Boolean {
        if (TextUtils.isEmpty(idCard)) {
            ToastUtils.showLong(context, "请输入正确身份证号")
            return false
        } else if (!Pattern.matches(REGEX_ID_CARD, idCard)) {
            ToastUtils.showLong(context, "请输入正确身份证号")
            return false
        }
        return true
    }

    /**
     * 检查验证码
     *
     * @param verCode 验证码
     */
    private fun checkInput(verCode: String): Boolean {
        // 检测密码
        if (TextUtils.isEmpty(verCode)) {
            ToastUtils.showLong(context, "验证码输入错误")
            return false
        }
        return true
    }

    /**
     * 检查手机号、验证码
     *
     * @param photoNumber 手机号
     * @param verCode     验证码
     */
    private fun checkInput(photoNumber: String, verCode: String): Boolean {
        // 检测密码
        if (!checkPhotoNumber(photoNumber)) {
            // 手机号有问题
            return false
        } else if (!checkInput(verCode)) {
            // 验证码有问题
            return false
        }
        return true
    }

    /**
     * 检查手机号、验证码、邀请码、身份证
     *
     * @param photoNumber      手机号
     * @param verificationCode 验证码
     * @param inviteCode       邀请码
     */
    fun checkInput(photoNumber: String, idCard: String, verificationCode: String, inviteCode: String): Boolean {
        if (!checkInput(photoNumber, verificationCode)) {
            // 手机号、验证码有问题
            return false
        } else if (!checkIdCard(idCard)) {
            return false
        } else if (TextUtils.isEmpty(inviteCode)) {
            ToastUtils.showShort(context, "请输入推荐码")
            return false
        }
        return true
    }

    /**
     * 检查身份证、验证码
     *
     * @param idNumber 身份证
     * @param verCode  验证码
     */
    fun checkInputs(idNumber: String, verCode: String): Boolean {
        // 检测密码
        if (!checkIdCard(idNumber)) {
            // 身份证号有问题
            return false
        } else if (!checkInput(verCode)) {
            // 验证码有问题
            return false
        }
        return true
    }

    /**
     * 设置值，并手机号设置高亮
     */
    fun setTextAndCheckPhoneHighLight(textView: TextView, rawContent: String, listener: OnPhoneClickListener) {
        if (TextUtils.isEmpty(rawContent)) {
            return
        }
        // 加载文章内容高亮多个关键字，只高亮一个关键字去掉循环
        val spannableString = SpannableString(rawContent)
        val p = Pattern.compile(REGEX_MOBILE)
        val m = p.matcher(spannableString)
        while (m.find()) {
            val start = m.start()
            val end = m.end()
            spannableString.setSpan(PhoneClickableSpan(rawContent.substring(start, end), listener), start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.highlightColor = Color.TRANSPARENT
    }

    class PhoneClickableSpan(private val phoneNum: String, private val listener: OnPhoneClickListener?) : ClickableSpan() {

        override fun onClick(widget: View) {
            listener?.onPhoneClick(phoneNum)
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.color = Color.parseColor("#4976ff")
            ds.isUnderlineText = true
        }
    }

    interface OnPhoneClickListener {
        fun onPhoneClick(phoneNum: String)
    }
}
