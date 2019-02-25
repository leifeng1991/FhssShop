package com.fhss.shop.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;


/**
 * Dialog工具类
 *
 * @author zhangrq
 */
@SuppressWarnings("unused")
public class DialogUtil {

    public static void showRawDialog(Activity activity, String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(activity)
                .setTitle("提示")
                .setMessage(message)
                .setNegativeButton("取消", (dialog, which) -> {
                })
                .setPositiveButton("确定", (dialog, which) -> {
                    if (listener != null)
                        listener.onClick(dialog, which);
                })
                .show();
    }

    public static Dialog showRawNoTitleDialog(Activity activity, String message, DialogInterface.OnClickListener listener) {
        return new AlertDialog.Builder(activity)
                .setMessage(message)
                .setNegativeButton("取消", (dialog, which) -> {
                })
                .setPositiveButton("确定", (dialog, which) -> {
                    if (listener != null)
                        listener.onClick(dialog, which);
                })
                .show();
    }

    public static AlertDialog showRawDialogOneButton(Activity activity, String message, DialogInterface.OnClickListener listener) {
        return new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton("确定", (dialog, which) -> {
                    if (listener != null)
                        listener.onClick(dialog, which);
                })
                .show();
    }

    public static Dialog showRawDialogTwoButton(Activity activity, String message, String buttonOneStr, DialogInterface.OnClickListener oneButtonListener, String buttonTwoStr, DialogInterface.OnClickListener twoButtonListener) {
        return new AlertDialog.Builder(activity)
                .setTitle("提示")
                .setMessage(message)
                .setNegativeButton(buttonOneStr, oneButtonListener)
                .setPositiveButton(buttonTwoStr, twoButtonListener)
                .show();
    }

    public static void showRawDialogOneButtonHint(Activity activity, String message) {
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton("确定", (dialog, which) -> dialog.dismiss())
                .show();
    }

}
