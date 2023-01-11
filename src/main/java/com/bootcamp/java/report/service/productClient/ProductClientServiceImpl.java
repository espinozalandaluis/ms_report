package com.bootcamp.java.report.service.productClient;

import com.bootcamp.java.report.common.exceptionHandler.FunctionalException;
import com.bootcamp.java.report.converter.ProductClientConvert;
import com.bootcamp.java.report.dto.ProductClientDTO;
import com.bootcamp.java.report.repository.ProductClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductClientServiceImpl implements ProductClientService{

    @Autowired
    private ProductClientRepository productClientRepository;

    @Autowired
    ProductClientConvert productClientConvert;


    @Override
    public Mono<ProductClientDTO> create(ProductClientDTO productClientDTO) {
        log.info("Registrar ProductClientDTO en BD");

        return productClientRepository.findById(productClientDTO.getId())
                .flatMap(productClient -> {
                    //Actualizar Balance
                    log.info("Actualizar Balance: {}", productClient.toString());
                    productClient.setBalance(productClientDTO.getBalance());
                    return productClientRepository.save(productClient)
                            .map(prdcli -> ProductClientConvert.EntityToDTO(prdcli));
                })
                .switchIfEmpty(productClientRepository.save(ProductClientConvert.DTOToEntity(productClientDTO))
                        .map(ProductClientConvert::EntityToDTO));

        //return Mono.just(productClientDTO);
    }

    @Override
    public Flux<ProductClientDTO> findAll() {
        log.debug("findAll executing");
        Flux<ProductClientDTO> dataProductClientDTO = productClientRepository.findAll()
                .map(ProductClientConvert::EntityToDTO);
        return dataProductClientDTO;
    }

    @Override
    public Flux<ProductClientDTO> findByDocumentNumber(String DocumentNumber) {
        log.debug("findByDocumentNumber executing");
        Flux<ProductClientDTO> dataProductClientDTO = productClientRepository.findByDocumentNumber(DocumentNumber)
                .map(ProductClientConvert::EntityToDTO)
                .switchIfEmpty(Mono.error(() -> new FunctionalException("No se encontraron registros")));;
        return dataProductClientDTO;
    }

    @Override
    public Mono<ProductClientDTO> findByAccountNumber(String AccountNumber) {
        return productClientRepository.findByAccountNumber(AccountNumber)
                .map(ProductClientConvert::EntityToDTO)
                .switchIfEmpty(Mono.error(() -> new FunctionalException("No se encontraron registros")));
    }
}
