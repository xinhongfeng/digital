package com.flyfox.digitalcenter.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.flyfox.digitalcenter.model.LabelInfo;

@Mapper
public interface LabelInfoDao {

	public void insert(LabelInfo labelInfo);
	public void update(LabelInfo labelInfo);
	public LabelInfo getByLabelId(Integer labelId);
	
	public LabelInfo getByNumber(@Param("number")String number);
}
