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
                .switchIfEmpty(productClientRepository.save(ProductClientConvert.DTOToEntity(productClientDTO)))
                .flatMap(productClient -> {
                    //Actualizar Balance
                    productClient.setBalance(productClientDTO.getBalance());
                    return productClientRepository.save(productClient)
                            .map(prdcli -> ProductClientConvert.EntityToDTO(prdcli));
                });

        //return Mono.just(productClientDTO);
    }
}
