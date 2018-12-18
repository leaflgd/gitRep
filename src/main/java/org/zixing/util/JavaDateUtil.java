package org.zixing.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class JavaDateUtil {
	//n天后的日期
	public String LaterDay(String str,int n) throws Exception{
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date=null;
		try {
			date = sdf.parse(str);
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(date.getTime());
			c.add(Calendar.DATE, n);//天后的日期
			Date dates= new Date(c.getTimeInMillis());
			return sdf.format(dates);
		} catch (ParseException e) {
			System.out.println("时间处理失败"+e.getMessage());
			throw new Exception("时间处理失败");
		}
	}
	
}
