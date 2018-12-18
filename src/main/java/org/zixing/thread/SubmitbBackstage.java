package org.zixing.thread;

import org.zixing.service.imp.SubmitbServiceImp;
import org.zixing.util.SpringContextHolder;

/***
 * 提交后台线程
 * 只做循环的调用处理方法
 * */

public class SubmitbBackstage implements Runnable{
	
	public void run() {
		SubmitbServiceImp ssi=(SubmitbServiceImp)SpringContextHolder.getBean("submitbServiceImp");
		while(true){
			try {
				ssi.SubmitbDispose();
				Thread.currentThread().sleep(110000l);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("后台线程错误");
			}
		}
	}
	
}
