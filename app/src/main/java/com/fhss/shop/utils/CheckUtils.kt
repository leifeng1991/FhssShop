package com.fhss.shop.utils

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.bean.User

/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/10 9:44
 */

fun View.clickCheckIsLogin(activity: Activity, listener: OnClickCheckIsLoginListener?) {
    this.setOnClickListener {
        val user = FhssApplication.getFhssApplication().getLoginUserDoLogin(activity)
        if (user != null)
            listener?.onClickCheckIsLogin(user)
    }
}

interface OnClickCheckIsLoginListener {
    fun onClickCheckIsLogin(user: User)
}

fun EditText.addTextChange(listener: OnTextChangedListener?) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            listener?.onTextChanged(s.toString())
        }
    })
}

interface OnTextChangedListener {
    fun onTextChanged(text: String)
}