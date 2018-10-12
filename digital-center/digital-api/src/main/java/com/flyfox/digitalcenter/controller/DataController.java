package com.flyfox.digitalcenter.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flyfox.digitalcenter.dao.DigitalLabelDao;
import com.flyfox.digitalcenter.dao.LabelInfoDao;
import com.flyfox.digitalcenter.model.DigitalLabel;
import com.flyfox.digitalcenter.model.LabelInfo;
import com.flyfox.digitalcenter.util.CodeAndText;
import com.flyfox.digitalcenter.util.FileUtil;
import com.flyfox.digitalcenter.util.ResponseUtil;
import com.flyfox.digitalcenter.vo.LabelVo;
/**
 * 
 * @author xinhongfeng
 *
 */
@RestController
public class DataController {
	
	@Autowired
	private LabelInfoDao  labelInfoDao;
	@Autowired
	private  DigitalLabelDao digitalLabelDao;
	
	@RequestMapping(value="miniGame/fresh",method=RequestMethod.GET)
	public void fresh(HttpServletRequest request, HttpServletResponse response, @RequestParam(name="filePath") String filePath){
		String  fileContent=FileUtil.getFileContentByJson(filePath);
		List<DigitalLabel> gameList=new ArrayList<DigitalLabel>();
		if(!StringUtils.isEmpty(fileContent)){
			JSONObject jsonObj = JSON.parseObject(fileContent.toString());
			Set<Entry<String,Object>> entrySet = jsonObj.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				JSONObject value = (JSONObject)entry.getValue();
//				String redisKey = keyFormat + key;
				LabelVo game=value.toJavaObject(LabelVo.class);
				LabelInfo label=new LabelInfo(game.getLabelId(),game.getShortDescription(),game.getDetailDescription());
				labelInfoDao.insert(label);
				if(!StringUtils.isEmpty(game.getNumbers())){
					String[] num=game.getNumbers().split(" ");
					for(int i=0;i<num.length;i++){
						DigitalLabel di=new DigitalLabel(num[i], game.getLabelId());
						digitalLabelDao.insert(di);
						gameList.add(di);
					}
				}
			} 
				
		}
		ResponseUtil.responseCodeAndText(response, CodeAndText.SUCCESS.getCode(), CodeAndText.SUCCESS.getText(), gameList.size() );
	    return ;
		
	}
}
