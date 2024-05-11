package com.tpe.repository;

import com.tpe.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository// optional // okunabilirlik icin bu anatasyonu koyduk
public interface BookRepository extends JpaRepository<Book,Long> {


    //6-c
    List<Book> findByTitle(String title); // select * from t_book where title=?

}
