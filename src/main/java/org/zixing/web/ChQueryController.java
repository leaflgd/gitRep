package org.zixing.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zixing.dto.ChQueryDate;
import org.zixing.entity.User;
import org.zixing.service.ChQueryService;
import org.zixing.service.UserService;
import org.zixing.util.StringUtil;


@Controller
//模块
@RequestMapping("/chQuery")
public class ChQueryController {

	@Autowired
	private ChQueryService chQueryService;
	
	@Autowired
	private UserService userService;
	
	//查询页面中的事件列表查询
	@ResponseBody
	@RequestMapping(value="/chQueryAll")
	public List<ChQueryDate> chQueryAll(HttpServletRequest request){
		String time1 = request.getParameter("time1");
		String time2 = request.getParameter("time2");
		String selectType = request.getParameter("selectType");
		String price = request.getParameter("price");
//		if(selectType.equals("1")){
//			price = price.toUpperCase();
//		}
//		if(selectType.equals("2")){
//			price = price.toUpperCase();
//			if(price.indexOf('，')>=0){
//				price = price.replace('，', ',');
//			}
//		}
		if(selectType!=null&&price!=null){
			if(selectType.equals("1")){
				price=price.toUpperCase();
			}else if(selectType.equals("2")){
				price=price.toUpperCase();
				if(price.indexOf('，')>=0){
					price = price.replace('，', ',');
				}
			}
		}
		System.out.println("selectType++++"+selectType+" "+"price+++++"+price);
		List<ChQueryDate> chQueryDatelist = chQueryService.chQueryAll(time1,time2,selectType,price);
		return chQueryDatelist;
	}
	
	//查询页面中的统计查询
	@ResponseBody
	@RequestMapping(value="/chQueryAllCount")
	public List<ChQueryDate> chQueryAllCount(HttpServletRequest request){
		System.out.println("统计列表查询");
		String time3 = request.getParameter("time3");
		String time4 = request.getParameter("time4");
		List<ChQueryDate> chQueryDatelist = chQueryService.chQueryAllCount(time3,time4);
//		for (ChQueryDate str : chQueryDatelist) {
//			System.out.println(str);
//		}
		return chQueryDatelist;
	}
	
