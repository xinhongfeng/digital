package com.flyfox.digitalcenter.model;

import java.io.Serializable;

public class LabelInfo implements Serializable {

	private Long id;
	private Integer labelId;
	private String shortDescription;//简短介绍   上上，上中 上下 中上 中中 中下 下上 下中 下下 9种签类
	private String detailDescription;//
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	 
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
	public LabelInfo(Integer labelId, String shortDescription,
			String detailDescription) {
		super();
		this.labelId = labelId;
		this.shortDescription = shortDescription;
		this.detailDescription = detailDescription;
	}
	
	public LabelInfo() {
		super();
	}
	
	
}
