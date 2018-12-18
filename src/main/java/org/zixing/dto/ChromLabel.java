package org.zixing.dto;

public class ChromLabel {
	private String inName;
	private String slideName;
	private String grayNumber;
	private String grayUrl;
	//普通染色体核型
	private String chromCary;
	//普通染色体核型个数
	private int chromCount;
	
	private String chromRemake;
	
	private String grayUrlOut;
	
	private String arithOffset;
	
	private String toSystemDate;

	private int chromBig;//分析中的放大
	
	private int chromColorOpera;//分析中的颜色调整
	
	private int chromSmall;//分析中的缩小
	
	private String chBabyPic;//从算法取出的胎儿的图片
	
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


	public String getChromCary() {
		return chromCary;
	}


	public void setChromCary(String chromCary) {
		this.chromCary = chromCary;
	}

	

	public int getChromCount() {
		return chromCount;
	}


	public void setChromCount(int chromCount) {
		this.chromCount = chromCount;
	}

	
	
	public String getChromRemake() {
		return chromRemake;
	}


	public void setChromRemake(String chromRemake) {
		this.chromRemake = chromRemake;
	}

	

	public String getGrayUrlOut() {
		return grayUrlOut;
	}


	public void setGrayUrlOut(String grayUrlOut) {
		this.grayUrlOut = grayUrlOut;
	}


	public String getArithOffset() {
		return arithOffset;
	}


	public void setArithOffset(String arithOffset) {
		this.arithOffset = arithOffset;
	}

	
	public String getToSystemDate() {
		return toSystemDate;
	}


	public void setToSystemDate(String toSystemDate) {
		this.toSystemDate = toSystemDate;
	}

	public int getChromBig() {
		return chromBig;
	}


	public void setChromBig(int chromBig) {
		this.chromBig = chromBig;
	}


	public int getChromColorOpera() {
		return chromColorOpera;
	}


	public void setChromColorOpera(int chromColorOpera) {
		this.chromColorOpera = chromColorOpera;
	}


	public int getChromSmall() {
		return chromSmall;
	}


	public void setChromSmall(int chromSmall) {
		this.chromSmall = chromSmall;
	}


	public String getChBabyPic() {
		return chBabyPic;
	}


	public void setChBabyPic(String chBabyPic) {
		this.chBabyPic = chBabyPic;
	}


	@Override
	public String toString() {
		return "ChromLabel [inName=" + inName + ", slideName=" + slideName
				+ ", grayNumber=" + grayNumber + ", grayUrl=" + grayUrl
				+ ", chromCary=" + chromCary + ", chromCount=" + chromCount
				+ ", chromRemake=" + chromRemake + ", grayUrlOut=" + grayUrlOut
				+ ", arithOffset=" + arithOffset + ", toSystemDate="
				+ toSystemDate + ", chromBig=" + chromBig
				+ ", chromColorOpera=" + chromColorOpera + ", chromSmall="
				+ chromSmall + ", chBabyPic=" + chBabyPic + "]";
	}
}
