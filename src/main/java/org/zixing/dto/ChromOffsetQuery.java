package org.zixing.dto;

/**
 * 染色单体原始灰底图和相对位置坐标类
 * */
public class ChromOffsetQuery {
	private String offset;
	private String url;
	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "ChromOffsetQuery [offset=" + offset + ", url=" + url + "]";
	}
	
}
