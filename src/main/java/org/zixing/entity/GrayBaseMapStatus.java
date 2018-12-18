package org.zixing.entity;

public class GrayBaseMapStatus {
	private int statusId;
	private int grayBaseMapId;
	private int chromsomeId;
	private String chromsomeStyle;
	private String chromsomeEnhance;
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public int getGrayBaseMapId() {
		return grayBaseMapId;
	}
	public void setGrayBaseMapId(int grayBaseMapId) {
		this.grayBaseMapId = grayBaseMapId;
	}
	public int getChromsomeId() {
		return chromsomeId;
	}
	public void setChromsomeId(int chromsomeId) {
		this.chromsomeId = chromsomeId;
	}
	public String getChromsomeStyle() {
		return chromsomeStyle;
	}
	public void setChromsomeStyle(String chromsomeStyle) {
		this.chromsomeStyle = chromsomeStyle;
	}
	
	
	public String getChromsomeEnhance() {
		return chromsomeEnhance;
	}
	public void setChromsomeEnhance(String chromsomeEnhance) {
		this.chromsomeEnhance = chromsomeEnhance;
	}
	
	@Override
	public String toString() {
		return "GrayBaseMapStatus [statusId=" + statusId + ", grayBaseMapId="
				+ grayBaseMapId + ", chromsomeId=" + chromsomeId
				+ ", chromsomeStyle=" + chromsomeStyle + ", chromsomeEnhance="
				+ chromsomeEnhance + "]";
	}
	
	
	
}
