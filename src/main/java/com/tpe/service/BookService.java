package com.tpe.service;

import com.tpe.domain.Book;
import com.tpe.domain.Owner;
import com.tpe.dto.BookDTO;
import com.tpe.exceptions.BookNotFoundException;
import com.tpe.exceptions.ConflictException;
import com.tpe.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OwnerService ownerService;

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


    //6-b
    public List<Book> filterBookByTitle(String title) {

        return bookRepository.findByTitle(title);

    }


    //8-b
    public void updateBookById(Long id, BookDTO bookDTO) {

        Book existingBook = getBookById(id);

        //title,author,pdate
        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setAuthor(bookDTO.getAuthor());
        existingBook.setPublicationDate(bookDTO.getPublicationDate());

        bookRepository.save(existingBook); // save or update

    }


    //7-b
    public Page<Book> getAllBooksWithPage(Pageable pageable){

        return bookRepository.findAll(pageable);

    }


    //9-b
    public List<Book> getBooksByAuthor(String author) {

        List<Book> bookList=bookRepository.findByAuthorWithJPQL(author);

        if(bookList.isEmpty()){
            throw new BookNotFoundException("Yazara ait kitap bulunamadi.");
        }
        return bookList;
    }


    //10-b
    public void addBookToOwner(Long bookID, Long ownerID) {

        Book foundBook=getBookById(bookID);

        Owner foundOwner = ownerService.getOwnerById(ownerID);

        //belirtilen id ye sahip kitap daha once ownera verilmis mi
        //foundOwner.getBookList().contains(foundBook);
        boolean isBookExists = foundOwner.getBookList().stream().
                anyMatch(
                        book->book.getId().equals(bookID)
                ); // eslesme var mi

        if(isBookExists){
            throw new ConflictException("Bu kitap zaten uyenin listesinde var"+ownerID);
        } else if (foundBook.getOwner() != null) {
            throw new ConflictException("Bu kitap baska uyededir");
        }else{
            //kitabi ownera verebiliriz
            foundBook.setOwner(foundOwner);
            //foundOwner.getBookList().add(foundBook); //  ---->  mappedBy ile yapildi zaten. bu koda gerek yok
            bookRepository.save(foundBook); // kitabi repo da save etti
        }

    }
}
