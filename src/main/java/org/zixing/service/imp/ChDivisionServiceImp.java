package org.zixing.service.imp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zixing.dao.ChAnalysisDao;
import org.zixing.dao.ChDivisionDao;
import org.zixing.dto.ChNames;
import org.zixing.dto.ChromLabel;
import org.zixing.dto.ChromOffsetQuery;
import org.zixing.dto.ChromQuery;
import org.zixing.dto.ChromSwopQuery;
import org.zixing.dto.InfLog;
import org.zixing.entity.Cancel;
import org.zixing.entity.GrayBaseMapStatus;
import org.zixing.service.ChDivisionService;
import org.zixing.util.Base64Toimage;
import org.zixing.util.GetAndPost;
import org.zixing.util.StringUtil;
import org.zixing.web.BootController;

@Service
public class ChDivisionServiceImp implements ChDivisionService{

	@Autowired
	private ChDivisionDao chDDao;
	
	@Autowired
	private ChAnalysisDao chAnalysisDao;
	
	private static final Logger log4j = Logger.getLogger(ChDivisionServiceImp.class);
	
	public Map<String, Object> chDivisionInit(String grayid) {
		ChromLabel chlabel = chDDao.chDivisionLabelQuery(grayid);
		List<ChromQuery> li1 = chDDao.chMultimerInit(grayid);
		List<GrayBaseMapStatus> li2 = chAnalysisDao.grayStatusList(Integer.parseInt(grayid));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("label", chlabel);
		map.put("ch", li1);
		map.put("style", li2);
		return map;
	}

	// 需分割的染色单体
	public String chQuery(String id) {
		//清空掉该染色体的画线步骤
		chDDao.DivisionStepDeletes(id);
		//将其处理步骤归零
		chDDao.chProcedureSumSet(0, id);
		//查询染色单体的原始图
		String str = chDDao.chRawQuery(id);
		return str;
	}

	// 坐标保存
	public String chCoordinateStore(JSONArray mess, int state, String chromid,
				String grayid) {
			// 编译坐标
		String str = "";
		for (Object obj : mess) {
			String objs = (String) obj;
			String[] ss = objs.substring(0, objs.length() - 1).split(",");
			for (String str1 : ss) {
				str += str1 + ",";
			}
		}
		
		if (str.length() > 0) {
			str = str.substring(0, str.length() - 1);
		}
		if(str.equals("")){
			return null;
		}
		// 查询并设置步骤总数
		int stepSum = 0;
		try {
			stepSum = chDDao.chProcedureSum(chromid);
		} catch (Exception e) {
			System.out.println("读取步骤总数异常");
		}
		chDDao.chProcedureSumSet(stepSum + 1, chromid);
		// 查询最后的坐标步骤数
		int stepNum = 0;
		try {
			stepNum = chDDao.chProcedureMaximum(chromid);
		} catch (Exception e) {
			System.out.println("读取步骤异常");
		}
		// System.out.println("第"+stepNum+1+"步骤");
		Cancel can = null;
		if (stepNum == 0) {
			can = new Cancel(chromid, str, 1, state);
		} else {
			can = new Cancel(chromid, str, stepNum + 1, state);
		}
		try {
			chDDao.chSplittingStep(can);
		} catch (Exception e) {
			System.out.println("步骤存入数据库出异常：" + e.getMessage());
		}

		// 交互算法
//		JSONObject json = new JSONObject();
//		json.accumulate("Chromosome_id", Integer.parseInt(chromid));
//		json.accumulate("Status", 0);
//		json.accumulate("GreyBaseMap_id", Integer.parseInt(grayid));
//		json.accumulate("Logs_Steps", "nulls");
//		json.accumulate("Login_Name", "nulls");
//		String strs = null;
//		try {
//			strs = new GetAndPost().start("post", json,
//						"http://192.168.100.118:35277/alg2/");
//		} catch (Exception e) {
//			System.out.println("算法交互异常" + e.getMessage());
//		}
//		System.out.println(strs);
//		String url = chDDao.chOperationQuery(chromid);
		// System.out.println(strs+",url:"+url);
		return "";
	}