	//导出execl表格
	@ResponseBody
	@RequestMapping(value="/chQueryExport")
	public List<String> chQueryExport(HttpServletRequest request){
		String fileName = "" + StringUtil.convertDates(new Date()) + ".xls";
        String realpath = request.getServletContext().getRealPath("/excel");
        String time1 = request.getParameter("time1");
		String time2 = request.getParameter("time2");
		String selectType = request.getParameter("selectType");
		String price = request.getParameter("price");
        List<ChQueryDate> chQueryDatelist = chQueryService.chQueryAll(time1,time2,selectType,price);
        WritableWorkbook wwb;
        try {
            File file = new File(realpath);
            if (!file.exists()) {
                boolean result = file.mkdirs();
                if (result) {
                    realpath += "/" + fileName;
                }
            } else {
                realpath += "/" + fileName;
            }

            wwb = Workbook.createWorkbook(new File(realpath));
            jxl.write.WritableSheet ws = wwb.createSheet("" + StringUtil.convertDates(new Date()) + "", chQueryDatelist.size());

//            ws.setColumnView(0, 20); // 设置列的宽度
//            ws.setColumnView(1, 20); // 设置列的宽度
//            ws.setColumnView(2, 20); // 设置列的宽度
//            ws.setColumnView(3, 25); // 设置列的宽度
//            ws.setColumnView(4, 35); // 设置列的宽度
//            ws.setColumnView(5, 30); // 设置列的宽度
            
            ws.setColumnView(0, 20);//采集日期
            ws.setColumnView(1, 20);//事件
            ws.setColumnView(2, 20);//病历号
            ws.setColumnView(3, 15);//姓名
            ws.setColumnView(4, 10);//性别
            ws.setColumnView(5, 10);//年龄
            ws.setColumnView(6, 30);//临床诊断
            ws.setColumnView(7, 35);//核型
            ws.setColumnView(8, 25);//阅片及报告打印人
            ws.setColumnView(9, 25);//审核人
            ws.setColumnView(10, 25);//校片人
            ws.setColumnView(11, 15);//报告日期

            
            /**
             * 定义单元格样式
             */
            WritableFont wf = new WritableFont(WritableFont.ARIAL, 15,
                    WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
                    jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
            WritableCellFormat wcf = new WritableCellFormat(wf); // 单元格定义
            wcf.setBackground(jxl.format.Colour.BLACK); // 设置单元格的背景颜色
            wcf.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
            //设置边框;
            wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
            //设置自动换行;
            wcf.setWrap(true);

            WritableFont wfs = new WritableFont(WritableFont.ARIAL, 12,
                    WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
                    jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色

            WritableCellFormat wcfs = new WritableCellFormat(wfs); // 单元格定义
            wcfs.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
            //设置边框;
            wcfs.setBorder(Border.ALL, BorderLineStyle.THIN);
            //设置自动换行;
            wcfs.setWrap(true);
            
//            ws.addCell(new jxl.write.Label(0, 1, "采集日期", wcf));
//            ws.addCell(new jxl.write.Label(1, 1, "事件", wcf));
//            ws.addCell(new jxl.write.Label(2, 1, "姓名", wcf));
//            ws.addCell(new jxl.write.Label(3, 1, "核型", wcf));
//            ws.addCell(new jxl.write.Label(4, 1, "阅片及报告打印人", wcf));
//            ws.addCell(new jxl.write.Label(5, 1, "报告日期", wcf));
            
            ws.addCell(new jxl.write.Label(0, 1, "采集日期", wcf));
            ws.addCell(new jxl.write.Label(1, 1, "事件", wcf));
            ws.addCell(new jxl.write.Label(2, 1, "病历号", wcf));
            ws.addCell(new jxl.write.Label(3, 1, "姓名", wcf));
            ws.addCell(new jxl.write.Label(4, 1, "性别", wcf));
            ws.addCell(new jxl.write.Label(5, 1, "年龄", wcf));
            ws.addCell(new jxl.write.Label(6, 1, "临床诊断", wcf));
            ws.addCell(new jxl.write.Label(7, 1, "核型", wcf));
            ws.addCell(new jxl.write.Label(8, 1, "阅片及报告打印人", wcf));
            ws.addCell(new jxl.write.Label(9, 1, "校片人", wcf));
            ws.addCell(new jxl.write.Label(10, 1, "审核人", wcf));
            ws.addCell(new jxl.write.Label(11, 1, "报告日期", wcf));
            
            int bookSize = chQueryDatelist.size();
            for (int i = 0; i < bookSize; i++) {
                ChQueryDate chQueryDate = chQueryDatelist.get(i);
                
//            ws.addCell(new jxl.write.Label(0, i + 2, chQueryDate.getCollectDate(), wcfs));
//            ws.addCell(new jxl.write.Label(1, i + 2, chQueryDate.getInName(), wcfs));
//            ws.addCell(new jxl.write.Label(2, i + 2, chQueryDate.getPatientName(), wcfs));
//            ws.addCell(new jxl.write.Label(3, i + 2, chQueryDate.getChromCary(), wcfs));
//            ws.addCell(new jxl.write.Label(4, i + 2, chQueryDate.getReportPrinter(), wcfs));
//            ws.addCell(new jxl.write.Label(5, i + 2, chQueryDate.getReportDate(), wcfs));
                if(chQueryDate.getPatientSex()!=null){
                	 if(Integer.parseInt(chQueryDate.getPatientSex())==0){
                     	chQueryDate.setPatientSex("男");
                     }else if(Integer.parseInt(chQueryDate.getPatientSex())==1){
                     	chQueryDate.setPatientSex("女");
                     }
                }else{
                	chQueryDate.setPatientSex("");
                }
         
            ws.addCell(new jxl.write.Label(0, i + 2, chQueryDate.getCollectDate(), wcfs));//采集日期
            ws.addCell(new jxl.write.Label(1, i + 2, chQueryDate.getInName(), wcfs));//事件
            ws.addCell(new jxl.write.Label(2, i + 2, chQueryDate.getCaseNumber(), wcfs));//病历号
            ws.addCell(new jxl.write.Label(3, i + 2, chQueryDate.getPatientName(), wcfs));//姓名
            ws.addCell(new jxl.write.Label(4, i + 2, chQueryDate.getPatientSex(), wcfs));//性别
            if(chQueryDate.getPatientAge()!=null){
            	ws.addCell(new jxl.write.Label(5, i + 2, ""+chQueryDate.getPatientAge(), wcfs));//年龄
            }else{
            	ws.addCell(new jxl.write.Label(5, i + 2, "", wcfs));
            }
            
            ws.addCell(new jxl.write.Label(6, i + 2, chQueryDate.getClinicalDiagnosis(), wcfs));//临床诊断
            ws.addCell(new jxl.write.Label(7, i + 2, chQueryDate.getChromCary(), wcfs));//核型
            ws.addCell(new jxl.write.Label(8, i + 2, chQueryDate.getViewAndPrinter(), wcfs));//阅片及报告打印人
            ws.addCell(new jxl.write.Label(9, i + 2, chQueryDate.getXpr(), wcfs));//审核人
            ws.addCell(new jxl.write.Label(10, i + 2, chQueryDate.getAuditor(), wcfs));//校片人
            ws.addCell(new jxl.write.Label(11, i + 2, chQueryDate.getReportDate(), wcfs));//报告日期
            
                
            }
            ws.addCell(new jxl.write.Label(0, 0, "", wcf));
            ws.mergeCells(0, 0, 12, 0); // 合并单元格
            wwb.write();
            // 关闭Excel工作薄对象
            wwb.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> strlist = new ArrayList<String>();
        strlist.add(fileName);
		return strlist;
	}
}
