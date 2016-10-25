package com.test;

import com.test.Repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by fantilong on 24/10/2016.
 */
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {

    protected final Logger logger = LoggerFactory.getLogger(CommandLineRunner.class);

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void run(String... strings) throws Exception {
        logger.info("Number of books " + bookRepository.count());
    }
}
