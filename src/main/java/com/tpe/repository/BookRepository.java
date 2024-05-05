package com.tpe.repository;

import com.tpe.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository// optional // okunabilirlik icin bu anatasyonu koyduk
public interface BookRepository extends JpaRepository<Book,Long> {



}
