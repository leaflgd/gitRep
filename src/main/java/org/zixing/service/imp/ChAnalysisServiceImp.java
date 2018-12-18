package org.zixing.service.imp;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.ibatis.annotations.Case;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zixing.dao.ChAnalysisDao;
import org.zixing.dao.ChCountDao;
import org.zixing.dao.ChromatidDao;
import org.zixing.dto.ChromLabel;
import org.zixing.dto.ChromQuery;
import org.zixing.dto.ChromSwopQuery;
import org.zixing.service.ChAnalyService;
import org.zixing.util.Base64Toimage;
import org.zixing.util.ChromJudge;
import org.zixing.util.FtpUtils;
import org.zixing.util.GetAndPost;

@Service
public class ChAnalysisServiceImp implements ChAnalyService {
	
	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private ChAnalysisDao chAnDao;
	@Autowired
	private ChCountDao chCountDao;
	
	// 染色单体分析技术页面标签查询
	public Map<String, Object> chromLabelQuery(String grayid) {
		ChromLabel cl = chAnDao.chCountLabelQuery(grayid);
		List<ChromLabel> label = new ArrayList<ChromLabel>();
		label.add(cl);
		List<ChromQuery> list = chAnDao.chQuery(grayid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("label", label);
		map.put("chrom", list);
		log.info("list:" + list);
		//查询所属灰底图的核对状态
		boolean bl=chAnDao.chromAncheckQuery(grayid);
		log.info("状态"+bl);
		map.put("status", bl);
		return map;
	}

	// 染色单体移动
	@Transactional
	public Map<String, List> chromMove(String[] strs, String greyid) {
		for (int i = 0; i < strs.length; i++) {
			if (strs[i] != null) {
				char s = strs[i].charAt(0);
				if (s == 'm') {
					strs[i] = strs[i].substring(4);
				}
			}
		}
		Map<String, List> map = new HashMap<String, List>();
		if (strs[0] != null && strs[2] != null) {
			ChromSwopQuery csq1 = chAnDao.chAlterSortNumberQuery(strs[0]);
			ChromSwopQuery csq2 = chAnDao.chAlterSortNumberQuery(strs[2]);
			// 存储处理完毕的染色体
			List<ChromSwopQuery> list = new ArrayList<ChromSwopQuery>();
			//交换排序信息
			
			//染色体编号交替
			if(csq1.getChromNum()!=csq2.getChromNum()){
				chAnDao.chAlternate(strs[0],csq2.getChromNum()+"" );
				chAnDao.chAlternate(strs[2],csq1.getChromNum()+"" );
				int num = csq1.getOrderByNum();
				//位置交替
				csq1.setOrderByNum(csq2.getOrderByNum());
				csq2.setOrderByNum(num);
			}else{
				//位置交替
				if(csq1.getOrderByNum()==csq2.getOrderByNum()){
					int num=csq1.getOrderByNum();
					if(csq1.getChromId()>csq2.getOrderByNum()){
						csq1.setOrderByNum(num+1);
						csq2.setOrderByNum(num-1);
					}else{
						csq1.setOrderByNum(num-1);
						csq2.setOrderByNum(num+1);
					}
				}else{
					int num = csq1.getOrderByNum();
					csq1.setOrderByNum(csq2.getOrderByNum());
					csq2.setOrderByNum(num);
				}
			}
			list.add(csq1);
			list.add(csq2);
			chAnDao.chAlterSortNumber(list);
			
			System.out.println("交替完毕");
			System.out.println(csq1);
			System.out.println(csq2);
			System.out.println(strs);
		} else {
			if (strs[0] != null) {
				switch (strs[3]) {
				case "an":
					chAnDao.chAlternate(strs[0], "25");
					break;
				case "chromAbnormal":
					chAnDao.chAlternate(strs[0], "");
					break;
				default:
					chAnDao.chAlternate(strs[0], strs[3]);
					break;
				}
			}
			if (strs[2] != null) {
				switch (strs[1]) {
				case "an":
					chAnDao.chAlternate(strs[2], "25");
					break;
				case "chromAbnormal":
					chAnDao.chAlternate(strs[2], "");
					break;
				default:
					chAnDao.chAlternate(strs[2], strs[1]);
					break;
				}
			}
		}
		map.put(strs[3], chAnDao.chCopyQuery(greyid, strs[3]));
		if (strs[1] != strs[3]) {
			map.put(strs[1], chAnDao.chCopyQuery(greyid, strs[1]));
		}
		return map;
	}

	// 染色体核型更新
	public List<String> chKaryQuery(String str) {
		List<String> list = new ArrayList<String>();
		String str1 = chAnDao.KaryotypeQuery("common", str);
		int str2 = Integer.parseInt(chAnDao.KaryotypeQuery("x", str));
		int str3 = Integer.parseInt(chAnDao.KaryotypeQuery("y", str));
		if (str2 != 0 || str3 != 0) {
			str1 += ",";
			for (int x = 0; x < str2; x++) {
				str1 = str1 + "X";
			}
			for (int y = 0; y < str3; y++) {
				str1 = str1 + "Y";
			}
		}
		list.add(str1);
		return list;
	}

	// 染色体复制粘贴
	public String chromCopy(String chromId,String mark) {
		// 根据id查找需要复制的染色体所有信息
		// System.out.println(grayid+"----------"+chromId+"+++++++++++"+mark);
		ChromSwopQuery cq = chAnDao.chromQuerys(chromId);
		System.out.println(cq);
		if (mark.equals("an")) {
			cq.setChromNum(25);
		} else {
			int i = Integer.parseInt(mark);
			cq.setChromNum(i);
		}
		chAnDao.chromInsertQuery(cq);
		System.out.println("存储的返回值:"+cq.getChromId());
		return cq.getChromId()+"";
	}

	// 核型一览
	public Map<String, List> caryviewQuery(String chromNum, String inid,
			int greyid) {
		Map<String, List> map = new HashMap<String, List>();
		List list = null;
		log.info(chromNum + "*************" + inid + "##############"
				+ greyid);
		if (!chromNum.equals("0")) {
			if (chromNum.substring(4).equals("an")) {
				list = chAnDao.caryviewQuery("25", inid);
			} else {
				list = chAnDao.caryviewQuery(chromNum.substring(4), inid);
			}
		}
		String path = chAnDao.greyPictureQuery(greyid);
		map.put(path, list);
		return map;
	}

	// 分析页面确定按钮把截图保存到本地并将路径保存到数据库中
	public boolean chromConfirmOneSave(String path,String inName,int greyId,String base64,boolean status) {
		boolean bl =false;
		
		String name=null;
		try {
			int nameSum = (int) (Math.random()*10000000);
			if(status){
				log.info("性染色体隐藏存储");
				name="ChromosomeHideAtlas"+greyId+nameSum+".png";
			}else{
				log.info("非隐藏存储");
				name="ChromosomeAtlas"+greyId+nameSum+".png";
			}
		
			FtpUtils fu = new FtpUtils();
			byte[] b = new Base64Toimage().imgBase64(base64);
			//图片输出
			InputStream instream = new ByteArrayInputStream(b);
			log.info("目录"+path+inName);
//        	保存在ftp
			bl= new FtpUtils().uploadFile(path+inName, name, instream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//图片上传操作完毕
		if(bl){
			log.info("截图成功保存图片");
			//成功保存图片数据并保存核型
			chAnDao.chromConfirmOneSave("screenshotImage/"+inName+"/"+name, greyId,status);
		}
		return bl;
	}

	// 分割页面返回按钮把截图保存到本地并将路径保存到数据库中
	public boolean chromConfirmTwoSave(String greyId) {
//		log.info("返回调用算法生成图片");
//		// 算法交互
//		JSONObject json = new JSONObject();
//		json.accumulate("Status", (int)0);
//		json.accumulate("GreyBaseMap_id", Integer.parseInt(greyId));
//		String strs = null;
//		try {
//			strs = new GetAndPost().start("post", json,"http://192.168.100.118:35278/alg2/");//156
//			strs = new GetAndPost().start("post", json,"http://192.168.80.75:35278/alg2/");//64
//			log.info(strs);

//		} catch (Exception e) {
//			log.info("算法交互异常" + e.getMessage());
//		}
//		if (strs != null) {
//			return true;
//		}
		return false;
	}

	// 查询玻片下所有的染色体截图的路径
	public List<ChromQuery> chromPicQuery(String inName) {
		return chAnDao.chromPicQuery(inName);
	}
	
	// 分析点确定更新分析核对的状态
	public int chromAncheckupdate(int greyId, String karyotype,int karyotype1) {
		// 更新核型
		chAnDao.chromKaryotype(greyId, karyotype ,karyotype1);
		return chAnDao.chromAncheckupdate(greyId);
	}

	// 分析中镜像的样式保存
	@Override
	public int chScacssSave(String scacss, String id) {
		return chAnDao.chScacssSave(scacss, id);
	}

	// 分析中旋转180的样式保存
	@Override
	public int chRotcssSave(String rotcss, String id) {
		return chAnDao.chRotcssSave(rotcss, id);
	}

	// 分析中旋转X度的样式保存
	@Override
	public boolean chCircumXSave(String chromId, String base64,String path) {
		//数据处理
		log.info("数据处理");
		byte[] b = new Base64Toimage().imgBase64(base64);
		InputStream instream = new ByteArrayInputStream(b);
		//获取摆正图片地址
		String str = chAnDao.chAdjustQuery(chromId);
		String url = null;
		//拼接出图片地址
		if("AI001".equals(str.substring(0, str.indexOf("/")))){
			url = path+"Web001"+str.substring(str.indexOf("/"), str.lastIndexOf("/"));
		}else{
			url = path+str.substring(0, str.lastIndexOf("/"));
		}
		 
		String file = str.substring(str.lastIndexOf("/")+1,str.lastIndexOf("."));
		log.info("图片地址"+url);
		log.info("图片名称"+file);
		//存图
		log.info("存图"+file);
		new FtpUtils().uploadFile(url, file+"_2.bmp", instream);
		//设置摆正图片地址
		chAnDao.chAdjustUpdate(chromId,"Web001"+str.substring(str.indexOf("/"),str.lastIndexOf("/")+1)+file+"_2.bmp");
		return true;
	}


	// 查询分割页面中灰底图的样式
	@Override
	public String grayStyleQuery(int chromId) {
		return chAnDao.grayStyleQuery(chromId);
	}

	// 更新全局染色体的样式
	@Override
	public int chromExcisionStatusUpdate(String chromStyle, int chromId) {
		return chAnDao.chromExcisionStatusUpdate(chromStyle, chromId);
	}

	// 插入全局染色体样式
	@Override
	public int chromExcisionStatusInset(int chromId, int greyId,
			String chromStyle) {
		return chAnDao.chromExcisionStatusInset(chromId, greyId, chromStyle);
	}

	// 染色单体分割完成后一键识别
	public boolean chDiscern(String greyid,String path) {
		// 交互算法
		JSONObject json = new JSONObject();
		json.accumulate("GreyBaseMap_id", Integer.parseInt(greyid));
		String strs = null;
		try {
			strs = new GetAndPost().start("post", json, path);//156
			
		} catch (Exception e) {
			log.info("算法交互异常" + e.getMessage());
		}
		if (strs != null) {
			return true;
		}
		return false;
	}

	// 分析中旋转180的样式查询
	@Override
	public String chRotcssQuery(int chromId) {
		return chAnDao.chRotcssQuery(chromId);
	}

	// 分析中镜像的样式查询
	@Override
	public String chScacssQuery(int chromId) {
		return chAnDao.chScacssQuery(chromId);
	}

	// 胎儿隐藏
	@Override
	public boolean chFetalHidden(String greyId) {
		// 算法交互
		JSONObject json = new JSONObject();
		json.accumulate("Status", (int)1);
		json.accumulate("GreyBaseMap_id", Integer.parseInt(greyId));
		String strs = null;
		try {
//			strs = new GetAndPost().start("post", json,"http://192.168.100.118:35278/alg2/");//156
//			strs = new GetAndPost().start("post", json,"http://192.168.80.75:35278/alg2/");//64
		} catch (Exception e) {
			log.info("算法交互异常" + e.getMessage());
		}
		if (strs != null)
			return true;
		return false;
	}

	// 染色体保存上下移动
	@Override
	public boolean chAlterTopDown(String chromId, Integer topDown) {
		return chAnDao.chAlter(chromId, topDown);
	}

	// 染色单体移动指定位置带排序
	@Override
	@Transactional
	public Map<String, List> chromMove(String[] strs, String greyid, String move) {
		// 格式化参数
		for (int i = 0; i < strs.length; i++) {
			if (strs[i] != null) {
				char s = strs[i].charAt(0);
				if (s == 'm') {
					strs[i] = strs[i].substring(4);
				}
			}
		}

		// 目标位置
		String destination = strs[3];
		// 原始位置
		String originalPosition = strs[1];
		// 移动染色体id
		String chromosomeId = strs[0];
		// 查询移动目标位置原先数据
		List<ChromSwopQuery> destinationList = chAnDao.chCopyQuery(greyid,
				destination);
		// 如果是水平移动
		if (originalPosition.equals(destination)) {
			tabsurf(destinationList, Integer.parseInt(chromosomeId));
		}

		List<ChromSwopQuery> updateList = new LinkedList<ChromSwopQuery>();
		Map<Integer, Integer> sortMap = new TreeMap();

		log.info("-------" + move + "---------");
		if (move.equals("right")) {
			int i = destinationList.size();
			int ii = i - 1;

			for (ChromSwopQuery e : destinationList) {
				sortMap.put(ii, e.getChromId());
				ii--;
			}
			sortMap.put(i, Integer.parseInt(chromosomeId));

		} else if (move.equals("left")) {
			// 需要将列表倒序
			int ii = 1;
			log.info("倒序数据：");
			ListIterator<ChromSwopQuery> li = destinationList.listIterator();// 获得ListIterator对象
			for (li = destinationList.listIterator(); li.hasNext();) {// 将游标定位到列表结尾
				li.next();
			}
			for (; li.hasPrevious();) {// 逆序输出列表中的元素
				sortMap.put(ii, li.previous().getChromId());
				ii++;
			}

			sortMap.put(0, Integer.parseInt(chromosomeId));
		}

		Iterator<Integer> iterator = sortMap.keySet().iterator();
		for (Iterator line = iterator; line.hasNext();) {
			Integer k = (Integer) line.next();
			Integer v = sortMap.get(k);
			ChromSwopQuery entity = new ChromSwopQuery();
			entity.setChromId(v);
			entity.setOrderByNum(k);
			updateList.add(entity);
		}
		log.info("updateList :" + updateList);
		// 更改排序
		int result = chAnDao.chAlterSortNumber(updateList);
		log.info("排序修改行数:" + result);
		// 更改位置
				switch (destination) {
				case "an":
					chAnDao.chAlternate(chromosomeId, "25");
					break;
				case "chromAbnormal":
					chAnDao.chAlternate(chromosomeId, "");
					break;
				default:
					chAnDao.chAlternate(chromosomeId, strs[3]);
					break;
				}

		Map<String, List> resultMap = new HashMap<String, List>();

		resultMap.put(strs[3], chAnDao.chCopyQuery(greyid, strs[3]));
		if (strs[1] != strs[3]) {
			resultMap.put(strs[1], chAnDao.chCopyQuery(greyid, strs[1]));
		}
		return resultMap;
	}

	// 如果移动染色体是同一位号移动,踢出移动染色体ID
	private void tabsurf(List<ChromSwopQuery> destinationList, int key) {

		for (ChromSwopQuery e : destinationList) {
			if (e.getChromId() == key) {
				destinationList.remove(e);
				log.info("同级别移动删除数据：" + e);
				break;
			}
		}

	}
	
	//分析确定同步更新事件核型
	public void confirmUpdate(String aryogram,String event,String karyotype,int greyid,String inName) {
			chAnDao.confirmUpdate(aryogram,event);
			//获取第一个数值的位置
			int sum = 0; 
			int sum2 = 0; 
			boolean bl = false;
			for (sum = 0; sum < karyotype.length(); sum++) {
				int num =karyotype.charAt(sum) - '0';
				if(!bl && num>=0 && num<10){
					//获取初始数值下标
					sum2=sum;
					bl=true;
				}else if(bl && (num<0 || num>9)){
					break;
				}
			}
			chAnDao.chromKaryotype(greyid, karyotype, Integer.parseInt(karyotype.substring(sum2,sum)));
			int anStatuThis =chCountDao.anStatuThisQuery(greyid);
			if(anStatuThis == 1 && karyotype != null && karyotype != ""){
				chCountDao.updateCaryogram(karyotype,inName);
			}else{
				List<String> strlist = chCountDao.arithKaryotypeQuery(inName);
				String temp = "";
				 for (int i = 0; i < strlist.size() - 1; i++)
			        {
			            temp = strlist.get(i);
			            for (int j = i + 1; j < strlist.size(); j++)
			            {
			                if (temp.equals(strlist.get(j)))
			                {
			                	chCountDao.updateCaryogram(temp,inName);
			                }
			            }
			        }
			}
	}
}
