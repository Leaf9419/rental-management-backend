public class TenancyAgreementRequest {

    private Long tenancyAgreementRequestId;
    private Long tenancyAgreementId;
    private String requestType;
    private Long incurredCosts;
    private Long applyBy;
    private LocalDateTime createTime;
    private Long handleBy;
    private LocalDate effectiveDate;
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
