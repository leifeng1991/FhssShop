package com.fhss.shop.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

import java.math.BigDecimal
import java.text.DecimalFormat

/**
 * 手机显示屏相关的工具类
 *
 * @author zhangrq
 */
object PixelUtil {

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        return (pxValue / getDensity(context) + 0.5f).toInt()
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    fun dip2px(context: Context, dipValue: Float): Int {
        return (dipValue * getDensity(context) + 0.5f).toInt()
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    fun px2sp(context: Context, pxValue: Float): Int {
        return (pxValue / getScaledDensity(context) + 0.5f).toInt()
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    fun sp2px(context: Context, spValue: Float): Int {
        return (spValue * getScaledDensity(context) + 0.5f).toInt()
    }

    /**
     * 获取按比例缩放的屏幕密度
     */
    fun getScaledDensity(context: Context): Float {
        return context.resources.displayMetrics.scaledDensity
    }

    /**
     * 获取屏幕密度
     */
    fun getDensity(context: Context): Float {
        return context.resources.displayMetrics.density
    }

    /**
     * 获取屏幕宽度
     */
    @JvmStatic
    fun getScreenWidth(context: Context): Int {
        return getDisplayMetrics(context).widthPixels
    }

    /**
     * 获取屏幕高度
     */
    fun getScreenHeight(context: Context): Int {
        return getDisplayMetrics(context).heightPixels
    }

    /**
     * 获取显示信息
     */
    fun getDisplayMetrics(context: Context): DisplayMetrics {
        val wm = context.getSystemService(
                Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm
    }

    /**
     * 给金额显示添加千分位","
     *
     * @param val 金额
     */
    fun parseMoney(`val`: Any?): String {
        val pattern = "##,###,##0.00"
        if (`val` == null || `val` == "")
            return ""
        var valStr = `val`.toString() + ""
        val df = DecimalFormat(pattern)
        valStr = df.format(BigDecimal(valStr))
        return subZeroAndDot(valStr)
    }

    /**
     * 字符串数字的加法
     *
     * @param num1 数字1
     * @param num2 数字2
     */
    fun stringAdd(num1: String, num2: String): String {
        val vNum1 = BigDecimal(num1)
        val vNum2 = BigDecimal(num2)
        return vNum1.add(vNum2).toString()
    }

    /**
     * 字符串数字的减法
     *
     * @param num1 数字1
     * @param num2 数字2
     */
    fun stringSubstract(num1: String, num2: String): String {
        val vNum1 = BigDecimal(num1)
        val vNum2 = BigDecimal(num2)
        return vNum1.subtract(vNum2).toString()
    }

    /**
     * 字符串数字的乘法
     *
     * @param num1 数字1
     * @param num2 数字2
     */
    fun stringMultiple(num1: String, num2: String): String {
        val vNum1 = BigDecimal(num1)
        val vNum2 = BigDecimal(num2)
        return vNum1.multiply(vNum2).toString()
    }

    /**
     * 小数的 取小数点和零 如 12.30 -> 12.3 | 23.00 -> 23
     */
    fun subZeroAndDot(s: String): String {
        var s = s
        if (s.indexOf(".") > 0) {
            s = s.replace("0+?$".toRegex(), "")// 去掉多余的0
            s = s.replace("[.]$".toRegex(), "")// 如最后一位是.则去掉
        }
        return s
    }

    fun getWidthScale(context: Context): Double {
        return 1.0 * getScreenWidth(context) / 750
    }

    fun getWidthScale(context: Context, width: Int): Double {
        return 1.0 * width / getScreenWidth(context)
    }

    fun getHeightScale(context: Context): Double {
        return 1.0 * getScreenHeight(context) / 1334
    }
}
