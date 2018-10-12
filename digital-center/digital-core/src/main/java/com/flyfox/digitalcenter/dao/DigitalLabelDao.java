package com.flyfox.digitalcenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.flyfox.digitalcenter.model.DigitalLabel;


@Mapper
public interface DigitalLabelDao {

	
	public List<String> getGeelyNumber(@Param("descs")String[] desc);
	
	public void insert(DigitalLabel label);
	

}
