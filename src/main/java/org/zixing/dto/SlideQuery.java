package org.zixing.dto;

import org.zixing.entity.Task;

public class SlideQuery {
	private int slideId;
	private String inName;
	private String slideName;
	private String count;
	private int status;
	private int checkCount;	//提交数量

	public int getCheckCount() {
		return checkCount;
	}

	public SlideQuery setCheckCount(int checkCount) {
		this.checkCount = checkCount;
		return this;
	}

	public int getSlideId() {
		return slideId;
	}
	public void setSlideId(int slideId) {
		this.slideId = slideId;
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
		return "SlideQuery [slideId=" + slideId + ", inName=" + inName
				+ ", slideName=" + slideName + ", count=" + count + ", status="
				+ status + "]";
	}
}
