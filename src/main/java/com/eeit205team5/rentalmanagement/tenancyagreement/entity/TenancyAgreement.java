package com.eeit205team5.rentalmanagement.tenancyagreement.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tenancy_agreements")
public class TenancyAgreement {

    @Id
    @Column(name = "tenancy_agreement_id")
    private Long tenancyAgreementId;

    @Column(name = "lodger_id")
    private Long lodgerId;

    @Column(name = "properties_id")
    private Long propertiesId;

    @Column(name = "landlord_id")
    private Long landlordId;

    @Column(name = "tenancy_duration")
    private Integer tenancyDuration;

    @Column(name = "tenancy_rent")
    private Long tenancyRent;

    @Column(name = "tenancy_deposit")
    private Long tenancyDeposit;

    @Column(name = "tenancy_start_time")
    private LocalDate tenancyStartTime;

    @Column(name = "tenancy_end_time")
    private String tenancyEndTime;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "tenancy_agreement_status")
    private String tenancyAgreementStatus;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "file_path")
    private String filePath;

    public Long getTenancyAgreementId() {
        return tenancyAgreementId;
    }

    public void setTenancyAgreementId(Long tenancyAgreementId) {
        this.tenancyAgreementId = tenancyAgreementId;
    }

    public Long getLodgerId() {
        return lodgerId;
    }

    public void setLodgerId(Long lodgerId) {
        this.lodgerId = lodgerId;
    }

    public Long getPropertiesId() {
        return propertiesId;
    }

    public void setPropertiesId(Long propertiesId) {
        this.propertiesId = propertiesId;
    }

    public Long getLandlordId() {
        return landlordId;
    }

    public void setLandlordId(Long landlordId) {
        this.landlordId = landlordId;
    }

    public Integer getTenancyDuration() {
        return tenancyDuration;
    }

    public void setTenancyDuration(Integer tenancyDuration) {
        this.tenancyDuration = tenancyDuration;
    }

    public Long getTenancyRent() {
        return tenancyRent;
    }

    public void setTenancyRent(Long tenancyRent) {
        this.tenancyRent = tenancyRent;
    }

    public Long getTenancyDeposit() {
        return tenancyDeposit;
    }

    public void setTenancyDeposit(Long tenancyDeposit) {
        this.tenancyDeposit = tenancyDeposit;
    }

    public LocalDate getTenancyStartTime() {
        return tenancyStartTime;
    }

    public void setTenancyStartTime(LocalDate tenancyStartTime) {
        this.tenancyStartTime = tenancyStartTime;
    }

    public String getTenancyEndTime() {
        return tenancyEndTime;
    }

    public void setTenancyEndTime(String tenancyEndTime) {
        this.tenancyEndTime = tenancyEndTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getTenancyAgreementStatus() {
        return tenancyAgreementStatus;
    }

    public void setTenancyAgreementStatus(String tenancyAgreementStatus) {
        this.tenancyAgreementStatus = tenancyAgreementStatus;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
