package com.bootcamp.java.report.service.transaction;

import com.bootcamp.java.report.common.exceptionHandler.FunctionalException;
import com.bootcamp.java.report.converter.ProductClientConvert;
import com.bootcamp.java.report.converter.TransactionConvert;
import com.bootcamp.java.report.dto.ProductClientReportDTO;
import com.bootcamp.java.report.dto.TransactionDTO;
import com.bootcamp.java.report.entity.Transaction;
import com.bootcamp.java.report.repository.ProductClientRepository;
import com.bootcamp.java.report.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private ProductClientRepository productClientRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    TransactionConvert transactionConverter;

    @Autowired
    ProductClientConvert productClientConvert;

    @Override
    public Mono<TransactionDTO> register(TransactionDTO transactionDTO) {

        log.info("Registrar transaction en BD");
        return transactionRepository.save(TransactionConvert.DTOtoEntity(transactionDTO))
                .map(trx -> TransactionConvert.EntityToDTO(trx));

        //return Mono.just(transactionDTO);
    }

    @Override
    public Flux<ProductClientReportDTO> findByDocumentNumber(String documentNumber) {
        return productClientRepository.findByDocumentNumber(documentNumber)
                .flatMap(prodCli -> {
                    var data = transactionRepository.findByIdProductClient(prodCli.getId())
                            .collectList()
                            .map(transactions -> ProductClientReportDTO.from(prodCli, transactions));
                    return data;
                }).switchIfEmpty(Mono.error(() -> new FunctionalException("No se encontraron registros de productos afiliados")));
    }

    @Override
    public Flux<TransactionDTO> findAll() {
        log.debug("findAll executing");
        Flux<TransactionDTO> dataTransactionDTO = transactionRepository.findAll()
                .map(TransactionConvert::EntityToDTO);
        return dataTransactionDTO;
    }
}
