public class SystemSetting {

    private Long systemSettingId;
    private String settingValue;
    private String description;
    private LocalDateTime updatedAt;

    public Long getSystemSettingId() {
        return systemSettingId;
    }

    public void setSystemSettingId(Long systemSettingId) {
        this.systemSettingId = systemSettingId;
    }

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
