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

import com.eeit205team5.rentalmanagement.booking.entity.BookablePeriod;
import com.eeit205team5.rentalmanagement.booking.service.BookablePeriodService;

@RestController
@RequestMapping("/bookingpreriod")
public class BookablePeriodController {

    @Autowired
    private BookablePeriodService bookablePeriodService;

    // 新增
    @PostMapping("/create")
    public BookablePeriod create(@RequestBody BookablePeriod bookablePeriod) {
        return bookablePeriodService.create(bookablePeriod);
    }

    // 修改
    @PutMapping("/{id}")
    public BookablePeriod modify(@PathVariable Long id, @RequestBody BookablePeriod bookablePeriod) {
        return bookablePeriodService.modify(id, bookablePeriod);
    }

    // 查詢全部
    @GetMapping("/findAll")
    public List<BookablePeriod> findAll() {
        return bookablePeriodService.findAll();
    }

    // 刪除
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookablePeriodService.delete(id);
    }

}
