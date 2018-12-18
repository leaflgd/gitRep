package org.zixing.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * 项目加载web.xml启动
 * **/

public class StartTaskListener implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//启动后台线程
//		new Thread(new SubmitbBackstage()).start();
	}

}
