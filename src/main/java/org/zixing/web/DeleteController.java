package org.zixing.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.zixing.dao.CrayCountDao;
import org.zixing.dao.DeleteDao;
import org.zixing.dao.KaryotypeAnalysisDao;
import org.zixing.dto.GrayOper;
import org.zixing.dto.IncidentCheck;
import org.zixing.service.UserService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 一键删除功能
 */
@Controller
public class DeleteController {
	
	@Autowired
    private KaryotypeAnalysisDao kaAnalysisDao;
	@Autowired
	private CrayCountDao countDao;
    @Autowired
    private DeleteDao deleteDao;

    //一键删除
    @RequestMapping("one_key_delete")
    @ResponseBody
    public count delete(String jumpCondition,String id,String parameter,@RequestParam(value="ids",required=false)String[] ids,count c,HttpServletRequest request){
        int resultLine;
        System.out.println("一键删除参数 id："+id+"，parameter："+parameter+"，ids："+ids);
        if(parameter.equals("DELETE_CountGreyBaseMap")){

            List<GrayOper> list = deleteDao.queryCountCheck(ids);
            for(GrayOper e : list){
                if(e.getCountCheck() || e.getAnalyCheck()){

                    if(e.getGrayCount()==45){
                        c.setCount45(c.getCount45()-1);
                    }else  if(e.getGrayCount()==46){
                        c.setCount46(c.getCount46()-1);
                    }else  if(e.getGrayCount()==47){
                        c.setCount47(c.getCount47()-1);
                    }else{
                        c.setCountElse(c.getCountElse()-1);
                    }

                }
            }

            resultLine = deleteDao.deleteCountGreyBaseMap(ids);
        }else {
            resultLine = deleteDao.oneKeyDelete(id,parameter);
        }

		//根据跳转参数jumpCondition分别判断更新list到session:计数核对上下页跳转
        if(jumpCondition != ""&& jumpCondition!=null){
        	String inName = request.getSession().getAttribute("inName").toString();
        	List<GrayOper> list = new ArrayList<GrayOper>();
        	if(jumpCondition.equals("AnalyDelete")){
        		list = countDao.grayQuerysC2(inName);	
        	}else{
        		list = countDao.grayQuerysC(inName,"","");	
        	}
        	request.getSession().setAttribute("chromQueryList", list);
        }
		
        c.setUserInfo(kaAnalysisDao.queryEventInfo(request.getSession().getAttribute("inName").toString()));
        System.out.println("一键删除受影响行数:"+resultLine);
        return c;
    }

}
enum DeleteParams{
    DELETE_Chrom,DELETE_GreyBaseMap,DELETE_DialPiece,DELETE_Event,DELETE_CountGreyBaseMap
}

class count{
    private int count45;
    private int count46;
    private int count47;
    private int countElse;
    private IncidentCheck userInfo;
    
    

    public IncidentCheck getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(IncidentCheck userInfo) {
		this.userInfo = userInfo;
	}

	public int getCount45() {
        return count45;
    }

    public count setCount45(int count45) {
        this.count45 = count45;
        return this;
    }

    public int getCount46() {
        return count46;
    }

    public count setCount46(int count46) {
        this.count46 = count46;
        return this;
    }

    public int getCount47() {
        return count47;
    }

    public count setCount47(int count47) {
        this.count47 = count47;
        return this;
    }

    public int getCountElse() {
        return countElse;
    }

    public count setCountElse(int countElse) {
        this.countElse = countElse;
        return this;
    }
}
