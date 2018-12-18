package org.zixing.dto;

public class FolderDate {
	private String evenNumber;//事件编号
	
	private String medicalRecord;//病例号
	
	private String name;//姓名
	
	private String age;//年龄
	
	private String sex;//性别
	
	private String pregnancy;//孕龄
	
	private String pregnancys;//孕龄d
	
	private String gatherDate;//采集日期
	
	private String vaccinateDate;//接种日期
	
	private String diagnose;//临床诊断
	
	private String suggestion;//意见
	
	private String caryogram;//核型
	
	private String specimenSource;//样本来源
	
	private String theProducers;//制片人
	
	private String putAsealOn;//阅片人及打印人
	
	private String examineAndNucleus;//校片人
	
	private String examineAndVerify;//审核人
	
	private String reportDate;//报告日期
	
	private String check;//核对 值为医生姓名
	
	private String whole;//中期(总数,计数的,分析的)
	
	private String cardOpinionStatus;//意见锁定
	
	private String reportDateStatus; //报告日期锁定
	
	private String cardTxtStatus; //文件读取txt锁定

	//12-14xzl
	private String lastUpdateCaryogram;//打印报告核型

	public String getLastUpdateCaryogram() {
		return lastUpdateCaryogram;
	}

	public FolderDate setLastUpdateCaryogram(String lastUpdateCaryogram) {
		this.lastUpdateCaryogram = lastUpdateCaryogram;
		return this;
	}

	public String getEvenNumber() {
		return evenNumber;
	}

	public void setEvenNumber(String evenNumber) {
		this.evenNumber = evenNumber;
	}

	public String getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(String medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPregnancy() {
		return pregnancy;
	}

	public void setPregnancy(String pregnancy) {
		this.pregnancy = pregnancy;
	}

	public String getPregnancys() {
		return pregnancys;
	}

	public void setPregnancys(String pregnancys) {
		this.pregnancys = pregnancys;
	}

	public String getGatherDate() {
		return gatherDate;
	}

	public void setGatherDate(String gatherDate) {
		this.gatherDate = gatherDate;
	}

	public String getVaccinateDate() {
		return vaccinateDate;
	}

	public void setVaccinateDate(String vaccinateDate) {
		this.vaccinateDate = vaccinateDate;
	}

	public String getDiagnose() {
		return diagnose;
	}

	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getCaryogram() {
		return caryogram;
	}

	public void setCaryogram(String caryogram) {
		this.caryogram = caryogram;
	}

	public String getSpecimenSource() {
		return specimenSource;
	}

	public void setSpecimenSource(String specimenSource) {
		this.specimenSource = specimenSource;
	}

	public String getTheProducers() {
		return theProducers;
	}

	public void setTheProducers(String theProducers) {
		this.theProducers = theProducers;
	}

	public String getPutAsealOn() {
		return putAsealOn;
	}

	public void setPutAsealOn(String putAsealOn) {
		this.putAsealOn = putAsealOn;
	}

	public String getExamineAndNucleus() {
		return examineAndNucleus;
	}

	public void setExamineAndNucleus(String examineAndNucleus) {
		this.examineAndNucleus = examineAndNucleus;
	}

	public String getExamineAndVerify() {
		return examineAndVerify;
	}

	public void setExamineAndVerify(String examineAndVerify) {
		this.examineAndVerify = examineAndVerify;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}


	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public String getWhole() {
		return whole;
	}

	public void setWhole(String whole) {
		this.whole = whole;
	}

	public String getCardOpinionStatus() {
		return cardOpinionStatus;
	}

	public void setCardOpinionStatus(String cardOpinionStatus) {
		this.cardOpinionStatus = cardOpinionStatus;
	}

	public String getReportDateStatus() {
		return reportDateStatus;
	}

	public void setReportDateStatus(String reportDateStatus) {
		this.reportDateStatus = reportDateStatus;
	}

	public String getCardTxtStatus() {
		return cardTxtStatus;
	}

	public void setCardTxtStatus(String cardTxtStatus) {
		this.cardTxtStatus = cardTxtStatus;
	}

	@Override
	public String toString() {
		return "FolderDate [evenNumber=" + evenNumber + ", medicalRecord="
				+ medicalRecord + ", name=" + name + ", age=" + age + ", sex="
				+ sex + ", pregnancy=" + pregnancy + ", pregnancys="
				+ pregnancys + ", gatherDate=" + gatherDate
				+ ", vaccinateDate=" + vaccinateDate + ", diagnose=" + diagnose
				+ ", suggestion=" + suggestion + ", caryogram=" + caryogram
				+ ", specimenSource=" + specimenSource + ", theProducers="
				+ theProducers + ", putAsealOn=" + putAsealOn
				+ ", examineAndNucleus=" + examineAndNucleus
				+ ", examineAndVerify=" + examineAndVerify + ", reportDate="
				+ reportDate + ", check=" + check + ", whole=" + whole
				+ ", cardOpinionStatus=" + cardOpinionStatus
				+ ", reportDateStatus=" + reportDateStatus + ", cardTxtStatus="
				+ cardTxtStatus + "]";
	}
}
