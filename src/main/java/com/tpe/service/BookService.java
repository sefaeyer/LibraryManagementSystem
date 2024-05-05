package com.tpe.service;

import com.tpe.domain.Book;
import com.tpe.exceptions.BookNotFoundException;
import com.tpe.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;


    //1-b
    public void saveBook(Book book) {

        //kontrol etmemiz gereken bir durum var mi?
        bookRepository.save(book);

    }

    //2-b
    public List<Book> getAll() {
        return bookRepository.findAll();
    }


    //3-b
    public Book getBookById(Long identity) {

        //kontrol etmemiz gereken bir durum var mi?
        return bookRepository.findById(identity).
                orElseThrow(()->new BookNotFoundException("Kitap bulunamadi, ID : "+identity));

    }


    //4-b
    public void deleteBookById(Long id) {

        //kontrol etmemiz gereken bir durum var mi?

        //bu id ye ait bir kitap var mi
        Book book= getBookById(id);
        //bookRepository.delete(book);
        bookRepository.deleteById(id);

    }
}
