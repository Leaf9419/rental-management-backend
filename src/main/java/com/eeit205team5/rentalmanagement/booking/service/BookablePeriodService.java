package com.eeit205team5.rentalmanagement.booking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eeit205team5.rentalmanagement.booking.entity.BookablePeriod;
import com.eeit205team5.rentalmanagement.booking.repository.BookablePeriodRepository;

@Service
@Transactional
public class BookablePeriodService {

    @Autowired
    private BookablePeriodRepository bookablePeriodRepository;

    // 新增
    public BookablePeriod create(BookablePeriod bookablePeriod) {

        BookablePeriod insert = new BookablePeriod();
        insert.setLandlordId(bookablePeriod.getLandlordId());
        insert.setBookableDate(bookablePeriod.getBookableDate());
        insert.setBookableStartTime(bookablePeriod.getBookableStartTime());
        insert.setBookableEndTime(bookablePeriod.getBookableEndTime());
        return bookablePeriodRepository.save(bookablePeriod);
    }

    // 修改
    public BookablePeriod modify(Long id, BookablePeriod bookablePeriod) {
        Optional<BookablePeriod> optional = bookablePeriodRepository.findById(id);
        if (optional.isPresent()) {
            BookablePeriod update = optional.get();
            update.setBookableDate(bookablePeriod.getBookableDate());
            update.setBookableStartTime(bookablePeriod.getBookableStartTime());
            update.setBookableEndTime(bookablePeriod.getBookableEndTime());
            return bookablePeriodRepository.save(update);
        }
        return null;
    }

    // 查詢全部
    public List<BookablePeriod> findAll() {
        return bookablePeriodRepository.findAll();
    }

    // 刪除單筆
    public void delete(Long id) {
        bookablePeriodRepository.deleteById(id);
    }

}
