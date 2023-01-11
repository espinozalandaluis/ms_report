package com.bootcamp.java.report.service.productClient;

import com.bootcamp.java.report.dto.ProductClientDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductClientService {

    public Mono<ProductClientDTO> create(ProductClientDTO productClientRequest);

    public Flux<ProductClientDTO> findAll();

    public Flux<ProductClientDTO> findByDocumentNumber(String DocumentNumber);

    public Mono<ProductClientDTO> findByAccountNumber(String AccountNumber);

}
