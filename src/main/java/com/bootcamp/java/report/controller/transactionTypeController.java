package com.bootcamp.java.report.controller;

import com.bootcamp.java.report.dto.TransactionTypeDTO;
import com.bootcamp.java.report.service.transactionType.TransactionTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/pasivoahorro/transactionType")
public class transactionTypeController {

    @Autowired
    private TransactionTypeService transactionTypeService;

    @GetMapping()
    public Mono<ResponseEntity<Flux<TransactionTypeDTO>>> getAll(){
        log.info("getAll executed");
        return Mono.just(ResponseEntity.ok()
                .body(transactionTypeService.findAll()));
    }

    @GetMapping("/{idTransactionType}")
    public Mono<ResponseEntity<TransactionTypeDTO>> getById(@PathVariable Integer idTransactionType){
        log.info("getById executed {}", idTransactionType);
        return transactionTypeService.findById(idTransactionType)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

}