	// 坐标撤销
	public int chCoordinateRepeal(String chromid) {
		//如果撤销的步骤属于一键交叉，一键粘连
				/*Object status = chDDao.chProcedureStatus(chromid);
				System.out.println("撤销的染色体status："+status);
				if(status !=null && (int)status>1){
					System.out.println("------------------ status !=null && (int)status>1");
					return chCoordinateRepealOneKey(chromid,(Integer)status);
				}*/
		
		// 查询并设置步骤总数
//		int stepSum = 0;
//		try {
//			stepSum = chDDao.chProcedureSum(chromid);
//		} catch (Exception e) {
//			System.out.println("读取步骤总数异常");
//		}
//		chDDao.chProcedureSumSet(stepSum + 1, chromid);
		// 查询最后的坐标步骤数
//		int stepNum = 0;
//		try {
//			stepNum = chDDao.chProcedureMaximum(chromid);
//		} catch (Exception e) {
//			System.out.println("读取步骤异常");
//		}
//		System.out.println("步骤总数：" + stepSum);
		// System.out.println("第"+stepNum+"步骤");
		// 规避步骤为空时
		int status=0;
		try {
			status=chDDao.DivisionStepDelete(chromid);
		} catch (Exception e) {
			e.printStackTrace();
			status=2;
		}
		return status;
	}

	// 分割保存
	public Map<String, List> chDivisionDecide(String chromid, String grayid,String LogName) {
		// 设置返回的Map集合
//		Map<String, List> map = new HashMap<String, List>();
//		//查询处理步骤
//		int num = chDDao.QueryStepsMark(grayid);
//			// 设置返回操作是否是异常状态0：不是 1：是
//			String Status = "0";
			// 给算法传的JSON
//			JSONObject json = new JSONObject();
//			json.accumulate("Chromosome_id", Integer.parseInt(chromid));
//			json.accumulate("Status", 1);
//			json.accumulate("GreyBaseMap_id", Integer.parseInt(grayid));
//			json.accumulate("Logs_Steps", "segment" + num);
//			json.accumulate("Login_Name", LogName);
//			String strs = null;
//			boolean bl = false;
//			try {
//				strs = new GetAndPost().start("post", json, "http://192.168.100.118:35277/alg2/");//156
////				strs = new GetAndPost().start("post", json, "http://192.168.80.75:35277/alg2/");//64
//				// 交互成功更改状态
//				bl = chDDao.chDivisionStatusAlter(chromid,null, 1);
//				// 步骤总数归0
//				chDDao.chProcedureSumSet(0, chromid);
//				//自增处理步骤
//				chDDao.SettingStepsMark(grayid, num+1);
//			} catch (Exception e) {
//				if (bl) {
//					System.out.println("染色体状态更改" + e.getMessage());
//				} else {
//					System.out.println("算法交互异常" + e.getMessage());
//				}
//				Status = "1";
//			} finally {
//				System.out.println(strs);
//				//删除步骤
//				chDDao.DivisionStepDeletes(chromid);
//				List lists = chDDao.chMultimerInit(grayid);
//				map.put(Status, lists);
//			}
			return null;
	}

