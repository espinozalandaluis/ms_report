package com.bootcamp.java.report.repository;

import com.bootcamp.java.report.entity.ProductClient;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Repository
public interface ProductClientRepository extends ReactiveMongoRepository<ProductClient,String> {
    Mono<ProductClient> findById(String IdProductClient);

    Flux<ProductClient> findByDocumentNumber(String DocumentNumber);

    Mono<ProductClient> findByAccountNumber(String AccountNumber);

    //@Query("{ 'registerDate' : { $gte: ?0, $lte: ?1}, 'idProduct' : ?2, 'documentNumber' : ?3 }")
    @Query("{'registerDate' : { $gte: ?0, $lte: ?1 }, 'documentNumber' : ?3, 'idProduct' : ?2 }")
    Flux<ProductClient> findProductsClient(Date initDate, Date finalDate,
                                           Integer idProduct,
                                           String documentNumber);
}
