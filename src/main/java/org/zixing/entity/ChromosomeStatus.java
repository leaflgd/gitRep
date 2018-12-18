package org.zixing.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class ChromosomeStatus implements Serializable {
    /**
     * 主键
     */
    private Integer statusId;

    /**
     * grayId
     */
    private Integer grayId;

    /**
     * 放大标记
     */
    private Integer magnifyTag;

    /**
     * 缩小标记
     */
    private Integer shrinkTag;

    /**
     * 颜色标记
     */
    private Integer colorTag;

    /**
     * 操作医生ID（暂时不用）
     */
    private Integer userId;

    private static final long serialVersionUID = 1L;

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getGrayId() {
		return grayId;
	}

	public void setGrayId(Integer grayId) {
		this.grayId = grayId;
	}

	public Integer getMagnifyTag() {
        return magnifyTag;
    }

    public void setMagnifyTag(Integer magnifyTag) {
        this.magnifyTag = magnifyTag;
    }

    public Integer getShrinkTag() {
        return shrinkTag;
    }

    public void setShrinkTag(Integer shrinkTag) {
        this.shrinkTag = shrinkTag;
    }

    public Integer getColorTag() {
        return colorTag;
    }

    public void setColorTag(Integer colorTag) {
        this.colorTag = colorTag;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
        ChromosomeStatus other = (ChromosomeStatus) that;
        return (this.getStatusId() == null ? other.getStatusId() == null : this.getStatusId().equals(other.getStatusId()))
            && (this.getGrayId() == null ? other.getGrayId() == null : this.getGrayId().equals(other.getGrayId()))
            && (this.getMagnifyTag() == null ? other.getMagnifyTag() == null : this.getMagnifyTag().equals(other.getMagnifyTag()))
            && (this.getShrinkTag() == null ? other.getShrinkTag() == null : this.getShrinkTag().equals(other.getShrinkTag()))
            && (this.getColorTag() == null ? other.getColorTag() == null : this.getColorTag().equals(other.getColorTag()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getStatusId() == null) ? 0 : getStatusId().hashCode());
        result = prime * result + ((getGrayId() == null) ? 0 : getGrayId().hashCode());
        result = prime * result + ((getMagnifyTag() == null) ? 0 : getMagnifyTag().hashCode());
        result = prime * result + ((getShrinkTag() == null) ? 0 : getShrinkTag().hashCode());
        result = prime * result + ((getColorTag() == null) ? 0 : getColorTag().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", statusId=").append(statusId);
        sb.append(", grayId=").append(grayId);
        sb.append(", magnifyTag=").append(magnifyTag);
        sb.append(", shrinkTag=").append(shrinkTag);
        sb.append(", colorTag=").append(colorTag);
        sb.append(", userId=").append(userId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

	public ChromosomeStatus(Integer statusId, Integer grayId,
			Integer magnifyTag, Integer shrinkTag, Integer colorTag,
			Integer userId) {
		super();
		this.statusId = statusId;
		this.grayId = grayId;
		this.magnifyTag = magnifyTag;
		this.shrinkTag = shrinkTag;
		this.colorTag = colorTag;
		this.userId = userId;
	}

	public ChromosomeStatus() {
		super();
	}
    
    
}