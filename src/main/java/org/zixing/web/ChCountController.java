package org.zixing.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zixing.dao.ChAnalysisDao;
import org.zixing.dao.ChCountDao;
import org.zixing.dao.CrayAnalysisDao;
import org.zixing.dao.UserDao;
import org.zixing.dto.ChromLabel;
import org.zixing.dto.ChromQuery;
import org.zixing.dto.FolderDate;
import org.zixing.dto.GrayOper;
import org.zixing.service.ChAnalyService;
import org.zixing.service.ChCountService;
import org.zixing.service.CrayCountService;
import org.zixing.service.UserService;
import org.zixing.util.ConfigLoad;
import org.zixing.util.FtpUtils;
import org.zixing.util.ReadTxtFile;
import org.zixing.util.UserList;
import org.zixing.util.his.call.BaseInfoEntity;
import org.zixing.util.his.call.WsZxBaseInfo;

@Controller
// 模块
@RequestMapping("/chCount")
public class ChCountController {

	@Autowired
	private ChCountService chCountService;

	@Autowired
	private ChAnalyService chAnService;

	@Autowired
	private CrayCountService crayCount;

	@Autowired
	private UserService userService;

	@Autowired
	private ChAnalysisDao chAnalysisDao;
	@Autowired
	private CrayAnalysisDao crayAnalysisDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ChCountDao countDao;
	
	private static final Logger log = Logger.getLogger(ChCountController.class);
	// 计数中的页面初始化
	@ResponseBody
	@RequestMapping(value = "/chromLabelQuery")
	public Map<String,Object> chromLabelQuery(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String greayId = (String) session.getAttribute("grayid");
		
		String inName = (String) session.getAttribute("inName");
		session.setAttribute("greyCounts", crayCount.grayQService(inName, ""));
		
		List<GrayOper> list = (List<GrayOper>) session.getAttribute("greyCounts");
		return chCountService.ChromQuery(greayId,list);
	}

	// 计数中的页面上下页数据加载
	@ResponseBody
	@RequestMapping(value = "CountQuery")
	public Map<String,Object> chCountQuery(HttpServletRequest request) {
		String status=request.getParameter("status");
		HttpSession session = request.getSession();
		String greyId = (String) session.getAttribute("grayid");
		String inName = (String) session.getAttribute("inName");
		if(status.equals("down")){
			return chCountService.chCountQuery(greyId,inName,true);
		}else if(status.equals("up")){
			return chCountService.chCountQuery(greyId,inName,false);
		}
		return null;
	}
	
	// 计数中查询单染色体中的确定按钮把数据更新到数据库中
	@ResponseBody
	@RequestMapping(value = "/chromUpdate")
	public int chromUpdate(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String grayid = (String) session.getAttribute("grayid");
		String chromCount = request.getParameter("chromcount");
		String remakes = request.getParameter("remake");
		return chCountService.chromNumberAndRemakesUpdate(chromCount, remakes,
				grayid);
	}

	// 计数页面中的单个染色体改变值后自动更新到数据库中
	@ResponseBody
	@RequestMapping(value = "/chromSingleUpdate")
	public int chromSingleUpdate(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String grayid = (String) session.getAttribute("grayid");
		String chromId = request.getParameter("chromId");
		String chromNum = request.getParameter("a");
		String chromCount = request.getParameter("sums");
		chCountService.chromNumberUpdate(chromCount, grayid);
		return chCountService.chromSingleUpdate(chromId, chromNum);
	}

