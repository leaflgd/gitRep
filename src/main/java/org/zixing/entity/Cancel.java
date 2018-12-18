package org.zixing.entity;

public class Cancel {
	private String chromId;
	private String offset;
	private String url;
	private int num;
	private int status;
	public String getChromId() {
		return chromId;
	}
	public void setChromId(String chromId) {
		this.chromId = chromId;
	}
	public String getOffset() {
		return offset;
	}
	public void setOffset(String offset) {
		this.offset = offset;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public Cancel(){
		super();
	}
	
	public Cancel(String chromId, String offset, int num, int status) {
		super();
		this.chromId = chromId;
		this.offset = offset;
		this.num = num;
		this.status = status;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return "Cancel [chromId=" + chromId + ", offset=" + offset + ", url="
				+ url + ", num=" + num + ", status=" + status + "]";
	}
}
