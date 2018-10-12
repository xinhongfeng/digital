package com.flyfox.digitalcenter.util;

/**
 *
 */

import com.alibaba.fastjson.JSONObject;
import com.flyfox.digitalcenter.util.DataFormatUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;

import java.io.OutputStream;

/**
 * @author  
 *
 */
@Component
public class ResponseUtil {

    private static Logger logger = LoggerFactory.getLogger(ResponseUtil.class);
    
	/**
	 * json response
	 * @param response
	 * @param json
	 */
	public static void printJson(HttpServletResponse response, String json) {
		printJson(response, json, "");
    }

	/**
	 * jsonp response
	 * @param response
	 * @param json
	 * @param callback
	 */
	public static void printJson(HttpServletResponse response, String json, String callback) {
		OutputStream out = null;
		if(StringUtils.isEmpty(json)){
			logger.warn("response json is empty");
			return ;
		}

		if(!"".equals(callback)){
			StringBuilder result = new StringBuilder(json.length() + 16);
			result.append(callback);
			result.append('(');
			result.append(json);
			result.append(')');
			json = result.toString();
		}

		try {
		    out = response.getOutputStream();
		    byte[] bytes = json.getBytes("utf-8");
		    response.setStatus(200);
		    response.setContentLength(bytes.length);
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json; charset=UTF-8");
			response.setHeader("Access-Control-Allow-Credentials", "true");
//			response.setHeader("Access-Control-Allow-Origin", "*");
			out.write(bytes);
			out.flush();
			
		} catch (Exception e) {
			logger.error("ResponseUtil--printJson--error ", e);
		} finally {
			try {
			if (out != null) {
				out.close();
			}
			} catch (Exception e) {
			}
		}
    }
 
	public static void responseCodeAndText(HttpServletResponse response, Integer code, String msg, Object text){
	    JSONObject obj = new JSONObject();
        obj.put("code", code);
        obj.put("msg", msg);
        if(!StringUtils.isEmpty(text)){
        	 obj.put("data", text);
        }
        printJson(response, DataFormatUtil.toJsonString(obj));
	}
	
	 
 
		/**
		 * json response
		 * @param response
		 */
		public static void printText(HttpServletResponse response, String text) {
			printText(response, text, "");
	    }
		/**
		 * jsonp response
		 * @param response
		 * @param callback
		 */
		public static void printText(HttpServletResponse response, String text, String callback) {
			OutputStream out = null;
			if(StringUtils.isEmpty(text)){
				logger.warn("response text is empty");
				return ;
			}

			if(!"".equals(callback)){
				StringBuilder result = new StringBuilder(text.length() + 16);
				result.append(callback);
				result.append('(');
				result.append(text);
				result.append(')');
				text = result.toString();
			}

			try {
			    out = response.getOutputStream();
			    byte[] bytes = text.getBytes("utf-8");
			    response.setStatus(200);
			    response.setContentLength(bytes.length);
				response.setCharacterEncoding("utf-8");
				response.setContentType("application/json; charset=UTF-8");
				response.setHeader("Access-Control-Allow-Credentials", "true");
//				response.setHeader("Access-Control-Allow-Origin", "*");
				out.write(bytes);
				out.flush();
				
			} catch (Exception e) {
				logger.error("ResponseUtil--printText--error ", e);
			} finally {
				try {
				if (out != null) {
					out.close();
				}
				} catch (Exception e) {
				}
			}
	    }
		
		/**
		 * jsonp response
		 * @param response
		 * @param callback
		 */
		public static void printText404(HttpServletResponse response) {
			OutputStream out = null;

			try {
			    out = response.getOutputStream();
//			    byte[] bytes = text.getBytes("utf-8");
			    response.setStatus(404);
//			    response.setContentLength(bytes.length);
//				response.setCharacterEncoding("utf-8");
//				response.setContentType("application/json; charset=UTF-8");
//				response.setHeader("Access-Control-Allow-Credentials", "true");
//				response.setHeader("Access-Control-Allow-Origin", "*");
//				out.write(bytes);
				out.flush();
				
			} catch (Exception e) {
				logger.error("ResponseUtil--printText--error ", e);
			} finally {
				try {
				if (out != null) {
					out.close();
				}
				} catch (Exception e) {
				}
			}
	    }
		
}
