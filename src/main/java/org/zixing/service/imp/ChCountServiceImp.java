package org.zixing.service.imp;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zixing.dao.ChAnalysisDao;
import org.zixing.dao.ChCountDao;
import org.zixing.dao.ChromatidDao;
import org.zixing.dao.CrayAnalysisDao;
import org.zixing.dao.UserDao;
import org.zixing.dto.ChromLabel;
import org.zixing.dto.ChromQuery;
import org.zixing.dto.FolderDate;
import org.zixing.dto.GrayOper;
import org.zixing.service.ChCountService;
import org.zixing.util.CardMapping;
import org.zixing.util.GetEventKaryotype;
import org.zixing.util.JavaDateUtil;
import org.zixing.util.PdfUtils;
import org.zixing.util.ReadTxtFile;
import org.zixing.util.UserList;
import org.zixing.util.his.call.BaseInfoEntity;
import org.zixing.util.his.call.WsZxBaseInfo;
import org.zixing.web.BootController;

@Service
public class ChCountServiceImp implements ChCountService {

    private static Logger log = Logger.getLogger(ChCountServiceImp.class);
    @Autowired
    private ChCountDao chCountDao;

    @Autowired
    private ChromatidDao chromatidDao;

    @Autowired
    private CrayAnalysisDao analysisDao;

    @Autowired
    private UserDao userdao;

    @Autowired
    private ChAnalysisDao chAnDao;


    //染色体标签查询
    public List<ChromLabel> chromLabelQuerys(String grayid) {
        return chCountDao.chromLabelQuerys(grayid);
    }

