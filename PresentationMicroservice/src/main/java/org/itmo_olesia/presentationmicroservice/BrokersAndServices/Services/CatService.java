package org.itmo_olesia.presentationmicroservice.BrokersAndServices.Services;


import lombok.RequiredArgsConstructor;

import lombok.SneakyThrows;
import org.itmo_olesia.dto.DTO.CatDTO;
import org.itmo_olesia.presentationmicroservice.BrokersAndServices.BrokerInteraction.CatsBrokers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Component
@RequiredArgsConstructor
public class CatService {
    private final CatsBrokers catBroker;

    //@SneakyThrows
    public CatDTO getById(Long Id) throws ExecutionException, InterruptedException, TimeoutException {
        return catBroker.getById(Id).orElse(null);
    }

    public CatDTO create(CatDTO CatDTO) throws ExecutionException, InterruptedException, TimeoutException {
        return catBroker.create(CatDTO);
    }

    public CatDTO update(CatDTO catDTO) throws ExecutionException, InterruptedException, TimeoutException {
        return catBroker.update(catDTO);
    }

    public void delete(Long Id) throws ExecutionException, InterruptedException, TimeoutException {
        catBroker.delete(Id);
    }
}
