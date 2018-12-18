package org.zixing.thread;

/**
 * 项目启动通过spring启动方法加载
 * */
public class SpringInitLoad {
	
	public void initMethod(){ 
		new Thread(new SubmitbBackstage()).start();
	}
}
