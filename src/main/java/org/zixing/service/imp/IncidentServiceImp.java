package org.zixing.service.imp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.zixing.dao.DataSiftDao;
import org.zixing.dao.IncidentDao;
import org.zixing.dao.SubmitbDao;
import org.zixing.dto.GrayQueryDate;
import org.zixing.dto.SlideQuery;
import org.zixing.entity.Incident;
import org.zixing.entity.Task;
import org.zixing.entity.TaskGrey;
import org.zixing.service.IncidentService;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class IncidentServiceImp implements IncidentService{
	//储存事件提交次数
	private static Map<String,Integer> submitCount = null;
	private Object lockObj = new Object();

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private IncidentDao inDao;

	@Autowired
	private DataSiftDao dataSiftDao;

	public List<Incident> inGaninService(String inName) {

		List<Incident> in = null;
		if(submitCount == null){
			synchronized (lockObj){
				if(submitCount == null){
					//初始化时
					submitCount = new ConcurrentHashMap<>();
					in=inDao.inGanDaoAndSubmitCount(inName);
					for(Incident i : in){
						submitCount.put(i.getIncidentName(),i.getSubmitCount());
					}
				}
			}

		}else {
			in = inDao.inGanDao(inName);
			for(Incident i : in){
				
				if(submitCount.containsKey(i.getIncidentName())){
					i.setSubmitCount(submitCount.get(i.getIncidentName()));
				}
				
			}
		}
		return in;
	}
	//查询事件下玻片
	public Map<String,Object> sQueryService(String inName) {

		List<SlideQuery> in=inDao.sQueryDao(inName);
		Map<String, Object> map = new HashMap<String, Object>();
		List<GrayQueryDate> grey=inDao.inGrayQueryDao(inName);
		if(!in.isEmpty()){
			map.put(in.get(0).getInName(), in);
		}
		//System.out.println("查询事件下玻片:"+grey.size());

		if(!grey.isEmpty()){
			grey = GrayQueryDate.compareToThis(grey);
			map.put("grey", grey);
		}
		List<String> str=inDao.grayLock(inName,true);
		System.out.println("状态："+!str.isEmpty());
		map.put("status", !str.isEmpty());
		return map;
	}
	
	//查询拨片下灰底图
	public Map<String, Object> grayQeryservice(String slideid) {
		//查询灰底图集合
		List<GrayQueryDate> gray = inDao.grayQueryDao(slideid);

		Map<String, Object> map = new HashMap<String, Object>();
		//Collections.sort(gray);
		gray = GrayQueryDate.compareToThis(gray);
		System.out.println(gray);
		map.put("gray",gray);
		//锁定状态查询
		List<String> str=inDao.grayLock(slideid,false);
		System.out.println("状态："+!str.isEmpty());
		map.put("status", !str.isEmpty());
		return map;
	}

	@Autowired
	private ApplicationContext ctx;

	//提交  (0:提交成功   , 1:提交失败已经被提交过  ,  2:提交失败数据写入失败  , 3:算法提交异常)
	@Transactional
	public int grayStateService(JSONObject sumMap, final String logName, boolean status, final String inName) {
		//System.out.println("sumMap:"+sumMap);

		// 返回的数据
		int ret = 0;
			try {
				String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());

				//提交判断

				/*DataSourceTransactionManager txManager = (DataSourceTransactionManager) ctx.getBean("transactionManager");
				DefaultTransactionDefinition def = new DefaultTransactionDefinition();
				def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
				TransactionStatus txStatus = txManager.getTransaction(def);// 获得事务状态*/

				Iterator<String> iterator = sumMap.keySet().iterator();
				while (iterator.hasNext()){
					final String pid = iterator.next();
					final Task task = new Task(){{
						setDialpieceId(Integer.parseInt(pid));
						setStatus(0);
						setSubmitCheckstatus(0);
						setSubmitUsername(logName);
					}};
					dataSiftDao.insertTask(task);
					
					

					//task.setId(1);

					List<TaskGrey> taskGreyList = new ArrayList<>();
					JSONArray greyBaseMapArray = sumMap.getJSONArray(pid);
					List<String> typeList = new ArrayList<>();
					for(int i = 0 ;i <greyBaseMapArray.size();i++){
						String greyBaseMap = greyBaseMapArray.getString(i);
						String greyBaseMapId = greyBaseMap.split(",")[0];
						final String type = greyBaseMap.split(",")[1];
						typeList.add(type);
						TaskGrey taskGrey = new TaskGrey(){{
							setDialpieceId(task.getDialpieceId());
							setTaskId(task.getId());
							setStatus(0);
							setSubmitCheckstatus(0);
							setCount(0);
							setType(type);
							setSubmitEventname(inName);
						}};
						taskGrey.setGreybasemapId(Integer.parseInt(greyBaseMapId));
						taskGrey.setCreateDate(currentDate);
						taskGreyList.add(taskGrey);
					}
					//System.out.println("taskGreyList:"+taskGreyList);
					//System.out.println("typeList:"+typeList);

					dataSiftDao.insertTaskGrey(taskGreyList);
					dataSiftDao.updateGreyBaseMapCheck(taskGreyList);
				}
				//提交时该事件自增一
				if(submitCount.containsKey(inName)){
					submitCount.put(inName,submitCount.get(inName)+1);
				}else{
					submitCount.put(inName,1);
				}
				//txManager.commit(txStatus);//事务处理成功，提交当前事务
			} catch (Exception e) {
				e.printStackTrace();
				ret=2;
				throw new NullPointerException("数据精选提交错误");
			}
		return ret;
	}

	/*//提交  (0:提交成功   , 1:提交失败已经被提交过  ,  2:提交失败数据写入失败  , 3:算法提交异常)
	@Transactional
	public int grayStateService(List<Integer> count, List<Integer> analy,String slideid,String logName,boolean status,String inName) {
		boolean bl = false;
		boolean bl2 = false;
		// 传给算法的对象
		InSlideDate is = null;
		// 返回的数据
		int ret = 0;
		List<String> list = inDao.inSlideNameQuery(inName);
		// 查询该玻片是否被提交过了
		boolean a = false;
		*//*try {
			if(status){
				a=list.isEmpty();
			}else{
//				inDao.grayStateDelete(slideid);
				a=inDao.slideStateQuery(slideid).equals("1");
			}
		} catch (Exception e) {
			return 4;
		}*//*
		//if (a) {
		if (1==2) {
			ret = 1;
		} else {
			//判断提交种类，清空原来的选中状态
			*//*if(status){
				for(String string:list){
					inDao.grayStateDelete(string);
				}
			}
			else{
				inDao.grayStateDelete(slideid);
			}*//*
			try {
				*//*if (!count.isEmpty()) {
					bl = inDao.grayStateDao("count", count);
				}
				if (!analy.isEmpty()) {
					bl2 = inDao.grayStateDao("analy", analy);
				}*//*

				if (bl || bl2) {
//					is = inDao.inSlideQuery(slideid);
//					System.out.println("查询出来的值为" + is);
					//提交判断
					if(status){
						final int dId = Integer.parseInt(list.get(0));

						for (String str : list) {
							// 修改提交状态
							//inDao.slideStateAlter(str);
							//提交成功进行提交写入
							//inDao.submitbAdd(str,logName);
							foreachAdd(dId,count,analy,logName,inName);
							//状态修改
							//sDao.submitbStatusUpdate(str, 1);
						}
					}else{
						// 修改提交状态
						//inDao.slideStateAlter(slideid);
						//提交成功进行提交写入
						//inDao.submitbAdd(slideid,logName);
						foreachAdd(Integer.parseInt(slideid),count,analy,logName,inName);
						//状态修改
						//sDao.submitbStatusUpdate(slideid, 1);
					}
				}else{
					ret=3;
				}
			} catch (Exception e) {
				e.printStackTrace();
				ret=2;
				//throw new RuntimeException("更改数据库失败");
			}
//			inDao.submitDate(slideid, new Date());
		}
		return ret;
	}*/


	//更新单个染色体的勾选状态
	@Transactional
	@Override
	public boolean grayStatusUpdate(String greyid, boolean flag, int tick) {
		try {
			//查询玻片锁定状态
	     	boolean bl=inDao.DialPieceStatusQuery(greyid);
	     	System.out.println("状态值："+bl);
	     	if(!bl){
	     		inDao.grayStatusUpdate(greyid, flag, tick);
	     	}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
