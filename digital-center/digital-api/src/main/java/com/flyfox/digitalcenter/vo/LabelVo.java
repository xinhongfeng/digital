package com.flyfox.digitalcenter.vo;

import java.io.Serializable;

public class LabelVo implements Serializable {

	private Integer labelId;
	private String shortDescription;//简短介绍   上上，上中 上下 中上 中中 中下 下上 下中 下下 9种签类
	private String detailDescription;//
	private String numbers;//以空格间隔的数字串
	
	public Integer getLabelId() {
		return labelId;
	}
	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getDetailDescription() {
		return detailDescription;
	}
	public void setDetailDescription(String detailDescription) {
		this.detailDescription = detailDescription;
	}
	public String getNumbers() {
		return numbers;
	}
	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}
	
}
