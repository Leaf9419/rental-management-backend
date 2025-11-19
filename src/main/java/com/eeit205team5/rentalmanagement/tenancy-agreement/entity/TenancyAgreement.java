public class TenancyAgreement {

    private Long tenancyAgreementId;
    private Long lodgerId;
    private Long propertiesId;
    private Long landlordId;
    private Integer tenancyDuration;
    private Long tenancyRent;
    private Long tenancyDeposit;
    private LocalDate tenancyStartTime;
    private String tenancyEndTime;
    private LocalDateTime createTime;
    private String tenancyAgreementStatus;
    private LocalDateTime updateTime;
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
