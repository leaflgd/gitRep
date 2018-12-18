package org.zixing.entity;

public class Gray {
	private String grayNumber;
	private String grayUrl;
	private String grayDate;
	private boolean grayCount;
	private boolean grayAnalyze;
	private Slide slide;
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
	public String getGrayDate() {
		return grayDate;
	}
	public void setGrayDate(String grayDate) {
		this.grayDate = grayDate;
	}
	public boolean isGrayCount() {
		return grayCount;
	}
	public void setGrayCount(boolean grayCount) {
		this.grayCount = grayCount;
	}
	public boolean isGrayAnalyze() {
		return grayAnalyze;
	}
	public void setGrayAnalyze(boolean grayAnalyze) {
		this.grayAnalyze = grayAnalyze;
	}
	public Slide getSlide() {
		return slide;
	}
	public void setSlide(Slide slide) {
		this.slide = slide;
	}
	public String toString() {
		return "GrayImage [grayNumber=" + grayNumber + ", grayUrl=" + grayUrl
				+ ", grayDate=" + grayDate + ", grayCount=" + grayCount
				+ ", grayAnalyze=" + grayAnalyze + ", slide=" + slide + "]";
	}
}
