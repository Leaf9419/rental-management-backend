package com.eeit205team5.rentalmanagement.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eeit205team5.rentalmanagement.booking.entity.Booking;
import com.eeit205team5.rentalmanagement.booking.service.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // 新增
    @PostMapping("/create")
    public Booking create(@RequestBody Booking booking) {
        return bookingService.create(booking);
    }

    // 修改
    @PutMapping("/{id}")
    public Booking modify(@PathVariable Long id, @RequestBody Booking booking) {
        return bookingService.modify(id, booking);
    }

    // 查詢全部
    @GetMapping("/findAll")
    public List<Booking> findAll() {
        return bookingService.findAll();
    }

    // 刪除
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookingService.delete(id);
    }

}
