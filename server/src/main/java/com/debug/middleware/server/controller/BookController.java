package com.debug.middleware.server.controller;


import com.debug.middleware.server.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    @RequestMapping("/info")
    public Book info(Integer bookNo,String bookName){

        Book book = new Book();

        book.setBookNo(bookNo);
        book.setName(bookName);

        return book;
    }
}
