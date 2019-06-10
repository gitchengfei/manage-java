package com.cheng.manage.common.exception;

import com.cheng.manage.common.result.ResultEnum;

/**
 * 自定义异常类
 * @author cheng fei
 */
public class MyException extends RuntimeException{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private ResultEnum resultEnum;
	private Integer code;
	private Integer status;
	private String msg;

	public MyException() {

	}
	public MyException(ResultEnum resultEnum) {
		this.resultEnum = resultEnum;
		this.code = resultEnum.getCode();
		this.status = resultEnum.getStatus();
		this.msg = resultEnum.getMsg();
	}

	public ResultEnum getResultEnum() {
		return resultEnum;
	}

	public void setResultEnum(ResultEnum resultEnum) {
		this.resultEnum = resultEnum;
	}

	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
