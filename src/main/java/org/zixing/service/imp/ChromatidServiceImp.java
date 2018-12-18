package org.zixing.service.imp;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zixing.dao.ChCountDao;
import org.zixing.dao.ChromatidDao;
import org.zixing.dao.CrayAnalysisDao;
import org.zixing.dto.ChromQuery;
import org.zixing.dto.FolderDate;
import org.zixing.dto.GrayOper;
import org.zixing.dto.IncidentCheck;
import org.zixing.dto.ChromLabel;
import org.zixing.dto.SubmitbQuery;
import org.zixing.entity.Gray;
import org.zixing.service.ChromatidService;
import org.zixing.util.GetEventKaryotype;
import org.zixing.util.PdfUtils;

@Service
public class ChromatidServiceImp implements ChromatidService {

	@Autowired
	private ChromatidDao chromatidDao;
	@Autowired
	private CrayAnalysisDao analysisDao;
	@Autowired
	private ChCountDao chCountDao;
	
	private static final Logger log = Logger.getLogger(ChromatidServiceImp.class);

	// 获取需核实事件
	public List<IncidentCheck> inQueryService(String inName, String date,
			String chenck) {


		String tomcatPath = System.getProperty("catalina.home");

		List<IncidentCheck> list = chromatidDao.incidentQuery(inName, date, chenck);
		for(IncidentCheck v : list){
			
			try {
				String pdfPath = tomcatPath+ "/webapps/pdf/items/" + v.getInName() + ".pdf";
				//输出最后打印报告的核型
				String caryogram = PdfUtils.readPdfReturnCaryogram(pdfPath);
				//如果是胎儿报告
				int i = caryogram.indexOf("XN");
				if(i>-1){
					//去事件卡中查找
					FolderDate f = chCountDao.foldeQuery(inName);
					v.setCaryogram(f.getCaryogram());
				}else {
					v.setCaryogram(caryogram);
				}
				
			} catch (Exception e) {
				List<String> analysis = analysisDao.queryAllArithKaryotype(v.getInName());
				//设置匹配规则的核型
				v.setCaryogram(GetEventKaryotype.getEventKaryotype(analysis));
			}
			
		}
		return list;
	}

	// 是否打印报告核对状态查询
	public String inNameChack(String inName) {
		return chromatidDao.inNameChack(inName);
	}

}
