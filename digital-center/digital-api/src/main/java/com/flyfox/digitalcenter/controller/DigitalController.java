package com.flyfox.digitalcenter.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.flyfox.digitalcenter.model.LabelInfo;
import com.flyfox.digitalcenter.service.DigitalLabelService;
import com.flyfox.digitalcenter.util.CodeAndText;
import com.flyfox.digitalcenter.util.ResponseUtil;
/**
 * 
 * @author xinhongfeng
 *
 */
@RestController
public class DigitalController {
	
	@Autowired
	DigitalLabelService digitalLabelService;
	
	@RequestMapping(value="shuzi/fenxi",method=RequestMethod.GET)
	public void digitalAnalysis(HttpServletRequest request, HttpServletResponse response,String number){
		
	 if(StringUtils.isEmpty(number)||number.length()==0||number.length()>12){
		 ResponseUtil.responseCodeAndText(response, CodeAndText.ERRORPARAM.getCode(), CodeAndText.ERRORPARAM.getText(), "数字长度不允许");
		 return;
	 }
     //拆分数字从右侧往前数4位一组，不够4位前面补0
	 Map<String,String> fenxi= dealNumber(number);
	 ResponseUtil.responseCodeAndText(response, CodeAndText.SUCCESS.getCode(),  CodeAndText.SUCCESS.getText(), fenxi);
	 return ;
	}
	
	private Map<String,String> dealNumber(String number){
		int lenth=0;
		int jizu=number.length()/4;
		int mol=number.length()%4;
		if(mol==0){
			//正好是4的倍数位
			lenth=jizu;
		}else{
			lenth=jizu+1;
			String pre="";
			for(int i=0;i<(4-mol);i++){
				pre=pre+"0";
			}
			number=pre+number;
		}
		Map<String,String> map=new HashMap<String,String>();
		for(int i=0;i<lenth;i++){
			String n=number.substring(i*4,(i+1)*4);
			LabelInfo info=digitalLabelService.getByNumber(n);
			if(info!=null)
			{
				map.put("第"+(i+1)+"部分："+n,info.getShortDescription()+" 解释为："+info.getDetailDescription());
			}else{
				map.put("第"+(i+1)+"部分："+n,"解释信息暂无");
			}
			
		}
		
		return map;
	}
	
	@RequestMapping(value="shuzi/chazhao",method=RequestMethod.GET)
	public void searchDigital(HttpServletRequest request, HttpServletResponse response,String yaoqiu){
		if(!StringUtils.isEmpty(yaoqiu)){
			String[] yaoqius=yaoqiu.split(",");
			List<String> list=digitalLabelService.getGeelyNumber(yaoqius);
			ResponseUtil.responseCodeAndText(response, CodeAndText.SUCCESS.getCode(),  CodeAndText.SUCCESS.getText(), list);
			 return ;
		}else{
			ResponseUtil.responseCodeAndText(response, CodeAndText.LACKPARAM.getCode(),  CodeAndText.LACKPARAM.getText(), "请提出你的要求！");
			 return ;
		}
	}
}
