package com.zrq.base.model;

import java.io.Serializable;

/**
 *  基础的bean
 * 
 * @author zhangrq
 * 
 */
@SuppressWarnings("unused")
public class BaseBean implements Serializable {


	/**
	 * code : 400
	 * message : 未注册
	 * data : []
	 */

	private int code;
	private String msg;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return msg;
	}

	public void setMessage(String message) {
		this.msg = message;
	}

}
