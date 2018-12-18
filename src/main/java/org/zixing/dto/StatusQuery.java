/**
 * 
 */
package org.zixing.dto;

/**
 * @author: 王骏浩
 * @date: 2018年11月6日
 * title:分析核对页面增加的可分析按钮
 * 
 */
public class StatusQuery {
	private int analyStatus;
	private int countStatus;
	private int analyCheck;
	private int countCheck;
	private int checkTag;
	public int getAnalyStatus() {
		return analyStatus;
	}
	@Override
	public String toString() {
		return "StatusQuery [analyStatus=" + analyStatus + ", countStatus=" + countStatus + ", analyCheck=" + analyCheck
				+ ", countCheck=" + countCheck + ", checkTag=" + checkTag + "]";
	}
	public void setAnalyStatus(int analyStatus) {
		this.analyStatus = analyStatus;
	}
	public int getCountStatus() {
		return countStatus;
	}
	public void setCountStatus(int countStatus) {
		this.countStatus = countStatus;
	}
	public int getAnalyCheck() {
		return analyCheck;
	}
	public void setAnalyCheck(int analyCheck) {
		this.analyCheck = analyCheck;
	}
	public int getCountCheck() {
		return countCheck;
	}
	public void setCountCheck(int countCheck) {
		this.countCheck = countCheck;
	}
	public int getCheckTag() {
		return checkTag;
	}
	public void setCheckTag(int checkTag) {
		this.checkTag = checkTag;
	}
}
