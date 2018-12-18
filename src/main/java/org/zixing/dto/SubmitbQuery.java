package org.zixing.dto;

public class SubmitbQuery {
	private String dialPieceId;
	private int status;
	public String getDialPieceId() {
		return dialPieceId;
	}
	public void setDialPieceId(String dialPieceId) {
		this.dialPieceId = dialPieceId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "SubmitbQuery [dialPieceId=" + dialPieceId + ", status="
				+ status + "]";
	}
}
