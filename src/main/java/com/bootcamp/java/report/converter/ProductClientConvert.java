package com.bootcamp.java.report.converter;


import com.bootcamp.java.report.dto.ProductClientDTO;
import com.bootcamp.java.report.entity.ProductClient;
import org.springframework.stereotype.Component;

@Component
public class ProductClientConvert {
    public static ProductClientDTO EntityToDTO(ProductClient productClient) {
        return ProductClientDTO.builder()
                .id(productClient.getId())
                .idProduct(productClient.getIdProduct())
                .productDescription(productClient.getProductDescription())
                .idProductType(productClient.getIdProductType())
                .productTypeDescription(productClient.getProductTypeDescription())
                .idProductSubType(productClient.getIdProductSubType())
                .productSubTypeDescription(productClient.getProductSubTypeDescription())
                .idClient(productClient.getIdClient())
                .idClientType(productClient.getIdClientType())
                .clientTypeDescription(productClient.getClientTypeDescription())
                .idClientDocumentType(productClient.getIdClientDocumentType())
                .clientDocumentTypeDescription(productClient.getClientDocumentTypeDescription())
                .documentNumber(productClient.getDocumentNumber())
                .fullName(productClient.getFullName())
                .authorizedSigners(productClient.getAuthorizedSigners())
                .creditLimit(productClient.getCreditLimit())
                .balance(productClient.getBalance())
                .debt(productClient.getDebt())
                .maintenanceCost(productClient.getMaintenanceCost())
                .movementLimit(productClient.getMovementLimit())
                .credits(productClient.getCredits())
                .accountNumber(productClient.getAccountNumber())
                .transactionFee(productClient.getTransactionFee())
                .creditCardNumber(productClient.getCreditCardNumber())
                .billingDay(productClient.getBillingDay())
                .billingDate(productClient.getBillingDate())
                .invoiceDebt(productClient.getInvoiceDebt())
                .expiredDebt(productClient.getExpiredDebt())
                .build();
    }


    public static ProductClient DTOToEntity(ProductClientDTO productClientDTO) {
        return ProductClient.builder()
                .idProduct(productClientDTO.getIdProduct())
                .productDescription(productClientDTO.getProductDescription())
                .idProductType(productClientDTO.getIdProductType())
                .productTypeDescription(productClientDTO.getProductTypeDescription())
                .idProductSubType(productClientDTO.getIdProductSubType())
                .productSubTypeDescription(productClientDTO.getProductSubTypeDescription())
                .idClient(productClientDTO.getIdClient())
                .idClientType(productClientDTO.getIdClientType())
                .clientTypeDescription(productClientDTO.getClientTypeDescription())
                .idClientDocumentType(productClientDTO.getIdClientDocumentType())
                .clientDocumentTypeDescription(productClientDTO.getClientDocumentTypeDescription())
                .documentNumber(productClientDTO.getDocumentNumber())
                .fullName(productClientDTO.getFullName())
                .authorizedSigners(productClientDTO.getAuthorizedSigners())
                .creditLimit(productClientDTO.getCreditLimit())
                .balance(productClientDTO.getBalance())
                .debt(productClientDTO.getDebt())
                .maintenanceCost(productClientDTO.getMaintenanceCost())
                .movementLimit(productClientDTO.getMovementLimit())
                .credits(productClientDTO.getCredits())
                .accountNumber(productClientDTO.getAccountNumber())
                .transactionFee(productClientDTO.getTransactionFee())
                .creditCardNumber(productClientDTO.getCreditCardNumber())
                .build();
    }


}
