package org.zixing.service.imp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zixing.dao.CrayAnalysisDao;
import org.zixing.dao.CrayCountDao;
import org.zixing.dao.KaryotypeAnalysisDao;
import org.zixing.dto.GrayOper;
import org.zixing.dto.IncidentCheck;
import org.zixing.service.CrayCountService;
import org.zixing.util.GetEventKaryotype;
import org.zixing.util.his.call.BaseInfoEntity;
import org.zixing.util.his.call.WsZxBaseInfo;


@Service
public class CrayCountServiceImp implements CrayCountService{
	
	@Autowired
	private CrayCountDao countDao;
	@Autowired
	private CrayAnalysisDao analysisDao;
	@Autowired
	private KaryotypeAnalysisDao karyotypeAnalysisDao;	
	public List<GrayOper> grayQService(String inName ,String sum) {
		List<GrayOper> list = countDao.grayQuerys(inName,sum);
		return list;
	}
	
	//查询初始化数据
	public List<GrayOper> grayQServiceC(String inName ,String dialPieceNum ,String sum) {
				List<GrayOper> list = countDao.grayQuerysC(inName,dialPieceNum,sum);
				return list;
			}
	
	public List<GrayOper> grayQServiceC2(String inName) {
		List<GrayOper> list = countDao.grayQuerysC2(inName);
		return list;
	}
	
	//核型分析计数页面数据查询
			public Map<String,Object> CrayCountQuery(String inName ,List<GrayOper> list) {
				Map<String,Object> map= new HashMap<String, Object>();
				
				map.put("CountQuery", list);
				IncidentCheck userInfo = karyotypeAnalysisDao.queryEventInfo(inName);
				 try {

					 // 如果是生产环境，调用his获取患者信息
					 if (new File("/home/uftp/his_web_service/orm.xml").exists()) {
						 try {
							 String hisResult = new WsZxBaseInfo(inName).getHisResult();
							 BaseInfoEntity e = WsZxBaseInfo.parsEntity(hisResult);
							 userInfo.setCardSex(e.getXb()!=null&&e.getXb().equals("男")?0:1);
							 userInfo.setCardName(e.getXm());
						 } catch (Exception ex) {
							 ex.printStackTrace();
							 //log.info("调用his系统获取信息错误："+ex.getMessage());
						 }
					 }

			            if(userInfo.getCaryogram()==null || userInfo.getCaryogram()=="")
			                throw new NullPointerException("无生产报告，改从匹配规则中查询");
			        } catch (Exception e) {
			            
			            List<String> analysis = analysisDao.queryAllArithKaryotype(inName);
			            //设置匹配规则的核型
			            userInfo.setCaryogram(GetEventKaryotype.getEventKaryotype(analysis));
			        }
				
				map.put("Info",userInfo);
//				map.put("Info", countDao.infoQuery(inName));
				map.put("reportRecord", countDao.recordFind(inName));
				
				map.put("Status", countDao.statusFind(inName));
				//染色体条数统计
				List<GrayOper> countList = new ArrayList<GrayOper>();
				countList = countDao.grayQuerys(inName,"");
				
				int	sum45 = 0;
			    int	sum46 = 0;
			    int	sum47 = 0;
			    int sumRest =0;
			    int	countAll = 0;
			    int count45 = 0;
			    int count46 = 0;
			    int count47 = 0;
			    int countRest = 0;
				for (GrayOper grayOper : countList) {
					
					//统计勾选计数所有
					switch (grayOper.getGrayCount()) {
					case 45:
						sum45++;
						break;
					case 46:
						sum46++;
						break;
					case 47:
						sum47++;
						break;
					default:
						sumRest++;
						break;
					}
					
					//统计已核对染色体条数
					if(grayOper.getCountCheck()||grayOper.getAnalyCheck()){
						switch (grayOper.getGrayCount()) {
						case 45:
							countAll++;
							count45++;
							break;
						case 46:
							countAll++;
							count46++;
							break;
						case 47:
							countAll++;
							count47++;
							break;
						default:
							countAll++;
							countRest++;
							break;
						}
					}
				}
				
				List<Integer> count = new ArrayList<Integer>(); 

				count.add(sum45);
				count.add(sum46);
				count.add(sum47);
				count.add(sumRest);
				count.add(countAll);
				count.add(count45);
				count.add(count46);
				count.add(count47);
				count.add(countRest);
				//System.out.println("count:"+count);
				map.put("count", count);
				
				List<IncidentCheck> incidentChecks = countDao.incidentQuery(inName);
				if(incidentChecks.size()>0){
					incidentChecks.get(0).setCountStatusSum(sum45+sum46+sum47+sumRest);
				}

				map.put("count0", incidentChecks);
				
				return map;
			}
			
			public Map<String,Object> CrayCountQuery1(String inName ,List<GrayOper> list) {
				Map<String,Object> map= new HashMap<String, Object>();
				
				IncidentCheck userInfo = karyotypeAnalysisDao.queryEventInfo(inName);			
				
				map.put("Info",userInfo);
				map.put("count0", countDao.incidentQuery(inName));
				map.put("inName", inName);
			return map;
			}

}
