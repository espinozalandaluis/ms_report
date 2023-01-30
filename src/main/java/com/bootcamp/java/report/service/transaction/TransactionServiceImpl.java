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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
    public Flux<ProductClientReportDTO> findByReport(String documentNumber, Integer idProduct,
                                                     Integer initDate, Integer finalDate) {

        if (initDate < 100000 || initDate > 999999) {
            new FunctionalException("La fecha inicial no es valida");
        }

        if (finalDate < 100000 || finalDate > 999999 || initDate > finalDate) {
            new FunctionalException("La fecha final no es valida");
        }

        Calendar result = Calendar.getInstance();

        int intYear = initDate/10000;
        int intMonth = (initDate/100)%100;
        int intDay = initDate % 100;

        result.set(Calendar.YEAR, intYear);
        result.set(Calendar.MONTH, intMonth - 1);
        result.set(Calendar.DAY_OF_MONTH, intDay);
        result.set(Calendar.HOUR_OF_DAY, 0);
        result.set(Calendar.MINUTE, 0);
        result.set(Calendar.SECOND, 0);
        result.set(Calendar.MILLISECOND, 0);

        log.info("result.getTime {}",result.getTime());

        //Convertir integer a date YYYYMMDD
        Date initDate1 = result.getTime();

        int intYear2 = finalDate/10000;
        int intMonth2 = (finalDate/100)%100;
        int intDay2 = finalDate % 100;

        result.set(Calendar.YEAR, intYear2);
        result.set(Calendar.MONTH, intMonth2 - 1);
        result.set(Calendar.DAY_OF_MONTH, intDay2);

        result.set(Calendar.HOUR_OF_DAY, 0);
        result.set(Calendar.MINUTE, 0);
        result.set(Calendar.SECOND, 0);
        result.set(Calendar.MILLISECOND, 0);

        Date finalDate1 = result.getTime();

        log.info("initDate1 {}",initDate1);
        log.info("finalDate1 {}",finalDate1);


        return productClientRepository.findProductsClient(initDate1, finalDate1, idProduct, documentNumber)
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
