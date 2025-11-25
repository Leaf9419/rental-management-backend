package com.eeit205team5.rentalmanagement.tenancyagreement.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tenancy_agreement_requests")
public class TenancyAgreementRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tenancy_agreement_request_id")
    private Long tenancyAgreementRequestId;

    @Column(name = "tenancy_agreement_id")
    private Long tenancyAgreementId;

    @Column(name = "request_type")
    private String requestType;

    @Column(name = "incurred_costs")
    private Long incurredCosts;

    @Column(name = "apply_by")
    private Long applyBy;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "handle_by")
    private Long handleBy;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @Column(name = "request_status")
    private String requestStatus;

    public Long getTenancyAgreementRequestId() {
        return tenancyAgreementRequestId;
    }

    public void setTenancyAgreementRequestId(Long tenancyAgreementRequestId) {
        this.tenancyAgreementRequestId = tenancyAgreementRequestId;
    }

    public Long getTenancyAgreementId() {
        return tenancyAgreementId;
    }

    public void setTenancyAgreementId(Long tenancyAgreementId) {
        this.tenancyAgreementId = tenancyAgreementId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public Long getIncurredCosts() {
        return incurredCosts;
    }

    public void setIncurredCosts(Long incurredCosts) {
        this.incurredCosts = incurredCosts;
    }

    public Long getApplyBy() {
        return applyBy;
    }

    public void setApplyBy(Long applyBy) {
        this.applyBy = applyBy;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getHandleBy() {
        return handleBy;
    }

    public void setHandleBy(Long handleBy) {
        this.handleBy = handleBy;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }
}
