package org.zixing.util.his.call;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.zixing.dto.FolderDate;

import javax.xml.namespace.QName;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 根据自兴提供的染色体编号查询HIS中相关基本信息。 染色体编号格式如 ：C146800
 *
 * @author xzl
 *
 */
public class WsZxBaseInfo {

	//public static final String wsdlLocation = "file:/D:/java/common.xml";
	public static final String wsdlLocation = "file:/home/uftp/his_web_service/orm.xml";


	private static final QName SERVICE_NAME = new QName(
			"http://common.ws.zxxy.com/", "WebServicePublishService");

	/**
	 * 染色体编号
	 */
	private String bh;


	/**
	 * 必须带染色体编号访问webservice数据
	 *
	 * @param bh
	 */
	public WsZxBaseInfo(String bh) {
		super();

		/**
		 * 请求参数为标准Xml字符串，顶层节点名称为request ，红色部分为所有接口调用共同参数，请求参数名统一都为小写，其具体格式如下：
		 * <request wsid='业务接口名' lyfs=‘1’ dybh = ‘’ dymm=’’> ……… </request>
		 **/
		this.bh = "<?xml version=\"1.0\" encoding=\"gbk\"?><request wsid=\"WS_ZX_RST_BASEINFO\" dybh=\"4891\" dymm=\"4891\" lyfs=\"92\"><bh>"
				+ bh + "</bh></request>";
	}

	/**
	 *
	 * 响应参数为标准Xml字符串 <response> <resultstatus>响应状态(1 成功,其它为失败(0))</resultstatus>
	 * <errorsode>错误码</errorcode> <errormsg>错误信息<errormsg> <result>
	 * <bh>染色体编号</bh> <pid>病历号</pid> <xm>姓名</xm> <csrq>出生日期</csrq> <xb>性别</xb>
	 * <kfrq>开单日期</kfrq> <yl>孕龄</yl> <cjrq>采集日期</cjrq> </result> </response>
	 *
	 * @return
	 */
	public String getHisResult() {
		URL wsdlURL = WebServicePublishService.WSDL_LOCATION;

		WebServicePublishService ss = new WebServicePublishService(wsdlURL,
				SERVICE_NAME);
		WebServiceInterface port = ss.getWebServicePublishPort();
		try {
			String result = port.webservice(bh);
			//System.out.println("webservice.result=" + result);

			return result;

		} catch (WsException_Exception e) {
			System.out.println("Expected exception: WsException has occurred.");
			System.out.println(e.toString());
			return null;
		}

	}

	/**
	 * 将结果String类型转换为实体类
	 * @return 为null即调用失败
	 */
	public static BaseInfoEntity parsEntity(String xmlResult) {

		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlResult);
			// 指向根节点
			Element root = doc.getRootElement();
			// 先判断是否成功
			Element resultStatus = root.element("resultStatus");
			if (resultStatus.getTextTrim().equals("1")) {
				// 获取结果节点
				Element resulElement = root.element("result");
				// 利用反射赋值
				Object o = BaseInfoEntity.class.newInstance();

				Field[] fields = BaseInfoEntity.class.getDeclaredFields();
				//System.out.println(fields.length);
				for (Field f : fields) {

					f.setAccessible(true);
					String attribute = f.getName();
					f.set(o, resulElement.element(attribute)==null?"null":resulElement.element(attribute).getText());

				}
				return (BaseInfoEntity) o;
			} else {
				throw new NullPointerException("调用web service失败，响应状态："
						+ resultStatus.getTextTrim() + "\t错误码:"
						+ root.element("errorcode").getTextTrim() + "\t错误信息:"
						+ root.element("errormsg").getTextTrim());
			}

		} catch (Exception e) {
			// TODO 自动生成 catch 块
			//e.printStackTrace();
			System.out.println(e.getMessage());

		}
		return null;

	}


	public static void transitionFolderDate(FolderDate f,BaseInfoEntity e){
		if(e!=null){
			f.setMedicalRecord(e.getPid());
			f.setName(e.getXm());
			//根据出生日期获取年龄
			String csrq = e.getCsrq();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			String currentTime = sdf.format(new Date());
			try {
				Integer age = Integer.parseInt(currentTime)-Integer.parseInt(csrq.substring(0,4));
				f.setAge(age+"");
			}catch (Exception ex){System.out.println("his获取当前年龄异常");}
			if(e.getXb()!=null&&e.getXb().equals("男"))
				f.setSex("0");
			else
				f.setSex("1");

		}
	}

}
