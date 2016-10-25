package com.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.sun.tools.corba.se.idl.StringGen;
import com.test.domain.Author;
import com.test.domain.Book;
import com.test.domain.Publisher;
import com.test.utils.Isbn;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by fantilong on 24/10/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookControllerTest {

    @Autowired
    private BookController bookController;

    @Test
    public void addBookTest(){
        Book book = new Book("12","大数据",new Author("范","体龙"),new Publisher("三元里出版社"));
        JSONObject bookJson = (JSONObject) JSONObject.toJSON(book);
        HashMap<String,Object> hashMap = new HashMap(bookController.addBook(bookJson));
        ArrayList arrayList = new ArrayList(hashMap.values());
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(arrayList.get(i));
        }
    }

    @Test
    public void getBookTest(){
        String isbn = "12";
        HashMap<String,Object> hashMap = new HashMap(bookController.getBook(new Isbn(isbn)));
        ArrayList arrayList = new ArrayList(hashMap.values());
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(arrayList.get(i).toString());
        }
    }

    @Test
    public void updataTitleTest(){
        String title = "小数据";
        HashMap<String,Object> hashMap = new HashMap(bookController.updataTitle(new Isbn("12"),title));
        ArrayList arrayList = new ArrayList(hashMap.values());
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(arrayList.get(i).toString());
        }
    }

    @Test
    public void getAllBooks() throws Exception {
        Iterable<Book> iterable = bookController.getAllBooks();

        Iterator<Book> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            Book next = iterator.next();
            System.out.println(next);
        }
    }

    @Test
    public void deleteBookTest(){
        HashMap<String,Object> hashMap = new HashMap(bookController.deleteBook(new Isbn("9781-1234-1111")));
        ArrayList arrayList = new ArrayList(hashMap.values());
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(arrayList.get(i));
        }
    }
}















