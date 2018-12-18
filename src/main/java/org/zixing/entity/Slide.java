package org.zixing.entity;

import java.util.List;

public class Slide {
	private int slideId;
	private String slideName;
	private Incident incident;
	private List<Gray> li;
	public int getSlideId() {
		return slideId;
	}
	public void setSlideId(int slideId) {
		this.slideId = slideId;
	}
	public String getSlidenName() {
		return slideName;
	}
	public void setSlidenName(String slideName) {
		this.slideName = slideName;
	}
	public Incident getIncident() {
		return incident;
	}
	public List<Gray> getLi() {
		return li;
	}
	public void setLi(List<Gray> li) {
		this.li = li;
	}

	public String toString() {
		return "Slide [slideId=" + slideId + ", slideName=" + slideName
				+ ", incident=" + incident + ", li=" + li + "]";
	}
	
}
