package org.zixing.dto;

/**
 * @Author: xzl
 * @Date: 2018/11/8 15:37
 * @Description:
 */
public class GreyBaseMap {

    private int grayId;
    private String inName;
    private String slideName;
    private String grayNumber;
    private String grayUrl;
    private String greyBaseMapAllChromosome;
    private String grayDate;
    private String grayCray;
    private boolean analyCheck;
    private boolean countCheck;
    private boolean printReport;
    private int greyBaseMapTag;
    
    public int getGreyBaseMapTag() {
		return greyBaseMapTag;
	}

	public void setGreyBaseMapTag(int greyBaseMapTag) {
		this.greyBaseMapTag = greyBaseMapTag;
	}

	public boolean isPrintReport() {
		return printReport;
	}

	public void setPrintReport(boolean printReport) {
		this.printReport = printReport;
	}

	public int getGrayId() {
        return grayId;
    }

    public GreyBaseMap setGrayId(int grayId) {
        this.grayId = grayId;
        return this;
    }

    public String getInName() {
        return inName;
    }

    public GreyBaseMap setInName(String inName) {
        this.inName = inName;
        return this;
    }

    public String getSlideName() {
        return slideName;
    }

    public GreyBaseMap setSlideName(String slideName) {
        this.slideName = slideName;
        return this;
    }

    public String getGrayNumber() {
        return grayNumber;
    }

    public GreyBaseMap setGrayNumber(String grayNumber) {
        this.grayNumber = grayNumber;
        return this;
    }

    public String getGrayUrl() {
        return grayUrl;
    }

    public GreyBaseMap setGrayUrl(String grayUrl) {
        this.grayUrl = grayUrl;
        return this;
    }

    public String getGreyBaseMapAllChromosome() {
        return greyBaseMapAllChromosome;
    }

    public GreyBaseMap setGreyBaseMapAllChromosome(String greyBaseMapAllChromosome) {
        this.greyBaseMapAllChromosome = greyBaseMapAllChromosome;
        return this;
    }

    public String getGrayDate() {
        return grayDate;
    }

    public GreyBaseMap setGrayDate(String grayDate) {
        this.grayDate = grayDate;
        return this;
    }

    public String getGrayCray() {
        return grayCray;
    }

    public GreyBaseMap setGrayCray(String grayCray) {
        this.grayCray = grayCray;
        return this;
    }

    public boolean isAnalyCheck() {
        return analyCheck;
    }

    public GreyBaseMap setAnalyCheck(boolean analyCheck) {
        this.analyCheck = analyCheck;
        return this;
    }

    public boolean isCountCheck() {
        return countCheck;
    }

    public GreyBaseMap setCountCheck(boolean countCheck) {
        this.countCheck = countCheck;
        return this;
    }
}
