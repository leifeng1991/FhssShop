package com.fhss.shop.account

/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/8 16:42
 */

class UserAccountManner {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            UserAccountManner()
        }
    }
}