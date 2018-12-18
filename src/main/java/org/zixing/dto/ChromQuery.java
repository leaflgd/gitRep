package org.zixing.dto;

public class ChromQuery {
	private int chromId;
	private int greyId;
	private int chromMark;
	private String chromUrl;
	private boolean chromState;
	private int chromCount;
	private String chromPath;
	private String chromEnhance;
	private String chromBin;
	private String chromNum;
	private String chromAllpic;
	private String cutOriPic;
	private String ChromFather;
	private String inName;
	private String slideName;
	private String grayNumber;
	private String grayUrl;
	private String chScacss;
	private String chRotcss;
	private String chCircumX;
	private String chPath;
	private String imageSize;
	private int chCount;
	// 普通染色体核型
	private String chromCary;

	private String chromRemake;

	private String grayUrlOut;

	private String arithOffset;

	private String toSystemDate;

	// 染色体分析排序专用
	private Integer orderByNum;

	// 染色体分析上下移动
	private Integer topDown;
	
	private String arithOffsetPicAna;
	
	private String logid;

	public int getChromId() {
		return chromId;
	}

	public void setChromId(int chromId) {
		this.chromId = chromId;
	}

	public int getGreyId() {
		return greyId;
	}

	public void setGreyId(int greyId) {
		this.greyId = greyId;
	}

	public int getChromMark() {
		return chromMark;
	}

	public void setChromMark(int chromMark) {
		this.chromMark = chromMark;
	}

	public String getChromUrl() {
		return chromUrl;
	}

	public void setChromUrl(String chromUrl) {
		this.chromUrl = chromUrl;
	}

	public boolean isChromState() {
		return chromState;
	}

	public void setChromState(boolean chromState) {
		this.chromState = chromState;
	}

	public int getChromCount() {
		return chromCount;
	}

	public void setChromCount(int chromCount) {
		this.chromCount = chromCount;
	}

	public String getChromPath() {
		return chromPath;
	}

	public void setChromPath(String chromPath) {
		this.chromPath = chromPath;
	}

	public String getChromEnhance() {
		return chromEnhance;
	}

	public void setChromEnhance(String chromEnhance) {
		this.chromEnhance = chromEnhance;
	}

	public String getChromBin() {
		return chromBin;
	}

	public void setChromBin(String chromBin) {
		this.chromBin = chromBin;
	}

	public String getChromNum() {
		return chromNum;
	}

	public void setChromNum(String chromNum) {
		this.chromNum = chromNum;
	}

	public String getChromAllpic() {
		return chromAllpic;
	}

	public void setChromAllpic(String chromAllpic) {
		this.chromAllpic = chromAllpic;
	}

	public String getCutOriPic() {
		return cutOriPic;
	}

	public void setCutOriPic(String cutOriPic) {
		this.cutOriPic = cutOriPic;
	}

	public String getChromFather() {
		return ChromFather;
	}

	public void setChromFather(String chromFather) {
		ChromFather = chromFather;
	}

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

	public String getChScacss() {
		return chScacss;
	}

	public void setChScacss(String chScacss) {
		this.chScacss = chScacss;
	}

	public String getChRotcss() {
		return chRotcss;
	}

	public void setChRotcss(String chRotcss) {
		this.chRotcss = chRotcss;
	}

	public String getChCircumX() {
		return chCircumX;
	}

	public void setChCircumX(String chCircumX) {
		this.chCircumX = chCircumX;
	}

	public String getChPath() {
		return chPath;
	}

	public void setChPath(String chPath) {
		this.chPath = chPath;
	}

	public int getChCount() {
		return chCount;
	}

	public void setChCount(int chCount) {
		this.chCount = chCount;
	}

	public String getChromCary() {
		return chromCary;
	}

	public void setChromCary(String chromCary) {
		this.chromCary = chromCary;
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

	public Integer getOrderByNum() {
		return orderByNum;
	}

	public void setOrderByNum(Integer orderByNum) {
		this.orderByNum = orderByNum;
	}

	public Integer getTopDown() {
		return topDown;
	}

	public void setTopDown(Integer topDown) {
		this.topDown = topDown;
	}

	public String getArithOffsetPicAna() {
		return arithOffsetPicAna;
	}

	public void setArithOffsetPicAna(String arithOffsetPicAna) {
		this.arithOffsetPicAna = arithOffsetPicAna;
	}

	public String getLogid() {
		return logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
	}

	public String getImageSize() {
		return imageSize;
	}

	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}

	@Override
	public String toString() {
		return "ChromQuery [chromId=" + chromId + ", greyId=" + greyId
				+ ", chromMark=" + chromMark + ", chromUrl=" + chromUrl
				+ ", chromState=" + chromState + ", chromCount=" + chromCount
				+ ", chromPath=" + chromPath + ", chromEnhance=" + chromEnhance
				+ ", chromBin=" + chromBin + ", chromNum=" + chromNum
				+ ", chromAllpic=" + chromAllpic + ", cutOriPic=" + cutOriPic
				+ ", ChromFather=" + ChromFather + ", inName=" + inName
				+ ", slideName=" + slideName + ", grayNumber=" + grayNumber
				+ ", grayUrl=" + grayUrl + ", chScacss=" + chScacss
				+ ", chRotcss=" + chRotcss + ", chCircumX=" + chCircumX
				+ ", chPath=" + chPath + ", imageSize=" + imageSize
				+ ", chCount=" + chCount + ", chromCary=" + chromCary
				+ ", chromRemake=" + chromRemake + ", grayUrlOut=" + grayUrlOut
				+ ", arithOffset=" + arithOffset + ", toSystemDate="
				+ toSystemDate + ", orderByNum=" + orderByNum + ", topDown="
				+ topDown + ", arithOffsetPicAna=" + arithOffsetPicAna
				+ ", logid=" + logid + "]";
	}
	
}
