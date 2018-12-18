package org.zixing.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;

/**
 *读取配置文件的类
 * 
 * request 请求
 * path 项目的相对路径
 *  
 ***/

public class ConfigLoad {
	private static Properties configMessageQuery(HttpServletRequest request,String path){
		Properties prop= new Properties();
		System.out.println("配置加载开始");
		try{
			System.out.println("读取属性文件a.properties");
			System.out.println("路径："+request.getServletContext().getRealPath(""));
			//读取属性文件
			InputStream in = new BufferedInputStream (new FileInputStream(request.getServletContext().getRealPath("")+path));
//			request.getServletContext().getResourceAsStream("/b.properties");
//					
			prop.load(new InputStreamReader(in, "utf-8"));     ///加载属性列表
            in.close();
            
            ///保存属性到b.properties文件
//            FileOutputStream oFile = new FileOutputStream("a.properties", true);//true表示追加打开
//            prop.setProperty("phone", "10086");
//            prop.store(oFile, "The New properties file");
//            oFile.close();
        }
		catch(Exception e){
            System.out.println(e);
        }
		System.out.println("配置加载结束");
		return prop;
	}
	//加载配置文件
	public Properties configLoads(HttpServletRequest request,String path){
		return configMessageQuery(request,path);
	}
	
	/**
	 * request  请求对象
	 * path     配置文件相对地址
	 * key     配置文件的键值
	 * **/
//  加载所需的配置信息
	public String configload(HttpServletRequest request,String path,String key){
		Properties prop= configMessageQuery(request,path);
		return prop.getProperty(key);
	}
}
