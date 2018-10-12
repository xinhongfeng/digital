package com.flyfox.digitalcenter.service;


import java.util.List;

import com.flyfox.digitalcenter.model.LabelInfo;

public interface DigitalLabelService {

	public LabelInfo getByNumber(String number);
	
	public List<String> getGeelyNumber(String[]  desc);
}
