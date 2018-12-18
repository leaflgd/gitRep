package org.zixing.util.his.call;

public class BaseInfoEntity {

	/**
	 * 染色体编号
	 */
	private String bh;

	/**
	 * 病历号
	 */
	private String pid;

	/**
	 * 姓名
	 */
	private String xm;

	/**
	 * 出生日期
	 */
	private String csrq;

	/**
	 * 性别
	 */
	private String xb;

	/**
	 * 开单日期
	 */
	private String kfrq;

	/**
	 * 孕龄
	 */
	private String yl;

	/**
	 * 采集日期
	 */
	private String cjrq;



	/**
	 * @return the bh
	 */
	public String getBh() {
		return bh;
	}



	/**
	 * @param bh the bh to set
	 */
	public void setBh(String bh) {
		this.bh = bh;
	}



	/**
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}



	/**
	 * @param pid the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}



	/**
	 * @return the xm
	 */
	public String getXm() {
		return xm;
	}



	/**
	 * @param xm the xm to set
	 */
	public void setXm(String xm) {
		this.xm = xm;
	}



	/**
	 * @return the csrq
	 */
	public String getCsrq() {
		return csrq;
	}



	/**
	 * @param csrq the csrq to set
	 */
	public void setCsrq(String csrq) {
		this.csrq = csrq;
	}



	/**
	 * @return the xb
	 */
	public String getXb() {
		return xb;
	}



	/**
	 * @param xb the xb to set
	 */
	public void setXb(String xb) {
		this.xb = xb;
	}



	/**
	 * @return the kfrq
	 */
	public String getKfrq() {
		return kfrq;
	}



	/**
	 * @param kfrq the kfrq to set
	 */
	public void setKfrq(String kfrq) {
		this.kfrq = kfrq;
	}



	/**
	 * @return the yl
	 */
	public String getYl() {
		return yl;
	}



	/**
	 * @param yl the yl to set
	 */
	public void setYl(String yl) {
		this.yl = yl;
	}



	/**
	 * @return the cjrq
	 */
	public String getCjrq() {
		return cjrq;
	}



	/**
	 * @param cjrq the cjrq to set
	 */
	public void setCjrq(String cjrq) {
		this.cjrq = cjrq;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BaseInfoEntity [bh=" + bh + ", pid=" + pid + ", xm=" + xm
				+ ", csrq=" + csrq + ", xb=" + xb + ", kfrq=" + kfrq + ", yl="
				+ yl + ", cjrq=" + cjrq + "]";
	}



}
