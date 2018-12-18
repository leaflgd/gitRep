package org.zixing.util;

public class CardMapping {
	public String opinion(String caryogram,String DialPiece){
		String suggestion ="";
		if ( "46,XX".equals(caryogram)|| "46,XY".equals(caryogram)) {
			if (DialPiece.indexOf("h") != -1||DialPiece.indexOf("H") != -1) {
				suggestion = "高分辨率G显带未见异常";  
			}else{
				suggestion = "普通G显带未见异常";    
			}
		}
		if (caryogram.indexOf("der") != -1 ||caryogram.indexOf("rob") != -1) {
			suggestion = "为罗氏易位携带者,可考虑行FISH-PGD或PGD-CCS,建议来我院看遗传咨询专家门诊";  
		}
		if ("47,XXY".equals(caryogram)) {
			suggestion = "为克氏征,建议来我院看遗传咨询专家门诊";  
		}
		if (caryogram.indexOf("+21") != - 1) {
			suggestion = "为21三体综合征,建议来我院看遗传咨询专家门诊";  
		}
		
		if (caryogram.indexOf("mos") != -1) {
			suggestion = "为嵌合体,建议来我院看遗传咨询专家门诊";   
		}
		if (caryogram.indexOf("inv(9)") != - 1) {
			suggestion = "为9号染色体次缢痕区臂间倒位。在临床上有争议,按《人类染色体命名 的国际体制（ISCN2013版）》"; 
		}
		if (caryogram.indexOf("inv(Y)") != - 1) {
			suggestion = "为Y染色体倒位,在临床上有争议,按《人类染色体命名 的国际体制（ISCN2013版）》";       
		}
		if (caryogram.indexOf("47,XY") != -1 && caryogram.indexOf("mar") != -1) {
			suggestion = "为47,XY,+mar,建议来我院看遗传咨询专家门诊";       
		} 
		if (caryogram.indexOf("47,XX") != -1 && caryogram.indexOf("mar") != -1) {
			suggestion = "为47,XX,+mar,建议来我院看遗传咨询专家门诊";     
		}
		if (caryogram.indexOf("15p+") != -1) {
			suggestion = "为15p+,建议行FISH做进一步检测";             
		}
		if (caryogram.indexOf("mat") != -1) {
			suggestion = "为母源性平衡易位,建议来我院看遗传咨询专家门诊";     
		}
		if (caryogram.indexOf("47,XYY") != -1) {
			suggestion = "为47,XYY,建议来我院看遗传咨询专家门诊";       
		}
		if (caryogram.indexOf("DSD") != -1) {
			suggestion = "为DSD（性发育异常）,建议来我院看遗传咨询专家门诊";   
		}
		
		if(caryogram.indexOf("t(") != -1 ){
			 int index1=caryogram.indexOf("t(");
			 int index2 = caryogram.indexOf(")",index1);
			 String t ="";
			 if(index2!=-1){
				t= caryogram.substring(index1, index2);
			 }else{
				 t= caryogram.substring(index1);
			 }
			 if(caryogram.indexOf("t(",index1+2) ==-1 && t.indexOf(";",t.indexOf(";")+1) ==-1 && t.indexOf(";") !=-1){
				 suggestion = "为平衡易位携带者,可考虑行FISH-PGD或PGD-CCS,建议来我院看遗传咨询专家";   
			 }else{
				 suggestion = "为复杂易位携带者,建议行PGD-CCS,建议来我院看遗传咨询专家门诊";
			 }
			
	    }
		return suggestion;
	}
	public int report(String Pregnancy,String Pregnancys , String DialPiece,String suggestion,String inName){
		System.out.println("匹配事件"+Pregnancy+":"+suggestion+":"+DialPiece+":"+inName);
		if((Pregnancy!=null && !"".equals(Pregnancy)) || 
				(!"".equals(Pregnancys) && Pregnancys!=null)
				|| inName.indexOf("P") != - 1 || inName.indexOf("p") != - 1 ){
			return 4;
		}else if(DialPiece.indexOf("h") != -1||DialPiece.indexOf("H") != -1
				|| suggestion.indexOf("高分辨率G显带未见异常") != -1) {
			return 2;													
		} else if (suggestion.indexOf("为平衡易位携带者,可考虑行FISH-PGD或PGD-CCS,建议来我院看遗传咨询专家") != -1
				|| suggestion.indexOf("为嵌合体,建议来我院看遗传咨询专家门诊") != -1
				|| suggestion.indexOf("为47,XY,+mar,建议来我院看遗传咨询专家门诊") != -1
				|| suggestion.indexOf("为47,XX,+mar,建议来我院看遗传咨询专家门诊") != -1
				|| suggestion.indexOf("为母源性平衡易位,建议来我院看遗传咨询专家门诊") != -1
				|| suggestion.indexOf("为DSD（性发育异常）,建议来我院看遗传咨询专家门诊") != -1
				|| suggestion.indexOf("为复杂易位携带者,建议行PGD-CCS,建议来我院看遗传咨询专家门诊") != -1) {
			return 3;
		}else if(suggestion.indexOf("普通G显带未见异常") != -1
				|| suggestion.indexOf("为罗氏易位携带者,可考虑行FISH-PGD或PGD-CCS,建议来我院看遗传咨询专家门诊") != -1
				|| suggestion.indexOf("为克氏征,建议来我院看遗传咨询专家门诊") != -1
				|| suggestion.indexOf("为21三体综合征,建议来我院看遗传咨询专家门诊") != -1
				|| suggestion.indexOf("为9号染色体次缢痕区臂间倒位。在临床上有争议,按《人类染色体命名 的国际体制（ISCN2013版）》") != -1
				|| suggestion.indexOf("为Y染色体倒位,在临床上有争议,按《人类染色体命名 的国际体制（ISCN2013版）》") != -1
				|| suggestion.indexOf("为15p+,建议行FISH做进一步检测") != -1
				|| suggestion.indexOf("为47,XYY,建议来我院看遗传咨询专家门诊") != -1
				) {
			return 1;
		}
		return 0;
	}
}
