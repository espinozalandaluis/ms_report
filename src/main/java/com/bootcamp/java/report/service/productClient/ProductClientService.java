package com.bootcamp.java.report.service.productClient;

import com.bootcamp.java.report.dto.ProductClientDTO;
import com.bootcamp.java.report.dto.ProductClientTransactionDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductClientService {

    public Mono<ProductClientDTO> create(ProductClientDTO productClientRequest);

}
