package com.test.Repository;

import com.test.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by fantilong on 24/10/2016.
 */

@Repository
public interface BookRepository extends CrudRepository<Book,Long> {
    Book findAllByIsbn(String isbn);
    void deleteByIsbn(String isbn);
}
