package com.fhss.shop.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.fhss.shop.activity.MessageCenterActivity

class NotificationClickReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val newIntent = Intent(context, MessageCenterActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(newIntent)

    }
}