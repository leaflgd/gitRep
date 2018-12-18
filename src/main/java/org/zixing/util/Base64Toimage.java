package org.zixing.util;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;

public class Base64Toimage {
	
	//bse64图片数据处理类方法
	public static byte[] imgBase64(String imgStr){
		 byte[] b = null;
		String[] strlist = imgStr.split(",");
		//对字节数组字符串进行Base64解码并生成图片  
		//strlist[1]是获取Base64图片传过来的,之后的字符串
        if (strlist[1] == null){ //图像数据为空  
            return b;  
        }
        
        BASE64Decoder decoder = new BASE64Decoder();
        //Base64解码   
		try {
			b = decoder.decodeBuffer(strlist[1]);
			for(int i=0;i<b.length;++i)  
	        {  
	            if(b[i]<0)  
	            {//调整异常数据  
	                b[i]+=256;  
	            }  
	        }
		} catch (IOException e) {
			System.out.println("图片数据异常");
			e.printStackTrace();
		}
		return b;  
	}
	/**
	 * 将base64的字符串转为图片保存到指定路径
	 * @param imgStr
	 * @return
	 */
	public boolean GenerateImage(String imgStr,String path){  
		
        try   
        {  
        	byte[] b = imgBase64(imgStr);
            //生成jpeg图片  
            OutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }   
        catch (Exception e)
        {  
        	e.printStackTrace();
        	System.out.println("图片生成异常");
            return false;  
        }  
	
    }
	/**
	 * 将base64的字符串转为图片保存到指定路径
	 * @param imgStr
	 * @return
	 */
	public boolean GenerateImageFTP(String imgStr,String path,String name){	
        try {  
        	//图片数据处理
        	byte[] b = imgBase64(imgStr);
        	//图片输出
            InputStream instream = new ByteArrayInputStream(b);
            new FtpUtils().uploadFile("/home/uftp/apache-tomcat-7.0.88/webapps/images/image/patient/"+path, name, instream);//156
//            new FtpUtils().uploadFile("/home/apache-tomcat-7.0.90/webapps/images/image/patient/"+path, name, instream);//64
            return true;
        }catch (Exception e) {  
        	e.printStackTrace();
        	System.out.println(false);
            return false;  
        }  
	
    }
}
