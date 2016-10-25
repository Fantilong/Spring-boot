package com.test.Repository;

import com.test.domain.Author;
import com.test.domain.Publisher;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by fantilong on 25/10/2016.
 */
@RepositoryRestResource
public interface ReviewerRepository extends PagingAndSortingRepository<Publisher.Reviewer,Long> {

}