	// 文件卡页面初始化
	@ResponseBody
	@RequestMapping(value = "/folderInit")
	public List<Object> folderInit(HttpServletRequest request) {

		List<Object> resultList = new ArrayList<Object>();
		HttpSession session = request.getSession();
		String inName = (String) session.getAttribute("inName");
		if (inName == null) {
			inName = request.getParameter("inName");
		}
		
		String caryograms = request.getParameter("caryograms");
		String greyId = (String) session.getAttribute("grayid");
		int anStatuSum = chCountService.anStatusSum(inName);
		Integer countStatuSum = chCountService.countStatusSum(inName);
		if (countStatuSum == null)
			countStatuSum = 0;
		int anCheckSum = chCountService.anCheckSum(inName);
		int countCheckSum = chCountService.countCheckSum(inName);
		int sumAnandCount = anStatuSum + countStatuSum;
		String wholes = sumAnandCount + "," + countCheckSum + "," + anCheckSum;
		chCountService.updateWholes(wholes,inName);
		ReadTxtFile read = new ReadTxtFile();
	
		String filePath = new ConfigLoad().configload(request,"/java.properties", "filePath") + inName + ".txt";// properties

		List<String> strList = read.readTxtFile(filePath);
		List<FolderDate> folderDaList = new ArrayList<FolderDate>();
		FolderDate folderDate = chCountService.foldeQuery1(inName);
		// 查询中期计数
		try {
			String countWhole = String.valueOf(crayAnalysisDao
					.querysAnalyzeCount(inName));
			String whole = folderDate.getWhole();
			String[] wholesArray = whole.split(",");
			wholesArray[1] = countWhole;
			String v = Arrays.toString(wholesArray);
			folderDate.setWhole(v.substring(1, v.length() - 1));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 如果是生产环境，调用his获取患者信息
		if (new File("/home/uftp/his_web_service/orm.xml").exists()) {
			try {
				String hisResult = new WsZxBaseInfo(inName).getHisResult();
				BaseInfoEntity e = WsZxBaseInfo.parsEntity(hisResult);
				// System.out.println("his result  data :"+e);
				WsZxBaseInfo.transitionFolderDate(folderDate, e);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			// 使用虚假数据
			folderDate.setName("王春秋");
			folderDate.setSex(1+"");
			folderDate.setAge("30");
			folderDate.setMedicalRecord("354712");
			folderDate.setGatherDate("2018-09-04");
		}

		// 为空指针附初始值
		try {
			//如果是A事件,隐藏姓名，性别，年龄
			if(folderDate.getEvenNumber().indexOf("A")>-1){
				folderDate.setName("");
				folderDate.setSex("-"+1);
				folderDate.setAge("");
			}
			
			if (folderDate.getCaryogram() == null)
				folderDate.setCaryogram("");
			if (folderDate.getExamineAndNucleus() == null)
				folderDate.setExamineAndNucleus("");
			if (folderDate.getExamineAndVerify() == null)
				folderDate.setExamineAndVerify("");
			if (folderDate.getMedicalRecord() == null)
				folderDate.setMedicalRecord("");
			if (folderDate.getPutAsealOn() == null)
				folderDate.setPutAsealOn("");
			if (folderDate.getReportDate() == null)
				folderDate.setReportDate("");
			if (folderDate.getSpecimenSource() == null)
				folderDate.setSpecimenSource("");
			if (folderDate.getSuggestion() == null)
				folderDate.setSuggestion("");
			if (folderDate.getTheProducers() == null)
				folderDate.setTheProducers("");
			if (folderDate.getEvenNumber() == null)
				folderDate.setEvenNumber("");
			if (folderDate.getDiagnose() == null)
				folderDate.setDiagnose("");
			if (folderDate.getGatherDate() == null)
				folderDate.setGatherDate("");
			if (folderDate.getPregnancy() == null)
				folderDate.setPregnancy("");
			if (folderDate.getPregnancys() == null)
				folderDate.setPregnancys("");
			if (folderDate.getCheck() == null)
				folderDate.setCheck("");
			if (folderDate.getVaccinateDate() == null)
				folderDate.setVaccinateDate("");
			if (folderDate.getWhole() == null)
				folderDate.setWhole("");
		} catch (Exception e) {
			e.printStackTrace();
		}

		String Str = folderDate.getEvenNumber();
		System.out.println("******************事件编号:" + Str);
		String name = (String) session.getAttribute("loginName");
		String checkName = userDao.checkName(name);
		if(folderDate.getPutAsealOn().equals("")){
			folderDate.setPutAsealOn(checkName);
		}
		folderDaList.add(folderDate);
		resultList.add(strList);
		resultList.add(folderDaList);
		for (int i = 0; i < resultList.size(); i++) {
			System.out.println(resultList.get(i));
		}
		return resultList;
	}
	
	// 分析文件卡页面初始化
	@ResponseBody
	@RequestMapping(value = "/folderInit1")
	public List<Object> folderInit1(HttpServletRequest request) {
	
		List<Object> resultList = new ArrayList<Object>();
		HttpSession session = request.getSession();
		String inName = (String) session.getAttribute("inName");
		if (inName == null) {
			inName = request.getParameter("inName");
		}
		
		String caryograms = request.getParameter("caryograms");
		String greyId = (String) session.getAttribute("grayid");
		int anStatuSum = chCountService.anStatusSum(inName);
		Integer countStatuSum = chCountService.countStatusSum(inName);
		if (countStatuSum == null)
			countStatuSum = 0;
		int anCheckSum = chCountService.anCheckSum(inName);
		int countCheckSum = chCountService.countCheckSum(inName);
		int sumAnandCount = anStatuSum + countStatuSum;
		String wholes = sumAnandCount + "," + countCheckSum + "," + anCheckSum;
		chCountService.updateWholes(wholes,inName);
		ReadTxtFile read = new ReadTxtFile();
		
		String filePath = new ConfigLoad().configload(request,"/java.properties", "filePath") + inName + ".txt";// properties
		
	//			String filePath = "/home/uftp/apache-tomcat-7.0.88/webapps/TXT/"+ inName + ".txt";// 156服务器上环境TXT路径
	//			String filePath = "/home/apache-tomcat-7.0.90/webapps/TXT/"+ inName + ".txt";// 64服务器上环境TXT路径
		List<String> strList = read.readTxtFile(filePath);
		List<FolderDate> folderDaList = new ArrayList<FolderDate>();
		FolderDate folderDate = chCountService.foldeQuery1(inName);
		// 查询中期计数
		try {
			String countWhole = String.valueOf(crayAnalysisDao
					.querysAnalyzeCount(inName));
			String whole = folderDate.getWhole();
			String[] wholesArray = whole.split(",");
			wholesArray[1] = countWhole;
			String v = Arrays.toString(wholesArray);
			folderDate.setWhole(v.substring(1, v.length() - 1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		// 如果是生产环境，调用his获取患者信息
		if (new File("/home/uftp/his_web_service/orm.xml").exists()) {
			try {
				String hisResult = new WsZxBaseInfo(inName).getHisResult();
				BaseInfoEntity e = WsZxBaseInfo.parsEntity(hisResult);
				// System.out.println("his result  data :"+e);
				WsZxBaseInfo.transitionFolderDate(folderDate, e);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			// 使用虚假数据
			folderDate.setName("王春秋");
			folderDate.setSex(1+"");
			folderDate.setAge("30");
			folderDate.setMedicalRecord("354712");
			folderDate.setGatherDate("2018-09-04");
		}
	
		// 为空指针附初始值
		try {
			//如果是A事件,隐藏姓名，性别，年龄
			if(folderDate.getEvenNumber().indexOf("A")>-1){
				folderDate.setName("");
				folderDate.setSex(""+-1);
				folderDate.setAge("");
			}
			
			if (folderDate.getCaryogram() == null)
				folderDate.setCaryogram("");
			if (folderDate.getExamineAndNucleus() == null)
				folderDate.setExamineAndNucleus("");
			if (folderDate.getExamineAndVerify() == null)
				folderDate.setExamineAndVerify("");
			if (folderDate.getMedicalRecord() == null)
				folderDate.setMedicalRecord("");
			if (folderDate.getPutAsealOn() == null)
				folderDate.setPutAsealOn("");
			if (folderDate.getReportDate() == null)
				folderDate.setReportDate("");
			if (folderDate.getSpecimenSource() == null)
				folderDate.setSpecimenSource("");
			if (folderDate.getSuggestion() == null)
				folderDate.setSuggestion("");
			if (folderDate.getTheProducers() == null)
				folderDate.setTheProducers("");
			if (folderDate.getEvenNumber() == null)
				folderDate.setEvenNumber("");
			if (folderDate.getDiagnose() == null)
				folderDate.setDiagnose("");
			if (folderDate.getGatherDate() == null)
				folderDate.setGatherDate("");
			if (folderDate.getPregnancy() == null)
				folderDate.setPregnancy("");
			if (folderDate.getPregnancys() == null)
				folderDate.setPregnancys("");
			if (folderDate.getCheck() == null)
				folderDate.setCheck("");
			if (folderDate.getVaccinateDate() == null)
				folderDate.setVaccinateDate("");
			if (folderDate.getWhole() == null)
				folderDate.setWhole("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		String Str = folderDate.getEvenNumber();
		System.out.println("******************事件编号:" + Str);
		String name = (String) session.getAttribute("loginName");
		String checkName = userDao.checkName(name);
		if(folderDate.getPutAsealOn().equals("")){
			folderDate.setPutAsealOn(checkName);
		}
		folderDaList.add(folderDate);
		resultList.add(strList);
		resultList.add(folderDaList);
		for (int i = 0; i < resultList.size(); i++) {
			System.out.println(resultList.get(i));
		}
		return resultList;
	}
	
	@ResponseBody
	@RequestMapping(value = "/folderInit2")
	public List<Object> folderInit2(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String inName = (String) session.getAttribute("inName");
		String logName = (String) session.getAttribute("loginName");
		String txtpath = new ConfigLoad().configload(request,"/java.properties", "filePath");
		String isMakeString = request.getParameter("isMake");
		boolean isMake = false;
		if(isMakeString!=null){
			isMake = Boolean.parseBoolean(isMakeString);
		}
		//获取text文件数据
		return chCountService.foldeQuery(txtpath,inName,logName,isMake);

	}

	//文件卡点击确定按钮将数据更新到数据库中
	@ResponseBody
	@RequestMapping(value="/updateFolder")
	public boolean updateFolder(HttpServletRequest requert, FolderDate FolderDate){
		System.out.println("输入的值+"+FolderDate);
		String txtpath = "/home/uftp/apache-tomcat-7.0.88/webapps/TXT/";
		String inName = (String) requert.getSession().getAttribute("inName");
		return chCountService.queryCheckStatu(FolderDate,txtpath,inName);
	}

	// 根据单染色体id查询对应的轮廓图
	@ResponseBody
	@RequestMapping(value = "/singleChromToLine")
	public String singleChromToLine(HttpServletRequest request) {
		String imgId = request.getParameter("imgId");
		String strl = chCountService.singleChromToLine(imgId);
		return strl;
	}

	//分析中的将生成将文件卡里的内容以及截图生成pdf
	@RequestMapping(value="/ChfolderTolocality")
	public ResponseEntity<byte[]> ChfolderTolocality(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("分析页面的pdf生成");
		HttpSession session = request.getSession();
		//获取所需要的参数
		String inName = (String) session.getAttribute("inName");
		String greyId = (String) session.getAttribute("grayid");
		String greyidCount= request.getParameter("greyid");
		if(greyidCount!=null&&greyidCount!=""){
			greyId=greyidCount;
		}
		String report = request.getParameter("report");
		
		String txtUrl=new ConfigLoad().configload(request,"/java.properties", "txtUrl"); //properties
		
		String path = request.getSession().getServletContext().getRealPath("/");
		
		String pdfpath=new ConfigLoad().configload(request,"/java.properties", "pdfpath"); //properties
		
		String greyPath=new ConfigLoad().configload(request,"/java.properties", "greyPath"); //properties
		
		String imgPath=new ConfigLoad().configload(request,"/java.properties", "imgPath"); //properties

		log.info("pdf生成*********");
		log.info(inName+"**"+greyId+"**"+report);
		//		调用业务层
		String url = chCountService.pdfGeneration(inName,Integer.parseInt(greyId),txtUrl,Integer.parseInt(report), imgPath, greyPath,path,pdfpath);
		log.info("pdf地址："+url);
		
		String returnUrlTest=new ConfigLoad().configload(request,"/java.properties", "returnUrlTest")+url; //properties


		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", url);

		if(FtpUtils.isServer){
			log.info("文件从FTP服务器取");
			InputStream in = null;
			try {
				System.out.println("pdfpath+url:"+pdfpath+url);
				in = new FtpUtils().readFile(pdfpath+url);
				System.out.println("读取结束");
				byte[] data  = IOUtils.toByteArray(in);
				return new ResponseEntity<byte[]>(data, headers, HttpStatus.CREATED);
			}catch (Exception e){
				e.printStackTrace();
				log.info("文件从FTP服务器取ERROR:"+e.getMessage());
			}finally {
				IOUtils.closeQuietly(in);
			}

		}else {
			log.info("文件从服务器本地取");
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(pdfpath+url)),
					headers, HttpStatus.CREATED);
		}

		return null;
		//return returnUrlTest;
	}

	// 将胎儿的灰底图传到前端
	@ResponseBody
	@RequestMapping(value = "/chBabyPicToJs")
	public String chBabyPicToJs(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String greyId = (String) session.getAttribute("grayid");
		List<ChromLabel> chromList = chCountService.chromLabelQuerys(greyId);
		String chBabyPic = null;
		for (ChromLabel chromLabel : chromList) {
			chBabyPic = chromLabel.getChBabyPic();
		}
		return chBabyPic;
	}
	// 生成报告初始化页面
	@ResponseBody
	@RequestMapping(value = "/creapReportInit")
	public List<ChromQuery> creapReportInit(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String inName = (String) session.getAttribute("inName");
		List<ChromQuery> chromQueryList = chAnService.chromPicQuery(inName);
		for (ChromQuery chromQuery : chromQueryList) {
			System.out.println(chromQuery);
		}
		return chromQueryList;

	}
	
	// 计数页面更新核型到分析、计数页面
	/*@ResponseBody
	@RequestMapping(value = "/update1")
	public void updateCaryogram(String caryogram,String chromId,String greyNum,String greyNum1) {	
		String caryogram1 = "";
		if(caryogram.length()>1){
			caryogram1 = caryogram.substring(0,2);
		}else{
			caryogram1 = "0"+caryogram;
		}
		//根据事件查找玻片号DialPiece_id
		String dialPieceNum = countDao.dialPieceNumFind(chromId,greyNum1);
		System.out.println("dialPieceNum:"+dialPieceNum);
		
		//获取该玻片分析核型
		String analyCaryogram = countDao.findCaryogram(dialPieceNum,greyNum);    //可能为null

		//替换染色体数目，更新到分析
		if(null != analyCaryogram && "" != analyCaryogram){
			String analyCaryogram1 = caryogram1+analyCaryogram.substring(2, analyCaryogram.length());
			
			countDao.updateCaryogram3(analyCaryogram1, dialPieceNum, greyNum);
		}
		
		//计数核对更新到计数
		if(caryogram.length()>2){
			//获取核型
			String caryogramType = caryogram.substring(3, caryogram.length());
			
			//将计数核型替换
			countDao.updateCaryogram2(caryogramType, dialPieceNum,greyNum,caryogram1);
		}else{
			countDao.updateCaryogram2("", dialPieceNum,greyNum,caryogram1);

		}
		
	}*/
	//计数页面更新核型到分析、计数页面
	@ResponseBody
	@RequestMapping(value = "/update1")
	public void updateCaryogram(String caryogram,String chromId,String greyNum,String greyNum1) {	
		String caryogram1 = "";
		
		//找出如果存在","的下标
		int a = -1;
		if(caryogram.indexOf('，')!=-1||caryogram.indexOf(',')!=-1){
			caryogram = caryogram.replace('，', ',');
			a = caryogram.indexOf(',');
		}
		//如果存在",",截取
		if(a > 0){
			caryogram1 = caryogram.substring(0,a);
		}else{
			caryogram1 = caryogram;
		}
		//根据事件查找玻片号DialPiece_id
		String dialPieceNum = countDao.dialPieceNumFind(chromId,greyNum1);
		System.out.println("dialPieceNum:"+dialPieceNum);
		
		//获取该玻片分析核型
		String analyCaryogram = countDao.findCaryogram(dialPieceNum,greyNum);    //可能为null

		//替换染色体数目，更新到分析
		if(null != analyCaryogram && "" != analyCaryogram){
			String analyCaryogram1 = "";
			if(analyCaryogram.indexOf(',')==-1){
				analyCaryogram1 = analyCaryogram;
			}else{
				int b = analyCaryogram.indexOf(',');
				analyCaryogram1 = caryogram1+analyCaryogram.substring(b, analyCaryogram.length());
			}
			countDao.updateCaryogram3(analyCaryogram1, dialPieceNum, greyNum);
		}
		System.out.println("caryogram1:"+caryogram1);
		//计数核对更新到计数
		if(a > 0){
			//获取核型
			String caryogramType = caryogram.substring(a+1, caryogram.length());
			
			//将计数核型替换
			countDao.updateCaryogram2(caryogramType, dialPieceNum,greyNum,caryogram1);
		}else{
			countDao.updateCaryogram2("", dialPieceNum,greyNum,caryogram1);

		}
		
	}

	
	// 更新可分析状态
	@ResponseBody
	@RequestMapping(value = "/update2")
	public void updateStatus(String chromId,String greyNum,String greyNum1) {
	
		//根据事件查找玻片号DialPiece_id
		String dialPieceNum = countDao.dialPieceNumFind(chromId,greyNum1);
		
		//将计数数量替换
		countDao.updateStatus1(dialPieceNum,greyNum);		
	}
	
	
	// 分析核对取消可分析状态
	@ResponseBody
	@RequestMapping(value = "/update3")
	public void updateStatus1(String chromId,String greyNum,String greyNum1) {
	
		//根据事件查找玻片号DialPiece_id
		String dialPieceNum = countDao.dialPieceNumFind(chromId,greyNum1);
		
		//取消可分析状态
		countDao.updateStatus2(dialPieceNum,greyNum);		
	}
	
	
	// 计数页面更新核型到分析、计数页面
	@RequestMapping(value = "/findSlideName")
	@ResponseBody
	public String findSlideName(String inName) {
//				String sildeName = slideName;
		//System.out.println("sildeName===="+slideName);
//				return sildeName;
		return null;
	} 
	
	// 分析核对点击事件号查找玻片号
	@ResponseBody
	@RequestMapping(value = "/findGreyId")
	public String findGreyId(String eventId, String greyNum,String dialPiece) {

		// 根据事件查找玻片号DialPiece_id
		return countDao.dialPieceNumFind1(eventId, greyNum ,dialPiece);
	}
	
	// 分析核对点击00X查找玻片号
	@ResponseBody
	@RequestMapping(value = "/findGreyId1")
	public String findGreyId1(String eventId, String greyNum ,String dialPiece) {
		System.out.println("eventId:"+eventId+" "+"greyNum:"+greyNum+" "+"dialPiece:"+dialPiece);
		// 根据事件查找玻片号DialPiece_id
		String dialPieceNum = countDao.dialPieceNumFind2(eventId, greyNum,dialPiece);
		System.out.println("dialPieceNum:"+dialPieceNum);
		if(dialPieceNum==""||dialPieceNum==null){
			dialPieceNum = "1";
		}
		return dialPieceNum;
	}
	
	// 分割页面点击编号跳转
	@ResponseBody
	@RequestMapping(value = "/setCountGreyId")
	public String setCountGreyId(String eventId, String greyNum,String dialPiece,HttpServletRequest request) {
			
		List<GrayOper> list = new ArrayList<GrayOper>();
		list = crayCount.grayQServiceC(eventId,"","");
		request.getSession().setAttribute("chromQueryList", list);
			
		// 根据事件查找玻片号DialPiece_id
		return countDao.dialPieceNumFind1(eventId, greyNum ,dialPiece);
	}
	
	// 计数核对点击事件号跳转
	@ResponseBody
	@RequestMapping(value = "/setAnalyGreyId")
	public String setAnalyGreyId(String eventId, String greyNum,String dialPiece,HttpServletRequest request) {
		//更新listSession对象
	    request.getSession().setAttribute("chromQueryList", crayCount.grayQServiceC2(eventId));
			
		// 根据事件查找玻片号DialPiece_id
		return countDao.dialPieceNumFind1(eventId, greyNum ,dialPiece);
	}
	
	//文件卡点击确定将下拉框的内容更新到数据库
	@ResponseBody
	@RequestMapping(value="/updateFolderList")
	public boolean updateFolderList(HttpServletRequest request,String strs){
		UserList user = new UserList();
		String logname=(String) request.getSession().getAttribute("loginName");
		user.setLogName(logname);
		JSONObject json = JSONObject.fromObject(strs);
		user.setCardOne((List<String>) JSONArray.toCollection(json.getJSONArray("cardOne")));
		user.setCardTwo((List<String>) JSONArray.toCollection(json.getJSONArray("cardTwo"))); 
		user.setCardThree((List<String>) JSONArray.toCollection(json.getJSONArray("cardThree"))); 
		user.setCardFour((List<String>) JSONArray.toCollection(json.getJSONArray("cardFour"))); 
		user.setCardFive((List<String>) JSONArray.toCollection(json.getJSONArray("cardFive"))); 
		user.setCardSix((List<String>) JSONArray.toCollection(json.getJSONArray("cardSix"))); 
		return chCountService.updateFolderList(user);
	}
	
	//自动生成pdf,意见匹配核型生成pdf
	@ResponseBody
	@RequestMapping(value="/ChMatchKaryotypes")
	public List<Object> ChMatchKaryotypes(HttpServletRequest request,HttpServletResponse response,@RequestBody String str){
		List<Object> list = new  ArrayList<Object>();
		JSONObject json  = JSONObject.fromObject(str);
		int report = json.getInt("report");
		HttpSession session= request.getSession();
		String inName = (String)session.getAttribute("inName");
		if(inName==null){
			try {
				response.sendRedirect("/chromosome/page/jsp/caryogram/operation/caryindex_new.jsp");
			} catch (IOException e) {
				log.info("事件名为空跳转异常:"+e);
				e.printStackTrace();
			}
		}
		
		if(report==0){
			report= chCountService.ChMatchOpinion(inName);
		}
		boolean bl = chCountService.ChMatchKaryotypes(inName);
		list.add(report);
		list.add(bl);
		return list;
	}

}
