public class TenancyAgreementLog {

    private Long tenancyAgreementStatusId;
    private Long tenancyAgreementId;
    private String oldStatus;
    private String nowStatus;
    private LocalDateTime updateTime;
    private Long updateBy;
    private String remark;

    public Long getTenancyAgreementStatusId() {
        return tenancyAgreementStatusId;
    }

    public void setTenancyAgreementStatusId(Long tenancyAgreementStatusId) {
        this.tenancyAgreementStatusId = tenancyAgreementStatusId;
    }

    public Long getTenancyAgreementId() {
        return tenancyAgreementId;
    }

    public void setTenancyAgreementId(Long tenancyAgreementId) {
        this.tenancyAgreementId = tenancyAgreementId;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getNowStatus() {
        return nowStatus;
    }

    public void setNowStatus(String nowStatus) {
        this.nowStatus = nowStatus;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
