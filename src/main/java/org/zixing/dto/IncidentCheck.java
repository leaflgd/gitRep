package org.zixing.dto;

public class IncidentCheck {
	private String inName;
	private boolean inCheck;
	private boolean status;
	private int analyStatusSum;//分析的勾选统计
	private int countStatusSum;//计数勾选统计
	private String caryogram;
	private int analyCheck;//分析的核对统计
	private int countCheck;//计数的核对统计
	private String maximumArithKaryotype;//重复值最多的核型
	private int count;		//分析的勾选统计+计数勾选统计
	private String cardName;    //11-7 事件卡姓名
	private Integer cardSex;    //11-7 事件卡性别
	
	
	
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
	
	
	public Integer getCardSex() {
		return cardSex;
	}
	public void setCardSex(Integer cardSex) {
		this.cardSex = cardSex;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	/**
	 * @return the maximumArithKaryotype
	 */
	public String getMaximumArithKaryotype() {
		return maximumArithKaryotype;
	}
	/**
	 * @param maximumArithKaryotype the maximumArithKaryotype to set
	 */
	public void setMaximumArithKaryotype(String maximumArithKaryotype) {
		this.maximumArithKaryotype = maximumArithKaryotype;
	}
	public String getInName() {
		return inName;
	}
	public void setInName(String inName) {
		this.inName = inName;
	}
	public boolean isInCheck() {
		return inCheck;
	}
	public void setInCheck(boolean inCheck) {
		this.inCheck = inCheck;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public int getAnalyStatusSum() {
		return analyStatusSum;
	}
	public void setAnalyStatusSum(int analyStatusSum) {
		this.analyStatusSum = analyStatusSum;
	}
	public int getCountStatusSum() {
		return countStatusSum;
	}
	public void setCountStatusSum(int countStatusSum) {
		this.countStatusSum = countStatusSum;
	}
	public String getCaryogram() {
		return caryogram;
	}
	public void setCaryogram(String caryogram) {
		this.caryogram = caryogram;
	}
	public int getAnalyCheck() {
		return analyCheck;
	}
	public void setAnalyCheck(int analyCheck) {
		this.analyCheck = analyCheck;
	}
	public int getCountCheck() {
		return countCheck;
	}
	public void setCountCheck(int countCheck) {
		this.countCheck = countCheck;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IncidentCheck [inName=" + inName + ", inCheck=" + inCheck
				+ ", status=" + status + ", analyStatusSum=" + analyStatusSum
				+ ", countStatusSum=" + countStatusSum + ", caryogram="
				+ caryogram + ", analyCheck=" + analyCheck + ", countCheck="
				+ countCheck + ", maximumArithKaryotype="
				+ maximumArithKaryotype + "]";
	}
	
}
