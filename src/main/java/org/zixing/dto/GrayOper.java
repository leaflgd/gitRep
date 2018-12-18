package org.zixing.dto;

import java.io.Serializable;

public class GrayOper  implements Serializable{
	private static final long serialVersionUID = 1L;
	private int grayId;
	private String inName;
	private String grayDate;
	private String grayNumber;
	private String grayUrl;
	private int  grayCount;
	private String grayCountRemark;
	private String grayCray;
//	private boolean grayCheck;
	private boolean analyCheck;
	private boolean countCheck;
//	private int countCheck;
	private String slideName;
	
	private int analyStatus;
	private int countStatus;
	private int checkTag;
	
	@Override
	public String toString() {
		return "GrayOper [grayId=" + grayId + ", inName=" + inName
				+ ", grayDate=" + grayDate + ", grayNumber=" + grayNumber
				+ ", grayUrl=" + grayUrl + ", grayCount=" + grayCount
				+ ", grayCountRemark=" + grayCountRemark + ", grayCray="
				+ grayCray + ", analyCheck=" + analyCheck + ", countCheck="
				+ countCheck + ", slideName=" + slideName + ", analyStatus="
				+ analyStatus + ", countStatus=" + countStatus + ", checkTag="
				+ checkTag + "]";
	}
	public int getAnalyStatus() {
		return analyStatus;
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
	public int getCheckTag() {
		return checkTag;
	}
	public void setCheckTag(int checkTag) {
		this.checkTag = checkTag;
	}
	public int getGrayId() {
		return grayId;
	}
	public void setGrayId(int grayId) {
		this.grayId = grayId;
	}
	public String getInName() {
		return inName;
	}
	public void setInName(String inName) {
		this.inName = inName;
	}
	public String getGrayDate() {
		return grayDate;
	}
	public void setGrayDate(String grayDate) {
		this.grayDate = grayDate;
	}
	public String getGrayNumber() {
		return grayNumber;
	}
	public void setGrayNumber(String grayNumber) {
		this.grayNumber = grayNumber;
	}
	public String getGrayUrl() {
		return grayUrl;
	}
	public void setGrayUrl(String grayUrl) {
		this.grayUrl = grayUrl;
	}
	public int getGrayCount() {
		return grayCount;
	}
	public void setGrayCount(int grayCount) {
		this.grayCount = grayCount;
	}
	public String getGrayCountRemark() {
		return grayCountRemark;
	}
	public void setGrayCountRemark(String grayCountRemark) {
		this.grayCountRemark = grayCountRemark;
	}
	public String getGrayCray() {
		return grayCray;
	}
	public void setGrayCray(String grayCray) {
		this.grayCray = grayCray;
	}
	public boolean getAnalyCheck() {
		return analyCheck;
	}
	public void setAnalyCheck(boolean analyCheck) {
		this.analyCheck = analyCheck;
	}
	public boolean getCountCheck() {
		return countCheck;
	}
	public void setCountCheck(boolean countCheck) {
		this.countCheck = countCheck;
	}
	public String getSlideName() {
		return slideName;
	}
	public void setSlideName(String slideName) {
		this.slideName = slideName;
	}
	
}
