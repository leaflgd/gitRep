package org.zixing.entity;

import java.util.List;

public class Incident {
	private int incidentId;
	private String incidentName;
	private boolean incidentCheck;
	//灰底图个数
	private String count;
	//时间是否提交完成
	private int status;
	private int submitCount;	//12-4提交总数

	public int getSubmitCount() {
		return submitCount;
	}

	public Incident setSubmitCount(int submitCount) {
		this.submitCount = submitCount;
		return this;
	}

	public int getIncidentId() {
		return incidentId;
	}
	public void setIncidentId(int incidentId) {
		this.incidentId = incidentId;
	}
	public String getIncidentName() {
		return incidentName;
	}
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}
	public boolean isIncidentCheck() {
		return incidentCheck;
	}
	public void setIncidentCheck(boolean incidentCheck) {
		this.incidentCheck = incidentCheck;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Incident [incidentId=" + incidentId + ", incidentName="
				+ incidentName + ", incidentCheck=" + incidentCheck
				+ ", count=" + count + ", status=" + status + "]";
	}
	
}
