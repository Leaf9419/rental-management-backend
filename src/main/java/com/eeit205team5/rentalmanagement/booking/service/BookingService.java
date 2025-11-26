package com.eeit205team5.rentalmanagement.booking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeit205team5.rentalmanagement.booking.entity.Booking;
import com.eeit205team5.rentalmanagement.booking.repository.BookingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    // 新增
    public Booking create(Booking booking) {

        // 使用 Builder 建立新的 Booking 物件
        Booking insert = Booking.builder()
                .propertiesId(booking.getPropertiesId())
                .memberId(booking.getMemberId())
                .bookingDate(booking.getBookingDate())
                .bookingStartTime(booking.getBookingStartTime())
                .bookingEndTime(booking.getBookingEndTime())
                .remark(booking.getRemark())
                // bookingStatus 可以選擇使用預設值，也可以從傳入物件帶入
                .bookingStatus(booking.getBookingStatus() != null ? booking.getBookingStatus() : null)
                .build();

        return bookingRepository.save(insert);
    }

    // 修改
    public Booking modify(Long id, Booking booking) {

        Optional<Booking> optional = bookingRepository.findById(id);
        if (optional.isPresent()) {
            Booking update = optional.get();
            update.setBookingDate(booking.getBookingDate());
            update.setBookingStartTime(booking.getBookingStartTime());
            update.setBookingEndTime(booking.getBookingEndTime());
            update.setBookingStatus(booking.getBookingStatus());
            update.setUpdateTime(LocalDateTime.now());
            update.setRemark(booking.getRemark());
            return bookingRepository.save(booking);
        }
        return null;
    }

    // 查詢全部
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    // 刪除單筆
    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }

}
