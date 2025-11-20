package com.eeit205team5.rentalmanagement.booking.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eeit205team5.rentalmanagement.booking.entity.BookingStatusLog;
import com.eeit205team5.rentalmanagement.booking.repository.BookingStatusLogRepository;

@Service
@Transactional
public class BookingStatusLogService {

    @Autowired
    private BookingStatusLogRepository bookingStatusLogRepository;

    // 新增
    public BookingStatusLog create(BookingStatusLog bookingStatusLog) {
        BookingStatusLog insert = new BookingStatusLog();
        insert.setOldStatus(bookingStatusLog.getOldStatus());
        insert.setNowStatus(bookingStatusLog.getNowStatus());
        insert.setUpdateTime(LocalDateTime.now());
        insert.setUpdateBy(bookingStatusLog.getUpdateBy());
        return bookingStatusLogRepository.save(bookingStatusLog);
    }
}
