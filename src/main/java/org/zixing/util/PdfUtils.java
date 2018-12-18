package org.zixing.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class PdfUtils {
	private static final Logger log = Logger.getLogger(PdfUtils.class);
	
	public void pdfout(Map<String, Object> o, String templatePath,String newPDFPath,String path,String inName){
		 // 模板路径  
//       String templatePath = "E:/PDFTEST.pdf";
       // 生成的新文件路径   
       PdfReader reader;
       FileOutputStream out;
       ByteArrayOutputStream bos;
       PdfStamper stamper;
       try {
       	String fontUrl =path+"page/font/simsun.ttc,1";
       	log.info("字体地址"+fontUrl);
         BaseFont bf = BaseFont.createFont( fontUrl, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);//ubuntu16.04上字体
           log.info("字体加载成功");
           log.info("新生成地址"+newPDFPath);
//           out = new FileOutputStream(newPDFPath);
           reader = new PdfReader(templatePath);// 读取pdf模板  
           bos = new ByteArrayOutputStream();
           stamper = new PdfStamper(reader, bos);
           AcroFields form = stamper.getAcroFields();
           //文字类的内容处理
           Map<String,String> datemap = (Map<String,String>)o.get("datemap");
           form.addSubstitutionFont(bf);
           for(String key : datemap.keySet()){
               String value = datemap.get(key);
               form.setField(key,value);
           }
           //图片类的内容处理
           Map<String,String> imgmap = (Map<String,String>)o.get("imgmap");
           if(imgmap!=null){
        	   for(String key : imgmap.keySet()) {
	                String imgpath = imgmap.get(key);
	                int pageNo = form.getFieldPositions(key).get(0).page;
	                Rectangle signRect = form.getFieldPositions(key).get(0).position;
	                float x = signRect.getLeft();
	                float y = signRect.getBottom();
	                //根据路径读取图片
	                Image image = Image.getInstance(imgpath);
	                float w = image.getWidth();
	                float h = image.getHeight();
	                float w1 = signRect.getWidth();
	                float h1 = signRect.getHeight();
	                //获取图片页面
	                PdfContentByte under = stamper.getOverContent(pageNo);
	                
	                
	                //图片大小自适应
	                if((w/h)>(w1/h1)){
	                	//求取比例差
	                	float val = h1-((h*1000)*(w1*1000)/(w*1000*1000));
	                	image.scaleToFit(w1, h1);
	                	//添加图片
		                image.setAbsolutePosition(x,y+(val/2));
	                }else{
	                	//求取比例差
	                	float val =w1-((w*1000)*(h1*1000)/(h*1000*1000));
	                	image.scaleToFit(w1,h1);
	                	//添加图片
		                image.setAbsolutePosition(x+(val/2),y);
	                }

	                under.addImage(image);
	            }

	            }
           
           stamper.setFormFlattening(true);// 如果为false，生成的PDF文件可以编辑，如果为true，生成的PDF文件不可以编辑
           stamper.close();
           log.info("数据处理完毕");
//         pdf文件输出
//         Document doc = new Document();
//         Font font = new Font(bf, 32);
//         PdfCopy copy = new PdfCopy(doc, out);
//         doc.open(); 
//         PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
//         copy.addPage(importPage);
//         doc.close();
//         out.close();
           log.info("上传数据"+bos.size());
         FtpUtils ftp = new FtpUtils();
         ftp.uploadFile(newPDFPath, inName+".pdf", new ByteArrayInputStream(bos.toByteArray()));
       } catch (IOException e) {
    	   log.info(e);
    	   log.error("打印报告IOException error:"+e);
       } catch (DocumentException e) {
       		log.error("打印报告DocumentException error:"+e);
    	   log.info(e);
       }
	}
	
	/**
     * 读取pdf文件内容
     * @param fileName
     * @return
     */
    public static String readPdf(String fileName){
        try {
            StringBuilder sb = new StringBuilder();
            PdfReader reader = new PdfReader(fileName);
            int pageNum = reader.getNumberOfPages();
            for(int i=1;i<=pageNum;i++){
                sb.append(PdfTextExtractor.getTextFromPage(reader, i));//读取第i页的文档内容
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    

    /**
     * 读取pdf文件内容返回核型
     * @param fileName
     * @return
     */
    public static String readPdfReturnCaryogram(String fileName) throws IOException{
        PDDocument document = PDDocument.load(new File(fileName));

            document.getClass();

            if(!document.isEncrypted()) {
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);
                PDFTextStripper tStripper = new PDFTextStripper();

                String pdfFileInText = tStripper.getText(document);
                StringBuilder result =new  StringBuilder();
                int v = 0;
                String[] lines = pdfFileInText.split("\\r?\\n");
                for (int i =0;i<lines.length;i++){
                    System.out.println(lines[i]);
                    if(lines[i].indexOf(",")>-1){
                        v=i;
                        break;
                    }
                }
                if(v==0)    v=lines.length-2;
                for(;v<lines.length;v++) {
                    if (v==lines.length-1) break;
                    result.append(lines[v]);
                }
                return result.toString();
            }


        return "";
    }
    
	public static void main(String[] args) {
        String s = "/home/uftp/apache-tomcat-7.0.88/webapps/chromosome/page\\pdfTemplate\\pdf3.pdf";
        System.out.println(s.replace("\\", "/"));
    }
}
