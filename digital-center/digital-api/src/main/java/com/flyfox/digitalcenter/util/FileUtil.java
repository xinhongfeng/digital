package com.flyfox.digitalcenter.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FileUtil {
	
 
	public static String getFileContentByJson(String filePath){
		File file = new File(filePath);
		StringBuilder localStrBuilder = new StringBuilder();
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			
			String line = null;
			while((line = br.readLine()) != null){
				localStrBuilder.append(line);
			}
			System.out.println(localStrBuilder.toString());
			br.close();
			isr.close();
			fis.close();
			return localStrBuilder.toString();
		} catch (Exception e) {
		}
		
		return null;
	}
}
