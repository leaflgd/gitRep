package org.zixing.util;

import org.zixing.dto.ChromSwopQuery;

public class ChromJudge {
	public ChromSwopQuery chromJudging(ChromSwopQuery csq,String str){
		switch(str){
			case "an" : 
				csq.setChromNum(25);
				csq.setChromStatus(false);
				break;
			case "chromAbnormal" :  
				csq.setChromStatus(true);
				break;
			default :  
				csq.setChromNum(Integer.parseInt(str));
				csq.setChromStatus(false);
				break;
		}
		return csq;
	}
}
