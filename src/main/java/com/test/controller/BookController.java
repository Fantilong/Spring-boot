package com.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.test.Repository.AuthorRepository;
import com.test.Repository.BookRepository;
import com.test.Repository.PublisherRepository;
import com.test.domain.Author;
import com.test.domain.Book;
import com.test.domain.Publisher;
import com.test.utils.Isbn;
import com.test.utils.IsbnEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by fantilong on 24/10/2016.
 */
@RestController
@RequestMapping("/books")
public class BookController {

    protected final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PublisherRepository publisherRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    @RequestMapping(value = "/{isbn}",method = RequestMethod.GET)
    public Map<String,Object> getBook(@PathVariable Isbn isbn){
        Book book = bookRepository.findAllByIsbn(isbn.getIsbn());

        Map<String,Object> response = new LinkedHashMap<>();
        response.put("massage","get book with isbn(" +  isbn.getIsbn() + ")");
        response.put("book",book);
        return response;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String,Object> addBook(@RequestBody JSONObject bookJson){
        JSONObject authorJson = bookJson.getJSONObject("author");
        Author author = new Author(authorJson.getString("firstName"),authorJson.getString("lastName"));
        authorRepository.save(author);
        Isbn isbn = new Isbn(bookJson.getString("isbn"));
        JSONObject publisherJson = bookJson.getJSONObject("publisher");
        Publisher publisher = new Publisher(publisherJson.getString("name"));
        publisherRepository.save(publisher);
        String title = bookJson.getString("title");
        String description = bookJson.getString("description");
        Book book = new Book(isbn.getIsbn(),title,author,publisher);
        book.setDescription(description);
        bookRepository.save(book);

        Map<String,Object> response = new LinkedHashMap<>();
        response.put("message","book add successfully");
        response.put("book",book);
        return response;
    }

    @RequestMapping(value = "/{isbn}",method = RequestMethod.DELETE)
    public Map<String,Object> deleteBook(@PathVariable Isbn isbn){
        Map<String,Object> response = new LinkedHashMap<>();
        try {
            bookRepository.delete(bookRepository.findAllByIsbn(isbn.getIsbn()));
        } catch (NullPointerException e) {
            logger.info("the book is not in database");
            response.put("massage","delete failure");
            response.put("code",0);
        }

        response.put("massage","delete successfully");
        response.put("code",1);
        return response;
    }

    @RequestMapping(value = "/{isbn}/{title}",method = RequestMethod.PUT)
    public Map<String,Object> updataTitle(@PathVariable Isbn isbn,@PathVariable String title){
        Map<String,Object> response = new LinkedHashMap<>();
        Book book = null;
        try {
            book = bookRepository.findAllByIsbn(isbn.getIsbn());
            book.setTitle(title);
            bookRepository.save(book);
        } catch (NullPointerException e) {
            response.put("message","can not find the book");
            return response;
        }

        response.put("message","book updata successfully");
        return response;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Isbn.class,new IsbnEditor());
    }


}











