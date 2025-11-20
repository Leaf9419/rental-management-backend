package com.eeit205team5.rentalmanagement.tenancyagreement.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tenancy_agreement_logs")
public class TenancyAgreementLog {

    @Id
    @Column(name = "tenancy_agreement_status_id")
    private Long tenancyAgreementStatusId;

    @Column(name = "tenancy_agreement_id")
    private Long tenancyAgreementId;

    @Column(name = "old_status")
    private String oldStatus;

    @Column(name = "now_status")
    private String nowStatus;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "update_by")
    private Long updateBy;

    @Column(name = "remark")
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
