package org.zixing.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

public class ReadTxtFile {
	
//	private final static String filePath = "/usr/tomcat/apache-tomcat-7.0.88/webapps/pdf/folder.txt";//txt的路径
//	private final static String filePath1 = "/usr/tomcat/apache-tomcat-7.0.88/webapps/pdf/folder1.txt";//txt的路径
	
//	private final static String filePath = "D:/folder.txt";//txt的路径
//	private final static String filePath1 = "D:/folder1.txt";//txt的路径
	private final static Logger log = Logger.getLogger(ReadTxtFile.class);
	
	//从txt中读取数据
	public List<String> readTxtFile(String filePath){ 
		List<String> strList = new ArrayList<String>();
	    try {
	        String encoding="GBK"; 
	        File file=new File(filePath); 
	        if(file.isFile() && file.exists()){ //判断文件是否存在 
	          InputStreamReader read = new InputStreamReader( 
	          new FileInputStream(file),encoding);//考虑到编码格式 
	          BufferedReader bufferedReader = new BufferedReader(read); 
	          String lineTxt = null; 
	          while((lineTxt = bufferedReader.readLine()) != null){ 
	            strList.add(lineTxt);
	          } 
	          read.close(); 
	    }else{ 
	      System.out.println("找不到指定的文件"); 
	    } 
	    } catch (Exception e) { 
	      System.out.println("读取文件内容出错"); 
	      e.printStackTrace(); 
	    } 
	    return strList;
	    
	  } 
	
	//把数据写入到txt中
	public void writeTxtFile(List<String> str,String filePath1){
		try {
			File file = new File(filePath1);
			if (!file.exists()) {
				file.createNewFile();
			}

			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file),"GBK"); 
			BufferedWriter bw = new BufferedWriter(write);
			for (int i = 0; i < str.size(); i++) {
			   bw.write(str.get(i)+"\r\n");
			}		   
			bw.close();
		  	} catch (IOException e) {
		  		e.printStackTrace();
		  	}
	}
	
	//把数据写入到FTP的txt中
	public void writeTxtFTPFile(List<String> strs,String path,String file){
		System.out.println("存储的TXT路径"+path+file);
		try {
			//数据处理
			String str="";
			for (int i = 0; i < strs.size(); i++) {
				str+=strs.get(i)+"\r\n";
			}
//			System.out.println(str);
			InputStream   input = new  ByteArrayInputStream(str.getBytes("UTF-8"));  
			new FtpUtils().uploadFile(path, file, input);
		} 
		catch (Exception e) {
		  		e.printStackTrace();
		}
	}
	
	//读取ftp的txt文件数据
	public List<String> readTxtFTPFile(String filePath1) throws Exception {
		System.out.println("存储的TXT路径"+filePath1);
		List<String> list = new ArrayList<String>();
		try {
			FtpUtils ftpUtil = new FtpUtils();
			BufferedReader reader = new BufferedReader(new InputStreamReader(ftpUtil.readFile(filePath1), "utf-8"));
    		String value = null;
    		while ((value=reader.readLine())!=null){
//    			System.out.println("txt数据"+value);
    			list.add(value);
    		}
//		ftpUtil.readFile(pathname, filename);
		}catch (Exception e) {
			throw new Exception("文件加载失败"+e);
		}
		return list;
	}
}
