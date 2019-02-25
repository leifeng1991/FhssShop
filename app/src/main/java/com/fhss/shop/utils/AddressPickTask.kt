package com.fhss.shop.utils

import android.app.Activity
import android.app.ProgressDialog
import android.os.AsyncTask

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import java.lang.ref.WeakReference
import java.util.ArrayList

import cn.qqtheme.framework.entity.Province
import cn.qqtheme.framework.picker.AddressPicker
import cn.qqtheme.framework.util.ConvertUtils

class AddressPickTask(activity: Activity) : AsyncTask<String, Void, ArrayList<Province>>() {
    private val activityReference: WeakReference<Activity> = WeakReference(activity)// 2018/6/1 StaticFieldLeak
    private var dialog: ProgressDialog? = null
    private var callback: Callback? = null
    private var selectedProvince = ""
    private var selectedCity = ""
    private var selectedCounty = ""
    private var hideProvince = false
    private var hideCounty = false

    fun setHideProvince(hideProvince: Boolean) {
        this.hideProvince = hideProvince
    }

    fun setHideCounty(hideCounty: Boolean) {
        this.hideCounty = hideCounty
    }

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    override fun onPreExecute() {
        val activity = activityReference.get() ?: return
        dialog = ProgressDialog.show(activity, null, "正在初始化数据...", true, true)
    }

    override fun doInBackground(vararg params: String): ArrayList<Province> {
        if (params != null) {
            when (params.size) {
                1 -> selectedProvince = params[0]
                2 -> {
                    selectedProvince = params[0]
                    selectedCity = params[1]
                }
                3 -> {
                    selectedProvince = params[0]
                    selectedCity = params[1]
                    selectedCounty = params[2]
                }
                else -> {
                }
            }
        }
        val data = ArrayList<Province>()
        try {
            val activity = activityReference.get()
            if (activity != null) {
                val json = ConvertUtils.toString(activity.assets.open("city.json"))
                val list = Gson().fromJson<List<Province>>(json, object : TypeToken<ArrayList<Province>>() {

                }.type)
                data.addAll(list)
            }
        } catch (e: java.io.IOException) {
            e.printStackTrace()
        }

        return data
    }

    override fun onPostExecute(result: ArrayList<Province>) {
        if (dialog != null) {
            dialog!!.dismiss()
        }
        if (result.size > 0) {
            val activity = activityReference.get() ?: return
            val picker = AddressPicker(activity, result)
            picker.setHideProvince(hideProvince)
            picker.setHideCounty(hideCounty)
            if (hideCounty) {
                picker.setColumnWeight((1 / 3.0f).toDouble(), (2 / 3.0f).toDouble())//将屏幕分为3份，省级和地级的比例为1:2
            } else {
                picker.setColumnWeight((2 / 8.0f).toDouble(), (3 / 8.0f).toDouble(), (3 / 8.0f).toDouble())//省级、地级和县级的比例为2:3:3
            }
            picker.setSelectedItem(selectedProvince, selectedCity, selectedCounty)
            picker.setOnAddressPickListener(callback)
            picker.show()
        } else {
            callback!!.onAddressInitFailed()
        }
    }

    interface Callback : AddressPicker.OnAddressPickListener {

        fun onAddressInitFailed()

    }
}
