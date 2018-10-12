package com.flyfox.digitalcenter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.flyfox.digitalcenter.dao.DigitalLabelDao;
import com.flyfox.digitalcenter.dao.LabelInfoDao;
import com.flyfox.digitalcenter.model.LabelInfo;
import com.flyfox.digitalcenter.service.DigitalLabelService;

@Component
public class DigitalLabelServiceImpl implements DigitalLabelService {

	@Autowired
	private DigitalLabelDao digitalLabelDao;
	@Autowired
	private LabelInfoDao labelInfoDao;
	@Override
	public LabelInfo getByNumber(String number) {
		return labelInfoDao.getByNumber(number);
	}
	
	@Override
	public List<String> getGeelyNumber(String[]  desc) {
		return digitalLabelDao.getGeelyNumber(desc);
	}

	
}
