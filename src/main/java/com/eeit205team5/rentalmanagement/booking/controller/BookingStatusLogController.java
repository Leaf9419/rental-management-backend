package com.eeit205team5.rentalmanagement.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eeit205team5.rentalmanagement.booking.entity.BookingStatusLog;
import com.eeit205team5.rentalmanagement.booking.service.BookingStatusLogService;

@RestController
@RequestMapping("/bookinglog")
public class BookingStatusLogController {

    @Autowired
    private BookingStatusLogService bookingStatusLogService;

    // 新增
    @PostMapping("/create")
    public BookingStatusLog create(@RequestBody BookingStatusLog bookingStatusLog) {
        return bookingStatusLogService.create(bookingStatusLog);
    }
}
