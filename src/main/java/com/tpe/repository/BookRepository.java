package com.tpe.repository;

import com.tpe.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository// optional // okunabilirlik icin bu anatasyonu koyduk
public interface BookRepository extends JpaRepository<Book,Long> {


    //6-c
    List<Book> findByTitle(String title); // select * from t_book where title=?



    //findByAuthor
    @Query("SELECT b FROM Book b WHERE b.author=:yazar") // select * from t_book author=?
    List<Book> findByAuthorWithJPQL(@Param(("yazar")) String author);


    //ODEV: select * from t_book where author=? and publication_date=? veya JPQL
    // findByAuthorAndPublicationDate(author, pubDate);


}
