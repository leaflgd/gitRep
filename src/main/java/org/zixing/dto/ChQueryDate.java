package org.zixing.dto;

public class ChQueryDate {
	//采集日期
	private String collectDate;
	//事件
	private String inName;
	//病人姓名
	private String patientName;
	//普通染色体核型
	private String chromCary;
	//报告打印人
	private String reportPrinter;
	//报告日期
	private String reportDate;
	//病例号
	private String caseNumber;
	//医生姓名
	private String userName;
	//用户工作量统计
	private String userWorkLoad;
	//阅片及报告打印人
	private String viewAndPrinter;
	//阅片及报告打印人的工作量统计
	private String viewAndPrinterCount;
	
	/*	9-30添加属性	*/
	//年龄
	private String patientAge;
	//性别
	private String patientSex;
	//临床诊断
	private String clinicalDiagnosis;
	//审核人
	private String xpr;
	//校片人
	private String auditor;

	public String getPatientSex() {
		return patientSex;
	}

	public ChQueryDate setPatientSex(String patientSex) {
		this.patientSex = patientSex;
		return this;
	}

	public String getClinicalDiagnosis() {
		return clinicalDiagnosis;
	}

	public ChQueryDate setClinicalDiagnosis(String clinicalDiagnosis) {
		this.clinicalDiagnosis = clinicalDiagnosis;
		return this;
	}

	public String getXpr() {
		return xpr;
	}

	public ChQueryDate setXpr(String xpr) {
		this.xpr = xpr;
		return this;
	}

	public String getAuditor() {
		return auditor;
	}

	public ChQueryDate setAuditor(String auditor) {
		this.auditor = auditor;
		return this;
	}
	
	public String getCollectDate() {
		return collectDate;
	}
	public void setCollectDate(String collectDate) {
		this.collectDate = collectDate;
	}
	public String getInName() {
		return inName;
	}
	public void setInName(String inName) {
		this.inName = inName;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getChromCary() {
		return chromCary;
	}
	public void setChromCary(String chromCary) {
		this.chromCary = chromCary;
	}
	public String getReportPrinter() {
		return reportPrinter;
	}
	public void setReportPrinter(String reportPrinter) {
		this.reportPrinter = reportPrinter;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	
	
	public String getCaseNumber() {
		return caseNumber;
	}
	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserWorkLoad() {
		return userWorkLoad;
	}
	public void setUserWorkLoad(String userWorkLoad) {
		this.userWorkLoad = userWorkLoad;
	}
	public String getViewAndPrinter() {
		return viewAndPrinter;
	}
	public void setViewAndPrinter(String viewAndPrinter) {
		this.viewAndPrinter = viewAndPrinter;
	}
	public String getViewAndPrinterCount() {
		return viewAndPrinterCount;
	}
	public void setViewAndPrinterCount(String viewAndPrinterCount) {
		this.viewAndPrinterCount = viewAndPrinterCount;
	}
	
	public String getPatientAge() {
		return patientAge;
	}

	public void setPatientAge(String patientAge) {
		this.patientAge = patientAge;
	}

	@Override
	public String toString() {
		return "ChQueryDate [collectDate=" + collectDate + ", inName=" + inName
				+ ", patientName=" + patientName + ", chromCary=" + chromCary
				+ ", reportPrinter=" + reportPrinter + ", reportDate="
				+ reportDate + ", caseNumber=" + caseNumber + ", userName="
				+ userName + ", userWorkLoad=" + userWorkLoad
				+ ", viewAndPrinter=" + viewAndPrinter
				+ ", viewAndPrinterCount=" + viewAndPrinterCount
				+ ", patientAge=" + patientAge + ", patientSex=" + patientSex
				+ ", clinicalDiagnosis=" + clinicalDiagnosis + ", xpr=" + xpr
				+ ", auditor=" + auditor + "]";
	}
	

	
	
}
