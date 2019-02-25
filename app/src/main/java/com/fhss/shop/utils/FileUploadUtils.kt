package com.fhss.shop.utils

import android.app.Activity

import com.fhss.shop.base.FhssApplication
import com.fhss.shop.bean.UploadBean
import com.fhss.shop.bean.User
import com.zrq.base.net.OnMyActivityRequestListener
import com.zrq.base.util.LogUtil

import java.io.File

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

object FileUploadUtils {
    fun upload(activity: Activity, path: String, listener: OnMyActivityRequestListener<UploadBean>) {
        val file = File(path)
        // 创建 RequestBody，用于封装构建RequestBody
        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        LogUtil.e("===============" + file.name)
        // uploadPic：和后台协商好的
        val part = MultipartBody.Part.createFormData("uploadPic", file.name, requestBody)
        val loginUserDoLogin = FhssApplication.getFhssApplication().getLoginUserDoLogin(activity)
        if (loginUserDoLogin != null) {
            FhssApplication.getFhssApplication().getFhssApi().upload(Integer.valueOf(loginUserDoLogin.map.user.userId), part).enqueue(listener)
        }
    }
}
