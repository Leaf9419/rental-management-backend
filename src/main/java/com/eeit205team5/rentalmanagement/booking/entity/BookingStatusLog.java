public class BookingStatusLog {

    private Long bookingStatusLogId;
    private Long bookingId;
    private String oldStatus;
    private String nowStatus;
    private LocalDateTime updateTime;
    private Long updateBy;

    public Long getBookingStatusLogId() {
        return bookingStatusLogId;
    }

    public void setBookingStatusLogId(Long bookingStatusLogId) {
        this.bookingStatusLogId = bookingStatusLogId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
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
}