	// 批量删除
	public Map<String, List> chromDeleteService(List chromid,String greyid ,String userName) {
		// 设置返回的Map集合
		Map<String, List> map = new HashMap<String, List>();
		// 设置返回操作是否是异常状态0：不是 1：是
		String Status = "0";
		try {
			log4j.info("批量删除开始");
			//删除隐藏
			chDDao.chromDelete(chromid,1);
			//处理需要删除的数据
			String ids = "";
			for(Object str: chromid){
				ids+=","+str+",";
			}
			//添加操作步骤
			int num = chDDao.QueryStepsMark(greyid);
			InfLog inlog = new InfLog();
			inlog.setChromCount(ids);
			inlog.setLogName(userName);
			inlog.setGreyId(Integer.parseInt(greyid));
			inlog.setLogSteps("BatchDelete"+num);
			inlog.setOperaWay(4);
			chDDao.processDeleteAdd(inlog);
			//步骤数自增
			chDDao.SettingStepsMark(greyid, num+1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			log4j.info("批量删除异常"+e);
			Status = "1";
		}
		log4j.info("批量删除结束");
		List list = chDDao.chMultimerInit(greyid);
		map.put(Status, list);
		return map;
	}

		//图像转换
		public List<String> greyImageConversion(String greyid, String status) {
			if(status.equals("yuanshi")){
				return chDDao.DivisionOriginalGrey(greyid);
			}else if(status.equals("chunli")){
				return chDDao.DivisionEnhanceGrey(greyid);
			}
			return null;
		}
		//处理步骤查询
		public List<InfLog> ProcessStepsQuery(String greyid) {
			List<InfLog> list= chDDao.processStepsQuery(greyid);
				return list;
		}
		
		//处理步骤点击事件
		@Override
		public Map<String, Object> processStepsClickQuery(String logid) {
			//查询处理步骤
			InfLog log = chDDao.stepsClickQuery(logid);
			//需要返回的数据
			Map<String, Object> map = new HashMap<String, Object>();
			System.out.println("isis"+log.getChromId());
			switch (log.getOperaWay()) {
			case 1:
				map.put("url", chDDao.chOutlineQuery(log.getChromId()+""));
				map.put("ids", chDDao.subgraphIdQuery("s"+log.getChromId()));
				break;
			case 2:
				map.put("url", chDDao.chOutlineQuery(log.getChromId()+""));
				List<String> list = new ArrayList<String>();
				list.add(log.getChromId()+"");
				list.add(chDDao.subgraphIdQuery("c"+log.getChromId()).get(0));
				map.put("ids", list);
				break;
			case 3:
				map.put("url", chDDao.chOutlineLogIdQuery(log.getLogId()+""));
				map.put("ids", log.getChromId());
				break;
			default:
				break;
			}
			return map;
		}


		//处理步骤删除
		@Transactional
		public Map<String,List> ProcessStepsDelete(String greyid, String logid) {
			//返回值集合
			Map<String, List> map = new HashMap<String, List>();
			boolean bl=false;
			//父类id
			String chromFatherId=null;
			//有其关联的步骤
			List<String> list3=new ArrayList<String>();
			//删除回滚的ID
			List<String> deleteId=new ArrayList<String>();
			//用来拼接fatherid 来删除相关子类
			String stape = null;
			int stepState =0;
			try {
				//查询删除的步骤种类
				stepState = chDDao.withdrawStepState(logid);
				//存储有子图的id
				List<String> list1 = new ArrayList<String>();
				if(stepState==4){
					//查询删除的父类id集合
					String[] ids =chDDao.chConnectFatherIdQuery(logid).split(",");
					for (String string : ids) {
						deleteId.add(string);
					}
				}else{
					if(stepState==3){
						//查询子类id
						list1.add(chDDao.chConnectSonIdQuery(logid));
					}else{
						switch (stepState) {
						case 1:
							stape="s";
							break;
						case 2:
							stape="c";
							break;
						default:
							break;
						}
						//查询删除的父类id 
						chromFatherId = chDDao.chDivisionDeleteIDQuery(logid);
						//根据父类id来查询其子类图是否有关联的子类图
						List<String> list2 = chDDao.subgraphIdQuery(stape+chromFatherId);
						list1.addAll(list2);
					}
				}
				//id处理查询其子类是否有操作
				if(!list1.isEmpty()){
					//查询子图带有连接的步骤
					for (String string : list1) {
						String countStep=chDDao.sonConnectStepQuery(string);
						if(countStep!=null){
							boolean repeat=true;
							for (String str2 : list3) {
								if(str2.equals(countStep)){
									repeat=false;
								}
							}
							if(repeat) list3.add(countStep);
						}
					}
					//查询子图带有复制分割的步骤
					List<String> list4 = chDDao.sonStepQuerys(list1);
					if(list4.size()>0){
						list3.addAll(list4);
					}
				}
					
				if(list3.isEmpty()&&stepState!=0){
					switch (stepState) {
					case 4:
						chDDao.chromDelete(deleteId,0);
						break;
					case 3:
						System.out.println("连接父图回滚");
						//相关父图状态回滚
						String[] fatherId =chDDao.chConnectFatherIdQuery(logid).split(",");
						List<String> fatherIds = new ArrayList<String>();
						for (String string : fatherId) {
							fatherIds.add(string);
						}
						System.out.println("隐藏显示"+fatherIds);
						if(!fatherIds.isEmpty()){
							chDDao.chDivisionStatusAlter(null,fatherIds, 0);
						}
						break;

					default:
						System.out.println("复制分割父图回滚");
						//删除关联的子图显示
						System.out.println("隐藏显示"+chromFatherId);
						//删除关联的子图显示
						if(chromFatherId!=null){
							chDDao.chDivisionStatusAlter(chromFatherId,null, 0);
						}
						chDDao.chDivisionDelete(stape+chromFatherId);
						break;
					}
//					System.out.println("删除步骤");
				bl = chDDao.processStepsDelete(logid);
//					System.out.println("获取步骤信息");
				List<InfLog> list = chDDao.processStepsQuery(greyid);
				if(bl){
					 map.put("1", list);
				}else{
					map.put("0", list);
				}
				//查询多体图
				List lists = chDDao.chMultimerInit(greyid);
				//获取灰底图
				List<String> li = chDDao.DivisionEnhanceGrey(greyid);
				
				map.put("chrom", lists);
				map.put("label", li);
//					查询是否是复制粘贴所做的撤回
				String  str= chDDao.chLogId(logid);
				if(!"".equals(str)){
					List<String> list41= new ArrayList<String>();
					list41.add(chromFatherId);
//						System.out.println("复制操作"+logid);
					map.put("id", list41);
				}
			}else{
				map.put("no", list3);
				for (String string : list3) {
					System.out.println(string);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("修改异常");
		}
		return map;
	}

	//复制粘贴
	@Transactional
	public ChromQuery chDivisionCopy(String greyid,String chromid, String userName) {
		try {
			int num = chDDao.QueryStepsMark(greyid);
			//复制粘贴
			ChromQuery cq = chDDao.chCopyQuery(chromid);
			cq.setChromFather("c"+chromid);
			chDDao.chCopy(cq);
			//设置步骤参数
			InfLog il = new InfLog();
			il.setChromId(Integer.parseInt(chromid));
			il.setGreyId(Integer.parseInt(greyid));
			il.setLogSteps("Copy"+num);
			il.setLogName(userName);
			il.setOperaWay(2);
			//存入步骤
			chDDao.processAdd(il);
			chDDao.SettingStepsMark(greyid, num+1);
			return cq;
		} catch (Exception e) {
			System.out.println("与数据库交互异常");
			e.printStackTrace();
			return null;
		}
	}


	//坐标一步撤销
	public boolean chCoordinateRepealAll(String chromid) {
		return chDDao.DivisionStepAllDelete(chromid);
	}
	//高亮轮廓图查询
		public List<String> chOutlineQuery(String chromid) {
			List<String> list  =new ArrayList<String>();
			String url=chDDao.chOutlineQuery(chromid);
			while (url==null) {
				chromid = chDDao.chFatherId(chromid).substring(1);
				url=chDDao.chOutlineQuery(chromid);
			}
			list.add(url);
			return list;
		}
		
		//连接功能
		public Map<String, Object> chromConnect(List<Integer> list, String grayid,String logName,String test1) {
			//查询处理步骤
			int num = chDDao.QueryStepsMark(grayid);
			String chromCount = ",";
			for (Integer i : list) {
				chromCount=chromCount+i+",";
			}
			System.out.println(chromCount);
			InfLog log = new  InfLog();
			log.setGreyId(Integer.parseInt(grayid));
			log.setLogName(logName);
			log.setLogSteps("Connect"+num);
			log.setChromCount(chromCount);
			log.setOperaWay(3);
			int ret = chDDao.processConnect(log);
//			System.out.println("^^^^^^^^^^^^^"+grayid+"**********"+log.getLogId());
//			System.out.println("***********"+ret+"^^^^^^^^^^"+log.getLogId());
			JSONObject json1 = new JSONObject();
			json1.accumulate("Chromosome_id", list);
			json1.accumulate("GreyBaseMap_id", Integer.parseInt(grayid));
			json1.accumulate("Logs_id",  log.getLogId());
			String strssss = null;
			try {
				strssss = new GetAndPost().start("post", json1,test1);//156
//				strssss = new GetAndPost().start("post", json1,"http://192.168.80.75:35279/alg2/");//64
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("算法提交异常");
			} 
			if(strssss!=null){
				System.out.println("成功");
				chDDao.SettingStepsMark(grayid, num+1);
				//连接成功隐藏父类图
				chDDao.chDivisionStatusAlter(null,list,1);

			}else{
				System.out.println("失败");
				chDDao.processStepsDelete(log.getLogId()+"");
			}
			return chDivisionInit(grayid);
		}
		
		// 一键交叉或一键粘连
		@Override
		public String oneKeyCross(JSONObject json,String testTwo) {
			String chromid = json.getString("Chromosome_id");
			// 查询并设置步骤总数
			int stepSum = 0;
			try {
				stepSum = chDDao.chProcedureSum(chromid);
			} catch (Exception e) {
				System.out.println("读取步骤总数异常");
			}
			chDDao.chProcedureSumSet(stepSum + 1, chromid);
			// 查询最后的坐标步骤数
			int stepNum = 0;
			try {
				stepNum = chDDao.chProcedureMaximum(chromid);
			} catch (Exception e) {
				System.out.println("读取步骤异常");
			}
			// System.out.println("第"+stepNum+1+"步骤");
			Cancel can = new Cancel(chromid, json.getString("canvas"), 
					stepNum==0 ? 1 : stepNum+1,
					json.getInt("Status"));;
			System.out.println("json.getInt(Status):" + json.getInt("Status"));
			
			try {
				chDDao.chSplittingStep(can);
			} catch (Exception e) {
				System.out.println("步骤存入数据库出异常：" + e.getMessage());
			}

			// 交互算法
			String strs = null;
			try {
				json.put("Status", 0);
				strs = new GetAndPost().start("post", json,testTwo);

			} catch (Exception e) {
				System.out.println("算法交互异常" + e.getMessage());
			}
			System.out.println("一键交叉算法RESULT:" + strs);
			String url = chDDao.chOperationQuery(json.getString("Chromosome_id"));
			System.out.println("一键交叉最新图片:" + url);
			return url;
		}

		// 一键交叉，粘连坐标撤回
		public String chCoordinateRepealOneKey(String chromid,int status) {
			chDDao.DivisionStepDeleteOneKey(chromid, status);
			return chDDao.chOperationQuery(chromid);
		}
		
		//分割存图
		@Transactional
		public boolean divisionImgStore(List<String> base,String chromid) {
			//根据id查询
			ChromSwopQuery chsq =chAnalysisDao.chromQuerys(chromid);
			List<ChromSwopQuery> list = new ArrayList<ChromSwopQuery>();
			//获取当前时间
			String strl = new StringUtil().convertDates(new Date());
			for (String str : base) {
				//获取随机数
				int intFlag = (int)(Math.random() * 100000000);
				ChromSwopQuery ch = new ChromSwopQuery();
				ch.setFatherId("s"+chsq.getChromId());	
				ch.setGreyid(chsq.getGreyid());
				ch.setChromStatus(chsq.isChromStatus());
				ch.setAdjustUrl("liujie/"+strl+intFlag+".bmp");
				ch.setEnhanceUrl("liujie/"+strl+intFlag+".bmp");
				ch.setPathUrl("liujie/"+strl+intFlag+".bmp");
				ch.setChromMark(chsq.isChromMark());
				ch.setChromNum(chsq.getChromNum());
				System.out.println("图片存储");
				new Base64Toimage().GenerateImageFTP(str, "liujie", strl+intFlag+".bmp");
				list.add(ch);
			}
			//开始存入数据库
			System.out.println("开始存入数据库");
			chAnalysisDao.chromInsert(list);
			return true;
		}

//		快捷删除处理步骤
		/**
		 * str 处理步骤的灰底图id
		 * */
		@Override
		public Map<String,List> chromStepsRollback(String str) {
			String id= chDDao.stepsQuery(str);
			System.out.println("查询的id为"+id);
			if(id!=null){
				return ProcessStepsDelete(str,id);
			}
			return null;
		}

		/**
		 * 迭代测试
		 * */
		//读取该染色体的画线画圈的坐标集
		@Override
		public Map<String, Object> chCoordinateQueryService(String chromid) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<Cancel> list= chDDao.chSplittingQuery(chromid);
			ChromOffsetQuery coq= chDDao.chOffsetQuery(chromid);
			map.put("procedures", list);
			map.put("offset", coq.getOffset());
			map.put("url", coq.getUrl());
			return map;
		}

		//存储染色单体图片
		@Transactional
		@Override
		public Map<String, Object> picPathStorageService(String greyid,
				int status, String logName, List<String> chromid, JSONArray web) {
			//获取时间差的参数
			long endTime=0;
			long startTime=0;
			//存储步骤容器新建
			InfLog log = new  InfLog();
			log.setGreyId(Integer.parseInt(greyid));
			log.setLogName(logName);
			//查询处理步骤
			int num = chDDao.QueryStepsMark(greyid);
			
			//处理步骤存储参数
			String chromCount = "";
			String chromFather = "";
			if(status==1){
				chromCount+=",";
				for (String id : chromid) {
					chromCount=chromCount+id+",";
				}
				log.setChromCount(chromCount);
			}else{
				chromFather = chromFather+"s"+chromid.get(0);
				log.setChromId(Integer.parseInt(chromid.get(0)));
			}
			int ret=0;
			//隐藏父类图
			chDDao.chDivisionStatusAlter(null,chromid, 1);
			//步骤添加
			startTime=System.currentTimeMillis();   //获取开始时间
			if(status==0){
				// 步骤总数归0
				chDDao.chProcedureSumSet(0, chromid.get(0));
				log.setLogSteps("Segment"+num);
				log.setOperaWay(1);
				System.out.println("分割步骤");
				chDDao.processAdd(log);
			}else if(status==1){
				log.setLogSteps("Connect"+num);
				log.setOperaWay(3);
				System.out.println("连接保存");
				ret = chDDao.processConnect(log);
			}
			endTime=System.currentTimeMillis(); //获取结束时间
			log4j.debug("步骤添加所用时间： "+(endTime-startTime)+"ms");
			
			System.out.println("步骤保存完毕");
			//自增步骤
			chDDao.SettingStepsMark(greyid, num+1);
			//排序查询用于自增
			int sortNum = chDDao.sortMAXNumQuery(greyid);
			
			boolean bl=false;
			List<ChromQuery > chroms = new  ArrayList<ChromQuery>();
			
			for(int i = 0; i<web.size() ; i++ ){ //但是对象中又嵌套着数组
				sortNum+=1;
				log4j.info("排序值"+sortNum);
				ChromQuery chrom = new ChromQuery();
				String t = web.getString(i); //遍历过程将web数组元素赋给String型变量
				System.out.println("对象数据"+t);
				JSONObject we = JSONObject.fromObject(t); //通过String又得到每个元素的对象
				chrom.setChromPath((String)we.get("picPath"));
				chrom.setChromUrl((String) we.get("adjustPicPath"));
				chrom.setChromEnhance((String) we.get("enhancePicPath"));  
				chrom.setArithOffset((String) we.get("Arith_Offset"));
				chrom.setArithOffsetPicAna((String) we.get("OffsetPicPath")); 
				chrom.setImageSize(we.getString("sinanaImageSize"));
				chrom.setGreyId(Integer.parseInt(greyid));
				chrom.setChromFather(chromFather);
				chrom.setChromState(true);
				chrom.setChromMark(0);
				chrom.setLogid(log.getLogId()+"");
				chrom.setChromNum(55+"");
				chrom.setOrderByNum(sortNum);
				if(status==0){
					chrom.setLogid(null);
				}else if(status==1){
					chrom.setChromFather(null);
				}
				chroms.add(chrom);
//				System.out.println(chrom); 
			}
			startTime=System.currentTimeMillis();   //获取开始时间
			System.out.println("染色体保存");
			bl = chDDao.picPathStorage(chroms);
			endTime=System.currentTimeMillis(); //获取结束时间
			log4j.debug("染色体保存所用时间： "+(endTime-startTime)+"ms");
			
			startTime=System.currentTimeMillis();   //获取开始时间
			Map<String,Object>map =chDivisionInit(greyid);
			map.put("new", chDDao.chSonIdQuery(chromFather, null));
			endTime=System.currentTimeMillis(); //获取结束时间
			log4j.debug("页面更新所用时间： "+(endTime-startTime)+"ms");
			return map;
		}

//		修改指定染色体的编号
		@Override
		public boolean chromUpateNum(List<Cancel> chroms) {
			return chDDao.chromUpateNum(chroms);
		}

		//根据灰底图查询未识别的染色体
		@Override
		public List<ChromQuery> chromDiscernQuery(String greyid) {
			return chDDao.chSonIdQuery(null, greyid);
		}
		//灰底图生成查询染色单体
		@Override
		public List<ChromQuery> greyCreateQuery(String greyid) {
			return chDDao.greyCreateQuery(greyid);
		}
		//报告所需灰底图写入
		@Override
		public boolean reportGreyStore(String greyId, String babyPic,String oriPic) {
			return chDDao.reportGreyStore(greyId,babyPic,oriPic);
		}

		//批量写入摆正图
		@Override
		public boolean writAdjustPaths(JSONArray json) {
			try {
				List<ChromSwopQuery> list = new ArrayList<ChromSwopQuery>();
				for (int i = 0; i < json.size(); i++) {
					JSONObject job = json.getJSONObject(i); 
					ChromSwopQuery chq = new ChromSwopQuery();
					chq.setChromId(Integer.parseInt(job.getString("id")));
					chq.setAdjustUrl(job.getString("url"));
					list.add(chq);
				}
				chDDao.writAdjustPaths(list);
			} catch (Exception e) {
				e.getStackTrace();
				return false;
			}
			
			return true;
		}

}
