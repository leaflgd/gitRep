package org.zixing.entity;

public class Chromatid {
	private int cheomatidId;
	private int chromatidMark;
	private String chromatidUrl;
	private int chromatidNumber;
	private Gray gray;
	public int getChromatidMark() {
		return chromatidMark;
	}
	public void setChromatidMark(int chromatidMark) {
		this.chromatidMark = chromatidMark;
	}
	public String getChromatidUrl() {
		return chromatidUrl;
	}
	public void setChromatidUrl(String chromatidUrl) {
		this.chromatidUrl = chromatidUrl;
	}
	public int getChromatidNumber() {
		return chromatidNumber;
	}
	public void setChromatidNumber(int chromatidNumber) {
		this.chromatidNumber = chromatidNumber;
	}
	public Gray getGray() {
		return gray;
	}
	public void setGray(Gray gray) {
		this.gray = gray;
	}
	public Chromatid(int chromatidMark, String chromatidUrl,
			int chromatidNumber, Gray gray) {
		super();
		this.chromatidMark = chromatidMark;
		this.chromatidUrl = chromatidUrl;
		this.chromatidNumber = chromatidNumber;
		this.gray = gray;
	}
	public Chromatid(){
		super();
	}
	@Override
	public String toString() {
		return "Chromatid [chromatidMark=" + chromatidMark + ", chromatidUrl="
				+ chromatidUrl + ", chromatidNumber=" + chromatidNumber
				+ ", gray=" + gray + "]";
	}
	public int getCheomatidId() {
		return cheomatidId;
	}
	public void setCheomatidId(int cheomatidId) {
		this.cheomatidId = cheomatidId;
	}
}
