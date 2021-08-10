package com.jcsa.eqmd.bean;


public class Data {
    /**
     * 数据格式（SEED/SAC）
     */
    private String dataType;

    /**
     *获取波形起始时间
     */
    private String startTime;

    /**
     * 获取波形的结束时间
     */
    private  String endTime;

    /**
     * 文件保存目录
     */
    private String dirPath;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }
}
