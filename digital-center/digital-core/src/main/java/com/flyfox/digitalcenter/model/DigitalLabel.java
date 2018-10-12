package com.flyfox.digitalcenter.model;

import java.io.Serializable;

public class DigitalLabel implements Serializable {

	private Long id;
	private String number;
	private Integer labelId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Integer getLabelId() {
		return labelId;
	}
	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}
	public DigitalLabel(String number, Integer labelId) {
		super();
		this.number = number;
		this.labelId = labelId;
	}
	public DigitalLabel() {
		super();
	}
	 
	
	
}
