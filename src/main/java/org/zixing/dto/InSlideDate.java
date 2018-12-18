package org.zixing.dto;

public class InSlideDate{
	private String inName;
	private String slideName;
	public String getInName() {
		return inName;
	}
	public void setInName(String inName) {
		this.inName = inName;
	}
	public String getSlideName() {
		return slideName;
	}
	public void setSlideName(String slideName) {
		this.slideName = slideName;
	}
	public String toString() {
		return "InSlideQuery [inName=" + inName + ", slideName=" + slideName
				+ "]";
	}
}
