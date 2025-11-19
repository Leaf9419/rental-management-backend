public class BookablePeriod {

    private Long bookablePeriodsId;
    private Long landlordId;
    private LocalDate bookableDate;
    private LocalDateTime bookableStartTime;
    private LocalDateTime bookableEndTime;

    public Long getBookablePeriodsId() {
        return bookablePeriodsId;
    }

    public void setBookablePeriodsId(Long bookablePeriodsId) {
        this.bookablePeriodsId = bookablePeriodsId;
    }

    public Long getLandlordId() {
        return landlordId;
    }

    public void setLandlordId(Long landlordId) {
        this.landlordId = landlordId;
    }

    public LocalDate getBookableDate() {
        return bookableDate;
    }

    public void setBookableDate(LocalDate bookableDate) {
        this.bookableDate = bookableDate;
    }

    public LocalDateTime getBookableStartTime() {
        return bookableStartTime;
    }

    public void setBookableStartTime(LocalDateTime bookableStartTime) {
        this.bookableStartTime = bookableStartTime;
    }

    public LocalDateTime getBookableEndTime() {
        return bookableEndTime;
    }

    public void setBookableEndTime(LocalDateTime bookableEndTime) {
        this.bookableEndTime = bookableEndTime;
    }
}
