package com.lc.misc.usualTest;



/**
 * 中心产能因子
 *
 * @author luochao
 * @version 1.0
 * @since 2020-08-03 11:30
 */
public class CenterFactor   {
    /**
     * 省区id
     */
    private Long zoneId;

    /**
     * 区域id
     */
    private Long regionId;

    /**
     * 中心id
     */
    private Long centerId;

    /**
     * 因子key
     */
    private String factorKey;

    /**
     * 因子值
     */
    private String factorValue;

    /**
     * 因子名称
     */
    private String factorName;

    /**
     * 因子场景
     */
    private String factorOrigin;

    /**
     * 版本
     */
    private Integer version;

    /**
     * creator
     */
    private String creator;

    /**
     * 修改人
     */
    private String modifier;


    /**
     * 出港/进港
     */
        private String belongOperationType;

    /**
     * 因子类型
     */
    private String factorType;

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Long getCenterId() {
        return centerId;
    }

    public void setCenterId(Long centerId) {
        this.centerId = centerId;
    }

    public String getFactorKey() {
        return factorKey;
    }

    public void setFactorKey(String factorKey) {
        this.factorKey = factorKey;
    }

    public String getFactorValue() {
        return factorValue;
    }

    public void setFactorValue(String factorValue) {
        this.factorValue = factorValue;
    }

    public String getFactorName() {
        return factorName;
    }

    public void setFactorName(String factorName) {
        this.factorName = factorName;
    }

    public String getFactorOrigin() {
        return factorOrigin;
    }

    public void setFactorOrigin(String factorOrigin) {
        this.factorOrigin = factorOrigin;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getBelongOperationType() {
        return belongOperationType;
    }

    public void setBelongOperationType(String belongOperationType) {
        this.belongOperationType = belongOperationType;
    }

    public String getFactorType() {
        return factorType;
    }

    public void setFactorType(String factorType) {
        this.factorType = factorType;
    }
}
