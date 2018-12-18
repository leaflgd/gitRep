package org.zixing.dto;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GrayQueryDate{
	private int grayId;
	private String grayDate;
	private String grayNumber;
	private String grayUrl;
	private boolean analyStatus;
	private boolean countStatus;
	private List<String> checkTypeList;
	private int dialPieceId;    //拨片ID

    public int getDialPieceId() {
        return dialPieceId;
    }

    public GrayQueryDate setDialPieceId(int dialPieceId) {
        this.dialPieceId = dialPieceId;
        return this;
    }

    public List<String> getCheckTypeList() {
		return checkTypeList;
	}

	public GrayQueryDate setCheckTypeList(List<String> checkTypeList) {
		this.checkTypeList = checkTypeList;
		return this;
	}

	public int getGrayId() {
		return grayId;
	}
	public void setGrayId(int grayId) {
		this.grayId = grayId;
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
	public boolean isAnalyStatus() {
		return analyStatus;
	}
	public void setAnalyStatus(boolean analyStatus) {
		this.analyStatus = analyStatus;
	}
	public boolean isCountStatus() {
		return countStatus;
	}
	public void setCountStatus(boolean countStatus) {
		this.countStatus = countStatus;
	}

	@Override
	public String toString() {
		return checkTypeList.toString() ;
	}


	/**
	 * 数据精选下沉排序
	 * @param o
	 * @return
	 */
	public static List<GrayQueryDate> compareToThis(List<GrayQueryDate> o) {
		List<GrayQueryDate> new0 = new LinkedList<>();
		List<GrayQueryDate> new1 = new LinkedList<>();
		List<GrayQueryDate> new2 = new LinkedList<>();
		for (int i = 0 ; i <o.size(); i++){
			GrayQueryDate g = o .get(i);
			if(g.getCheckTypeList().size()==2){
				new2.add(g);
			}else if(g.getCheckTypeList().size()==1){
				if(g.getCheckTypeList().contains("1")){
					new1.add(g);
				}else if(g.getCheckTypeList().contains("2")){
					new2.add(g);
				}
			}else {
				new0.add(g);
			}
		}
		List<GrayQueryDate> returnList = new ArrayList<>();
		returnList.addAll(new0);
		returnList.addAll(new1);
		returnList.addAll(new2);
		return returnList;
	}
}
