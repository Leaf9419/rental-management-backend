public class Bookings {

    private Long bookingId;
    private Long propertiesId;
    private Long memberId;
    private LocalDate bookingDate;
    private LocalTime bookingStartTime;
    private LocalTime bookingEndTime;
    private String bookingStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String remark;

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getPropertiesId() {
        return propertiesId;
    }

    public void setPropertiesId(Long propertiesId) {
        this.propertiesId = propertiesId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalTime getBookingStartTime() {
        return bookingStartTime;
    }

    public void setBookingStartTime(LocalTime bookingStartTime) {
        this.bookingStartTime = bookingStartTime;
    }

    public LocalTime getBookingEndTime() {
        return bookingEndTime;
    }

    public void setBookingEndTime(LocalTime bookingEndTime) {
        this.bookingEndTime = bookingEndTime;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
