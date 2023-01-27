package com.bootcamp.java.report.controller;

import com.bootcamp.java.report.dto.*;
import com.bootcamp.java.report.service.productClient.ProductClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/report")
public class productClientController {

    @Autowired
    private ProductClientService productClientService;

    @GetMapping()
    public Mono<ResponseEntity<Flux<ProductClientDTO>>> getAll(){
        log.info("getAll executed");
        return Mono.just(ResponseEntity.ok()
                .body(productClientService.findAll()));
    }

    @GetMapping("getByDocumentNumber/{documentNumber}")
    public Mono<ResponseEntity<Flux<ProductClientDTO>>> getByDocumentNumber(@PathVariable String documentNumber){
        log.info("getByDocumentNumber executed {}", documentNumber);
        return Mono.just(ResponseEntity.ok()
                .body(productClientService.findByDocumentNumber(documentNumber)));
    }

    @GetMapping("getByAccountNumber/{accountNumber}")
    public Mono<ResponseEntity<ProductClientDTO>> getByAccountNumber(@PathVariable String accountNumber){
        log.info("findByAccountNumber executed {}", accountNumber);
        return productClientService.findByAccountNumber(accountNumber)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

}
