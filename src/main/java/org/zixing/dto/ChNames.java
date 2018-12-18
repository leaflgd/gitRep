package org.zixing.dto;

public class ChNames {
	private String slideName;
	private int grayName;
	private int chromName;
	private String chromUrl;
	
	public int getGrayName() {
		return grayName;
	}

	public void setGrayName(int grayName) {
		this.grayName = grayName;
	}

	public int getChromName() {
		return chromName;
	}

	public void setChromName(int chromName) {
		this.chromName = chromName;
	}
	
	public String getSlideName() {
		return slideName;
	}

	public void setSlideName(String slideName) {
		this.slideName = slideName;
	}
	public String getChromUrl() {
		return chromUrl;
	}

	public void setChromUrl(String chromUrl) {
		this.chromUrl = chromUrl;
	}
	@Override
	public String toString() {
		return "ChNames [slideName=" + slideName + ", grayName=" + grayName
				+ ", chromName=" + chromName + ", chromUrl=" + chromUrl + "]";
	}
}
