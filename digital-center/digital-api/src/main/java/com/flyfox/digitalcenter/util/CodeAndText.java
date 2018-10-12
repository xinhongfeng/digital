package com.flyfox.digitalcenter.util;
/**
 * 
 * @author xinhongfeng
 * @date 2017-11-24
 */
public enum CodeAndText {
	
	SYSTEM_BUSY(-1,"系统忙"),
	FAIL(0,"失败"),
	SUCCESS(200,"success"),
	 
	LACKPARAM(100007,"缺少参数（100007）"),
    ERRORPARAM(100008,"参数错误（100008）");
	
	private String text;
	private Integer code;
	
	private CodeAndText(Integer code, String text) {
		this.code = code;
		this.text = text;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	
	
}
