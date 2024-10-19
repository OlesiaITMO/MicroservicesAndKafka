package org.itmo_olesia.presentationmicroservice.BrokersAndServices.Services;


import lombok.RequiredArgsConstructor;
import org.itmo_olesia.dto.DTO.OwnerDTO;
import org.itmo_olesia.presentationmicroservice.BrokersAndServices.BrokerInteraction.OwnersBrokers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Component
@RequiredArgsConstructor
public class OwnerService {
    private final OwnersBrokers ownersBrokers;


    public OwnerDTO getById(Long Id) throws ExecutionException, InterruptedException, TimeoutException {
        return ownersBrokers.getById(Id).orElse(null);
    }

    public OwnerDTO create(OwnerDTO ownerData) throws ExecutionException, InterruptedException, TimeoutException {
        return ownersBrokers.create(ownerData);
    }



    public void delete(Long Id) throws ExecutionException, InterruptedException, TimeoutException {
        ownersBrokers.delete(Id);
    }
}
