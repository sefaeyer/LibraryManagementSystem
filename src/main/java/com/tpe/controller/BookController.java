package com.tpe.controller;

import com.tpe.domain.Book;
import com.tpe.dto.BookDTO;
import com.tpe.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController // Body,   springBoot da *->Controller yerine *->Restcontroller kullanilir
//@Controller // Model , View
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService service;


    //CREATE
    //1- Save a Book & Return : Message
    // http://localhost:8080/books + POST + JSON body
    @PostMapping
    public ResponseEntity<String> saveBook(@Valid @RequestBody Book book){

        service.saveBook(book);

        return new ResponseEntity<>("Kitap basariyla kaydedildi", HttpStatus.CREATED); //201
    }


    //READ
    //2- Get All Books
    // http://localhost:8080/books + GET
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> bookList= service.getAll();
        return ResponseEntity.ok(bookList);//200
    }


    //3- Get a Book by its ID Return:Book
    // http://localhost:8080/books/2 + GET
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long identity){

        Book found= service.getBookById(identity);

            return new ResponseEntity<>(found, HttpStatus.OK);//200

    }


    //4- Delete a Book by its ID, Return : message
    // http://localhost:8080/books/2 + DELETE
    @DeleteMapping("/{no}")
    public ResponseEntity<String> deleteBook(@PathVariable("no") Long id){

        service.deleteBookById(id);

        return ResponseEntity.ok("Kitap basariyla silindi. ID : "+id);
    }


    //5- Get a Book by its ID with RequestParam, Return: Book
    // http://localhost:8080/books/q?id=2 + GET
    @GetMapping("/q")
    public ResponseEntity<Book> getBookByIdWithQuery(@RequestParam("id") Long identity){

        Book book = service.getBookById(identity);

        return ResponseEntity.ok(book); // 200
    }

    //6- Get a Book by its Title with RequestParam
    //http://localhost:8080/books/search?title=Suc ve Ceza + GET
    @GetMapping("/search")
    public ResponseEntity<List<Book>> filterBooksByTitle(@RequestParam("title") String title){

        List<Book> books=service.filterBookByTitle(title);

        return ResponseEntity.ok(books); // 200
    }



    //8- Update a Book With Using DTO
    // http://localhost:8080/books/update/2 + PUT
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBook(@PathVariable("id") Long id,@Valid @RequestBody BookDTO bookDTO){

        service.updateBookById(id,bookDTO);

        return ResponseEntity.ok(""+id);

    }



}
