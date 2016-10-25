package com.test.Repository;

import com.test.domain.Publisher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by fantilong on 24/10/2016.
 */
@RepositoryRestResource
public interface PublisherRepository extends PagingAndSortingRepository<Publisher,Long> {

}
