package com.fhss.shop.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.fhss.shop.R
import com.fhss.shop.broadcast.NotificationClickReceiver

object NotificationManagerUtils {
    fun showManager(context: Context) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                ?: return
        val builder = Notification.Builder(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 8.0
            // 创建渠道
            val channelId = "1"
            val channel = NotificationChannel(channelId, "富和双盛", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
            // 设置渠道id
            builder.setChannelId(channelId)
        }


        val clickIntent = Intent(context, NotificationClickReceiver::class.java)
        val contentIntent = PendingIntent.getBroadcast(context, 123, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("你有一条新消息，请注意查收")
                .setContentText("下单成功")
                .setContentIntent(contentIntent)
        manager.notify(123, builder.build())
    }
}
