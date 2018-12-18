package org.zixing.dto;


public class InfLog {
	private int logId;
	private int greyId;
	private int chromId;
	private String logName;
	private String logSteps;
	private String chromCount;
	private int operaWay;
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
	public String getLogName() {
		return logName;
	}
	public void setLogName(String logName) {
		this.logName = logName;
	}
	public String getLogSteps() {
		return logSteps;
	}
	public void setLogSteps(String logSteps) {
		this.logSteps = logSteps;
	}
	public String getChromCount() {
		return chromCount;
	}
	public void setChromCount(String chromCount) {
		this.chromCount = chromCount;
	}
	public int getOperaWay() {
		return operaWay;
	}
	public void setOperaWay(int operaWay) {
		this.operaWay = operaWay;
	}
	public int getGreyId() {
		return greyId;
	}
	public void setGreyId(int greyId) {
		this.greyId = greyId;
	}
	public int getChromId() {
		return chromId;
	}
	public void setChromId(int chromId) {
		this.chromId = chromId;
	}
	@Override
	public String toString() {
		return "InfLog [logId=" + logId + ", greyId=" + greyId + ", chromId="
				+ chromId + ", logName=" + logName + ", logSteps=" + logSteps
				+ ", chromCount=" + chromCount + ", operaWay=" + operaWay + "]";
	}
}
