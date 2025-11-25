package com.eeit205team5.rentalmanagement.booking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eeit205team5.rentalmanagement.booking.entity.Booking;
import com.eeit205team5.rentalmanagement.booking.repository.BookingRepository;

@Service
@Transactional
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    // 新增
    public Booking create(Booking booking) {

        Booking insert = new Booking();
        insert.setPropertiesId(booking.getPropertiesId());
        insert.setMemberId(booking.getMemberId());
        insert.setBookingDate(booking.getBookingDate());
        insert.setBookingStartTime(booking.getBookingStartTime());
        insert.setBookingEndTime(booking.getBookingEndTime());
        insert.setBookingStatus("預約成功"); 
        // pending / accepted / rejected / cancelled / expired
        insert.setCreateTime(LocalDateTime.now());
        insert.setRemark(booking.getRemark());
        return bookingRepository.save(booking);
    }

    // 修改
    public Booking modify(Long id, Booking booking) {

        Optional<Booking> optional = bookingRepository.findById(id);
        if (optional.isPresent()) {
            Booking update = optional.get();
            update.setBookingDate(booking.getBookingDate());
            update.setBookingStartTime(booking.getBookingStartTime());
            update.setBookingEndTime(booking.getBookingEndTime());
            update.setBookingStatus("修改預約成功");
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
