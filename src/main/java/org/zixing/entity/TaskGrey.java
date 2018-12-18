package org.zixing.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class TaskGrey implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 拨片id
     */
    private Integer dialpieceId;

    /**
     * 关联inf_task表
     */
    private Integer taskId;

    /**
     * 事件名
     */
    private String submitEventname;

    /**
     * 状态值0:初始化1：已完成2.失败3.重做
     */
    private Integer status;

    /**
     * 核对状态
     */
    private Integer submitCheckstatus;

    /**
     * 更新次数
     */
    private Integer count;

    /**
     * 类型1：分析2：计数
     */
    private String type;

    /**
     * 灰底图id
     */
    private Integer greybasemapId;

    /**
     * 备注
     */
    private String reamake;

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

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getSubmitEventname() {
        return submitEventname;
    }

    public void setSubmitEventname(String submitEventname) {
        this.submitEventname = submitEventname;
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

    public void setSubmitCheckstatus(Integer submitCheckstatus) {
        this.submitCheckstatus = submitCheckstatus;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getGreybasemapId() {
        return greybasemapId;
    }

    public void setGreybasemapId(Integer greybasemapId) {
        this.greybasemapId = greybasemapId;
    }

    public String getReamake() {
        return reamake;
    }

    public void setReamake(String reamake) {
        this.reamake = reamake;
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
        TaskGrey other = (TaskGrey) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDialpieceId() == null ? other.getDialpieceId() == null : this.getDialpieceId().equals(other.getDialpieceId()))
            && (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getSubmitEventname() == null ? other.getSubmitEventname() == null : this.getSubmitEventname().equals(other.getSubmitEventname()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getSubmitCheckstatus() == null ? other.getSubmitCheckstatus() == null : this.getSubmitCheckstatus().equals(other.getSubmitCheckstatus()))
            && (this.getCount() == null ? other.getCount() == null : this.getCount().equals(other.getCount()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getGreybasemapId() == null ? other.getGreybasemapId() == null : this.getGreybasemapId().equals(other.getGreybasemapId()))
            && (this.getReamake() == null ? other.getReamake() == null : this.getReamake().equals(other.getReamake()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDialpieceId() == null) ? 0 : getDialpieceId().hashCode());
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getSubmitEventname() == null) ? 0 : getSubmitEventname().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getSubmitCheckstatus() == null) ? 0 : getSubmitCheckstatus().hashCode());
        result = prime * result + ((getCount() == null) ? 0 : getCount().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getGreybasemapId() == null) ? 0 : getGreybasemapId().hashCode());
        result = prime * result + ((getReamake() == null) ? 0 : getReamake().hashCode());
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
        sb.append(", taskId=").append(taskId);
        sb.append(", submitEventname=").append(submitEventname);
        sb.append(", status=").append(status);
        sb.append(", submitCheckstatus=").append(submitCheckstatus);
        sb.append(", count=").append(count);
        sb.append(", type=").append(type);
        sb.append(", greybasemapId=").append(greybasemapId);
        sb.append(", reamake=").append(reamake);
        sb.append(", createDate=").append(createDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}