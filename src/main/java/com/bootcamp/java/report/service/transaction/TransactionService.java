package com.bootcamp.java.report.service.transaction;

import com.bootcamp.java.report.dto.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

    public Mono<TransactionDTO> register(TransactionDTO transactionDTO);

    public Flux<ProductClientReportDTO> findByDocumentNumber(String documentNumber);

    public Flux<TransactionDTO> findAll();

}