    //计数页面初始化数据查询
    public Map<String, Object> ChromQuery(String grayid, List<GrayOper> list) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", grayid);
        map.put("label", chCountDao.chromLabelQuerys(grayid));
        map.put("sing", chCountDao.singleChromQuery(grayid));
        map.put("countCheck", list);
        map.put("analysAble", chCountDao.StatusQueryFind(grayid));
        int sum45 = 0;
        int sum46 = 0;
        int sum47 = 0;
        int sumy = 0;
        int sums = 0;
        for (GrayOper grayOper : list) {
            if (grayOper.getCountCheck() || grayOper.getAnalyCheck()) {
                switch (grayOper.getGrayCount()) {
                    case 45:
                        sum45++;
                        sums++;
                        break;
                    case 46:
                        sum46++;
                        sums++;
                        break;
                    case 47:
                        sum47++;
                        sums++;
                        break;
                    default:
                        sumy++;
                        sums++;
                        break;
                }
            }
        }
        List<Integer> count = new ArrayList<Integer>();
        System.out.println("45:" + sum45 + ",46:" + sum46 + ",47:" + sum47 + ",ss；" + sums + ",sumy" + sumy);
        count.add(sum45);
        count.add(sum46);
        count.add(sum47);
        count.add(sumy);
        count.add(sums);
        map.put("count", count);
        return map;
    }

    // 计数中的页面上下页数据加载
    @Override
    public Map<String, Object> chCountQuery(String greyId, String inName, boolean b) {
        String greyid = chCountDao.CountGreyIdQuery(greyId, inName, b);
        if (greyId == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", greyid);
        map.put("label", chCountDao.chromLabelQuerys(greyid));
        map.put("sing", chCountDao.singleChromQuery(greyid));
        return map;
    }

    //修改染色体的数量及备注
    public int chromNumberAndRemakesUpdate(String chromCount, String remakes, String grayid) {
        return chCountDao.chromNumberAndRemakesUpdate(chromCount, remakes, grayid);
    }

    //修改文件卡
    @Override
    public int updateFolder(FolderDate folderDate) {
        return chCountDao.updateFolder(folderDate, folderDate.getEvenNumber());
    }

    //修改单个染色体的数目
    @Override
    public int chromSingleUpdate(String chromId, String chromNum) {
        return chCountDao.chromSingleUpdate(chromId, chromNum);
    }

    //查询时间编号对应的文件卡
    @Override
    public List<Object> foldeQuery(String path, String inName, String logName, boolean isMake) {
        List<Object> list = new ArrayList<Object>();
        FolderDate data = chCountDao.foldeQuery(inName);
        //如果未点击核型分析中的确定按钮
        if (!isMake) {
            data.setCaryogram(data.getLastUpdateCaryogram());
        }
        System.out.println("123:" + data.getWhole());
        log.info("事件" + inName + "文件卡开始初始化" + data.getCardTxtStatus());
        //初始化加载控制
        if ("0".equals(data.getCardTxtStatus())) {
            List<String> txts = new ArrayList<String>();
            try {
                if (new File("/home/uftp/his_web_service/orm.xml").exists()) {
                    String hisResult = new WsZxBaseInfo(inName).getHisResult();
                    BaseInfoEntity e = WsZxBaseInfo.parsEntity(hisResult);
                    log.info("读取的文件数据" + e);
                    txts.add(0, e.getBh());
                    txts.add(1, e.getPid());
                    txts.add(2, e.getXm());
                    //年龄
                    String csrq = e.getCsrq();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                    String currentTime = sdf.format(new Date());
                    try {
                        Integer age = Integer.parseInt(currentTime) - Integer.parseInt(csrq.substring(0, 4));
                        txts.add(3, age + "");
                    } catch (Exception ex) {
                        System.out.println("his获取当前年龄异常");
                    }

                    txts.add(4, e.getXb());
                    txts.add(5, e.getCjrq());
                    if (e.getYl() != null || e.getYl() != "") data.setPregnancy(e.getYl());
                    if (e.getCjrq() != null || e.getCjrq() != "") data.setGatherDate(e.getCjrq());
                    log.info("数据处理成功");
                    // System.out.println("his result  data :"+e);
                    //WsZxBaseInfo.transitionFolderDate(folderDate, e);
                } else {
                    txts = new ReadTxtFile().readTxtFTPFile(path + "/" + inName + ".txt");
                    log.info("txt文件数据" + txts);
                }
            } catch (Exception e) {
                log.info("加载文件数据失败" + e.getMessage());
                // 使用虚假数据
                txts.add(inName);
//				txts.add("354712");   
//				txts.add("王春秋");     
//	 			txts.add("30");       
//				txts.add("女");        
//				txts.add("2018-09-04");  

            } finally {
                //添加txt数据
                try {
                    if (txts.get(1) != null) data.setMedicalRecord(txts.get(1));
                } catch (Exception e) {
                    log.info("加载txt文件的病历号失败" + e);
                }
                //姓名
                try {
                    if (txts.get(2) != null) data.setName(txts.get(2));
                } catch (Exception e) {
                    log.info("加载txt文件的姓名失败" + e);
                }
                //年龄
                try {
                    if (txts.get(3) != null) data.setAge(txts.get(3));
                } catch (Exception e) {
                    log.info("加载txt文件的年龄失败" + e);
                }
                //性别
                try {
                    if (txts.get(4) != null) data.setSex(txts.get(4));
                } catch (Exception e) {
                    log.info("加载txt文件的性别失败" + e);
                }
                //时间
                try {
                    if (txts.get(5) != null) data.setGatherDate(txts.get(5));
                } catch (Exception e) {
                    log.info("加载txt文件的日期失败" + e);
                }
            }
        }
        //核型匹配意见
        List<String> DialPieces = chCountDao.findSlideName(inName);
        String DialPiece = "";
        for (String str : DialPieces) {
            DialPiece += str;
        }
        String str = "";
        log.info("文件卡状态" + data.getCardOpinionStatus());
        if ("0".equals(data.getCardOpinionStatus()) || data.getSuggestion() == null) {
            if (data.getCaryogram() != null) {
                str = new CardMapping().opinion(data.getCaryogram(), DialPiece);
            } else {
                str = new CardMapping().opinion("", DialPiece);
            }
            data.setSuggestion(str);
        }
        //文件卡性别转换
        if ("0".equals(data.getSex())) {
            data.setSex("男");
        } else if ("1".equals(data.getSex())) {
            data.setSex("女");
        }
        //阅片及报告打印人
        if (data.getPutAsealOn() == null) {
            data.setPutAsealOn(userdao.checkName(logName));
        }
        int SUM = analysisDao.querysAnalyzeCount1(inName);
        int countsum = chCountDao.countCheckSum1(inName);
        int Analysum = chCountDao.anCheckSum(inName);
        data.setWhole(SUM + "," + countsum + "," + Analysum);

        //报告日期
        if ("0".equals(data.getReportDateStatus())) {
            if (!"".equals(data.getGatherDate()) && data.getGatherDate() != null) {
                try {
                    data.setReportDate(new JavaDateUtil().LaterDay(data.getGatherDate(), 18));
                } catch (Exception e) {
                    log.info("报告日期时间");
                }
            } else {
                data.setReportDate(null);
            }
        }

        //文件卡初始化A事件隐藏信息
        if ("a".equals(inName.charAt(0) + "") || "A".equals(inName.charAt(0) + "")) {
            log.info("A事件信息隐藏");
            data.setMedicalRecord(null);
            data.setName(null);
            data.setAge(null);
            data.setSex(null);
            data.setPregnancy(null);
            data.setPregnancys(null);
            data.setDiagnose(null);
            list.add(true);
        } else {
            list.add(false);
        }
//		p事件默认值
        if (data.getSpecimenSource() == null) {
            if ("P".equals(inName.charAt(0) + "") || "p".equals(inName.charAt(0) + "")) {
                data.setSpecimenSource("羊水");
            } else {
                data.setSpecimenSource("外周血");
            }
        }
        log.info("初始化文件卡数据" + data);
        list.add(data);
        return list;
    }

    //分析查询时间编号对应的文件卡
    @Override
    public FolderDate foldeQuery1(String inName) {

        FolderDate v = chCountDao.foldeQuery(inName);
        return v;
    }

    //根据单染色体id查询对应的轮廓图
    @Override
    public String singleChromToLine(String imgId) {
        return chCountDao.singleChromToLine(imgId);
    }

    //根据截图查询染色体信息
    @Override
    public List<ChromLabel> chromAllPicQuerys(String chAllpic) {
        return chCountDao.chromAllPicQuerys(chAllpic);
    }

    //生成报告后改变事件的生成报告状态
//	@Override
//	public int updateReportstatus(String inName,String caryogram) {
//		return chCountDao.updateReportstatus(inName,caryogram);
//	}

    //统计分析中勾选的
    @Override
    public int anStatusSum(String inName) {
        return chCountDao.anStatusSum(inName);
    }

    //统计除了分析中勾选的  计数勾选的
    @Override
    public Integer countStatusSum(String inName) {
        return chCountDao.countStatusSum(inName);
    }

    //统计分析中核对的
    @Override
    public int anCheckSum(String inName) {
        return chCountDao.anCheckSum(inName);
    }

    //统计计数中核对的
    @Override
    public int countCheckSum(String inName) {
        return chCountDao.countCheckSum(inName);
    }

    @Override
    public int updateWholes(String wholes, String inName) {
        return chCountDao.updateWholes(wholes, inName);
    }

    //文件卡提交存储信息
    @Override
    public boolean queryCheckStatu(FolderDate data, String pathFile, String inName) {
//		txt文档数据加载
        List<String> strs = new ArrayList<String>();
        strs.add(data.getEvenNumber());
        strs.add(data.getMedicalRecord());
        strs.add(data.getName());
        strs.add(data.getAge());
        strs.add(data.getSex());
        strs.add(data.getGatherDate());
        strs.add(data.getCaryogram());
        strs.add(data.getCheck());
        strs.add(data.getDiagnose());
        strs.add(data.getExamineAndNucleus());
        strs.add(data.getExamineAndVerify());
        strs.add(data.getPregnancy());
        strs.add(data.getPregnancys());
        strs.add(data.getPutAsealOn());
        strs.add(data.getReportDate());
        strs.add(data.getSpecimenSource());
        strs.add(data.getSuggestion());
        strs.add(data.getTheProducers());
        strs.add(data.getVaccinateDate());
        strs.add(data.getWhole());
        for (int i = 0; i < strs.size(); i++) {
            if (strs.get(i) == null) {
                strs.set(i, "");
            }
        }
        //储存txt文档
        new ReadTxtFile().writeTxtFTPFile(strs, pathFile, data.getEvenNumber() + ".txt");
        //存储数据库
        String putAsealOn = data.getPutAsealOn();
        String result = chCountDao.queryCheckStatu(inName);
        if ("0".equals(result) && putAsealOn != null && putAsealOn != "") {
            System.out.println("用户查询");
            String results = userdao.userNameQuery(putAsealOn);
            if (results != null) {
                System.out.println("工作量输入");
                userdao.updateUserWork(putAsealOn);
            }
        }
        //文件卡性别转换
        if ("男".equals(data.getSex())) {
            data.setSex("0");
        } else if ("女".equals(data.getSex())) {
            data.setSex("1");
        } else {
            data.setSex("");
        }
        System.out.println("存储文件卡");
        System.out.println(data);
        int results = chCountDao.updateFolder(data, inName);
        //修改文件卡锁定状态
        log.info("修改文件卡状态");
        chCountDao.CardOpinionStatusUpdate(inName, data);
        System.out.println("最后" + results);
        return true;
    }

    //根据事件编号查询分析中的核型
    @Override
    public List<String> arithKaryotypeQuery(String inName) {
        return chCountDao.arithKaryotypeQuery(inName);
    }

    //如果核对中有相同的核型则更新文件卡中的核型
    @Override
    public int updateCaryogram(String caryogram, String inName) {
        return chCountDao.updateCaryogram(caryogram, inName);
    }

    //修改单个染色体的数目同时修改染色体数量
    @Override
    public int chromNumberUpdate(String chromCount, String grayid) {
        return chCountDao.chromNumberUpdate(chromCount, grayid);
    }

    //根据灰底图id查询当前核对状态
    @Override
    public int anStatuThisQuery(int greyId) {
        return chCountDao.anStatuThisQuery(greyId);
    }

//	pdf文件生成

    /***
     * inName     事件名称
     * greyId	  灰底图ID
     * greyPath 灰底图路径
     * url        Txt存储路径
     * report     分辨采用PDF模版的参数
     * imgPath    读取图片的路径
     * pdfPath    生成pdf的路径
     */
    @Override
    public String pdfGeneration(String inName, int greyId, String txtUrl, int report, String imgPath, String greyPath, String path, String pdfpath) {
        //修改灰底图已打印报告
        chromatidDao.updatePrintReort(greyId);

        FolderDate folderDate = chCountDao.foldeQuery(inName);
        String reportStatus = folderDate.getReportDateStatus();
        //报告日期更新
        //报告日期
        if ("0".equals(folderDate.getReportDateStatus())) {
            if (!"".equals(folderDate.getGatherDate()) && folderDate.getGatherDate() != null) {
                try {
                    folderDate.setReportDate(new JavaDateUtil().LaterDay(folderDate.getGatherDate(), 18));
                } catch (Exception e) {
                    log.info("报告日期时间");
                }
            } else {
                folderDate.setReportDate(null);
            }
        }
        log.info("读取文件夹成功");
        ChromQuery chromQuery = chAnDao.chromSinglePicQuery(greyId, report);
        List<String> strList = new ArrayList<String>();
        strList.add(folderDate.getEvenNumber());
        strList.add(folderDate.getMedicalRecord());
        strList.add(folderDate.getName());
        strList.add(folderDate.getAge());
        strList.add(folderDate.getSex());
        strList.add(folderDate.getGatherDate());
        strList.add(folderDate.getCaryogram());
        strList.add(folderDate.getCheck());
        strList.add(folderDate.getDiagnose());
        strList.add(folderDate.getExamineAndNucleus());
        strList.add(folderDate.getExamineAndVerify());
        strList.add(folderDate.getPregnancy());
        strList.add(folderDate.getPregnancys());
        strList.add(folderDate.getPutAsealOn());
        strList.add(folderDate.getReportDate());
        strList.add(folderDate.getSpecimenSource());
        strList.add(folderDate.getSuggestion());
        strList.add(folderDate.getTheProducers());
        strList.add(folderDate.getVaccinateDate());
        strList.add(folderDate.getWhole());
        for (int i = 0; i < strList.size(); i++) {
            if (strList.get(i) == null) {
                strList.set(i, "");
            }
        }
        //TXT数据保存
        new ReadTxtFile().writeTxtFTPFile(strList, txtUrl, inName + ".txt");
        log.info("TXT文件保存成功");
        //数据处理
        //文件卡初始化A事件隐藏信息
        if ("a".equals(inName.charAt(0) + "") || "A".equals(inName.charAt(0) + "")) {
            log.info("A事件信息隐藏");
            folderDate.setMedicalRecord(null);
            folderDate.setName(null);
            folderDate.setAge(null);
            folderDate.setSex(null);
            folderDate.setPregnancy(null);
            folderDate.setPregnancys(null);
            folderDate.setDiagnose(null);
        }
//				p事件默认值
        if (folderDate.getSpecimenSource() == null) {
            if ("P".equals(inName.charAt(0) + "") || "p".equals(inName.charAt(0) + "")) {
                folderDate.setSpecimenSource("羊水");
            } else {
                folderDate.setSpecimenSource("外周血");
            }
        }
        Map<String, String> map = new HashMap();
        if ("0".equals(folderDate.getSex())) {
            map.put("Rep_Sex", "男");
        } else if ("1".equals(folderDate.getSex())) {
            map.put("Rep_Sex", "女");
        }
        //年龄处理
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        if (folderDate.getAge() != null) {
            if (pattern.matcher(folderDate.getAge()).matches()) {
                folderDate.setAge(folderDate.getAge() + "岁");
            }
        }
        map.put("Rep_ChromoNo", inName);
        if (folderDate.getMedicalRecord() != null) map.put("Rep_MedicalNo", folderDate.getMedicalRecord());
        if (folderDate.getName() != null) map.put("Rep_Name", folderDate.getName());
        if (folderDate.getAge() != null) map.put("Rep_Age", folderDate.getAge());
        if (folderDate.getPregnancy() != null) map.put("Rep_DateWeek", folderDate.getPregnancy());//w
        if (folderDate.getPregnancys() != null) map.put("Rep_DateDay", "+" + folderDate.getPregnancys());//d
        if (folderDate.getSpecimenSource() != null) map.put("Rep_SampleSource", folderDate.getSpecimenSource());
        if (folderDate.getGatherDate() != null) map.put("Rep_CollectDate", folderDate.getGatherDate());
        if (folderDate.getDiagnose() != null) map.put("Rep_ClinicDiagnose", folderDate.getDiagnose());
        if (report == 4) {
            String c = folderDate.getCaryogram().replace("XY", "XN");
            c = c.replace("XX", "XN");
            map.put("Rep_Caryogram", c);
        } else {
            if (folderDate.getCaryogram() != null) map.put("Rep_Caryogram", folderDate.getCaryogram());
        }
        if (folderDate.getSuggestion() != null) map.put("Rep_Opinion", folderDate.getSuggestion());
        if (folderDate.getTheProducers() != null) map.put("Rep_Producer", folderDate.getTheProducers());
        if (folderDate.getPutAsealOn() != null) map.put("Rep_ViewPrinter", folderDate.getPutAsealOn());
        if (folderDate.getExamineAndNucleus() != null) map.put("Rep_FilmReader", folderDate.getExamineAndNucleus());
        if (folderDate.getReportDate() != null) map.put("Rep_ReportDate", folderDate.getReportDate());
        if (folderDate.getExamineAndVerify() != null) map.put("Rep_Auditor", folderDate.getExamineAndVerify());
        //核对人
        if (folderDate.getCheck() != null) map.put("Rep_Checker", folderDate.getCheck());
        //图片域，pdf表单上应该为文本域而不是图片域
        Map<String, String> map2 = new HashMap();
        map2.put("Rep_ChromoAll", imgPath + chromQuery.getChromAllpic());
        map2.put("Rep_GreyBaseMap", greyPath + chromQuery.getCutOriPic());
        log.info("图片地址" + chromQuery);
        System.out.println("图片地址" + chromQuery);
        Map<String, Object> o = new HashMap();
        o.put("datemap", map);
        o.put("imgmap", map2);
        PdfUtils pdfUtils = new PdfUtils();
        String templatePath = null;
        log.info("相对路径" + path);
        switch (report) {
            case 1:
                templatePath = path + "page\\pdfTemplate\\pdf3.pdf";//156服务器上pdf3路径
                break;
            case 2:
                templatePath = path + "page\\pdfTemplate\\pdf4.pdf";//156服务器上pdf4路径
                break;
            case 3:
                templatePath = path + "page\\pdfTemplate\\pdf1.pdf";//156服务器上pdf1路径
                break;
            case 4:
                templatePath = path + "page\\pdfTemplate\\pdf2.pdf";//156服务器上pdf2路径
                break;
            default:
                templatePath = "";
                break;
        }
        templatePath = templatePath.replace("\\", "/");
        if (templatePath != null) {
            log.info("模板号" + report);
            log.info("pdf生成:" + "模版位置" + templatePath + "\n生成的文件名:" + inName + report);
            pdfUtils.pdfout(o, templatePath, pdfpath, path, inName);
            //生成报告后改变事件的生成报告状态
            chCountDao.updateReportstatus(inName, folderDate.getCaryogram(), folderDate.getReportDate());

        }
        log.info("PDF文件保存成功");
        return inName + ".pdf";
    }

    //文件卡下拉框存储
    @Override
    public boolean updateFolderList(UserList user) {
        return chCountDao.updateFolderList(user);
    }

    //意见匹配核型并确定出返回的模版
    @Override
    public int ChMatchOpinion(String inName) {
        CardMapping cardm = new CardMapping();
        //查询核型和意见
        FolderDate data = chCountDao.chOpinionQuery(inName);
        List<String> DialPieces = chCountDao.findSlideName(inName);
        String DialPiece = "";
        for (String str : DialPieces) {
            DialPiece += str;
        }
        String suggestion = "";
        String caryogram = data.getCaryogram();
        System.out.println("核型:" + caryogram);
        //核型匹配意见
        if ("0".equals(data.getCardOpinionStatus()) || data.getSuggestion() == null) {
            System.out.println("匹配意见:" + suggestion);
            suggestion = cardm.opinion(caryogram, DialPiece);
            if ("".equals(suggestion) || suggestion == null) {
                suggestion = "";
            } else {
                //匹配意见写入
                chCountDao.chOpinionStorage(inName, suggestion);
            }
        } else {
            suggestion = data.getSuggestion();
        }
        //意见匹配模版
        return cardm.report(data.getPregnancy(), data.getPregnancys(), DialPiece, suggestion, inName);
    }

    //看核型和性别是否匹配
    @Override
    public boolean ChMatchKaryotypes(String inName) {
        FolderDate data = chCountDao.chGenderQuery(inName);
        System.out.println("性别核对");
        System.out.println(data);
        String[] a = data.getCaryogram().split(",");
        if (a.length > 1) {
            String karyotype = a[1];
            if (("XX".equals(karyotype) && "1".equals(data.getSex())) || ("XY".equals(karyotype) && "0".equals(data.getSex()))) {
                return false;
            }
        }
        return true;
    }


}
