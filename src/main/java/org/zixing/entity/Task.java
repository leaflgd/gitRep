package org.zixing.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class Task implements Serializable {
    /**
     * 自增字段
     */
    private Integer id;

    /**
     * 拨片id
     */
    private Integer dialpieceId;

    /**
     * 状态值
     */
    private Integer status;

    /**
     * 核对状态
     */
    private Integer submitCheckstatus;

    /**
     * 用户名
     */
    private String submitUsername;

    /**
     * 拨片名
     */
    private String submitDialpiecename;

    /**
     * 创建时间
     */
    private String createDate;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDialpieceId() {
        return dialpieceId;
    }

    public void setDialpieceId(Integer dialpieceId) {
        this.dialpieceId = dialpieceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSubmitCheckstatus() {
        return submitCheckstatus;
    }

    public void setSubmitCheckstatus(int submitCheckstatus) {
        this.submitCheckstatus = submitCheckstatus;
    }

    public String getSubmitUsername() {
        return submitUsername;
    }

    public void setSubmitUsername(String submitUsername) {
        this.submitUsername = submitUsername;
    }

    public String getSubmitDialpiecename() {
        return submitDialpiecename;
    }

    public void setSubmitDialpiecename(String submitDialpiecename) {
        this.submitDialpiecename = submitDialpiecename;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Task other = (Task) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDialpieceId() == null ? other.getDialpieceId() == null : this.getDialpieceId().equals(other.getDialpieceId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getSubmitCheckstatus() == null ? other.getSubmitCheckstatus() == null : this.getSubmitCheckstatus().equals(other.getSubmitCheckstatus()))
            && (this.getSubmitUsername() == null ? other.getSubmitUsername() == null : this.getSubmitUsername().equals(other.getSubmitUsername()))
            && (this.getSubmitDialpiecename() == null ? other.getSubmitDialpiecename() == null : this.getSubmitDialpiecename().equals(other.getSubmitDialpiecename()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDialpieceId() == null) ? 0 : getDialpieceId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getSubmitCheckstatus() == null) ? 0 : getSubmitCheckstatus().hashCode());
        result = prime * result + ((getSubmitUsername() == null) ? 0 : getSubmitUsername().hashCode());
        result = prime * result + ((getSubmitDialpiecename() == null) ? 0 : getSubmitDialpiecename().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", dialpieceId=").append(dialpieceId);
        sb.append(", status=").append(status);
        sb.append(", submitCheckstatus=").append(submitCheckstatus);
        sb.append(", submitUsername=").append(submitUsername);
        sb.append(", submitDialpiecename=").append(submitDialpiecename);
        sb.append(", createDate=").append(createDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}