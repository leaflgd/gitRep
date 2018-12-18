package org.zixing.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class StringUtil {
	
	@org.junit.Test
	public void test(){
		String txt = "c48,XY,c,c2,";
		System.out.println(Arrays.toString(StringPart(txt)));
	}
	
	public String [] StringPart(String str){
		String[] str1= str.split(",");
		
		//如果是4 ，证明是按核型查找
		if(str1.length==4){
			str1 = new String[]{str1[0]+","+str1[1],str1[2],str1[3]};
		}
	
		
		String[] str3=new String[str1.length];;
		for (int i =0 ;i<str1.length;i++) {
			String str2 = str1[i];
			if(str2.length()-1!=0){
				str3[i] = str2.substring(1, str2.length());
			}else{
				str3[i]="";
			}
			
		}
		System.out.println(Arrays.toString(str1));
		return str3;
	} 

	/**
	 * 把日期型数据转化成字符串
	 * yyyyMMddHHmmss
	 */

	public static String convertDates(Date date) {
		String temp = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		temp = sdf.format(date);
		return temp;
	}
}
